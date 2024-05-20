package DTO;

public class BoMonChuQuan {
	private String maBoMonCQ;
	private String tenBoMon;
	
	public BoMonChuQuan(String maBoMonCQ, String tenBoMon) {
		super();
		this.maBoMonCQ = maBoMonCQ;
		this.tenBoMon = tenBoMon;
	}
	
	public BoMonChuQuan(String maBoMonCQ) {
		super();
		this.maBoMonCQ = maBoMonCQ;
	}

	public BoMonChuQuan() {
		// TODO Auto-generated constructor stub
	}

	public String getMaBoMonCQ() {
		return maBoMonCQ;
	}
	public void setMaBoMonCQ(String maBoMonCQ) {
		this.maBoMonCQ = maBoMonCQ;
	}
	public String getTenBoMon() {
		return tenBoMon;
	}
	public void setTenBoMon(String tenBoMon) {
		this.tenBoMon = tenBoMon;
	}
}
