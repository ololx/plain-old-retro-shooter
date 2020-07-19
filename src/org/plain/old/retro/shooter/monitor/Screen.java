package org.plain.old.retro.shooter.monitor;

import org.plain.old.retro.shooter.Texture;
import org.plain.old.retro.shooter.linear.Vector2d;

import java.awt.*;
import java.util.ArrayList;
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
            Vector2d temp_ray = rayPos.clone();

            boolean hit = false;
            do {

                temp_ray = temp_ray.add(rayDir.multiply(0.01));

                if (map[(int) temp_ray.getX()][(int) temp_ray.getY()] != 0) hit = true;

                rayPos = temp_ray.clone();

            } while (!hit);

            angle -= angleStep;

            double rayLength = Math.sqrt(Math.pow(rayPos.getX() - pos.getX(), 2) + Math.pow(rayPos.getY() - pos.getY(), 2));

            int wallHeight = (rayLength == 0) ? height : (int) (height / (rayLength * Math.cos(angle)));

            int drawStart = (int) (-wallHeight / 2 + height / 2);
            if (drawStart < 0) drawStart = 0;

            int drawEnd = (int) (wallHeight / 2 + height / 2);
            if (drawEnd >= height) drawEnd = height;

            /*for (int y = drawStart; y < drawEnd; y++) {
                pixels[x + y * (width)] = colors[map[(int) rayPos.getX()][(int) rayPos.getY()] - 1].getRGB();
            }*/

            int texNum = map[(int) rayPos.getX()][(int) rayPos.getY()] - 1;

            double rayX = Math.floor(rayPos.getX());
            double rayY = Math.floor(rayPos.getY());
            boolean perpendicular = rayPos.getX() % rayX == 0 ? true : false;

            double wallX = 0;
            if (perpendicular) {
                wallX = rayPos.getX() > rayX ? rayPos.getX() - rayX : rayPos.getX() - (rayX + 1) ;
            } else {
                wallX = rayPos.getY() > rayY ? rayPos.getY() - rayY : rayPos.getY() - (rayY + 1);
            }

            //wallX *= 10;
            wallX -= Math.floor(wallX);

            int texX = (int)(wallX * (textures.get(texNum).SIZE));
            for(int y=drawStart; y<drawEnd; y++) {
                int texY = (((y * 2 - height + wallHeight) << 6) / wallHeight) / 2;
                int color;
                color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).SIZE)];
                pixels[x + y*(width)] = color;
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