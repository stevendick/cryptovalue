package ch.stevendick.cryptovalue;

import java.util.Set;

public class ConstantPositionSource implements PositionSource {

    private final Set<Position> holdings;

    public ConstantPositionSource(Set<Position> holdings) {
        this.holdings = holdings;
    }

    @Override
    public Set<Position> getAll() {
        return holdings;
    }
}
