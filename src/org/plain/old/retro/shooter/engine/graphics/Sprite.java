package org.plain.old.retro.shooter.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
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
            this.setPixels(this.width, this.height, pixels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sprite(String imageUri, double scaleWidth, double scaleHeight) {
        try {
            BufferedImage image = ImageIO.read(new File(imageUri));
            this.width = (int) (image.getWidth() * scaleWidth);
            this.height = (int) (image.getHeight() * scaleHeight);
            BufferedImage resized = new BufferedImage(this.width, this.height, image.getType());
            Graphics2D g = resized.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(image, 0, 0, this.width, this.height, 0, 0, image.getWidth(),
                    image.getHeight(), null);
            g.dispose();

            this.pixels = new int[this.width][this.height];
            int[] pixels = new int[this.width * this.height];
            resized.getRGB(
                    0,
                    0,
                    this.width,
                    this.height,
                    pixels,
                    0,
                    this.width
            );
            this.setPixels(this.width, this.height, pixels);
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

    private void setPixels(int width, int height, int[] pixels) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.pixels[x][y] = pixels[x + y * width];
            }
        }
    }
}
