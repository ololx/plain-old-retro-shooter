package org.plain.old.retro.shooter.engine;

import java.util.Objects;

/**
 * The type Space 2 d.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 05.07.2020 08:37 <p>
 */
public class Space2d {

    /**
     * The Space.
     */
    private final int[][] space;

    /**
     * The Width.
     */
    public final int width;

    /**
     * The Height.
     */
    public final int height;

    /**
     * Instantiates a new Space 2 d.
     *
     * @param space the space
     */
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

    /**
     * Get space int [ ] [ ].
     *
     * @return the int [ ] [ ]
     */
    public int[][] getSpace() {
        return this.space;
    }
}
