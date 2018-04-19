package by.reghor.medlinetask.model;

import java.time.LocalDate;
import java.util.Objects;

public class Part {
    private String name;
    private String number;
    private String vendor;
    private Integer quantity;
    private LocalDate shipped;
    private LocalDate received;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public void setShipped(LocalDate shipped) {
        this.shipped = shipped;
    }

    public LocalDate getReceived() {
        return received;
    }

    public void setReceived(LocalDate received) {
        this.received = received;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(name, part.name) &&
                Objects.equals(number, part.number) &&
                Objects.equals(vendor, part.vendor) &&
                Objects.equals(quantity, part.quantity) &&
                Objects.equals(shipped, part.shipped) &&
                Objects.equals(received, part.received);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, number, vendor, quantity, shipped, received);
    }

    @Override
    public String toString() {
        return "Part{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", vendor='" + vendor + '\'' +
                ", quantity=" + quantity +
                ", shipped=" + shipped +
                ", received=" + received +
                '}';
    }
}
