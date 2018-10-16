package TraveExDB;

import javax.lang.model.type.ArrayType;
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
                if (results.wasNull()) c.setAgentId(null);
                customers.add(c);

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return customers;

    }

    public static int update(Customer oldCustomer, Customer newCustomer) throws SQLException, ClassNotFoundException {
        Connection conn = DBTools.getConnection();

        String sql = "UPDATE customers " +
                "SET CustFirstName=?, CustLastName=?, " +
                "CustAddress=?, CustCity=?, CustProv=?, CustPostal=?, CustCountry=?, " +
                "CustHomePhone=?, CustBusPhone=?, CustEmail=?, AgentId=? " +
                "WHERE CustomerId=? AND CustFirstName=? AND CustLastName=? " +
                "AND CustAddress=? AND CustCity=? AND CustProv=? AND CustPostal=? AND CustCountry=? AND " +
                "CustHomePhone=? AND CustBusPhone=? AND CustEmail=? AND " +
                "(AgentId=? OR AgentId IS NULL)";
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

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            // Print error
            SQLWarning warnings = stmt.getWarnings();
            if (warnings != null) {
                System.out.println(warnings);
                warnings.printStackTrace();
            } else {
                System.out.println("No warnings found");
            }
        }

        return rowsAffected;
    }

    public static boolean delete(Customer customer) {
        Connection conn;
        try {
             conn = DBTools.getConnection();
            String sql = "DELETE FROM customers WHERE CustomerId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, customer.getCustomerId());
            if (stmt.execute()) {
                conn.close();
                return true;
            }

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean insert(Customer customer) {
        try {
            Connection conn = DBTools.getConnection();
            String sql = "INSERT INTO customers(custFirstName, CustLastName, " +
                    "CustAddress, CustCity, CustProv, CustPostal, CustCountry, " +
                    "CustHomePhone, CustBusPhone,  CustEmail, AgentId) " +
                    "VALUES (?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Input parameters
            stmt.setString(1, customer.getCustFirstName());
            stmt.setString(2, customer.getCustLastName());

            stmt.setString(3, customer.getCustAddress());
            stmt.setString(4, customer.getCustCity());
            stmt.setString(5, customer.getCustProv());
            stmt.setString(6, customer.getCustPostal());
            stmt.setString(7, customer.getCustCountry());

            stmt.setString(8, customer.getCustHomePhone());
            stmt.setString(9, customer.getCustBusPhone());
            stmt.setString(10, customer.getCustEmail());
            if (customer.getAgentId() == null)
                stmt.setNull(11, Types.INTEGER);
            else
                stmt.setInt(11, customer.getAgentId());

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

    public static ArrayList<Agent> getAgents() {
        ArrayList<Agent> agents = new ArrayList<>();

        try {
            Connection conn = DBTools.getConnection();
            String sql = "SELECT AgentId, AgtFirstName, AgtLastName FROM Agents";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                int agentId = rs.getInt("AgentId");
                String fName = rs.getString("AgtFirstName");
                String lName = rs.getString("AgtLastName");
                Agent agt = new Agent(agentId, fName, lName);

                agents.add(agt);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return agents;
    }

    public static ArrayList<NamedPair> getProvinces(String country) {
        ArrayList<NamedPair> provinces = new ArrayList<>();

        try {
            Connection conn = DBTools.getConnection();
            String sql = "SELECT provCode, provName FROM provinces WHERE provCountry=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, country);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String code = resultSet.getString("provCode");
                String name = resultSet.getString("provName");

                provinces.add(new NamedPair(code, name));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return provinces;
    }
}
