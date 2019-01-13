package ch.stevendick.cryptovalue;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * An investment position in a currency
 */
public class Position {

    public static final String BTC = "BTC";
    public static final String ETH = "ETH";
    public static final String EUR = "EUR";
    public static final String XRP = "XRP";

    private final String symbol;
    private final BigDecimal amount;

    /**
     * Construct a position from a currency and amount
     *
     * @param symbol the currency
     * @param amount the amount of the currency
     */
    public Position(String symbol, BigDecimal amount) {
        this.symbol = symbol;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Position that = (Position) object;
        return Objects.equals(symbol, that.symbol) &&
                amount.compareTo(that.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, amount);
    }

    @Override
    public String toString() {
        return "Position{" +
                "symbol='" + symbol + '\'' +
                ", amount=" + amount +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Converts the position to another currency at the given rate
     * @param symbol the currency
     * @param rate the rate of conversion
     * @return a new {@link Position} in the specified currency converted at the given rate
     */

    public Position convertedTo(String symbol, BigDecimal rate) {
        return new Position(symbol, amount.multiply(rate));
    }

    public boolean isNotZero() {
        return amount.compareTo(BigDecimal.ZERO) != 0;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
