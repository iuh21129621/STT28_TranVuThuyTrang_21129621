package DAO;

import java.sql.*;
import java.util.ArrayList;

import ConnectDB.connectDB;
import DTO.BoMonChuQuan;
import DTO.MonHoc;

public class MonHoc_DAO {
	Connection conn;
    private static MonHoc_DAO instance;

	public static MonHoc_DAO getInstance() throws SQLException {
		if (instance == null)
			instance = new MonHoc_DAO();
		return instance;
	}

    public MonHoc_DAO() throws SQLException {
        // Sử dụng connectDB để lấy kết nối
		conn = connectDB.getConnection();
    }   

	public ArrayList<MonHoc> getMonHocList() {
	    ArrayList<MonHoc> monHocList = new ArrayList<>();
	    try {
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT m.maMon, m.tenMon, m.maBoMonCQ, b.tenBoMon, m.soTiet FROM MonHoc m, BoMonChuQuan b WHERE m.maBoMonCQ = b.maBoMonCQ");
	
	        while (rs.next()) {
	            MonHoc monHoc = new MonHoc();
	            monHoc.setMaMon(rs.getString("maMon"));
	            monHoc.setTenMon(rs.getString("tenMon"));
	            BoMonChuQuan boMonChuQuan = new BoMonChuQuan (rs.getString("maBoMonCQ"));
                monHoc.setMaBoMonCQ(boMonChuQuan);
	            monHoc.setSoTiet(rs.getInt("soTiet"));
	            monHocList.add(monHoc);
	        }
	
	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return monHocList;
	}

	public boolean insert(MonHoc monHoc) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO MonHoc (maMon, tenMon, maBoMonCQ, soTiet) VALUES (?, ?, ?, ?)");
            stmt.setString(1, monHoc.getMaMon());
            stmt.setString(2, monHoc.getTenMon());
            stmt.setString(3, monHoc.getMaBoMonCQ().getMaBoMonCQ());
            stmt.setInt(4, monHoc.getSoTiet());
            int addedRows = stmt.executeUpdate();
            stmt.close();
            return addedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maMon) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM MonHoc WHERE maMon = ?");
            stmt.setString(1, maMon);
            int deletedRows = stmt.executeUpdate();
            stmt.close();
            return deletedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(MonHoc monHoc) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE MonHoc SET tenMonHoc = ? WHERE maMonHoc = ?");
            stmt.setString(1, monHoc.getTenMon());
            stmt.setString(2, monHoc.getMaMon());
            int updatedRows = stmt.executeUpdate();
            stmt.close();
            return updatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

		public ArrayList<MonHoc> searchByMaMonHoc(String maBoMonCQ) {
			ArrayList<MonHoc> monHocList = new ArrayList<>();
			try {
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MonHoc WHERE maBoMonCQ = ?");
				stmt.setString(1, maBoMonCQ);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					MonHoc monHoc = new MonHoc();
		            monHoc.setMaMon(rs.getString("maMon"));
		            monHoc.setTenMon(rs.getString("tenMon"));
		            BoMonChuQuan boMonChuQuan = new BoMonChuQuan(rs.getString("maBoMonCQ"));
		            monHoc.setMaBoMonCQ(boMonChuQuan);
		            monHoc.setSoTiet(rs.getInt("soTiet"));
					monHocList.add(monHoc);
				}

				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return monHocList;
		}
}


