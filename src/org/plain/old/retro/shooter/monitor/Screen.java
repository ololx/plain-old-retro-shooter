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

    public int[] render(int[] pixels, Vector2d pos, Vector2d dir, Vector2d plain) {
        int p  = this.map.length;
        int q  = this.map[0].length;

        int width = p * this.cellSize;
        int height = q * this.cellSize;

        for(int i = pixels.length / 2; i< pixels.length; i++){
            if(pixels[i] != Color.BLUE.getRGB()) pixels[i] = Color.BLUE.getRGB();
        }

        for(int i = 0; i< pixels.length / 2; i++){
            if(pixels[i] != Color.GREEN.getRGB()) pixels[i] = Color.GREEN.getRGB();
        }

        int pix = this.cellSize / 5;

        for(int x = 0; x < width; x+= pix) {

            double cameraX = 2.5 * x / (double)(width) -1;
            Vector2d rayVector = dir.add(plain.multiply(cameraX));

            int currentPosOnX = (int)pos.getX();
            int currentPosOnY = (int)pos.getY();

            double sideDistX;
            double sideDistY;

            double deltaDistX = Math.sqrt(1 + (rayVector.getY()*rayVector.getY()) / (rayVector.getX()*rayVector.getX()));
            double deltaDistY = Math.sqrt(1 + (rayVector.getX()*rayVector.getX()) / (rayVector.getY()*rayVector.getY()));
            double perpWallDist;

            int stepX, stepY;
            boolean hit = false;
            int side = 0;

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

            while (!hit) {

                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    currentPosOnX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    currentPosOnY += stepY;
                    side = 1;
                }

                if(map[currentPosOnX][currentPosOnY] > 0) hit = true;
            }

            if (side == 0) perpWallDist = Math.abs((currentPosOnX - pos.getX() + (1 - stepX) / 2) / rayVector.getX());
            else perpWallDist = Math.abs((currentPosOnY - pos.getY() + (1 - stepY) / 2) / rayVector.getY());

            int wallHeight;
            if (perpWallDist > 0) wallHeight = Math.abs((int)(height / perpWallDist));
            else wallHeight = height;

            int drawStart = -wallHeight / 2+ height / 2;
            if (drawStart < 0) drawStart = 0;

            int drawEnd = wallHeight / 2 + height / 2;
            if (drawEnd >= height) drawEnd = height - 1;

            for (int y = drawStart; y < drawEnd; y++) {
                for (int k = 0; k < pix; k++) {
                    pixels[k + x + y * (width)] = Color.darkGray.getRGB();
                }
            }
        }
        return pixels;
    }

    private Color adjustColor(Color base, double p) {
        p = p > 1 ? 1 : p;
        return Color.getHSBColor((int)(255*base.getRed()*(1-p)), (int)(255*base.getGreen()*(1-p)), (int)(255*base.getBlue()*(1-p)));
    }
}