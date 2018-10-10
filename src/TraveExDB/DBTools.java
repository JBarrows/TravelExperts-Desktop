package TraveExDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public abstract class DBTools {

    private static final SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:MM:mm");

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/travelexperts", "root", "");
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
}
