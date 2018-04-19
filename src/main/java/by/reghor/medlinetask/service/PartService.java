package by.reghor.medlinetask.service;

import by.reghor.medlinetask.dao.PartDao;
import by.reghor.medlinetask.model.*;
import by.reghor.medlinetask.util.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

public class PartService {
    private static final PartService instance = new PartService();
    private static final String SEARCH_CRITERIA_ATTR = "search_criteria";
    private static final String FILTER_SUBMIT_PARAM = "filter_submit";
    private static final String PART_NAME_PARAM = "part_name";
    private static final String PART_NUMBER_PARAM = "part_number";
    private static final String VENDOR_PARAM = "vendor";
    private static final String QUANTITY_PARAM = "quantity";
    private static final String SHIPPED_BEFORE_PARAM = "shipped_before";
    private static final String SHIPPED_AFTER_PARAM = "shipped_after";
    private static final String RECEIVED_BEFORE_PARAM = "received_before";
    private static final String RECEIVED_AFTER_PARAM = "received_after";

    private static final String SORT_CRITERIA_ATTR = "sort_criteria";
    private static final String SORT_FIELD_PARAM = "sort_field";
    private static final PartTableColumn DEFAULT_SORT_FIELD = PartTableColumn.part_name;
    private static final SortDirection DEFAULT_SORT_DIRECTION = SortDirection.ASC;

    private static final String HTML_TABLE_CELL_PLACEHOLDER = "<td>%s</td>";

    public static PartService getInstance() {
        return instance;
    }

    public String getParts(HttpServletRequest request) {
        SearchCriteria searchCriteria = buildSearchCriteria(request);
        SortCriteria sortCriteria = buildSortCriteria(request);
        List<Part> parts = PartDao.getInstance().getParts(sortCriteria, searchCriteria);
        String partsTable = convertPartsToHtmlTable(parts);
        return partsTable;
    }

    private SearchCriteria buildSearchCriteria(HttpServletRequest request) {
        if (request.getParameter(FILTER_SUBMIT_PARAM) == null) {
            SearchCriteria searchCriteria
                    = (SearchCriteria) request.getSession().getAttribute(SEARCH_CRITERIA_ATTR);
            if (searchCriteria == null) {
                searchCriteria = new SearchCriteria();
            }
            return searchCriteria;
        } else {
            SearchCriteria searchCriteria = new SearchCriteria();
            String partName = request.getParameter(PART_NAME_PARAM);
            searchCriteria.setPartName(partName);
            String partNumber = request.getParameter(PART_NUMBER_PARAM);
            searchCriteria.setPartNumber(partNumber);
            String vendor = request.getParameter(VENDOR_PARAM);
            searchCriteria.setVendor(vendor);
            String quantityString = request.getParameter(QUANTITY_PARAM);
            if (NumberUtils.isCreatable(quantityString)) {
                searchCriteria.setQuantity(Integer.parseInt(quantityString));
            }
            DateRange shippedRange = new DateRange();
            searchCriteria.setShippedRange(shippedRange);
            LocalDate shippedBefore = parseDate(request.getParameter(SHIPPED_BEFORE_PARAM));
            LocalDate shippedAfter = parseDate(request.getParameter(SHIPPED_AFTER_PARAM));
            shippedRange.setDateBefore(shippedBefore);
            shippedRange.setDateAfter(shippedAfter);

            DateRange receivedRange = new DateRange();
            searchCriteria.setReceivedRange(receivedRange);
            LocalDate receivedBefore = parseDate(request.getParameter(RECEIVED_BEFORE_PARAM));
            LocalDate recievedAfter = parseDate(request.getParameter(RECEIVED_AFTER_PARAM));
            receivedRange.setDateBefore(receivedBefore);
            receivedRange.setDateAfter(recievedAfter);

            request.getSession().setAttribute(SEARCH_CRITERIA_ATTR, searchCriteria);
            return searchCriteria;
        }
    }

    private SortCriteria buildSortCriteria(HttpServletRequest request) {
        SortCriteria existingSortCriteria = (SortCriteria) request.getSession().getAttribute(SORT_CRITERIA_ATTR);
        String newSortFieldAttr = request.getParameter(SORT_FIELD_PARAM);
        PartTableColumn newSortField = null;
        if (newSortFieldAttr != null) {
            newSortField = PartTableColumn.valueOf(newSortFieldAttr);
        }

        if (newSortField == null && existingSortCriteria != null) {
            return existingSortCriteria;
        }


        SortCriteria sortCriteria;
        if (existingSortCriteria != null) {
            sortCriteria = existingSortCriteria;
        } else {
            sortCriteria = new SortCriteria();
        }

        if (newSortField != null) {
            if (sortCriteria.getSortField() != null && newSortField.equals(sortCriteria.getSortField())) {
                if (SortDirection.ASC.equals(sortCriteria.getSortDirection())) {
                    sortCriteria.setSortDirection(SortDirection.DESC);
                } else {
                    sortCriteria.setSortDirection(SortDirection.ASC);
                }
            } else {
                sortCriteria.setSortDirection(DEFAULT_SORT_DIRECTION);
            }
            sortCriteria.setSortField(newSortField);
        } else {
            sortCriteria.setSortField(DEFAULT_SORT_FIELD);
            sortCriteria.setSortDirection(DEFAULT_SORT_DIRECTION);
        }
        request.getSession().setAttribute(SORT_CRITERIA_ATTR, sortCriteria);
        return sortCriteria;
    }

    private LocalDate parseDate(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        } else {
            return DateConverter.parse(dateString);
        }
    }

    private String convertPartsToHtmlTable(List<Part> parts) {
        StringBuilder tableRowsBuilder = new StringBuilder();
        for (Part part : parts) {
            tableRowsBuilder
                    .append("<tr>")
                    .append(String.format(HTML_TABLE_CELL_PLACEHOLDER, part.getNumber()))
                    .append(String.format(HTML_TABLE_CELL_PLACEHOLDER, part.getName()))
                    .append(String.format(HTML_TABLE_CELL_PLACEHOLDER, part.getVendor()))
                    .append(String.format(HTML_TABLE_CELL_PLACEHOLDER, part.getQuantity()))
                    .append(String.format(HTML_TABLE_CELL_PLACEHOLDER, DateConverter.format(part.getShipped())))
                    .append(String.format(HTML_TABLE_CELL_PLACEHOLDER, DateConverter.format(part.getReceived())))
                    .append("</tr>")
                    .append("\n");
        }
        return tableRowsBuilder.toString();
    }
}
