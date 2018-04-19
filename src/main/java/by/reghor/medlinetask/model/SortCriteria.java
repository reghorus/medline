package by.reghor.medlinetask.model;

import java.util.Objects;

public class SortCriteria {
    private PartTableColumn sortField;
    private SortDirection sortDirection;

    public PartTableColumn getSortField() {
        return sortField;
    }

    public void setSortField(PartTableColumn sortField) {
        this.sortField = sortField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortCriteria that = (SortCriteria) o;
        return sortField == that.sortField &&
                sortDirection == that.sortDirection;
    }

    @Override
    public int hashCode() {

        return Objects.hash(sortField, sortDirection);
    }

    @Override
    public String toString() {
        return "SortCriteria{" +
                "sortField=" + sortField +
                ", sortDirection=" + sortDirection +
                '}';
    }
}
