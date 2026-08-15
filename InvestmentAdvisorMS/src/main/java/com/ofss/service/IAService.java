package com.ofss.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ofss.model.InvestmentAdvisor;
import com.ofss.model.Basket;
import com.ofss.model.Stock;
import com.ofss.model.StockInput;

@Service
public class IAService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RestTemplate restTemplate;

    // Fetch all Investment Advisors
    public List<InvestmentAdvisor> getAllIAs() {
        String query = "SELECT * FROM Investment_Advisors";
        return jdbcTemplate.query(query, new IARowMapper());
    }

    //Add IA
    public ResponseEntity<String> addIA(InvestmentAdvisor IA) {
        String insertIAQuery = "INSERT INTO Investment_Advisors VALUES (?, ?, ?)";
        jdbcTemplate.update(insertIAQuery, IA.getIAid(), IA.getEmail(), IA.getPassword());
        return ResponseEntity.ok("IA created successfully.");
    }
    
    //Remove IA
    public ResponseEntity<String> removeIA(String uname) {
        String insertIAQuery = "delete from investment_advisors where email=?";
        jdbcTemplate.update(insertIAQuery, uname);
        return ResponseEntity.ok("IA deleted successfully.");
    }
    
    // Fetch Investment Advisor by ID
    public InvestmentAdvisor getIAById(int id) {
        String query = "SELECT * FROM Investment_Advisors WHERE IAid = ?";
        return jdbcTemplate.queryForObject(query, new IARowMapper(), id);
    }

    // Fetch all stocks from StockMS
    public ResponseEntity<List<Stock>> getAllStocksFromStockMS() {
        String stockServiceUrl = "http://STOCKMS/stocks";
        ResponseEntity<List<Stock>> response = restTemplate.getForEntity(stockServiceUrl, (Class<List<Stock>>) (Object) List.class);
        return response;
    }

    // Fetch stock by name from StockMS
    public ResponseEntity<Stock> getStockByNameFromStockMS(String stockName) {
        String stockServiceUrl = "http://STOCKMS/stocks/name/" + stockName;
        return restTemplate.getForEntity(stockServiceUrl, Stock.class);
    }

    // Fetch stock by ID from StockMS
    public ResponseEntity<Stock> getStockByIdFromStockMS(String stockId) {
        String stockServiceUrl = "http://STOCKMS/stocks/id/" + stockId;
        return restTemplate.getForEntity(stockServiceUrl, Stock.class);
    }

    // Create a new Basket - PUT method
    public ResponseEntity<String> createBasket(Basket basket) {
        String insertBasketQuery = "INSERT INTO Baskets (basket_id, basket_name, strategy, IAid) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertBasketQuery, basket.getBasket_id(), basket.getBasket_name(), basket.getStrategy(), basket.getIAid());

        // Assuming there is a mapping table for Basket-Stock and IA-Basket relations
        String insertBasketStockMapQuery = "INSERT INTO Basket_Stock_Map (basket_id, stock_id, quantity) VALUES (?, ?, ?)";
        // Logic to insert into Basket_Stock_Map
        // You may need to loop through basket's stock details

        // Similarly, insert into IA-Basket map
        String insertIABasketMapQuery = "INSERT INTO IA_Basket_Map (IAid, basket_id) VALUES (?, ?)";
        jdbcTemplate.update(insertIABasketMapQuery, basket.getIAid(), basket.getBasket_id());

        return ResponseEntity.ok("Basket created successfully.");
    }
    public ResponseEntity<String> addStock(StockInput stockInput) {
    	String insertBasketStockMapQuery = "INSERT INTO Basket_Stock_Map (basket_id, stock_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertBasketStockMapQuery, stockInput.getBasketId(), stockInput.getStockId(), stockInput.getQuantity());

        return ResponseEntity.ok("Stock added to basket successfully.");
    }

    // RowMapper for InvestmentAdvisor
    private class IARowMapper implements RowMapper<InvestmentAdvisor> {
        @Override
        public InvestmentAdvisor mapRow(ResultSet rs, int rowNum) throws SQLException {
            InvestmentAdvisor ia = new InvestmentAdvisor();
            ia.setIAid(rs.getLong("IAid"));
            ia.setEmail(rs.getString("email"));
            ia.setPassword(rs.getString("password"));
            return ia;
        }
    }
}
