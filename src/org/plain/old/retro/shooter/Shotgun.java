package org.plain.old.retro.shooter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @project plain-old-retro-shooter
 * @created 19.07.2020 09:02
 * <p>
 * @author Alexander A. Kropotin
 */
public class Shotgun {

    BufferedImage image = null;

    public Shotgun(String imageUri) {
        BufferedImage img = null;
        try {
            this.image = ImageIO.read(new File(imageUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public int getWidth() {
        return this.image.getWidth();
    }

    public int getHeight() {
        return this.image.getHeight();
    }
}
