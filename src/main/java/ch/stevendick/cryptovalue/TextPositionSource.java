package ch.stevendick.cryptovalue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;

/**
 * Reads positions from a text file
 */
public class TextPositionSource implements PositionSource {

    private Set<Position> positions;

    /**
     * Construct a depot source from a text file on the classpath
     * @param sourceFileName the source file name
     */
    TextPositionSource(String sourceFileName) {
        List<String> source = new BufferedReader(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream(sourceFileName)))
                .lines()
                .collect(Collectors.toList());

        parse(source);
    }

    /**
     * Construct a depot source from a text file {@link Path}
     * @param source the source {@link Path}
     */
    public TextPositionSource(Path source) throws IOException {
        if(!Files.isRegularFile(source)) {
            throw new IllegalArgumentException("The source should be a file: "  + source);
        }

        parse(readAllLines(source));
    }

    TextPositionSource(List<String> source) {
        parse(source);
    }

    private void parse(List<String> positions) {
        this.positions = positions.stream()
                .map(position -> position.split("="))
                .map(array -> new Position(array[0], new BigDecimal(array[1])))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Position> getAll() {
        return positions;
    }


}
