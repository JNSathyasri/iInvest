package com.ofss.model;

public class Basket 
{
int basket_id;
String basket_name;
String strategy;
int IAid;
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
public int getIAid() {
	return IAid;
}
public void setIAid(int iAid) {
	IAid = iAid;
}
@Override
public String toString() {
	return "BasketService [basket_id=" + basket_id + ", basket_name=" + basket_name + ", strategy=" + strategy
			+ ", IAid=" + IAid + "]";
}
public Basket() {
	super();
	// TODO Auto-generated constructor stub
}
public Basket(int basket_id, String basket_name, String strategy, int iAid) {
	super();
	this.basket_id = basket_id;
	this.basket_name = basket_name;
	this.strategy = strategy;
	IAid = iAid;
}
}
