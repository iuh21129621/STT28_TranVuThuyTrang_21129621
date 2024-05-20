package DTO;

public class MonHoc{
	private String maMon;
	private String tenMon;
	private BoMonChuQuan maBoMonCQ;
	private int soTiet;
	public MonHoc(String maMon, String tenMon, BoMonChuQuan maBoMonCQ, int soTiet) {
		super();
		this.maMon = maMon;
		this.tenMon = tenMon;
		this.maBoMonCQ = maBoMonCQ;
		this.soTiet = soTiet;
	}
	public MonHoc() {
		// TODO Auto-generated constructor stub
	}
	public String getMaMon() {
		return maMon;
	}
	public void setMaMon(String maMon) {
		this.maMon = maMon;
	}
	public String getTenMon() {
		return tenMon;
	}
	public void setTenMon(String tenMon) {
		this.tenMon = tenMon;
	}
	public BoMonChuQuan getMaBoMonCQ() {
		return maBoMonCQ;
	}
	public void setMaBoMonCQ(BoMonChuQuan maBoMonCQ) {
		this.maBoMonCQ = maBoMonCQ;
	}
	public int getSoTiet() {
		return soTiet;
	}
	public void setSoTiet(int soTiet) {
		this.soTiet = soTiet;
	}
	

}
