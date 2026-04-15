package com.charter.rewardpoints.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.charter.rewardpoints.repository.TransactionRepository;
import com.charter.rewardpoints.service.RewardsService;

@WebMvcTest
class RewardsControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Mock
    private TransactionRepository transactionRepository;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateRewards_NoTransactions() throws Exception {
        when(transactionRepository.findByCustomerAndDateRange(any(), any()))
            .thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/rewards/calculate")
                .param("fromDate", "2026-01-15")
                .param("toDate", "2026-04-15"))
                .andExpect(status().isOk());
    }
}
