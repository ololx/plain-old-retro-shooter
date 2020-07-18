package org.plain.old.retro.shooter.monitor;

import org.plain.old.retro.shooter.linear.Vector2d;

import java.awt.*;
import java.util.Map;
import java.util.Vector;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class Screen {

    private int[][] map;
    private int width;
    private int height;

    public Screen(int[][] map, int width, int height) {
        this.map = map;
        this.width = width;
        this.height = height;

    }

    public int[] renderFloor(int[] pixels) {
        for (int i = 0; i< pixels.length / 2; i++){
            pixels[i] = Color.BLUE.getRGB();
        }

        return pixels;
    }

    public int[] renderCeiling(int[] pixels) {
        for (int i = pixels.length / 2; i< pixels.length; i++){
            pixels[i] = Color.GREEN.getRGB();
        }

        return pixels;
    }

    public int[] renderWall(int[] pixels, Vector2d pos, Vector2d dir, Vector2d plain) {
        Color[] colors = new Color[]{Color.darkGray, Color.YELLOW, Color.CYAN, Color.RED};

        double angleStep = 1.20 / width;
        double angle = 0.60;

        for (int x = 0; x < width; x++) {
            Vector2d rayDir = dir.rotate(angle);
            Vector2d rayPos = pos.clone();

            Color color = colors[0];
            Vector2d temp_ray = rayPos.clone();

            boolean hit = false;
            while (!hit) {

                temp_ray = temp_ray.add(rayDir.multiply(0.01));

                if (map[(int) temp_ray.getX()][(int) temp_ray.getY()] != 0) {
                    hit = true;
                    color = colors[map[(int) temp_ray.getX()][(int) temp_ray.getY()] - 1];
                }

                rayPos = temp_ray.clone();

            }

            angle -= angleStep;

            double rayLength = Math.sqrt(Math.pow(rayPos.getX() - pos.getX(), 2) + Math.pow(rayPos.getY() - pos.getY(), 2));

            int wallHeight = (rayLength == 0) ? height : (int) (height / (rayLength * Math.cos(angle)));

            int drawStart = (int) (-wallHeight / 2 + height / 2);
            if (drawStart < 0) drawStart = 0;

            int drawEnd = (int) (wallHeight / 2 + height / 2);
            if (drawEnd >= height) drawEnd = height;

            for (int y = drawStart; y < drawEnd; y++) {
                pixels[x + y * (width)] = color.getRGB();
            }
        }

        return pixels;
    }

    public int[] render(int[] pixels, Vector2d pos, Vector2d dir, Vector2d plain) {

        pixels = this.renderFloor(pixels);
        pixels = this.renderCeiling(pixels);
        pixels = this.renderWall(pixels, pos, dir, plain);

        return pixels;
    }
}