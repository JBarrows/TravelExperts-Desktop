package TraveExDB;

import java.util.Date;

public class Booking {
    private int bookingId;
    private String bookingNo;
    private String tripType;
    private Date bookingDate;
    private Integer packageId;
    private String pkgName;

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String toString() {
        String value;

        if (this.pkgName == null) {
            value = String.format("%d: %s %s [%s]", bookingId, tripType.trim(), bookingNo, bookingDate.toString());
        } else {
            value = String.format("%d: %s [%s]", bookingId, pkgName, bookingDate.toString());
        }

        return value;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }
}
