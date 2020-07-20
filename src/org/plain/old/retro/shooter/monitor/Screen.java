package org.plain.old.retro.shooter.monitor;

import org.plain.old.retro.shooter.Texture;
import org.plain.old.retro.shooter.linear.Vector2d;

import java.awt.*;
import java.util.ArrayList;

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
    private ArrayList<Texture> textures;

    public Screen(int[][] map, int width, int height, ArrayList<Texture> textures) {
        this.map = map;
        this.width = width;
        this.height = height;
        this.textures = textures;
    }

    public int[] renderFloor(int[] pixels) {
        for (int i = 0; i< pixels.length / 2; i++){
            pixels[i] = Color.LIGHT_GRAY.getRGB();
        }

        return pixels;
    }

    public int[] renderCeiling(int[] pixels) {
        for (int i = pixels.length / 2; i< pixels.length; i++){
            pixels[i] = Color.DARK_GRAY.getRGB();
        }

        return pixels;
    }

    public int[] renderWall(int[] pixels, Vector2d pos, Vector2d dir, Vector2d plain) {
        Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN};

        double angleStep = 1.20 / width;
        double angle = 0.60;

        for (int x = 0; x < width; x++) {
            Vector2d rayDir = dir.rotate(angle);
            Vector2d rayPos = pos.clone();
            double offsetLength = Math.sqrt(
                    Math.pow(rayPos.getX() - (int)rayPos.getX(), 2)
                            + Math.pow(rayPos.getY() - (int)rayPos.getY(), 2)
            );

            boolean hit = false;
            double steps = 0.01;
            while (!hit) {
                rayPos = rayPos.add(rayDir.multiply(steps));

                if (map[(int) rayPos.getX()][(int) rayPos.getY()] != 0) hit = true;
            }

            angle -= angleStep;

            double rayLength = Math.sqrt(Math.pow(rayPos.getX() - pos.getX(), 2) + Math.pow(rayPos.getY() - pos.getY(), 2));

            int wallHeight = (rayLength == 0) ? height : (int) (height / (rayLength * Math.cos(angle)));

            int drawStart = (int) (-wallHeight / 2 + height / 2);
            if (drawStart < 0) drawStart = 0;

            int drawEnd = (int) (wallHeight / 2 + height / 2);
            if (drawEnd >= height) drawEnd = height;


            int texNum = map[(int) rayPos.getX()][(int) rayPos.getY()] - 1;

            double intersectionX = pos.getX() < rayPos.getX() ? Math.abs(rayPos.getX() - (int) rayPos.getX()) : 1 - Math.abs(rayPos.getX() - (int) rayPos.getX());//Math.round((pos.getX() < rayPos.getX() ? Math.abs(rayPos.getX() - (int) rayPos.getX()) : 1 - Math.abs(rayPos.getX() - (int) rayPos.getX())) * 100.0) / 100.0;
            double intersectionY = pos.getY() < rayPos.getY() ? Math.abs(rayPos.getY() - (int) rayPos.getY()) : 1 - Math.abs(rayPos.getY() - (int) rayPos.getY());//Math.round((pos.getY() < rayPos.getY() ? Math.abs(rayPos.getY() - (int) rayPos.getY()) : 1 - Math.abs(rayPos.getY() - (int) rayPos.getY())) * 100.0) / 100.0;
            boolean horizontal = intersectionX < intersectionY && intersectionX <= 0.1
                    ? true
                    : false;
            double wallX = horizontal ? intersectionY  : intersectionX;
            wallX -= Math.floor(wallX);

            /*if (angle == 0.60 - angleStep) {
                System.out.printf(
                        "rayPos.getX(): %s; (int) rayPos.getX(): %s; intersectionX: %s;   rayPos.getY(): %s; (int) rayPos.getY(): %s; intersectionY: %s\r",
                        rayPos.getX(),
                        (int) rayPos.getX(),
                        intersectionX,
                        rayPos.getY(),
                        (int) rayPos.getY(),
                        intersectionY
                );
            }*/

            int texX = (int)(wallX * (textures.get(texNum).SIZE));
            if (!horizontal && rayDir.getX() >= 0) texX = (textures.get(texNum).SIZE) - texX - 1;
            if (horizontal && rayDir.getY() <= 0) texX = (textures.get(texNum).SIZE) - texX - 1;

            for (int y = drawStart; y < drawEnd; y++) {
                int texY = (((y * 2 - height + wallHeight) << 6) / wallHeight) / 2;
                int color;
                color = textures.get(texNum).pixels[(int) (texX + (texY * textures.get(texNum).SIZE))];
                //if(!horizontal) color = (color >> 1) & 8355711;
                pixels[x + y * width] = color;
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