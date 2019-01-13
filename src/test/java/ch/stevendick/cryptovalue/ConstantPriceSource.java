package ch.stevendick.cryptovalue;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Always returns the same price for a given symbol
 */
public class ConstantPriceSource implements PriceSource {
    private final Map<String, BigDecimal> prices;

    public ConstantPriceSource(Map<String, BigDecimal> prices) {
        this.prices = prices;
    }

    @Override
    public BigDecimal getPrice(String symbol) {
        return prices.getOrDefault(symbol, BigDecimal.ONE);
    }
}
