package com.ofss.model;

public class StockInput {
	private int basketId;
    private String stockId;
    private int quantity;
	public StockInput() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StockInput(int basketId, String stockId, int quantity) {
		super();
		this.basketId = basketId;
		this.stockId = stockId;
		this.quantity = quantity;
	}
	public int getBasketId() {
		return basketId;
	}
	public void setBasketId(int basketId) {
		this.basketId = basketId;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "StockInput [basketId=" + basketId + ", stockId=" + stockId + ", quantity=" + quantity + "]";
	}

}
