package net.whg.match;

import org.javatuples.Pair;

public class MatchPositionIterator {
    private int x = 2;
    private int z = -1;
    private int leg = 0;
    private int size;

    public MatchPositionIterator(int size) {
        this.size = size;
    }

    public Pair<Integer, Integer> next() {
        if (leg == 0) {
            x--;
            z++;
            if (x == 0)
                leg = 1;
        } else if (leg == 1) {
            x--;
            z--;

            if (z == 0)
                leg = 2;
        } else if (leg == 2) {
            x++;
            z--;
            if (x == 0)
                leg = 3;
        } else if (leg == 3) {
            x++;
            z++;
            if (z == 0) {
                x++;
                leg = 0;
            }
        }

        return Pair.with(x * size - size / 2, z * size - size / 2);
    }
}
