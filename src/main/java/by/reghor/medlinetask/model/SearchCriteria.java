package by.reghor.medlinetask.model;

import java.util.Objects;

public class SearchCriteria {
    private String partName;
    private String partNumber;
    private String vendor;
    private Integer quantity;
    private DateRange shippedRange;
    private DateRange receivedRange;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
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

    public DateRange getShippedRange() {
        return shippedRange;
    }

    public void setShippedRange(DateRange shippedRange) {
        this.shippedRange = shippedRange;
    }

    public DateRange getReceivedRange() {
        return receivedRange;
    }

    public void setReceivedRange(DateRange receivedRange) {
        this.receivedRange = receivedRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCriteria that = (SearchCriteria) o;
        return Objects.equals(partName, that.partName) &&
                Objects.equals(partNumber, that.partNumber) &&
                Objects.equals(vendor, that.vendor) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(shippedRange, that.shippedRange) &&
                Objects.equals(receivedRange, that.receivedRange);
    }

    @Override
    public int hashCode() {

        return Objects.hash(partName, partNumber, vendor, quantity, shippedRange, receivedRange);
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "partName='" + partName + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", vendor='" + vendor + '\'' +
                ", quantity=" + quantity +
                ", shippedRange=" + shippedRange +
                ", receivedRange=" + receivedRange +
                '}';
    }
}
