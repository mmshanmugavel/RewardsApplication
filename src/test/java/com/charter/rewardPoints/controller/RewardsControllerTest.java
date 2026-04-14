package com.charter.rewardPoints.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.MockMvc;

import com.charter.rewardPoints.dto.RewardsResponse;
import com.charter.rewardPoints.service.RewardsService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Test
    void whenBothDatesNull_thenDefaultsToLast3Months() throws Exception {
        Mockito.when(rewardsService.calculateRewards(anyString(), any(), any()))
                .thenReturn(new RewardsResponse());
        mockMvc.perform(get("/api/rewards/calculate")
                .param("customerId", "123"))
                .andExpect(status().isOk());
    }

    @Test
    void whenOneDateNull_thenMissingDateException() throws Exception {
        mockMvc.perform(get("/api/rewards/calculate")
                .param("customerId", "123")
                .param("fromDate", LocalDate.now().toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFutureDate_thenFutureDateException() throws Exception {
        LocalDate future = LocalDate.now().plusDays(1);
        mockMvc.perform(get("/api/rewards/calculate")
                .param("customerId", "123")
                .param("fromDate", future.toString())
                .param("toDate", future.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidDates_thenReturnsRewards() throws Exception {
        Mockito.when(rewardsService.calculateRewards(anyString(), any(), any()))
                .thenReturn(new RewardsResponse());
        LocalDate now = LocalDate.now();
        mockMvc.perform(get("/api/rewards/calculate")
                .param("customerId", "123")
                .param("fromDate", now.minusMonths(1).toString())
                .param("toDate", now.toString()))
                .andExpect(status().isOk());
    }
}
