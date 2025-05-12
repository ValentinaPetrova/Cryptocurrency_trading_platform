package com.crypto.traiding.controller;

import com.crypto.traiding.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import com.crypto.traiding.crypto_traiding.CryptoTraidingApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CryptoTraidingApplication.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private TransactionService transactionService;

    @Test
    public void shouldReturnTransactionHistory() throws Exception {
        Mockito.when(transactionService.getHistory(1L))
                .thenReturn(List.of(Map.of("symbol", "BTC", "type", "buy")));

        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("BTC"));
    }

    @Test
    public void shouldResetAccountBalance() throws Exception {
        mockMvc.perform(post("/api/transactions/reset?userId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account balance reset to initial value!"));
    }
}
