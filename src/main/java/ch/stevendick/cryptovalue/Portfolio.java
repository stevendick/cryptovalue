package ch.stevendick.cryptovalue;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A portfolio holds zero or more positions across multiple currencies
 */
class Portfolio {

    private final PriceSource priceSource;
    private final Map<String, Position> positions;

    /**
     * Construct a portfolio with a price source and positions
     *
     * @param priceSource price source
     * @param positions positions
     */

    Portfolio(PriceSource priceSource, Set<Position> positions) {
        this.priceSource = priceSource;
        this.positions = positions.stream()
                .collect(Collectors.toMap(Position::getSymbol, Function.identity()));
    }

    /**
     * Returns the positions priced in Euros
     * @return A Map of currencies to positions in Euroes
     */

    Map<String, Position> getPositionsInEuros() {
        return positions.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> toEuro(entry.getValue())));
    }

    /**
     * Returns the value of the portfolio in euros
     * @return A {@link BigDecimal} representing the portfolio value in Euros
     */

    BigDecimal getEuroValue() {
        return positions.values().stream()
                .filter(Position::isNotZero)
                .map(this::toEuro)
                .map(Position::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Convert a position to Euros
     *
     * @param currency the currency
     * @return the {@link Position} converted to Euros
     */

    private Position toEuro(Position currency) {
        if(currency.getSymbol().equals(Position.EUR)) return currency;
        return currency.convertedTo(Position.EUR, priceSource.getPrice(currency.getSymbol()));
    }

    /**
     * Prints out to console the value of the portfolio in Euros
     */
    void print() {
        System.out.println("Portfolio valuation in Euro");
        getPositionsInEuros().entrySet().stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue().getAmount())
                .forEach(System.out::println);
        System.out.println("Total Euro Value: " + getEuroValue());
    }
}
