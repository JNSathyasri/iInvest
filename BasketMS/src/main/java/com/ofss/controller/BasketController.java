package com.ofss.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ofss.model.Basket;
import com.ofss.model.StockQuantity;
import com.ofss.service.BasketService;
@RestController
public class BasketController 
{
	@Autowired
	BasketService bs;
	
	@GetMapping("/baskets")
	public List<Basket> fetchAllStocks()
	{
		return bs.getAllBaskets();
	}
	@GetMapping("/baskets/id/{id}")
	public Basket fetchByID(@PathVariable("id") int id) {
		return bs.getBasketByID(id);
	}
	
	@GetMapping("/baskets/name/{name}")
	public Basket fetchByName(@PathVariable("name") String basket_name) {
		return bs.getBasketByName(basket_name);
	}
	@GetMapping("/baskets/value/id/{id}")
	public double basketValue(@PathVariable("id") int id)
	{
		return bs.getBasketValue(id);
	}
	 @GetMapping("/baskets/stockQuantities/{id}")
	    public List<StockQuantity> getStockQuantities(@PathVariable("id") int id) {
	        return bs.getStockQuantities(id);
	    }
	 @GetMapping("/baskets/svalue/id/{id}")
		public double SbasketValue(@PathVariable("id") int id)
		{
			return bs.getBasketSValue(id);
		}
}
