package com.reward.Controller;

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

@RestController
@RequestMapping("/api/rewards")

public class RewardsController {

    @Autowired
    private RewardsService rewardsService;


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
            throw new InvalidAmountException("Failed to add purchase: " + e.getMessage());
        }
    }


    @GetMapping("/points")
    public ResponseEntity<Integer> getTotalPoints(
            @Parameter(description = "Customer ID") @RequestParam Long customerId) {
        try {
            int points = rewardsService.getTotalPoints(customerId);
            return new ResponseEntity<>(points, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating total points: " + e.getMessage());
        }
    }
}
