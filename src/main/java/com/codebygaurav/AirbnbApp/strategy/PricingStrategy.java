package com.codebygaurav.AirbnbApp.strategy;


import com.codebygaurav.AirbnbApp.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {


    BigDecimal calculatePrice(Inventory inventory);
}
