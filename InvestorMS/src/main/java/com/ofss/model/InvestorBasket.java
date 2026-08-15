package com.ofss.model;

public class InvestorBasket {
	int Invid;
	int Basketid;
	String basket_name;
    String strategy;
    int iaid;
    double BasketValue;
	public InvestorBasket() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvestorBasket(int invid, int basketid, String basket_name, String strategy, int iaid, double basketValue) {
		super();
		Invid = invid;
		Basketid = basketid;
		this.basket_name = basket_name;
		this.strategy = strategy;
		this.iaid = iaid;
		BasketValue = basketValue;
	}
	public int getInvid() {
		return Invid;
	}
	public void setInvid(int invid) {
		Invid = invid;
	}
	public int getBasketid() {
		return Basketid;
	}
	public void setBasketid(int basketid) {
		Basketid = basketid;
	}
	public String getBasket_name() {
		return basket_name;
	}
	public void setBasket_name(String basket_name) {
		this.basket_name = basket_name;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public int getIaid() {
		return iaid;
	}
	public void setIaid(int iaid) {
		this.iaid = iaid;
	}
	public double getBasketValue() {
		return BasketValue;
	}
	public void setBasketValue(double basketValue) {
		BasketValue = basketValue;
	}
	@Override
	public String toString() {
		return "InvestorBasket [Invid=" + Invid + ", Basketid=" + Basketid + ", basket_name=" + basket_name
				+ ", strategy=" + strategy + ", iaid=" + iaid + ", BasketValue=" + BasketValue + "]";
	}

}
