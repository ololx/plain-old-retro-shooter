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
import org.plain.old.retro.shooter.math.Vector2d;

/**
 * The type Game.
 *
 * @author Alexander A. Kropotin This is a main game class, which is runnable and will manage all game logics (such as fps, screen update && e.t.c.)
 * @project plain -old-retro-shooter
 * @created 19.06.2020 08:58 <p>
 */
public class Game {

    private RateTimer gameLoop;

    /**
     * Instantiates a new Game.
     */
    public Game() {
        Vector2d OM = new Vector2d(4.5, 4.5);
        this.gameLoop = new RateTimer(
                120,
                () -> System.out.println(gameLoop.getHerz()),
                () -> System.out.println(OM.length())
        );
    }

    /**
     * Init.
     */
    public void init() {
        this.gameLoop.start();
    }
}
