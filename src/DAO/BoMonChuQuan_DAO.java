package DAO;

import java.sql.*;
import java.util.ArrayList;
import DTO.BoMonChuQuan;
import ConnectDB.connectDB; // Import package ConnectDB

public class BoMonChuQuan_DAO {
    Connection conn;
    private static BoMonChuQuan_DAO instance;
    
	public static BoMonChuQuan_DAO getInstance() throws SQLException {
		if (instance == null)
			instance = new BoMonChuQuan_DAO();
		return instance;
	}

    public BoMonChuQuan_DAO() throws SQLException {
        // Sử dụng connectDB để lấy kết nối
		conn = connectDB.getConnection();
    }
    
    public ArrayList<BoMonChuQuan> getBoMonChuQuanList() {
        ArrayList<BoMonChuQuan> boMonChuQuanList = new ArrayList<BoMonChuQuan>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BoMonChuQuan");

            while (rs.next()) {
                BoMonChuQuan boMonChuQuan = new BoMonChuQuan();
                boMonChuQuan.setMaBoMonCQ(rs.getString("maBoMonCQ"));
                boMonChuQuan.setTenBoMon(rs.getString("tenBoMon"));
                boMonChuQuanList.add(boMonChuQuan);
            }

            rs.close();
            stmt.close();
            // Không đóng kết nối ở đây nếu bạn muốn sử dụng lại kết nối
            // conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return boMonChuQuanList;
    }
}