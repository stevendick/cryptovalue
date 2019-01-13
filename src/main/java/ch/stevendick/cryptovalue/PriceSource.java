package ch.stevendick.cryptovalue;

import java.math.BigDecimal;

/**
 * A source of prices
 */
public interface PriceSource {

    /**
     * Returns the price for a symbol
     * @param symbol
     * @return A price for a symbol
     */
    BigDecimal getPrice(String symbol);
}
