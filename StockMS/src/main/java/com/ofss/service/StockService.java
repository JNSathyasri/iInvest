package com.ofss.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ofss.model.Stock;

@Service
public class StockService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // Fetch all stocks
    public ResponseEntity<List<Stock>> getAllStocks() {
        String query = "SELECT * FROM Stocks";
        List<Stock> stocks = jdbcTemplate.query(query, new StockRowMapper());
        return ResponseEntity.ok(stocks);
    }

    // Fetch stock by ID
    public Stock getStockByID(String id) {  // Changed parameter type to String
        String query = "SELECT * FROM Stocks WHERE stock_id = ?";
        return jdbcTemplate.queryForObject(query, new StockRowMapper(), id); // Use stockRowMapper directly
    }

    // Fetch stock by Name
    public Stock getStockByName(String name) {
        String query = "SELECT * FROM Stocks WHERE stock_name = ?";
        return jdbcTemplate.queryForObject(query, new StockRowMapper(), name);
    }

    // RowMapper class for mapping result set to Stock object
    class StockRowMapper implements RowMapper<Stock> {
        @Override
        public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
            Stock stock = new Stock();
            stock.setStock_id(rs.getString("stock_id"));
            stock.setStock_name(rs.getString("stock_name"));
            stock.setIndustry(rs.getString("industry"));
            stock.setSymbol(rs.getString("symbol"));
            stock.setSeries(rs.getString("series"));
            stock.setStock_price(rs.getDouble("stock_price"));
            stock.setSelling_price(rs.getDouble("selling_price"));
            return stock;
        }
    }
}
