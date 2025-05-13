package com.crypto.traiding.controller;

import com.crypto.traiding.service.PortfolioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.crypto.traiding.crypto_traiding.CryptoTraidingApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTraidingApplication.class)
@WebMvcTest(PortfolioController.class)
public class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private PortfolioService portfolioService;

    @Test
    public void shouldReturnHoldings() throws Exception {
        Mockito.when(portfolioService.getCurrentHoldings(1L))
                .thenReturn(Map.of("BTC", new BigDecimal("2.5")));

        mockMvc.perform(get("/api/portfolio/1/holdings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.BTC").value("2.5"));
    }

    @Test
    public void shouldReturnPortfolioValue() throws Exception {
        Mockito.when(portfolioService.calculatePortfolioValue(1L))
                .thenReturn(new BigDecimal("12000.00"));

        mockMvc.perform(get("/api/portfolio/1/value"))
                .andExpect(status().isOk())
                .andExpect(content().string("12000.00"));
    }
}
