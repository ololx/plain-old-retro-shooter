package org.plain.old.retro.shooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @project plain-old-retro-shooter
 * @created 21.07.2020 14:18
 * <p>
 * @author Alexander A. Kropotin
 */
public class Sprite {

    private int[][] pixels;

    private int width;

    private int height;

    public Sprite(String imageUri) {
        try {
            BufferedImage image = ImageIO.read(new File(imageUri));
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.pixels = new int[this.width][this.height];
            int[] pixels = new int[this.width * this.height];
            image.getRGB(
                    0,
                    0,
                    this.width,
                    this.height,
                    pixels,
                    0,
                    this.width);
            for (int x = 0; x < this.width; x++) {
                for (int y = 0; y < this.height; y++) {
                    this.pixels[x][y] = pixels[x + y * this.width];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPixel(int x, int y) {
        Objects.checkIndex(x, this.width);
        Objects.checkIndex(y, this.height);

        return this.pixels[x][y];
    }

    public int getPixelSafty(int x, int y) {
        if (x < 0) x = -x;
        if (x >= this.getWidth()) x = x % this.getWidth();
        if (y < 0) y = -y;
        if (y >= this.getHeight()) y = y % this.getHeight();


        return this.getPixel(x, y);
    }
}
