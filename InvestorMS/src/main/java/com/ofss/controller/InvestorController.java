package com.ofss.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ofss.model.Basket;
import com.ofss.model.BasketValue;
import com.ofss.model.InvestorBasket;
import com.ofss.model.Stock;
import com.ofss.service.InvestorService;

@RestController
public class InvestorController {

    @Autowired
    InvestorService investorService;
    //by basket id
    @GetMapping("/investors/baskets/{id}")
    public BasketValue getAllBasketsbyid(@PathVariable("id") int id) {
        return investorService.fetchBasketsById(id);
    }
    //all baskets
    @GetMapping("/investors/baskets")
    public List<BasketValue> getAllBaskets() {
        return investorService.fetchAllBaskets();
    }
    
    //by investor id
    @GetMapping("/investor/{invid}/baskets")
    public ResponseEntity<List<InvestorBasket>> getBasketsByInvestorId(@PathVariable int invid) {
        List<InvestorBasket> investorBaskets = investorService.fetchBasketsByInvestorId(invid);
        return ResponseEntity.ok(investorBaskets);
    }
    
    //Recharge Account Balance
    @PutMapping("/{invid}/addbalance/{amount}")
    public ResponseEntity<String> addBalance(@PathVariable int invid,@PathVariable Double amount)
    {
        return investorService.recharge(invid, amount);
    }
    //Buy Basket by id
    @PutMapping("/buy/{invid}/{bid}")
    public ResponseEntity<String> buyBasketbyId(@PathVariable int invid,@PathVariable int bid)
    {
    	return investorService.buyId(invid,bid);
    }
    //Buy Basket by id
    @PutMapping("/buy/{invid}/name/{bname}")
    public ResponseEntity<String> buyBasketbyName(@PathVariable int invid,@PathVariable String bname)
    {
    	return investorService.buyName(invid,bname);
    }
    
    //Sell Basket by id
    @PutMapping("/sell/{invid}/{bid}")
    public ResponseEntity<String> sellBasketbyId(@PathVariable int invid,@PathVariable int bid)
    {
    	return investorService.SellId(invid,bid);
    }
  //Sell Basket by name
    @PutMapping("/sell/{invid}/name/{bname}")
    public ResponseEntity<String> sellBasketbyName(@PathVariable int invid,@PathVariable String bname)
    {
    	return investorService.SellName(invid,bname);
    }
    
    //Get Returns or Profits
    @GetMapping("/investors/profit/{invid}")
    public ResponseEntity<String> getReturns(@PathVariable int invid) {
    	return investorService.getReturn(invid);
    }
    
    //Get Portfolio, total value
    @GetMapping("/investors/portfolio/{invid}")
    public ResponseEntity<String> getPortfolio(@PathVariable int invid) {
    	return investorService.getPortfolio(invid);
    }
}