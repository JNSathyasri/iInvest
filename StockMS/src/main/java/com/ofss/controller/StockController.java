package com.ofss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ofss.model.Stock;
import com.ofss.service.StockService;

@RestController
public class StockController {
	@Autowired
	StockService ss;

	@GetMapping("/stocks")
	public ResponseEntity<List<Stock>> fetchAllStocks() {
		return ss.getAllStocks();
	}

	@GetMapping("/stocks/id/{id}")
	public Stock fetchByID(@PathVariable("id") String id) {
		return ss.getStockByID(id);
	}
	
	@GetMapping("/stocks/name/{name}")
	public Stock fetchByName(@PathVariable("name") String stock_name) {
		return ss.getStockByName(stock_name);
	}
}
