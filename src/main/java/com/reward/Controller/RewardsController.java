package com.reward.Controller;

import com.reward.Entity.Customer;
import com.reward.Entity.Purchase;
import com.reward.Exception.InvalidAmountException;
import com.reward.Service.RewardsService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    // Add a new purchase
    @PostMapping("/purchase")
    public ResponseEntity<String> addPurchase(
            @Parameter(description = "Purchase details") @Valid @RequestBody Purchase purchase,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid purchase data", HttpStatus.BAD_REQUEST);
        }
        try {
            if (purchase.getAmount() <= 0) {
                throw new InvalidAmountException("Amount must be greater than zero");
            }
            rewardsService.addPurchase(purchase);
            return new ResponseEntity<>("Purchase added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add purchase: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get total points for a customer
    @GetMapping("/points")
    public ResponseEntity<Integer> getTotalPoints(
            @Parameter(description = "Customer ID") @RequestParam Long customerId) {
        try {
            int points = rewardsService.getTotalPoints(customerId);
            return new ResponseEntity<>(points, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add a new customer
    @PostMapping("/customer")
    public ResponseEntity<String> addCustomer(
            @Parameter(description = "Customer details") @Valid @RequestBody Customer customer,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid customer data", HttpStatus.BAD_REQUEST);
        }
        try {
            rewardsService.addCustomer(customer);
            return new ResponseEntity<>("Customer added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        try {
            Optional<Customer> customer = rewardsService.getCustomerById(id);
            return customer.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all customers
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = rewardsService.getAllCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update customer details
    @PutMapping("/customer/{id}")
    public ResponseEntity<String> updateCustomer(
            @PathVariable Long id,
            @Parameter(description = "Updated customer details") @Valid @RequestBody Customer customer,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Invalid customer data", HttpStatus.BAD_REQUEST);
        }
        try {
            boolean updated = rewardsService.updateCustomer(id, customer);
            if (updated) {
                return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a customer by ID
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        try {
            boolean deleted = rewardsService.deleteCustomer(id);
            if (deleted) {
                return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
