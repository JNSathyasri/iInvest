package com.ofss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ofss.model.InvestmentAdvisor;
import com.ofss.model.Basket;
import com.ofss.model.Stock;
import com.ofss.model.StockInput;
import com.ofss.service.IAService;

@RestController
public class IAController {

    @Autowired
    IAService iaService;

    // Get all Investment Advisors
    @GetMapping("/investmentAdvisors")
    public List<InvestmentAdvisor> getAllIAs() {
    	System.out.println("in the controller get all api");
        return iaService.getAllIAs();
    }
    
    // add investment advisor. api/investment-advisors
    @PostMapping("/api/investment-advisors")
    public ResponseEntity<String> addIA(@RequestBody InvestmentAdvisor IA) {
        return iaService.addIA(IA);
    }
    // delete investment advisor api/investment-advisors/" + id
    @DeleteMapping("/api/investment-advisors/{uname}")
    public ResponseEntity<String> removeIA(@PathVariable String uname) {
        return iaService.removeIA(uname);
    }

    // Get Investment Advisor by ID
    @GetMapping("/investmentAdvisors/id/{id}")
    public InvestmentAdvisor getIAById(@PathVariable("id") int id) {
        return iaService.getIAById(id);
    }

    // Get all stocks from StockMS
    @GetMapping("/investmentAdvisors/stocks")
    public ResponseEntity<List<Stock>> getAllStocks() {
        return iaService.getAllStocksFromStockMS();
    }

    // Get stock by name from StockMS
    @GetMapping("/investmentAdvisors/stocks/name/{name}")
    public ResponseEntity<Stock> getStockByName(@PathVariable("name") String name) {
        return iaService.getStockByNameFromStockMS(name);
    }

    // Get stock by ID from StockMS
    @GetMapping("/investmentAdvisors/stocks/id/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable("id") String id) {
        return iaService.getStockByIdFromStockMS(id);
    }

    // Create Basket - PUT method to update respective database tables
    @PutMapping("/investmentAdvisors/basket")
    public ResponseEntity<String> createBasket(@RequestBody Basket basket) {
        return iaService.createBasket(basket);
    }
    
    //Adding Stocks to the basket and quantities
    @PutMapping("/basket/{basketId}/addStock")
    public ResponseEntity<String> addStockToBasket(@RequestBody StockInput stockInput) {
    	return iaService.addStock(stockInput);
    }
}
