package com.activedge.interview.exercise.solution.exercise2.controller;

import com.activedge.interview.exercise.solution.exercise2.entity.Stock;
import com.activedge.interview.exercise.solution.exercise2.request.StockRequest;
import com.activedge.interview.exercise.solution.exercise2.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createNewStock(@Valid @RequestBody StockRequest stockRequest, UriComponentsBuilder uriComponentsBuilder) {
        Long primaryKey = stockService.createNewStock(stockRequest);

        UriComponents uriComponents = uriComponentsBuilder.path("/api/stocks/{id}").buildAndExpand(primaryKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(stockService.getStockById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable("id") Long id, @Valid @RequestBody StockRequest stockRequest) {
        return ResponseEntity.ok(stockService.updateStock(id, stockRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable("id") Long id) {
        stockService.deleteStockById(id);
        return ResponseEntity.ok().build();
    }
}
