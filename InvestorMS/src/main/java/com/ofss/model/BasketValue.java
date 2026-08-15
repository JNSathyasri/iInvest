package com.ofss.model;

public class BasketValue {
	int basket_id;
    String basket_name;
    String strategy;
    int iaid;
    double BasketValue;
	public BasketValue() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BasketValue(int basket_id, String basket_name, String strategy, int iaid, double basketValue) {
		super();
		this.basket_id = basket_id;
		this.basket_name = basket_name;
		this.strategy = strategy;
		this.iaid = iaid;
		BasketValue = basketValue;
	}
	public int getBasket_id() {
		return basket_id;
	}
	public void setBasket_id(int basket_id) {
		this.basket_id = basket_id;
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
	public void setBasketValue(double basketvalue2) {
		BasketValue = basketvalue2;
	}
	@Override
	public String toString() {
		return "BasketValue [basket_id=" + basket_id + ", basket_name=" + basket_name + ", strategy=" + strategy
				+ ", iaid=" + iaid + ", BasketValue=" + BasketValue + "]";
	}
}
