package org.plain.old.retro.shooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    public int[] pixels;
    private String loc;
    public final int SIZE;

    public Texture(String location, int size) {
        loc = location;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Texture wood = new Texture("src/resources/wall/wood.png", 100);
    public static Texture brick = new Texture("src/resources/wall/redbrick.png", 100);
    public static Texture bluestone = new Texture("src/resources/wall/bluestone.png", 100);
    public static Texture stone = new Texture("src/resources/wall/greystone.png", 100);
}