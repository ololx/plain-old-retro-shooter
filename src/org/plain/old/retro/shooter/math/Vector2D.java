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
package org.plain.old.retro.shooter.math;

import org.plain.old.retro.shooter.math.point.Coordinate2D;
import org.plain.old.retro.shooter.math.point.Point2D;

/**
 * @project plain-old-retro-shooter
 * @created 03.07.2020 18:06
 * <p>
 * @author Alexander A. Kropotin
 */
public class Vector2D<P extends Point2D> extends AbstractVector<P> {

    /**
     * Instantiates a new Abstract vector.
     *
     * @param b the b
     */
    public Vector2D(P b) {
        super((P) new Point2D(), b);
    }

    /**
     * Instantiates a new Abstract vector.
     *
     * @param a the a
     * @param b the b
     */
    public Vector2D(P a, P b) {
        super(a, b);
    }

    void rotate(double angle) {
        P oldB = (P) b.clone();

        /*this.setB(
                (P) new Point2D(
                        oldB.getX() * Math.cos(angle) - oldB.getY() * Math.sin(angle),
                        oldB.getY() * Math.sin(angle) - oldB.getX() * Math.cos(angle)
                )
        );*/
    }
}
