package org.plain.old.retro.shooter.monitor;

import org.plain.old.retro.shooter.linear.Vector2d;

import java.awt.*;
import java.util.Vector;

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
        for (int x = 0; x < width; x++) {
            Vector2d rayVector = dir.add(plain.multiply(2 * x / (double)(width) - 1));
            double deltaDistX = Math.abs(rayVector.getModule() / rayVector.getX());
            double deltaDistY = Math.abs(rayVector.getModule() / rayVector.getY());


            int currentPosOnX = (int)pos.getX();
            int currentPosOnY = (int)pos.getY();
            double sideDistX;
            double sideDistY;
            int stepX, stepY;
            if (rayVector.getX() < 0) {
                stepX = -1;
                sideDistX = (pos.getX() - currentPosOnX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (currentPosOnX + 1.0 - pos.getX()) * deltaDistX;
            }

            if (rayVector.getY() < 0) {
                stepY = -1;
                sideDistY = (pos.getY() - currentPosOnY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (currentPosOnY + 1.0 - pos.getY()) * deltaDistY;
            }

            boolean perpendicular = false;
            boolean hit = false;
            while (!hit) {

                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    currentPosOnX += stepX;
                    perpendicular = false;
                } else {
                    sideDistY += deltaDistY;
                    currentPosOnY += stepY;
                    perpendicular = true;
                }

                if(map[currentPosOnX][currentPosOnY] != 0) hit = true;
            }

            Vector2d wallDistancePerpendicular = new Vector2d(
                    (currentPosOnX - pos.getX() + (1 - stepX) / 2),
                    (currentPosOnY - pos.getY() + (1 - stepY) / 2)
            );
            double perpWallDist = perpendicular
                    ? Math.abs(wallDistancePerpendicular.getY() / rayVector.getY())
                    : Math.abs(wallDistancePerpendicular.getX() / rayVector.getX());

            int wallHeight;
            if (perpWallDist > 0) wallHeight = Math.abs((int)(height / perpWallDist));
            else wallHeight = height;

            int drawStart = -wallHeight / 2 + height / 2;
            if (drawStart < 0) drawStart = 0;

            int drawEnd = wallHeight / 2 + height / 2;
            if (drawEnd >= height) drawEnd = height;

            for (int y = drawStart; y < drawEnd; y++) {
                pixels[x + y * (width)] = Color.darkGray.getRGB();
            }
        }

        return pixels;
    }

    public int[] renderWall2(int[] pixels, Vector2d pos, Vector2d dir, Vector2d plain) {
        double angleStep = (Math.PI / 3.0) / (width - 1);
        double angle = (Math.PI / 3.0);

        for (int x = 0; x < width; x++) {
            Vector2d rayDir = dir.clone().rotate(angle);
            Vector2d rayPos = pos.clone();

            boolean hit = false;
            while (!hit) {
                Vector2d temp_ray = rayPos.clone().add(rayDir.multiply(0.01));

                if (map[(int) temp_ray.getX()][(int) temp_ray.getY()] != 0) hit = true;

                rayPos = temp_ray.clone();

            }

            angle -= angleStep;

            int wallHeight;
            double rayLength;

            if (rayPos.getModule() > pos.getModule()) rayLength = rayPos.getModule() - pos.getModule();
            else rayLength = pos.getModule() - rayPos.getModule();

            if (rayLength > 0) wallHeight = Math.abs((int)(height / rayLength));
            else wallHeight = height;

            int drawStart = -wallHeight / 2 + height / 2;
            if (drawStart < 0) drawStart = 0;

            int drawEnd = wallHeight / 2 + height / 2;
            if (drawEnd >= height) drawEnd = height;

            for (int y = drawStart; y < drawEnd; y++) {
                pixels[x + y * (width)] = Color.CYAN.getRGB();
            }
        }

        return pixels;
    }

    public int[] render(int[] pixels, Vector2d pos, Vector2d dir, Vector2d plain) {

        pixels = this.renderFloor(pixels);
        pixels = this.renderCeiling(pixels);
        //pixels = this.renderWall(pixels, pos, dir, plain);
        pixels = this.renderWall2(pixels, pos, dir, plain);

        return pixels;
    }
}