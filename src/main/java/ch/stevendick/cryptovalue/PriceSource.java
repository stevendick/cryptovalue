package ch.stevendick.cryptovalue;

import java.math.BigDecimal;

/**
 * A source of prices
 */
interface PriceSource {

    /**
     * Returns the price for a symbol
     * @param symbol the symbol you want a price for
     * @return A price for a symbol
     */
    BigDecimal getPrice(String symbol);
}
