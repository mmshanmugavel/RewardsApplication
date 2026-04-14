package com.charter.rewardPoints.service;

import com.charter.rewardPoints.exception.GenericException;
import com.charter.rewardPoints.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardsServiceTest {

	@Mock
    private TransactionRepository transactionRepository;

    private RewardsServiceImpl rewardsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rewardsService = new RewardsServiceImpl(transactionRepository);
    }

    @Test
    void testCalculateRewards_NoTransactions() {
        when(transactionRepository.findByCustomerAndDateRange(anyString(), any(), any()))
                .thenReturn(Collections.emptyList());
        assertThrows(GenericException.class, () -> 
        rewardsService.calculateRewards("123", LocalDate.now().minusMonths(1), LocalDate.now()));
    }

}

