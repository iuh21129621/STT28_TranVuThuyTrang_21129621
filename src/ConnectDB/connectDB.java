package ConnectDB;

import java.sql.*;

public class connectDB {
    private static connectDB instance;
    private static Connection con = null;

    public static connectDB getInstance() {
        if (instance == null)
            instance = new connectDB();
        return instance;
    }

    public static void connect() throws SQLException {
        String serverName = "localhost";
        String databaseName = "QLMonHoc";
        String userName = "sa";
        String password = "sapassword";
        String url = "jdbc:sqlserver://" + serverName + ":1433;databaseName=" + databaseName + ";encrypt=true;trustServerCertificate=true";
        
        con = DriverManager.getConnection(url, userName, password);
    }

    public void disconnect() {
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
	public static Connection getConnection() throws SQLException {
		if (con == null) {
			connect();
		}
		return con;
	}
}
