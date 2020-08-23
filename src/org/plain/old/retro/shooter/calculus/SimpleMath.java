package org.plain.old.retro.shooter.calculus;

/**
 * @project plain-old-retro-shooter
 * @created 23.08.2020 11:51
 * <p>
 * @author Alexander A. Kropotin
 */
public interface SimpleMath {

    static int min(int a, int b) {
        return a & ((a - b) >> 31) | b & (~(a - b) >> 31);
    }

    static int max(int a, int b) {
        return b & ((a - b) >> 31) | a & (~(a - b) >> 31);
    }
}
