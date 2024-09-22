package com.reward.service;

import com.reward.Entity.Customer;
import com.reward.Entity.Purchase;
import com.reward.Repository.CustomerRepository;
import com.reward.Repository.PurchaseRepository;
import com.reward.Service.RewardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RewardServiceTest {

    @InjectMocks
    private RewardsService rewardsService;

    @Mock
    private CustomerRepository customerRepository;  // Mock the repository dependencies

    @Mock
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    public void testAddPurchase_Success() {
        Purchase purchase = new Purchase();
        purchase.setAmount(100);
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        rewardsService.addPurchase(purchase);

        verify(purchaseRepository, times(1)).save(purchase);
    }

    @Test
    public void testAddCustomer_Success() {
        Customer customer = new Customer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        rewardsService.addCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetCustomerById_Found() {
        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = rewardsService.getCustomerById(1L);

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    public void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Customer> result = rewardsService.getCustomerById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetTotalPoints() {
        // Assuming getTotalPoints calculates points based on purchases
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        int totalPoints = rewardsService.getTotalPoints(1L);

        assertEquals(0, totalPoints);
    }

    @Test
    public void testDeleteCustomer_Success() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);

        boolean result = rewardsService.deleteCustomer(1L);

        assertTrue(result);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCustomer_NotFound() {
        when(customerRepository.existsById(1L)).thenReturn(false);

        boolean result = rewardsService.deleteCustomer(1L);

        assertFalse(result);
        verify(customerRepository, never()).deleteById(1L);
    }
}
