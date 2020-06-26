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
 * @created 24.06.2020 19:20
 * <p>
 * @author Alexander A. Kropotin
 */
public class RateTimer extends Thread {

    private long lastTime;
    private double fps;

    public void run(){
    }
    public double fps() {
        return fps;
    }
}
