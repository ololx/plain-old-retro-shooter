/**
 * Copyright 2020 the project plain-old-retro-shooter authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.plain.old.retro.shooter;

import org.plain.old.retro.shooter.clock.RateTimer;
import org.plain.old.retro.shooter.listener.KeyboardController;
import org.plain.old.retro.shooter.math.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * The type Game.
 * This is a main game class, which is runnable and
 * will manage all game logics (such as fps, screen update && e.t.c.)
 *
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 19.06.2020 08:58 <p>
 */
public class Game extends JFrame {

    private RateTimer gameLoop;

    /**
     * Instantiates a new Game.
     */
    public Game() {
        Vector2d OM = new Vector2d(4.5, 4.5);
        KeyboardController controller = new KeyboardController(new HashMap<Integer, String>(){{
            put(KeyEvent.VK_W, "MV_FWD");
            put(KeyEvent.VK_S, "MV_BWD");
        }});
        this.gameLoop = new RateTimer(
                120,
                () -> System.out.printf("FPS: %s; KEYS: %s \r", gameLoop.getHerz(), controller.getState())
        );
        addKeyListener(controller);
    }

    /**
     * Init.
     */
    public void init() {
        setSize(1000, 1000);
        setResizable(true);
        setTitle("3D Fuck Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);

        this.gameLoop.start();
    }
}
