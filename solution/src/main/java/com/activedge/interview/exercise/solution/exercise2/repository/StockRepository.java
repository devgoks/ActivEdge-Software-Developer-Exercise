package com.activedge.interview.exercise.solution.exercise2.repository;

import com.activedge.interview.exercise.solution.exercise2.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
