package org.plain.old.retro.shooter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @project plain-old-retro-shooter
 * @created 21.07.2020 13:41
 * <p>
 * @author Alexander A. Kropotin
 */
public class BoomStick {

    private List<Sprite> sprites;

    private boolean isActive;

    {
        this.sprites = new ArrayList<>();
        this.isActive = false;
    }

    public BoomStick(Sprite ... sprites) {
        this.sprites.addAll(Arrays.asList(sprites).stream().collect(Collectors.toList()));
    }

    public Sprite getSprite() {
        return this.isActive ? sprites.get(1) : sprites.get(0);
    }

    public void setActive() {
        this.isActive = true;
    }

    public void resetActive() {
        this.isActive = false;
    }
}
