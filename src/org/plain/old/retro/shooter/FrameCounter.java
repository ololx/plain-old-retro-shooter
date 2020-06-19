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
 * @created 19.06.2020 09:16
 * <p>
 * @author Alexander A. Kropotin
 */
public class FrameCounter {

    public final static double NANO_SEC_IN_SEC = 1000000000.0;

    public final static double DEFAULT_FRAME_RATE = 120.0;

    private double frameRate;

    private long previousMoment;

    public FrameCounter() {
        this(DEFAULT_FRAME_RATE);
    }

    public FrameCounter(double frameRate) {
        this.frameRate = frameRate;
        this.previousMoment = System.nanoTime();
    }

    public void process() {
        double sleepMoment = this.getSleep();

        while (sleepMoment >= 1) {
            sleepMoment--;
        }
    }

    private double getSleep() {
        long currentMoment = System.nanoTime();
        double sleepMoment = ((currentMoment - previousMoment) / (NANO_SEC_IN_SEC / this.frameRate));
        previousMoment = currentMoment;

        return sleepMoment;
    }
}
