package ch.stevendick.cryptovalue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sources a price from an HTTP source
 */
public class HttpPriceSource implements PriceSource {

    private static final String BASE_URL    = "https://min-api.cryptocompare.com/data/price?";
    private static final String FROM        = "fsym=";
    private static final String TO          = "&tsyms=EUR";

    @Override
    public BigDecimal getPrice(String symbol) {

        HttpURLConnection con = openConnection(createUrl(symbol));

        setup(con);

        return handleResponse(con);
    }

    private URL createUrl(String symbol) {
        try {
            return new URL(BASE_URL + FROM + symbol + TO);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    private HttpURLConnection openConnection(URL url) {
        try {
            return  (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void setup(HttpURLConnection con) {
        try {
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.setRequestProperty("Accept", "application/json");
        } catch (ProtocolException e) {
            throw new IllegalStateException(e);
        }
    }

    private BigDecimal handleResponse(HttpURLConnection con) {
        int status;

        try {
            status = con.getResponseCode();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        List<String> lines = getContent(con);

        if(status != 200) {
            throw new IllegalStateException(String.join("\n", lines));
        }

        return getPrice(lines);
    }

    private List<String> getContent(HttpURLConnection con) {
        List<String> lines;
        try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            lines = in.lines().collect(Collectors.toList());
        }
        catch(IOException e) {
           throw new IllegalStateException(e);
        }
        return lines;
    }

    private BigDecimal getPrice(List<String> lines) {
        return lines.stream()
                .findFirst()
                .map(JsonParser::parse)
                .orElseThrow(IllegalArgumentException::new);
    }
}
