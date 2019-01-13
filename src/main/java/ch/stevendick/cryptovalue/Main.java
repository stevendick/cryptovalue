package ch.stevendick.cryptovalue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final String FILENAME = "bobs_crypto.txt";

    public static void main(String[] args) throws IOException {
        PositionSource source = new TextPositionSource(FILENAME);

        Portfolio portfolio = new Portfolio(new HttpPriceSource(), source.getAll());

        portfolio.print();
    }
}
