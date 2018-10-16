package TraveExDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class ProductSupplierDB {
    public static ArrayList<ProductSupplier> getProductsSuppliers(TravelPackage travelPackage) {
        ArrayList<ProductSupplier> psList = new ArrayList<>();
        try {
            Connection conn = DBTools.getConnection();

            String sql = "SELECT p.ProductId, ProdName, s.SupplierId, SupName, ps.ProductSupplierId " +
                "FROM ((travelexperts.products p " +
                "INNER JOIN products_suppliers ps on p.ProductId = ps.ProductId) " +
                "INNER JOIN travelexperts.suppliers s on s.SupplierId = ps.SupplierId) " +
                "INNER JOIN travelexperts.packages_products_suppliers pps on pps.ProductSupplierId = ps.ProductSupplierId " +
                "WHERE PackageId=? " +
                "ORDER BY ps.ProductSupplierId";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, travelPackage.getPackageId());

            ResultSet rs = stmt.executeQuery();

            // Add query results to list
            while (rs.next()) {
                ProductSupplier ps = new ProductSupplier();
                ps.setProductId(rs.getInt(1));
                ps.setProdName(rs.getString(2));
                ps.setSupplierId(rs.getInt(3));
                ps.setSupName(rs.getString(4));
                ps.setProductSupplierId(rs.getInt(5));

                psList.add(ps);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return psList;
    }
}
