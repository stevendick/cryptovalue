package ch.stevendick.cryptovalue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static ch.stevendick.cryptovalue.Position.*;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

class CryptoTestSuite {

    @DisplayName("Read a position from a String")
    @Test
    void readPositionFromString() {
        //when
        PositionSource source = new TextPositionSource(singletonList("BTC=10"));

        //expect
        assertTrue(source.getAll().contains(new Position(BTC, new BigDecimal("10"))));
    }

    @DisplayName("Convert JSON rate to BigDecimal")
    @Test
    void priceToBigDecimal() {
        //when
        BigDecimal result = JsonParser.parse("{\"EUR\":20.50}");

        //expect
        assertTrue(matches(new BigDecimal("20.50"), result));
    }

    @DisplayName("The sum of the depot in EUR matches expectation")
    @Test
    void totalValue() {
        // given
        Position[] positions = {
                new Position(BTC, new BigDecimal("10")),
                new Position(ETH, new BigDecimal("5")),
                new Position(XRP, new BigDecimal("2000"))
        };

        Map<String, BigDecimal> euroRates = new HashMap<>();
        euroRates.put(BTC, new BigDecimal("300"));
        euroRates.put(ETH, new BigDecimal("20"));
        euroRates.put(XRP, new BigDecimal("0.25"));

        PriceSource euroPriceSource = new ConstantPriceSource(euroRates);

        Portfolio portfolio = new Portfolio(euroPriceSource, Arrays.stream(positions).collect(Collectors.toSet()));

        //when
        Map<String, Position> values = portfolio.getPositionsInEuros();
        
        // expect
        assertAll(
                () -> assertEquals(new Position(EUR, new BigDecimal("3000")), values.get(BTC)),
                () -> assertEquals(new Position(EUR, new BigDecimal("100")),  values.get(ETH)),
                () -> assertEquals(new Position(EUR, new BigDecimal("500")),  values.get(XRP)),
                () -> assertTrue(() -> matches(new BigDecimal("3600"), portfolio.getEuroValue()))
        );
    }

    @DisplayName("REST call returns BTC price")
    @Test
    void httpGet() {
        //given
        PriceSource source = new HttpPriceSource();

        //when
        BigDecimal price = source.getPrice(BTC);

        //expect
        assertNotNull(price);
    }

    @DisplayName("Read positions from a text file on the classpath")
    @Test
    void readPositionsFromClasspathFile() throws IOException {
        //given
        PositionSource source = new TextPositionSource("bobs_crypto.txt");

        //when
        Set<Position> positions = source.getAll();

        //expect
        assertAll(
                () -> assertTrue(positions.contains(new Position(BTC, new BigDecimal("10")))),
                () -> assertTrue(positions.contains(new Position(ETH, new BigDecimal("5")))),
                () -> assertTrue(positions.contains(new Position(XRP, new BigDecimal("2000"))))
        );
    }

    private boolean matches(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) == 0;
    }

}
