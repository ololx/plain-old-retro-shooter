package org.plain.old.retro.shooter.monitor;

import java.awt.*;

public class Screen {

    private int[][] map;
    private int cellSize;

    public Screen(int[][] map, int cellSize) {
        this.map = map;
        this.cellSize = cellSize;

    }

    public int[] update(int[] pixels, double x, double y, double x2, double y2) {
        int p  = this.map.length;
        int q  = this.map[0].length;

        int width = p * this.cellSize;
        int height = q * this.cellSize;

        int xx = (int) ((x * this.cellSize));
        int yy = (int) ((y * this.cellSize));

        int xx2 = (int) ((x2 * this.cellSize));
        int yy2 = (int) ((y2 * this.cellSize));
        int offset = this.cellSize / 5;

        for (int i = 0; i < p; i++) {
            for (int j = 0; j < q; j++) {
                int cl = map[i][j] == 0 ? Color.BLUE.getRGB() : Color.GREEN.getRGB();
                for (int n = i * this.cellSize; n < (1 + i) * this.cellSize; n++) {
                    for (int nn = j * this.cellSize; nn < (1 + j) * this.cellSize; nn++) {
                        pixels[(n * width) + nn] = cl;

                        if (n >= xx - offset && n <= xx + offset
                            && nn >= yy - offset && nn <= yy + offset) {
                            pixels[(n * width) + nn] = Color.RED.getRGB();
                        }

                        if (n >= (xx2 + xx) - offset && n <= (xx2 + xx) + offset
                                && nn >= (yy2 + yy) - offset && nn <= (yy2 + yy) + offset) {
                            pixels[(n * width) + nn] = Color.RED.getRGB();
                        }
                    }
                }
            }
        }

        return pixels;
    }
}