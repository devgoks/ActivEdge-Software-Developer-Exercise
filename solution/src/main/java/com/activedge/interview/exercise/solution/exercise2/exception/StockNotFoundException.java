package com.activedge.interview.exercise.solution.exercise2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockNotFoundException extends RuntimeException {

        public StockNotFoundException(String message) {
            super(message);
        }
}
