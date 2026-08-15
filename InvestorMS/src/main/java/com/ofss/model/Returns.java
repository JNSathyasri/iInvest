package com.ofss.model;

public class Returns {
	int invid;
	int basket_id;
	double cbasketvalue;
	double sbasketvalue;
	public Returns() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Returns(int invid, int basket_id, double cbasketvalue, double sbasketvalue) {
		super();
		this.invid = invid;
		this.basket_id = basket_id;
		this.cbasketvalue = cbasketvalue;
		this.sbasketvalue = sbasketvalue;
	}
	public int getInvid() {
		return invid;
	}
	public void setInvid(int invid) {
		this.invid = invid;
	}
	public int getBasket_id() {
		return basket_id;
	}
	public void setBasket_id(int basket_id) {
		this.basket_id = basket_id;
	}
	public double getCbasketvalue() {
		return cbasketvalue;
	}
	public void setCbasketvalue(double cbasketvalue) {
		this.cbasketvalue = cbasketvalue;
	}
	public double getSbasketvalue() {
		return sbasketvalue;
	}
	public void setSbasketvalue(double sbasketvalue) {
		this.sbasketvalue = sbasketvalue;
	}
	@Override
	public String toString() {
		return "Returns [invid=" + invid + ", basket_id=" + basket_id + ", cbasketvalue=" + cbasketvalue
				+ ", sbasketvalue=" + sbasketvalue + "]";
	}
}
