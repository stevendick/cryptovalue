package ch.stevendick.cryptovalue;

import java.util.Set;

/**
 * A source of crypto currency holdings
 */
public interface PositionSource {

    /**
     * Returns
     * @return a {@link Set} of {@link Portfolio}s
     */
    Set<Position> getAll();
}
