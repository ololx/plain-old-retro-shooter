package org.plain.old.retro.shooter.monitor;

import org.plain.old.retro.shooter.math.Vector2d;

import java.awt.*;

public class Screen {

    private int[][] map;
    private int cellSize;

    public Screen(int[][] map, int cellSize) {
        this.map = map;
        this.cellSize = cellSize;

    }

    public int[] update(int[] pixels, Vector2d pos, Vector2d dir) {
        int p  = this.map.length;
        int q  = this.map[0].length;

        int width = p * this.cellSize;
        int height = q * this.cellSize;

        int xx = (int) ((pos.getX() * this.cellSize));
        int yy = (int) ((pos.getY() * this.cellSize));

        int xx2 = (int) ((dir.getX() / 4 * this.cellSize));
        int yy2 = (int) ((dir.getY() / 4 * this.cellSize));
        int offset = this.cellSize / 10;

        Vector2d ray = pos.clone();

        boolean hit = false;
        while (!hit) {
            Vector2d temp_ray = ray.clone().add(dir.multiply(0.01));

            if (map[(int) temp_ray.getX()][(int) temp_ray.getY()] != 0) hit = true;

            ray = temp_ray.clone();
        }

        int xx3 = (int) ((ray.getX() * this.cellSize));
        int yy3 = (int) ((ray.getY() * this.cellSize));

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

                        if (n >= xx3 - offset && n <= xx3 + offset
                                && nn >= yy3 - offset && nn <= yy3 + offset) {
                            pixels[(n * width) + nn] = Color.CYAN.getRGB();
                        }
                    }
                }
            }
        }

        return pixels;
    }
}