package ch.stevendick.cryptovalue;

import java.math.BigDecimal;

/**
 * Prices the price from JSON
 */
class JsonParser {

    static BigDecimal parse(String price) {
        String raw = price.split(":")[1];
        return new BigDecimal(raw.substring(0, raw.length()-1));
    }
}
