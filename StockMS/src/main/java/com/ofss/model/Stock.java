package com.ofss.model;

public class Stock {
	String stock_id;
	String stock_name;
	String industry;
	String symbol;
	String series;
	double stock_price; //IT IS COST PRICE
	double selling_price; 

	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Stock(String stock_id, String stock_name, String industry, String symbol, String series,
			double stock_price, double selling_price) {
		super();
		this.stock_id = stock_id;
		this.stock_name = stock_name;
		this.industry = industry;
		this.symbol = symbol;
		this.series = series;
		this.stock_price = stock_price;
		this.selling_price=selling_price;
	}

	public String getStock_id() {
		return stock_id;
	}

	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public double getStock_price() {
		return stock_price;
	}

	public void setStock_price(double stock_price) {
		this.stock_price = stock_price;
	}

	public double getSelling_price() {
		return selling_price;
	}

	public void setSelling_price(double selling_price) {
		this.selling_price = selling_price;
	}

	@Override
	public String toString() {
		return "Stock [stock_id=" + stock_id + ", stock_name=" + stock_name + ", industry=" + industry + ", symbol="
				+ symbol + ", series=" + series + ", stock_price=" + stock_price + ", selling_price=" + selling_price
				+ "]";
	}

}