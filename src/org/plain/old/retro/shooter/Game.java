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

/**
 * @project plain-old-retro-shooter
 * @created 19.06.2020 08:58
 * <p>
 * @author Alexander A. Kropotin
 *
 * This is a main game class, which is runnable and will manage
 * all game logics (such as fps, screen update && e.t.c.)
 */
public class Game implements Runnable {

    private Thread thread;

    private boolean running;

    private FrameCounter frameCounter;

    public Game() {
        thread = new Thread(this);
        this.frameCounter = new FrameCounter(60.0);
    }

    @Override
    public void run() {
        long previousMoment = System.nanoTime();

        while (running) {
            long currentMoment = System.nanoTime();
            double frameTime = (currentMoment - previousMoment);

            previousMoment = currentMoment;
            System.out.println("FPS : " + 1.0 / frameTime);
            frameCounter.process();
        }
    }

    public synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;

        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
