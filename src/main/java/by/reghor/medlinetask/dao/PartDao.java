package by.reghor.medlinetask.dao;

import by.reghor.medlinetask.dao.connection.ConnectionPool;
import by.reghor.medlinetask.dao.connection.PooledConnection;
import by.reghor.medlinetask.exception.MedlineException;
import by.reghor.medlinetask.model.*;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartDao {
    private static final PartDao instance = new PartDao();
    private static final MessageFormat LIKE_QUERY_PART_SQL = new MessageFormat("{0} LIKE ?");
    private static final MessageFormat BETWEEN_QUERY_PART_SQL = new MessageFormat("{0} BETWEEN ? AND ?");
    private static final MessageFormat LESS_THAN_QUERY_PART_SQL = new MessageFormat("{0} < ?");
    private static final MessageFormat ORDER_BY_PART_SQL = new MessageFormat(" ORDER BY {0} {1}");
    private static final MessageFormat LIKE_WILDCARD_PLACEHOLDER = new MessageFormat("%{0}%");
    private static final String BASE_SELECT_PARTS_QUERY_SQL = "SELECT * FROM PART";

    private PartDao() {
    }

    public static PartDao getInstance() {
        return instance;
    }

    public List<Part> getParts(SortCriteria sortCriteria, SearchCriteria searchCriteria) {
        List<Part> parts = new ArrayList<>();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (PooledConnection connection = connectionPool.getConnection()) {
            String sqlQuery = BASE_SELECT_PARTS_QUERY_SQL;

            List<String> conditions = new ArrayList<>();
            List<Object> conditionValues = new ArrayList<>();
            if (searchCriteria != null) {
                if (StringUtils.isNotBlank(searchCriteria.getPartName())) {
                    conditions.add(LIKE_QUERY_PART_SQL.format(new Object[]{PartTableColumn.part_name}));
                    conditionValues.add(searchCriteria.getPartName());
                }
                if (StringUtils.isNotBlank(searchCriteria.getPartNumber())) {
                    conditions.add(LIKE_QUERY_PART_SQL.format(new Object[]{PartTableColumn.part_number}));
                    conditionValues.add(searchCriteria.getPartNumber());
                }
                if (StringUtils.isNotBlank(searchCriteria.getVendor())) {
                    conditions.add(LIKE_QUERY_PART_SQL.format(new Object[]{PartTableColumn.vendor}));
                    conditionValues.add(searchCriteria.getVendor());
                }
                if (searchCriteria.getQuantity() != null) {
                    conditions.add(LESS_THAN_QUERY_PART_SQL.format(new Object[]{PartTableColumn.quantity}));
                    conditionValues.add(searchCriteria.getQuantity());
                }
                if (isDateRangeApplyable(searchCriteria.getShippedRange())) {
                    conditions.add(BETWEEN_QUERY_PART_SQL.format(new Object[]{PartTableColumn.date_shipped}));
                    conditionValues.add(searchCriteria.getShippedRange());
                }
                if (isDateRangeApplyable(searchCriteria.getReceivedRange())) {
                    conditions.add(BETWEEN_QUERY_PART_SQL.format(new Object[]{PartTableColumn.date_received}));
                    conditionValues.add(searchCriteria.getReceivedRange());
                }
                if (!conditions.isEmpty()) {
                    sqlQuery = new StringBuilder(sqlQuery).append(" WHERE ").append(
                            conditions.stream().collect(Collectors.joining(" AND "))).toString();
                }
            }
            if (sortCriteria != null) {
                SortDirection sortDirection = sortCriteria.getSortDirection();
                PartTableColumn sortField = sortCriteria.getSortField();
                sqlQuery = new StringBuilder(sqlQuery)
                        .append(ORDER_BY_PART_SQL.format(new Object[]{sortField, sortDirection}))
                        .toString();
            }
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                if (!conditions.isEmpty()) {
                    int parameterIndex = 1;
                    for (Object currentValue : conditionValues) {
                        Class<?> valueClass = currentValue.getClass();
                        if (valueClass.equals(String.class)) {
                            statement.setString(parameterIndex, LIKE_WILDCARD_PLACEHOLDER.format(new Object[]{currentValue}));
                        } else if (valueClass.equals(Integer.class)) {
                            statement.setInt(parameterIndex, (Integer) currentValue);
                        } else if (valueClass.equals(DateRange.class)) {
                            DateRange dateRange = (DateRange) currentValue;
                            statement.setDate(parameterIndex, Date.valueOf(dateRange.getDateAfter()));
                            parameterIndex++;
                            statement.setDate(parameterIndex, Date.valueOf(dateRange.getDateBefore()));
                        } else {
                            throw new MedlineException("new classes to be defined");
                        }
                        parameterIndex++;
                    }
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Part part = new Part();
                        part.setName(resultSet.getString(PartTableColumn.part_name.name()));
                        part.setNumber(resultSet.getString(PartTableColumn.part_number.name()));
                        part.setQuantity(resultSet.getInt(PartTableColumn.quantity.name()));
                        part.setVendor(resultSet.getString(PartTableColumn.vendor.name()));
                        part.setReceived(resultSet.getDate(PartTableColumn.date_received.name()).toLocalDate());
                        part.setShipped(resultSet.getDate(PartTableColumn.date_shipped.name()).toLocalDate());
                        parts.add(part);
                    }
                }
            } catch (SQLException e) {
                throw new MedlineException(e);
            }

        }
        return parts;
    }

    private boolean isDateRangeApplyable(DateRange dateRange) {
        if (dateRange != null) {
            return dateRange.getDateAfter() != null && dateRange.getDateBefore() != null &&
                    dateRange.getDateAfter().isBefore(dateRange.getDateBefore());
        }
        return false;
    }
}
