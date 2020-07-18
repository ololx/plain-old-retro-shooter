package org.plain.old.retro.shooter.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @project plain-old-retro-shooter
 * @created 05.07.2020 08:37
 * <p>
 * @author Alexander A. Kropotin
 */
public class KeyboardController implements KeyListener {

    private final Map<Integer, String> keys;

    private Map<String, Boolean> state;

    public KeyboardController() {
        this(null);
    }

    public KeyboardController(Map<Integer, String> keys) {
        this.keys = keys == null ? Collections.EMPTY_MAP : keys;
        state = keys.entrySet().stream()
                .collect(Collectors.toMap(
                        key -> key.getValue(),
                        key -> false
                ));
    }

    @Override
    public void keyPressed(KeyEvent key) {
        if (this.keys.containsKey(key.getKeyCode())) this.state.put(this.keys.get(key.getKeyCode()), true);
    }

    @Override
    public void keyReleased(KeyEvent key) {
        if (this.keys.containsKey(key.getKeyCode())) this.state.put(this.keys.get(key.getKeyCode()), false);
    }

    @Override
    public void keyTyped(KeyEvent key) {
        this.keyPressed(key);
        this.keyReleased(key);
    }

    public Map<String, Boolean> getState() {
        return this.state;
    }
}