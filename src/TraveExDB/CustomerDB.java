package TraveExDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class CustomerDB {

    public static List<Customer> getAll() {

        List<Customer> customers = new ArrayList<>();

        try {

            Connection conn = DBTools.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM customers");

            while (results.next())
            {

                Customer c = new Customer();
                c.setCustomerId(results.getInt("CustomerID"));
                c.setCustFirstName(results.getString("CustFirstName"));
                c.setCustLastName(results.getString("CustLastName"));
                c.setCustAddress(results.getString("CustAddress"));
                c.setCustCity(results.getString("CustCity"));
                c.setCustProv(results.getString("CustProv"));
                c.setCustPostal(results.getString("CustPostal"));
                c.setCustCountry(results.getString("CustCountry"));
                c.setCustHomePhone(results.getString("CustHomePhone"));
                c.setCustBusPhone(results.getString("CustBusPhone"));
                c.setCustEmail(results.getString("CustEmail"));
                c.setAgentId(results.getInt("AgentId"));

                customers.add(c);

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return customers;

    }

    public static boolean update(Customer oldCustomer, Customer newCustomer) throws SQLException, ClassNotFoundException {
        Connection conn = DBTools.getConnection();

        String sql = "UPDATE customers " +
                "SET CustFirstName=?, CustLastName=?, " +
                "CustAddress=?, CustCity=?, CustProv=?, CustPostal=?, CustCountry=?, " +
                "CustHomePhone=?, CustBusPhone=?, CustEmail=?, AgentId=? " +
                "WHERE CustomerId=? AND CustFirstName=? AND CustLastName=? " +
                "AND CustAddress=? AND CustCity=? AND CustProv=? AND CustPostal=? AND CustCountry=? AND " +
                "CustHomePhone=? AND CustBusPhone=? AND CustEmail=? AND " +
                "(AgentId=? OR travelexperts.customers.AgentId IS NULL)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        // Input Parameters
        stmt.setString(1, newCustomer.getCustFirstName());
        stmt.setString(2, newCustomer.getCustLastName());
        stmt.setString(3, newCustomer.getCustAddress());
        stmt.setString(4, newCustomer.getCustCity());
        stmt.setString(5, newCustomer.getCustProv());
        stmt.setString(6, newCustomer.getCustPostal());
        stmt.setString(7, newCustomer.getCustCountry());
        stmt.setString(8, newCustomer.getCustHomePhone());
        stmt.setString(9, newCustomer.getCustBusPhone());
        stmt.setString(10, newCustomer.getCustEmail());
        if (newCustomer.getAgentId() == null)
            stmt.setNull(11, Types.INTEGER);
        else
            stmt.setInt(11, newCustomer.getAgentId());
        //Check parameters
        stmt.setInt(12, oldCustomer.getCustomerId());
        stmt.setString(13, oldCustomer.getCustFirstName());
        stmt.setString(14, oldCustomer.getCustLastName());
        stmt.setString(15, oldCustomer.getCustAddress());
        stmt.setString(16, oldCustomer.getCustCity());
        stmt.setString(17, oldCustomer.getCustProv());
        stmt.setString(18, oldCustomer.getCustPostal());
        stmt.setString(19, oldCustomer.getCustCountry());
        stmt.setString(20, oldCustomer.getCustHomePhone());
        stmt.setString(21, oldCustomer.getCustBusPhone());
        stmt.setString(22, oldCustomer.getCustEmail());
        if (oldCustomer.getAgentId() == null)
            stmt.setInt(23, 0);
        else
            stmt.setInt(23, oldCustomer.getAgentId());

        return stmt.execute();
    }
}
