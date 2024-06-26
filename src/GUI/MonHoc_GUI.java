package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

import DAO.*;
import DTO.*;


public class MonHoc_GUI extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = -1554680235689968471L;

	private JButton btnThem;
	private JButton btnLuu;
	private JButton btnXoa;
	private JButton btnKetThuc;

	private DefaultTableModel dataModel;
	private JTable table;

	private JScrollPane scroll;

	private JComboBox<String> cboBoMonChuQuan;
	private JTextField txtMaMonHoc;
	private JTextField txtTenMonHoc;
	private JTextField txtSoTiet;

	private JButton btnLoc;

	public MonHoc_GUI() throws SQLException {
		setTitle("Bài thi cuối kỳ - TL HSK Java - HK2 (2020-2021)");
		setSize(630, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		Box b, b1, b2, b3, b4, b5, b6, b7, b8;
		add(b = Box.createVerticalBox());
		b.add(Box.createVerticalStrut(10));
		b.add(b1 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b.add(b2 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b.add(b3 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b.add(b4 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b.add(b5 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b.add(b8 = Box.createHorizontalBox());

		b.add(Box.createVerticalStrut(10));
		b.add(b6 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b.add(b7 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		b.add(b8 = Box.createHorizontalBox());
		b.add(Box.createVerticalStrut(10));
		JLabel lblTieuDe, lblMaMH, lblTenMon, lblBMCQ, lblCLB;
		b1.add(lblTieuDe = new JLabel("-THÔNG TIN MÔN HỌC- ", JLabel.CENTER));
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblTieuDe.setForeground(Color.BLUE);

		b2.add(lblMaMH = new JLabel("  Mã môn học:  ", JLabel.RIGHT));
		b2.add(txtMaMonHoc = new JTextField());
		b2.add(Box.createHorizontalStrut(50));
		b3.add(lblTenMon = new JLabel("Tên môn học:  ", JLabel.RIGHT));
		b3.add(txtTenMonHoc = new JTextField());
		b3.add(Box.createHorizontalStrut(50));
		b4.add(lblBMCQ = new JLabel("Bộ Môn Chủ Quản:  ", JLabel.RIGHT));
		b4.add(cboBoMonChuQuan = new JComboBox<String>());
		b4.add(Box.createHorizontalStrut(300));

		b5.add(lblCLB = new JLabel("Số Tiết:  ", JLabel.RIGHT));
		b5.add(txtSoTiet = new JTextField());
		b5.add(Box.createHorizontalStrut(50));

		DefaultComboBoxModel<String> dataModelLop = new DefaultComboBoxModel<String>();
		cboBoMonChuQuan.setModel(dataModelLop);

		lblMaMH.setPreferredSize(lblBMCQ.getPreferredSize());
		lblTenMon.setPreferredSize(lblBMCQ.getPreferredSize());
		lblCLB.setPreferredSize(lblBMCQ.getPreferredSize());

		b6.add(Box.createHorizontalStrut(40));
		b6.add(btnThem = new JButton("Thêm Mới "));
		b6.add(Box.createHorizontalStrut(10));
		b6.add(btnLuu = new JButton("Lưu "));
		b6.add(Box.createHorizontalStrut(10));
		b6.add(btnXoa = new JButton("Xóa"));
		b6.add(Box.createHorizontalStrut(50));
		b6.add(btnKetThuc = new JButton("Kết Thúc"));

		String[] tieuDe = { "Mã Môn Học", "Tên Môn Học", "Số Tiết", "Mã Số Bộ Môn Chủ Quản" };
		b7.add(scroll = new JScrollPane(table = new JTable(dataModel = new DefaultTableModel(tieuDe, 0))));
		scroll.setBorder(BorderFactory.createTitledBorder("Danh sách Môn Học:"));

		JLabel lblName;
		b8.add(lblName = new JLabel("Họ tên sv: Phạm Đăng Đan massv: 19529651"));
		lblName.setFont(new Font("Times", Font.ITALIC, 12));
		lblName.setForeground(Color.RED);
		b8.add(Box.createHorizontalStrut(50));
		b8.add(btnLoc = new JButton("   Lọc danh sách Môn Học theo Bộ Môn Chủ Quản "));
		btnLoc.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		btnLoc.setForeground(Color.BLUE);
		table.addMouseListener(this);
		btnKetThuc.addActionListener(this);
		btnLoc.addActionListener(this);
		btnLuu.addActionListener(this);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);

		loadCbo();
		loadDataTable(MonHoc_DAO.getInstance().getMonHocList());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (checkData()) { // Kiểm tra dữ liệu hợp lệ
	            MonHoc mh = getDataTable(); // Lấy dữ liệu từ các trường nhập liệu
	            boolean rs = false;
	            try {
	                rs = MonHoc_DAO.getInstance().insert(mh); // Thêm môn học mới vào hệ thống
	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	            if (rs) {
	                dataModel.addRow(new Object[] { mh.getMaMon(), mh.getTenMon(), mh.getSoTiet(), mh.getMaBoMonCQ().getMaBoMonCQ() });
	                dataModel.fireTableDataChanged();
	                JOptionPane.showMessageDialog(this, "Thêm môn học thành công");
	                
	                txtMaMonHoc.setText("");
	    			txtTenMonHoc.setText("");
	    			cboBoMonChuQuan.setSelectedIndex(0);
	    			txtSoTiet.setText("");
	            } else {
	                JOptionPane.showMessageDialog(this, "Thêm môn học thất bại");
	            }
	        }
			
		} else if (o.equals(btnKetThuc)) {
			int chon = JOptionPane.showConfirmDialog(this, "Bạn muốn thoát chướng trình", "Thoát",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (chon == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		} else if (o.equals(btnLuu)) {
			if (checkData()) {
				MonHoc mh = getDataTable();
				boolean rs = false;
				try {
					rs = MonHoc_DAO.getInstance().insert(mh);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (rs) {
					dataModel.addRow(new Object[] { mh.getMaMon(), mh.getTenMon(),mh.getSoTiet(), mh.getMaBoMonCQ().toString()
							 });
					dataModel.fireTableDataChanged();
					JOptionPane.showMessageDialog(this, "Them mon hoc thanh cong");
				} else {
					JOptionPane.showMessageDialog(this, "Them mon hoc that bai");
				}
			}
		} else if (o.equals(btnXoa)) {
			if (checkData()) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(null, "Vui lòng chọn Môn Học cần xóa trên bảng.", "Thông báo", JOptionPane.WARNING_MESSAGE);
		        } else {
		            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa Môn Học này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		            if (confirm == JOptionPane.YES_OPTION) {
		                String maMonHoc = (String) table.getValueAt(selectedRow, 0);
		                MonHoc mh = new MonHoc(maMonHoc, "", null, 0);
		                boolean result = false;
						try {
							result = MonHoc_DAO.getInstance().delete(mh.getMaMon());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
		                if (result) {
		                    JOptionPane.showMessageDialog(null, "Xóa Môn Học thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		                    dataModel.removeRow(selectedRow);
		                } else {
		                    JOptionPane.showMessageDialog(null, "Xóa Môn Học thất bại.", "Thông báo", JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        }
		    }
			
		} else if (o.equals(btnLoc)) {
			String maMonHoc = cboBoMonChuQuan.getSelectedItem().toString();
			ArrayList<MonHoc> dataList = null;
			try {
				dataList = MonHoc_DAO.getInstance().searchByMaMonHoc(maMonHoc);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dataModel.getDataVector().removeAllElements();
			dataModel.fireTableDataChanged();
			loadDataTable(dataList);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		txtMaMonHoc.setText(dataModel.getValueAt(row, 0).toString());
		txtTenMonHoc.setText(dataModel.getValueAt(row, 1).toString());
		txtSoTiet.setText(dataModel.getValueAt(row, 2).toString());
		cboBoMonChuQuan.setSelectedItem(dataModel.getValueAt(row, 3).toString());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	private void loadCbo() throws SQLException {
		ArrayList<BoMonChuQuan> dataList = BoMonChuQuan_DAO.getInstance().getBoMonChuQuanList();
		for (BoMonChuQuan item : dataList) {
			cboBoMonChuQuan.addItem(item.getMaBoMonCQ());
		}
	}

	private void loadDataTable(ArrayList<MonHoc> dataList) {
		for (MonHoc item : dataList) {
			dataModel.addRow(new Object[] { item.getMaMon(), item.getTenMon(),item.getSoTiet(), item.getMaBoMonCQ().getMaBoMonCQ() });
			dataModel.fireTableDataChanged();
		}
	}

	private MonHoc getDataTable() {
		String maMonHoc = txtMaMonHoc.getText().trim();
		String tenMonHoc = txtTenMonHoc.getText().trim();
		int soTiet = Integer.parseInt(txtSoTiet.getText().trim());
		String maBoMon = cboBoMonChuQuan.getSelectedItem().toString();
		BoMonChuQuan boMon = new BoMonChuQuan(maBoMon, "");
	    System.out.println("BoMonChuQuan object: " + boMon); // Print the BoMonChuQuan object

		return new MonHoc(maMonHoc, tenMonHoc, boMon, soTiet);
	}

	private boolean checkData() {
		String maMon = txtMaMonHoc.getText().trim();
		String tenMon = txtTenMonHoc.getText().trim();
		String soTietStr = txtSoTiet.getText().trim();
		if (!(maMon.length() > 0 && maMon.matches("\\d{7}"))) {
			JOptionPane.showMessageDialog(this, "Mã số môn học là một dãy gồm 7 chử số");
			txtMaMonHoc.requestFocus();
			return false;
		}
		if (!(tenMon.length() > 0 && maMon.matches("[\\w\\s]+"))) {
			JOptionPane.showMessageDialog(this,
					"Tên môn học có thể gồm nhiều từ ngăn cách bằng khoảng trắng, có thể chứa ký số nhưng không chứa các ký tự đặc biệt");
			txtTenMonHoc.requestFocus();
			return false;
		}
		if (soTietStr.length() > 0) {
			try {
				int soTiet = Integer.parseInt(soTietStr);
				if (soTiet < 15 || soTiet > 90) {
					JOptionPane.showMessageDialog(this, "Số tiết của 1 môn học phải từ 15 đến 90 tiết");
					txtSoTiet.requestFocus();
					return false;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Số tiết không được rỗng");
				txtSoTiet.requestFocus();

				return false;
			}
		}
		return true;
	}
}
