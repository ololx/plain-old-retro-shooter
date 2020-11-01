package org.plain.old.retro.shooter.engine.calculus;

/**
 * The interface Simple math.
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 23.08.2020 11:51 <p>
 */
public interface SimpleMath {

    /**
     * Min int.
     *
     * @param a the a
     * @param b the b
     * @return the int
     */
    static int min(int a, int b) {
        return a & ((a - b) >> 31) | b & (~(a - b) >> 31);
    }

    /**
     * Max int.
     *
     * @param a the a
     * @param b the b
     * @return the int
     */
    static int max(int a, int b) {
        return b & ((a - b) >> 31) | a & (~(a - b) >> 31);
    }
}
