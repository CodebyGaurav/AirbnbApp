package com.codebygaurav.AirbnbApp.strategy;

import com.codebygaurav.AirbnbApp.entity.Inventory;

import java.math.BigDecimal;

//TODO: Decorator design pattern implementation
public class BasePricingStrategy implements  PricingStrategy{
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
