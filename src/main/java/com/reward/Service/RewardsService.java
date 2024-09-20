package com.reward.Service;

import com.reward.Entity.Purchase;
import com.reward.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardsService {

    @Autowired
    private PurchaseRepository purchaseRepository;

       public  Purchase addPurchase(Purchase purchase)
       {
           return purchaseRepository.save(purchase);
       }


    public int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;
            points += 50; // Points for amount between 50 and 100
        } else if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }

    public int getTotalPoints(Long customerId) {
        List<Purchase> purchases = purchaseRepository.findByCustomerId(customerId);
        int totalPoints = 0;
        for (Purchase purchase : purchases) {
            totalPoints += calculatePoints(purchase.getAmount());
        }
        return totalPoints;
    }
}
