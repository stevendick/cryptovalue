package ch.stevendick.cryptovalue;

import java.io.IOException;

public class Main {
    private static final String FILENAME = "bobs_crypto.txt";

    public static void main(String[] args) {
        PositionSource source = new TextPositionSource(FILENAME);

        Portfolio portfolio = new Portfolio(new HttpPriceSource(), source.getAll());

        portfolio.print();
    }
}
