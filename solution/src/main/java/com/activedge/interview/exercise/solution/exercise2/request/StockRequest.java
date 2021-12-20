package com.activedge.interview.exercise.solution.exercise2.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

@Data
public class StockRequest {
    @NotEmpty
    @Size(max = 100)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull(message = "current price is required.")
    @Min(value = 0, message = "current price must be greater than 0.")
    private BigDecimal currentPrice;
}
