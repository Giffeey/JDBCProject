package book.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection createConnection(String url, String username, String psw) 
            throws ClassNotFoundException, SQLException {
        Connection cn = DriverManager.getConnection(url, username, psw);
        return cn;
    }

    public static void closeConnection(Connection cn)throws SQLException {
        cn.close();
    }
}
