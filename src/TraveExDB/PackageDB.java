package TraveExDB;

import sample.Traveller;

import java.sql.*;
import java.util.ArrayList;

public class PackageDB {

    public static ArrayList<TravelPackage> getAll() {

        ArrayList<TravelPackage> all = new ArrayList<>();

        try {
            Connection conn = DBTools.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM packages");

            while (results.next()) {
                TravelPackage pkg = new TravelPackage();

                pkg.setPackageId(results.getInt("PackageId"));
                pkg.setPkgName(results.getString("PkgName"));
                pkg.setPkgStartDate(results.getDate("PkgStartDate"));
                pkg.setPkgEndDate(results.getDate("PkgEndDate"));
                pkg.setPkgDesc(results.getString("PkgDesc"));
                pkg.setPkgBasePrice(results.getFloat("PkgBasePrice"));
                Float commish = results.getFloat("PkgAgencyCommission");
                if (results.wasNull())  commish = null;
                pkg.setPkgAgencyCommission(commish);

                all.add(pkg);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return all;
    }

    public static int update(TravelPackage oldPackage, TravelPackage newPackage) throws SQLException, ClassNotFoundException {
        Connection conn = DBTools.getConnection();

        String sql = "UPDATE packages " +
                "SET PkgName=?, " +
                "PkgStartDate=?, " +
                "PkgEndDate=?," +
                "PkgDesc=?, " +
                "PkgBasePrice=?," +
                "PkgAgencyCommission=? " +
                "WHERE PackageId=? AND " +
                "PkgName=? AND " +
                "PkgStartDate " + (oldPackage.getPkgStartDate() == null? "IS":"=") + " ? AND " +
                "PkgEndDate " + (oldPackage.getPkgEndDate() == null? "IS":"=") + " ? AND " +
                "PkgDesc" + (oldPackage.getPkgDesc() == null?" IS ":"=") + "? AND " +
                "PkgBasePrice=? AND " +
                "(PkgAgencyCommission=? OR PkgAgencyCommission IS NULL)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        //Input parameters
        stmt.setString  (1, newPackage.getPkgName());
        stmt.setDate    (2, newPackage.getPkgStartDate());
        stmt.setDate    (3, newPackage.getPkgEndDate());
        stmt.setString  (4, newPackage.getPkgDesc());
        stmt.setFloat   (5, newPackage.getPkgBasePrice());
        if (newPackage.getPkgAgencyCommission() == null)
            stmt.setNull(6, Types.DECIMAL);
        else
            stmt.setFloat   (6, newPackage.getPkgAgencyCommission());

        //Check parameters
        stmt.setInt     (7, oldPackage.getPackageId());
        stmt.setString  (8, oldPackage.getPkgName());
        stmt.setDate    (9, oldPackage.getPkgStartDate());
        stmt.setDate    (10, oldPackage.getPkgEndDate());
        stmt.setString  (11, oldPackage.getPkgDesc());
        stmt.setFloat   (12, oldPackage.getPkgBasePrice());
        if (oldPackage.getPkgAgencyCommission() == null)
            stmt.setNull(13, Types.DECIMAL);
        else
            stmt.setFloat   (13, oldPackage.getPkgAgencyCommission());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected;
    }

    public static ArrayList<Traveller> getTravellers(TravelPackage travelPackage) {
        ArrayList<Traveller> travellers = new ArrayList<>();
        String sql = "SELECT CustFirstName, CustLastName, TravelerCount " +
                "FROM travelexperts.customers c INNER JOIN travelexperts.bookings b ON c.CustomerId = b.CustomerId " +
                "WHERE PackageId=?";

        try {
            Connection conn = DBTools.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, travelPackage.getPackageId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Traveller traveller = new Traveller();
                String fname = rs.getString(1);
                String lname = rs.getString(2);
                int count = rs.getInt(3);
                traveller.set(fname, lname, count);
                travellers.add(traveller);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return travellers;
    }

    public static TravelPackage getPackage(int packageId) {
        TravelPackage pkg = null;

        try {
            Connection conn = DBTools.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM packages WHERE PackageId=?");
            stmt.setInt(1, packageId);

            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                pkg = new TravelPackage();
                pkg.setPackageId(results.getInt("PackageId"));
                pkg.setPkgName(results.getString("PkgName"));
                pkg.setPkgStartDate(results.getDate("PkgStartDate"));
                pkg.setPkgEndDate(results.getDate("PkgEndDate"));
                pkg.setPkgDesc(results.getString("PkgDesc"));
                pkg.setPkgBasePrice(results.getFloat("PkgBasePrice"));
                Float commish = results.getFloat("PkgAgencyCommission");
                if (results.wasNull())  commish = null;
                pkg.setPkgAgencyCommission(commish);

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pkg;
    }

    public static boolean insert(TravelPackage newPackage) {

        try {
            Connection conn = DBTools.getConnection();
            String sql = "INSERT INTO packages(PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice, PkgAgencyCommission) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Input parameters
            stmt.setString(1, newPackage.getPkgName());
            stmt.setDate(2, newPackage.getPkgStartDate());
            stmt.setDate(3, newPackage.getPkgEndDate());
            stmt.setString(4, newPackage.getPkgDesc());
            stmt.setFloat(5, newPackage.getPkgBasePrice());
            if (newPackage.getPkgAgencyCommission() == null)
                stmt.setNull(6, Types.DECIMAL);
            else
                stmt.setFloat(6, newPackage.getPkgAgencyCommission());

            boolean result = stmt.execute();

            if (!result) {
                if (stmt.getWarnings()!= null)
                    throw new SQLException(stmt.getWarnings().toString());

            }

            return true;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void delete(TravelPackage pkg) {
        try {
            Connection conn = DBTools.getConnection();
            String sql = "DELETE FROM packages WHERE PackageId=? AND PkgName=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pkg.getPackageId());
            stmt.setString(2, pkg.getPkgName());

            stmt.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
