package TraveExDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class BookingDB {

    public static ArrayList<Booking> getBookings(Customer cust) {
        ArrayList<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.BookingId, b.BookingNo, b.BookingDate, p.PackageId, p.PkgName , t.TTName FROM (bookings b INNER JOIN triptypes t ON b.TripTypeId = t.TripTypeId) LEFT JOIN packages p ON p.PackageId=b.PackageId WHERE CustomerId=?";

        try {
            Connection conn = DBTools.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cust.getCustomerId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking bk = new Booking();
                bk.setBookingId(rs.getInt("BookingId"));
                bk.setBookingNo (rs.getString("BookingNo"));
                bk.setBookingDate(rs.getDate("BookingDate"));
                bk.setTripType(rs.getString("TTName"));
                bk.setPackageId(rs.getInt("PackageId"));
                if (rs.wasNull()) bk.setPackageId(null);
                bk.setPkgName(rs.getString("PkgName"));
                if (rs.wasNull())   bk.setPackageId(null);

                bookings.add(bk);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public static ArrayList<BookingDetail> getDetails(Booking booking) {
        ArrayList<BookingDetail> details = new ArrayList<>();
        boolean isPackage = booking.getPackageId() != null;

        if (isPackage) {
            TravelPackage travelPackage = PackageDB.getPackage(booking.getPackageId());
            ArrayList<ProductSupplier> productsSuppliers = ProductSupplierDB.getProductsSuppliers(travelPackage);
            for (ProductSupplier ps: productsSuppliers) {
                details.add(new PackageDetail(ps));
            }

            return details;
        }

        String sql ="SELECT BookingDetailId, Description FROM bookingdetails WHERE BookingId=?";
        try {
            Connection conn = DBTools.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, booking.getBookingId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookingDetail bd;
                bd = new BookingDetail(rs.getInt("BookingDetailId"), rs.getString("Description"));
                details.add(bd);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return details;
    }
}
