package com.reward.Service;

import com.reward.Entity.Customer;
import com.reward.Entity.Purchase;
import com.reward.Repository.CustomerRepository;
import com.reward.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardsService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Add a new purchase
    public Purchase addPurchase(Purchase purchase) {
        if (purchase == null || purchase.getAmount() <= 0) {
            throw new IllegalArgumentException("Invalid purchase data. Amount must be greater than zero.");
        }
        return purchaseRepository.save(purchase);
    }

    // Calculate reward points based on purchase amount
    public int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;  // 2 points for every dollar above 100
            points += 50;  // Points for amount between 50 and 100
        } else if (amount > 50) {
            points += (amount - 50);  // 1 point for every dollar above 50
        }
        return points;
    }

    // Get total points for a customer based on their purchases
    public int getTotalPoints(Long customerId) {
        List<Purchase> purchases = purchaseRepository.findByCustomerId(customerId);
        int totalPoints = 0;
        for (Purchase purchase : purchases) {
            totalPoints += calculatePoints(purchase.getAmount());
        }
        return totalPoints;
    }

    // Add a new customer
    public Customer addCustomer(Customer customer) {
        if (customer == null || customer.getName() == null) {
            throw new IllegalArgumentException("Customer data is invalid.");
        }
        return customerRepository.save(customer);
    }

    // Get a customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Update a customer by ID
    public boolean updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            customerRepository.save(customer);
            return true;
        } else {
            return false;  // Customer not found
        }
    }

    // Delete a customer by ID
    public boolean deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;  // Customer not found
        }
    }
}
