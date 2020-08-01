package org.plain.old.retro.shooter.engine;

import java.util.Objects;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class Space2d {

    private final int[][] space;

    public final int width;

    public final int height;

    public Space2d(int[][] space) {
        Objects.requireNonNull(space);
        this.space = space;

        if (space.length > 0) {
            this.width = space.length;
            this.height = space[0].length;
        } else {
            this.width = 0;
            this.height = 0;
        }
    }

    public int[][] getSpace() {
        return this.space;
    }
}
