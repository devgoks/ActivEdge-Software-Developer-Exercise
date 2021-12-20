package com.activedge.interview.exercise.solution.exercise2;

import com.activedge.interview.exercise.solution.exercise2.controller.StockController;
import com.activedge.interview.exercise.solution.exercise2.entity.Stock;
import com.activedge.interview.exercise.solution.exercise2.exception.StockNotFoundException;
import com.activedge.interview.exercise.solution.exercise2.request.StockRequest;
import com.activedge.interview.exercise.solution.exercise2.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StockService stockService;

    @Captor
    private ArgumentCaptor<StockRequest> stockRequestArgumentCaptor;

    @Test
    public void postingANewStockShouldCreateANewStock() throws Exception {
        StockRequest stockRequest = new StockRequest();
        stockRequest.setName("Stock A Name");
        stockRequest.setDescription("Stock A Description");
        stockRequest.setCurrentPrice(new BigDecimal(2700.60));

        when(stockService.createNewStock(stockRequestArgumentCaptor.capture())).thenReturn(1L);

        this.mockMvc
                .perform(post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/stocks/1"));

        assertThat(stockRequestArgumentCaptor.getValue().getName(), is("Stock A Name"));
        assertThat(stockRequestArgumentCaptor.getValue().getDescription(), is("Stock A Description"));
        assertThat(stockRequestArgumentCaptor.getValue().getCurrentPrice(), is(new BigDecimal(2700.60)));
    }

    @Test
    public void allStocksEndpointShouldReturnTwoStocks() throws Exception {

        when(stockService.getAllStocks()).thenReturn(Arrays.asList(
                createStock(1L, "Stock A Name", "Stock A Description", new BigDecimal(2700.60)),
                createStock(2L, "Stock B Name", "Stock B Description", new BigDecimal(5000))));

        this.mockMvc
                .perform(get("/api/stocks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Stock A Name")))
                .andExpect(jsonPath("$[0].description", is("Stock A Description")))
                .andExpect(jsonPath("$[0].currentPrice", is(new BigDecimal(2700.60))))
                .andExpect(jsonPath("$[0].id", is(1)));

    }

    @Test
    public void getStockWithIdOneShouldReturnAStock() throws Exception {

        when(stockService.getStockById(1L)).thenReturn(createStock(1L, "Stock A Name", "Stock A Description", new BigDecimal(2700.60)));

        this.mockMvc
                .perform(get("/api/stocks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("Stock A Name")))
                .andExpect(jsonPath("$.description", is("Stock A Description")))
                .andExpect(jsonPath("$.currentPrice", is(new BigDecimal(2700.60))))
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    public void getStockWithUnknownIdShouldReturn404() throws Exception {

        when(stockService.getStockById(1L)).thenThrow(new StockNotFoundException("Stock with id '1' not found"));

        this.mockMvc
                .perform(get("/api/stocks/1"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void updateStockWithKnownIdShouldUpdateTheStock() throws Exception {

        StockRequest stockRequest = new StockRequest();
        stockRequest.setName("Stock A Update");
        stockRequest.setDescription("Stock A Description");
        stockRequest.setCurrentPrice(new BigDecimal(5000.50));

        when(stockService.updateStock(eq(1L), stockRequestArgumentCaptor.capture()))
                .thenReturn(createStock(1L, "Stock A Update", "Stock A Description", new BigDecimal(5000.50)));

        this.mockMvc
                .perform(put("/api/stocks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("Stock A Update")))
                .andExpect(jsonPath("$.description", is("Stock A Description")))
                .andExpect(jsonPath("$.currentPrice", is(5000.50)))
                .andExpect(jsonPath("$.id", is(1)));

        assertThat(stockRequestArgumentCaptor.getValue().getName(), is("Stock A Update"));
        assertThat(stockRequestArgumentCaptor.getValue().getDescription(), is("Stock A Description"));
        assertThat(stockRequestArgumentCaptor.getValue().getCurrentPrice(), is(new BigDecimal(5000.50)));

    }

    @Test
    public void updateStockWithUnknownIdShouldReturn404() throws Exception {

        StockRequest stockRequest = new StockRequest();
        stockRequest.setName("Stock A Update");
        stockRequest.setDescription("Stock A Description");
        stockRequest.setCurrentPrice(new BigDecimal(4000));

        when(stockService.updateStock(eq(2L), stockRequestArgumentCaptor.capture()))
                .thenThrow(new StockNotFoundException("The stock with id '2' was not found"));

        this.mockMvc
                .perform(put("/api/stocks/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(stockRequest)))
                .andExpect(status().isNotFound());

    }

    private Stock createStock(Long id, String name, String description, BigDecimal currentPrice) {
        Stock stock = new Stock();
        stock.setName(name);
        stock.setDescription(description);
        stock.setCurrentPrice(currentPrice);
        stock.setCreateDate(Timestamp.from(Instant.now()));
        stock.setLastUpdate(Timestamp.from(Instant.now()));
        stock.setId(id);
        return stock;
    }

}
