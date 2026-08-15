package com.ofss.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ofss.model.Basket;
import com.ofss.model.Stock;
import com.ofss.model.StockQuantity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.client.RestTemplate;

@Service
public class BasketService {
	 private static final Logger logger = LoggerFactory.getLogger(BasketService.class);

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    RestTemplate restTemplate;

    // Fetch all baskets
    public List<Basket> getAllBaskets() {
        String query = "SELECT * FROM Baskets";
        return jdbcTemplate.query(query, new BasketRowMapper());
    }

    // Fetch basket by ID
    public Basket getBasketByID(int id) {
        String query = "SELECT * FROM Baskets WHERE basket_id = ?";
        return jdbcTemplate.queryForObject(query, new BasketRowMapper(), id);
    }

    // Fetch basket by Name
    public Basket getBasketByName(String name) {
        String query = "SELECT * FROM Baskets WHERE basket_name = ?";
        return jdbcTemplate.queryForObject(query, new BasketRowMapper(), name);
    }
    // Fetch stock details from StockMS using RestTemplate
    public Stock fetchStockObject(String stockId) {
        // Assuming RestTemplate is set up correctly.
    	String stockServiceUrl = "http://STOCKMS/stocks/id/" + stockId;
    	ResponseEntity<Stock> response = restTemplate.getForEntity(stockServiceUrl, Stock.class);


        if (response.getStatusCode().is2xxSuccessful()) {
            Stock stock = response.getBody();
            logger.info("Fetched stock: {}, Stock Price: {}", stock.getStock_name(), stock.getStock_price());
            return stock;
        } else {
            logger.warn("Failed to fetch stock for stockId: {}", stockId);
            return null;
        }
    }
    // Fetch basket value (stock price * quantity) by Basket ID
    public double getBasketValue(int basketId) 
    {
        String query = "SELECT bs.stock_id, bs.quantity FROM Basket_Stock_Map bs WHERE bs.basket_id = ?";
        List<StockQuantity> stockQuantities = jdbcTemplate.query(query, new StockQuantityRowMapper(), basketId);

        double basketValue = 0.0;

        // Debugging logs for tracking stock and quantities
        logger.info("Calculating Basket Value for Basket ID: " + basketId);

        for (StockQuantity sq : stockQuantities) {
            Stock stock = fetchStockObject(sq.getStock_id());
            
            if (stock != null) {
                double stockPrice = stock.getStock_price();
                int quantity = sq.getQuantity();

                // Log each stock's price and quantity for debugging
                logger.info("Stock ID: " + sq.getStock_id() + ", Stock Price: " + stockPrice + ", Quantity: " + quantity);

                basketValue += stockPrice * quantity;
            } else {
                logger.warn("No stock found for Stock ID: " + sq.getStock_id());
            }
        }

        logger.info("Total Basket Value: " + basketValue);

        return basketValue;
    }



    // Fetch stock quantities for each stock in the basket by Basket ID
    public List<StockQuantity> getStockQuantities(int basketId) {
        String query = "SELECT stock_id, quantity FROM Basket_Stock_Map WHERE basket_id = ?";
        return jdbcTemplate.query(query, new StockQuantityRowMapper(), basketId);
    }

   

    // BasketRowMapper to map the result set to Basket objects
    private class BasketRowMapper implements RowMapper<Basket> {
        @Override
        public Basket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Basket basket = new Basket();
            basket.setBasket_id(rs.getInt("basket_id"));
            basket.setBasket_name(rs.getString("basket_name"));
            basket.setStrategy(rs.getString("strategy"));
            basket.setIAid(rs.getInt("IAid"));
            return basket;
        }
    }

    // RowMapper to map stock quantities from Basket_Stock_Map table
    private class StockQuantityRowMapper implements RowMapper<StockQuantity> {
        @Override
        public StockQuantity mapRow(ResultSet rs, int rowNum) throws SQLException {
            StockQuantity sq = new StockQuantity();
            sq.setStock_id(rs.getString("stock_id"));
            sq.setQuantity(rs.getInt("quantity"));
            return sq;
        }
    }
    public double getBasketSValue(int basketId) 
    {
        String query = "SELECT bs.stock_id, bs.quantity FROM Basket_Stock_Map bs WHERE bs.basket_id = ?";
        List<StockQuantity> stockQuantities = jdbcTemplate.query(query, new StockQuantityRowMapper(), basketId);

        double basketValue = 0.0;

        // Debugging logs for tracking stock and quantities
        logger.info("Calculating Basket Value for Basket ID: " + basketId);

        for (StockQuantity sq : stockQuantities) {
            Stock stock = fetchStockObject(sq.getStock_id());
            
            if (stock != null) {
                double sellingPrice = stock.getSelling_price();
                int quantity = sq.getQuantity();

                // Log each stock's price and quantity for debugging
                logger.info("Stock ID: " + sq.getStock_id() + ", sellingPrice: " + sellingPrice + ", Quantity: " + quantity);

                basketValue += sellingPrice * quantity;
            } else {
                logger.warn("No stock found for Stock ID: " + sq.getStock_id());
            }
        }

        logger.info("Total Basket Value: " + basketValue);

        return basketValue;
    }
}