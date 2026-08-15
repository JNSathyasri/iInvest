package com.ofss.model;

public class StockQuantity 
{
 String stock_id;
 int quantity;
public String getStock_id() {
	return stock_id;
}
public void setStock_id(String stock_id) {
	this.stock_id = stock_id;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
@Override
public String toString() {
	return "StockQuantity [stock_id=" + stock_id + ", quantity=" + quantity + "]";
}
public StockQuantity(String stock_id, int quantity) {
	super();
	this.stock_id = stock_id;
	this.quantity = quantity;
}
public StockQuantity() {
	super();
	// TODO Auto-generated constructor stub
}

}
