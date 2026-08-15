package com.ofss.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ofss.model.Basket;
import com.ofss.model.BasketValue;
import com.ofss.model.Investor;
import com.ofss.model.InvestorBasket;
import com.ofss.model.Returns;

@Service
public class InvestorService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // Fetch all baskets and their value
    public BasketValue fetchBasketsById(int basketId) {
        BasketValue bv = new BasketValue();
        
        // Fetch basket details
        Basket basket = getBasketAsObject(basketId);
        if (basket != null) {
            bv.setBasket_id(basketId);
            bv.setBasket_name(basket.getBasket_name());
            bv.setStrategy(basket.getStrategy());
            bv.setIaid(basket.getIAid());
            
            // Fetch basket value
            double basketValue = getBasketValue(basketId);
            bv.setBasketValue(basketValue);
        } else {
            throw new RuntimeException("Basket not found for ID: " + basketId);
        }
        
        return bv;
    }
    public List<BasketValue> fetchAllBaskets() {
        // Fetch all baskets from the API
        Basket[] baskets = getAllBasketsFromApi();

        // Create a list to hold BasketValue objects
        List<BasketValue> basketValues = new ArrayList<>();

        // Iterate through each Basket and map to BasketValue
        for (Basket basket : baskets) {
            BasketValue bv = mapBasketToBasketValue(basket);
            basketValues.add(bv);
        }

        return basketValues;
    }
    
    private Basket[] getAllBasketsFromApi() {
        String url = "http://BASKETMS/baskets"; 
        return restTemplate.getForObject(url, Basket[].class);
    }
    
    private BasketValue mapBasketToBasketValue(Basket basket) {
        BasketValue bv = new BasketValue();
        bv.setBasket_id(basket.getBasket_id());
        bv.setBasket_name(basket.getBasket_name());
        bv.setStrategy(basket.getStrategy());
        bv.setIaid(basket.getIAid());

        // Fetch basket value
        double basketValue = getBasketValue(basket.getBasket_id());
        bv.setBasketValue(basketValue);

        return bv;
    }

    // Fetch basket as an object from BasketMS
    public Basket getBasketAsObject(int basketId) {
        String url = "http://BASKETMS/baskets/id/{id}";
        return restTemplate.getForObject(url, Basket.class, basketId);
    }
    
    //Fetch basket as an object from BasketMS
    public Basket getBasketAsObjectN(String basket_name) {
        String url = "http://BASKETMS/baskets/name/{name}";
        return restTemplate.getForObject(url, Basket.class, basket_name);
    }

    // Fetch basket value from BasketMS
    private double getBasketValue(int basketId) {
        String valueServiceUrl = "http://BASKETMS/baskets/value/id/" + basketId;
        ResponseEntity<Double> valueResponse = restTemplate.getForEntity(valueServiceUrl, Double.class);
        return valueResponse.getBody() != null ? valueResponse.getBody() : 0.0;
    }
 // Fetch  selling basket value from BasketMS
    private double getBasketSValue(int basketId) {
        String valueServiceUrl = "http://BASKETMS/baskets/svalue/id/" + basketId;
        ResponseEntity<Double> valueResponse = restTemplate.getForEntity(valueServiceUrl, Double.class);
        return valueResponse.getBody() != null ? valueResponse.getBody() : 0.0;
    }
    private List<Integer> getBasketIdsByInvestorId(int invid) {
        String sql = "SELECT basket_id FROM investor_basket_map WHERE inv_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("basket_id"), invid);    }
    private InvestorBasket mapBasketToInvestorBasket(Basket basket, int invid) {
        InvestorBasket investorBasket = new InvestorBasket();
        investorBasket.setInvid(invid);
        investorBasket.setBasketid(basket.getBasket_id());
        investorBasket.setBasket_name(basket.getBasket_name());
        investorBasket.setStrategy(basket.getStrategy());
        investorBasket.setIaid(basket.getIAid());

        // Fetch basket value
        double basketValue = getBasketValue(basket.getBasket_id());
        investorBasket.setBasketValue(basketValue);

        return investorBasket;
    }
    public List<InvestorBasket> fetchBasketsByInvestorId(int invid)
    {
        // Fetch basket IDs that belong to this investor from the DB
        List<Integer> basketIds = getBasketIdsByInvestorId(invid);

        List<InvestorBasket> investorBaskets = new ArrayList<>();

        // Fetch and map each basket using the existing logic
        for (Integer basketId : basketIds) {
            Basket basket = getBasketAsObject(basketId);
            if (basket != null) {
                InvestorBasket investorBasket = mapBasketToInvestorBasket(basket, invid);
                investorBaskets.add(investorBasket);
            }
        }

        return investorBaskets;
    }
    public ResponseEntity<String> recharge(int invid, Double amount) {
        // Check for null or invalid amount
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body("Invalid amount");
        }

        // Fetch the current balance from the database
        String sql = "SELECT account_Balance FROM Investors WHERE inv_id = ?";
        try {
            Double currentBalance = jdbcTemplate.queryForObject(sql, Double.class, invid);

            if (currentBalance == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Investor not found");
            }

            // Calculate the new balance
            double newBalance = currentBalance + amount;

            // Update the balance in the database
            String updateSql = "UPDATE Investors SET account_Balance = ? WHERE inv_id = ?";
            jdbcTemplate.update(updateSql, newBalance, invid);

            return ResponseEntity.ok("Account Recharged. New balance: " + newBalance);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Investor not found");
        } catch (Exception e) {
            e.printStackTrace(); // Log for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
    }
    public ResponseEntity<String> buyId(int invid,int bid)
    {
    	Basket bask=getBasketAsObject(bid);
    	double value=getBasketValue(bid);
    	String sql = "SELECT account_balance FROM Investors WHERE inv_id = ?";
    	Double previousBalance = jdbcTemplate.queryForObject(sql, Double.class, invid);
    	if(previousBalance>=value)
    	{
        String sql1= "insert into investor_basket_map values(?,?)";
    	jdbcTemplate.update(sql1,invid,bid);
    	String updateSql = "UPDATE Investors SET account_Balance = account_Balance - ? WHERE inv_id = ?";
        jdbcTemplate.update(updateSql, value, invid);
    	return ResponseEntity.ok("You have successfully purchased the basket number "+bid+". Your previous account balance was "+previousBalance+"Current Balance is "+(previousBalance-value));
    	}
    	else
    	{
    		return ResponseEntity.ok("Insufficient Balance.");
    	}
    }
    public ResponseEntity<String> buyName(int invid,String bname)
    {
    	Basket bask=getBasketAsObjectN(bname);
    	double value=getBasketValue(bask.getBasket_id());
    	String sql = "SELECT account_balance FROM Investors WHERE inv_id = ?";
    	Double previousBalance = jdbcTemplate.queryForObject(sql, Double.class, invid);
    	if(previousBalance>=value)
    	{
        String sql1= "insert into investor_basket_map values(?,?)";
    	jdbcTemplate.update(sql1,invid,bask.getBasket_id());
    	String updateSql = "UPDATE Investors SET account_Balance = account_Balance - ? WHERE inv_id = ?";
        jdbcTemplate.update(updateSql, value, invid);
    	return ResponseEntity.ok("You have successfully purchased the basket name "+bask.getBasket_name()+". Your previous account balance was "+previousBalance+"Current Balance is "+(previousBalance-value));
    	}
    	else
    	{
    		return ResponseEntity.ok("Insufficient Balance.");
    	}
    }
    public ResponseEntity<String> SellId(int invid, int bid) {
        Basket bask = getBasketAsObject(bid);
        double value = getBasketSValue(bid);
        
        // Check ownership
        String query = "SELECT count(*) FROM investor_basket_map WHERE inv_id=? AND basket_id=?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, invid, bid);
        
        // Log the count of owned baskets
        System.out.println("Basket count: " + count);
        
        if (count == 1) {
            // Fetch previous balance
            String sql = "SELECT account_balance FROM Investors WHERE inv_id = ?";
            Double previousBalance = jdbcTemplate.queryForObject(sql, Double.class, invid);
            
            // Check if previous balance is not null
            if (previousBalance == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body("Account balance is unavailable.");
            }

            // Delete basket from investor_basket_map
            String sql1 = "DELETE FROM investor_basket_map WHERE inv_id=? AND basket_id=?";
            jdbcTemplate.update(sql1, invid, bid);

            // Update account balance
            String updateSql = "UPDATE Investors SET account_Balance = account_Balance + ? WHERE inv_id = ?";
            jdbcTemplate.update(updateSql, value, invid);

            return ResponseEntity.ok("You have successfully sold the basket number " + bid + 
                                       ". Your previous account balance was " + previousBalance + 
                                       ". Current Balance is " + (previousBalance + value));
        } else {
            return ResponseEntity.ok("You don't own a basket with id " + bid);
        }
    }
    
    public ResponseEntity<String> SellName(int invid, String bname) {
        Basket bask = getBasketAsObjectN(bname);
        double value = getBasketSValue(bask.getBasket_id());
        
        // Check ownership
        String query = "SELECT count(*) FROM investor_basket_map WHERE inv_id=? AND basket_id=?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, invid, bask.getBasket_id());
        
        // Log the count of owned baskets
        System.out.println("Basket count: " + count);
        
        if (count == 1) {
            // Fetch previous balance
            String sql = "SELECT account_balance FROM Investors WHERE inv_id = ?";
            Double previousBalance = jdbcTemplate.queryForObject(sql, Double.class, invid);
            
            // Check if previous balance is not null
            if (previousBalance == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body("Account balance is unavailable.");
            }

            // Delete basket from investor_basket_map
            String sql1 = "DELETE FROM investor_basket_map WHERE inv_id=? AND basket_id=?";
            jdbcTemplate.update(sql1, invid, bask.getBasket_id());

            // Update account balance
            String updateSql = "UPDATE Investors SET account_Balance = account_Balance + ? WHERE inv_id = ?";
            jdbcTemplate.update(updateSql, value, invid);

            return ResponseEntity.ok("You have successfully sold the basket number " + bask.getBasket_id() + 
                                       ". Your previous account balance was " + previousBalance + 
                                       ". Current Balance is " + (previousBalance + value));
        } else {
            return ResponseEntity.ok("You don't own a basket with id " + bask.getBasket_id());
        }
    }
    public ResponseEntity<String> getReturn(int invid)
    {
    	double cvalue=0.0;
    	double svalue=0.0;
    	List<Returns> returns=new ArrayList<>();
    	List<Integer> basketIds = getBasketIdsByInvestorId(invid);
    	for(Integer basketid:basketIds)
    	{
    		Basket basket=getBasketAsObject(basketid);
    		if(basket!=null)
    		{
    			    Returns ret = new Returns();
    				ret.setBasket_id(basket.getBasket_id());
    				ret.setCbasketvalue(getBasketValue(basket.getBasket_id()));
    				ret.setInvid(invid);
    				ret.setSbasketvalue(getBasketSValue(basket.getBasket_id()));
    				returns.add(ret);
    		}
    	}
    	for(Returns rets:returns)
    	{
    		cvalue=cvalue+rets.getCbasketvalue();
    		svalue=svalue+rets.getSbasketvalue();
    	}
    	if(svalue>cvalue)
    	{
    		return ResponseEntity.ok("The returns for your baskets is Rs. "+(svalue-cvalue));
    	}
    	else
    	{
    		return ResponseEntity.ok("You have no returns for your baskets "+cvalue+" "+svalue);
    	}
    }

    public ResponseEntity<String> getPortfolio(int invid)
    {
    	double svalue=0.0;
    	List<Returns> returns=new ArrayList<>();
    	List<Integer> basketIds = getBasketIdsByInvestorId(invid);
    	for(Integer basketid:basketIds)
    	{
    		Basket basket=getBasketAsObject(basketid);
    		if(basket!=null)
    		{
    			    Returns ret = new Returns();
    				ret.setBasket_id(basket.getBasket_id());
    				ret.setInvid(invid);
    				ret.setSbasketvalue(getBasketSValue(basket.getBasket_id()));
    				returns.add(ret);
    		}
    	}
    	for(Returns rets:returns)
    	{
    		svalue=svalue+rets.getSbasketvalue();
    	}
    	if(svalue==0.0)
    	{
    		return ResponseEntity.ok("You have no baskets");
    	}
    	else
    	{
    		return ResponseEntity.ok("Your Portfolio is"+svalue);
    	}
    }
 
}