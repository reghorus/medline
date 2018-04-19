package by.reghor.medlinetask.model;

import java.time.LocalDate;
import java.util.Objects;

public class DateRange {

    private LocalDate dateAfter;
    private LocalDate dateBefore;

    public LocalDate getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(LocalDate dateAfter) {
        this.dateAfter = dateAfter;
    }

    public LocalDate getDateBefore() {

        return dateBefore;
    }

    public void setDateBefore(LocalDate dateBefore) {
        this.dateBefore = dateBefore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateRange dateRange = (DateRange) o;
        return Objects.equals(dateAfter, dateRange.dateAfter) &&
                Objects.equals(dateBefore, dateRange.dateBefore);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateAfter, dateBefore);
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "dateAfter=" + dateAfter +
                ", dateBefore=" + dateBefore +
                '}';
    }
}
