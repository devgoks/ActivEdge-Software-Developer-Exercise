package com.activedge.interview.exercise.solution.exercise2.service;

import com.activedge.interview.exercise.solution.exercise2.entity.Stock;
import com.activedge.interview.exercise.solution.exercise2.exception.StockNotFoundException;
import com.activedge.interview.exercise.solution.exercise2.repository.StockRepository;
import com.activedge.interview.exercise.solution.exercise2.request.StockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Long createNewStock(StockRequest stockRequest) {
        Stock stock = new Stock();
        stock.setName(stockRequest.getName());
        stock.setDescription(stockRequest.getDescription());
        stock.setCurrentPrice(stockRequest.getCurrentPrice());

        stock = stockRepository.save(stock);

        return stock.getId();
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id) {
        Optional<Stock> requestedStock = stockRepository.findById(id);

        if (!requestedStock.isPresent()) {
            throw new StockNotFoundException(String.format("Stock with id: '%s' not found", id));
        }

        return requestedStock.get();
    }

    @Transactional
    public Stock updateStock(Long id, StockRequest stockToUpdateRequest) {

        Optional<Stock> stockFromDatabase = stockRepository.findById(id);

        if (!stockFromDatabase.isPresent()) {
            throw new StockNotFoundException(String.format("Stock with id: '%s' not found", id));
        }

        Stock stockToUpdate = stockFromDatabase.get();

        stockToUpdate.setName(stockToUpdateRequest.getName());
        stockToUpdate.setDescription(stockToUpdateRequest.getDescription());
        stockToUpdate.setCurrentPrice(stockToUpdateRequest.getCurrentPrice());

        stockToUpdate = stockRepository.save(stockToUpdate);

        return stockToUpdate;
    }

    public void deleteStockById(Long id) {
        stockRepository.deleteById(id);
    }
}
