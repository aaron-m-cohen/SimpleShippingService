package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.Map;

public class WeightedCostStrategy implements CostStrategy {

    private Map<BigDecimal, CostStrategy> strategyWeights;

    /**
     * Constructor.
     * @param strategyWeights - the weights for the different strategies. Key is weight, value is strategy.
     */

    public WeightedCostStrategy(Map<BigDecimal, CostStrategy> strategyWeights) {
        this.strategyWeights = strategyWeights;
    }

    /**
     * @param shipmentOption a shipment option with packaging
     * @return - ShipmentCost object when applying this strategy
     */
    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        BigDecimal cost = BigDecimal.ZERO;
        for (Map.Entry<BigDecimal, CostStrategy> entry : strategyWeights.entrySet()) {
            cost = cost.add(entry.getValue().getCost(shipmentOption).getCost().multiply(entry.getKey()));
        }
        return new ShipmentCost(shipmentOption, cost);
    }
}
