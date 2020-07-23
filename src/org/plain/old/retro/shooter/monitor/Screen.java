package org.plain.old.retro.shooter.monitor;

import org.plain.old.retro.shooter.Sprite;
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
    private ArrayList<Sprite> textures;

    public Screen(int[][] map, int width, int height) {
        this.map = map;
        this.width = width;
        this.height = height;
    }

    public int[] renderFloor(int[] pixels) {
        for (int i = 0; i< pixels.length / 2; i++) {
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

    public int[] renderWall(int[] pixels, Vector2d pos, Vector2d dir) {
        Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN};

        double angleStep = 1.20 / width;
        double angle = 0.60;

        for (int x = 0; x < width; x++) {
            Vector2d rayDir = dir.rotate(angle);
            Vector2d rayPos = pos.clone();

            boolean hit = false;
            double steps = 0.01;
            while (!hit) {
                rayPos = rayPos.add(rayDir.multiply(steps));

                if (map[(int) rayPos.getX()][(int) rayPos.getY()] != 0) hit = true;

            }

            angle -= angleStep;

            double rayLength = Math.hypot(rayPos.getX() - pos.getX(), rayPos.getY() - pos.getY());
            int wallHeight = (rayLength == 0) ? height : (int) (height / (rayLength * Math.cos(angle)));
            //wallHeight = wallHeight > height ? height : wallHeight;

            int drawStart = (int) (-wallHeight / 2 + height / 2);
            if (drawStart < 0) drawStart = 0;

            int drawEnd = (int) (wallHeight / 2 + height / 2);
            if (drawEnd >= height) drawEnd = height;


            int texNum = map[(int) rayPos.getX()][(int) rayPos.getY()] - 1;

            double intersectionX = pos.getX() < rayPos.getX() ? Math.abs(rayPos.getX() - (int) rayPos.getX()) : 1 - Math.abs(rayPos.getX() - (int) rayPos.getX());//Math.round((pos.getX() < rayPos.getX() ? Math.abs(rayPos.getX() - (int) rayPos.getX()) : 1 - Math.abs(rayPos.getX() - (int) rayPos.getX())) * 100.0) / 100.0;
            double intersectionY = pos.getY() < rayPos.getY() ? Math.abs(rayPos.getY() - (int) rayPos.getY()) : 1 - Math.abs(rayPos.getY() - (int) rayPos.getY());//Math.round((pos.getY() < rayPos.getY() ? Math.abs(rayPos.getY() - (int) rayPos.getY()) : 1 - Math.abs(rayPos.getY() - (int) rayPos.getY())) * 100.0) / 100.0;
            boolean horizontal = intersectionX < intersectionY ? true : false;
            double wallX = horizontal ? intersectionY  : intersectionX;
            wallX -= Math.floor(wallX);

            //int texX = (int)(wallX * (textures.get(texNum).getWidth()));
            //if (!horizontal && rayDir.getX() > 0) texX = (textures.get(texNum).getWidth()) - texX - 1;
            //if (horizontal && rayDir.getY() < 0) texX = (textures.get(texNum).getWidth()) - texX - 1;

            int drawLimit = drawEnd - drawStart;
            //if (angle >= 0.4 && angle <= 0.6)
                //System.out.printf("W : " + drawLimit + "    H : " + height + "    R : " + rayLength + "    WX : " + (int) ((360 - drawStart) * 1.0 * textures.get(texNum).getHeight() / wallHeight) + "\r");

            for (int y = drawStart; y < drawEnd; y++) {
                //int texY = (int) ((y - drawStart) * 1.0 * textures.get(texNum).getHeight() / wallHeight);
                int color = colors[texNum].getRGB();
                //color = textures.get(texNum).getPixel(texX, texY);
                pixels[x + y * width] = color;
            }
        }

        return pixels;
    }

    public int[] renderGun(int[] pixels, Sprite gun) {
        int xStart = width / 2 - gun.getWidth() / 2;
        int yStart = height - gun.getHeight();

        for (int x = 0; x < gun.getWidth(); x++) {
            for (int y = 0; y < gun.getHeight(); y++) {
                int pixelColor = gun.getPixel(x, y);
                if (pixelColor != 0) pixels[(x + xStart) + (y + yStart) * width] = gun.getPixel(x, y);
            }
        }

        return pixels;
    }

    public int[] render(int[] pixels, Vector2d pos, Vector2d dir, Vector2d plain, Sprite gun) {

        pixels = this.renderFloor(pixels);
        pixels = this.renderCeiling(pixels);
        pixels = this.renderWall(pixels, pos, dir);
        pixels = this.renderGun(pixels, gun);

        return pixels;
    }
}