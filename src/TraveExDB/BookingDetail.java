package TraveExDB;

public class BookingDetail {
    private int bookingDetailId;
    private String description;

    public BookingDetail(int bookingDetailId, String description) {
        this.bookingDetailId = bookingDetailId;
        this.description = description;
    }

    public BookingDetail() {
    }

    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", bookingDetailId, description);
    }
}
