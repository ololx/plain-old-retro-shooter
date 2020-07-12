package org.plain.old.retro.shooter.monitor;

import org.plain.old.retro.shooter.math.Vector2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        int offset = this.cellSize / 10;

        Vector2d sideRay = dir.rotate(30);
        double angleBetween = dir.getAngle(sideRay);
        List<Vector2d> rays = new ArrayList<>();
        for (double angle = -angleBetween; angle <= angleBetween; angle += angleBetween / 5) {
            Vector2d rayDir = dir.clone().rotate(angle);
            Vector2d ray = pos.clone();

            boolean hit = false;
            while (!hit) {
                Vector2d temp_ray = ray.clone().add(rayDir.multiply(0.1));

                if (map[(int) temp_ray.getX()][(int) temp_ray.getY()] != 0) hit = true;

                ray = temp_ray.clone();
            }

            rays.add(ray);
        }

        /*for(int i = pixels.length/2; i< pixels.length; i++){
            if(pixels[i] != Color.BLUE.getRGB()) pixels[i] = Color.BLUE.getRGB();
        }

        for(int i = 0; i< pixels.length/2; i++){
            if(pixels[i] != Color.GREEN.getRGB()) pixels[i] = Color.GREEN.getRGB();
        }

        for(int i = 0; i < rays.size(); i++){

            for (int k = i * width; k < width / rays.size(); k++) {

                double hight = rays.get(i).subtract(pos).getModule();
                for (int j = 0; j < height / hight; j++) {
                    pixels[i + j * width] = Color.RED.getRGB();
                }
            }
        }*/

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

                        for (Vector2d ray : rays) {

                            int xx3 = (int) ((ray.getX() * this.cellSize));
                            int yy3 = (int) ((ray.getY() * this.cellSize));

                            if (n >= xx3 - offset && n <= xx3 + offset
                                    && nn >= yy3 - offset && nn <= yy3 + offset) {
                                pixels[(n * width) + nn] = Color.CYAN.getRGB();
                            }
                        }
                    }
                }
            }
        }

        return pixels;
    }
}