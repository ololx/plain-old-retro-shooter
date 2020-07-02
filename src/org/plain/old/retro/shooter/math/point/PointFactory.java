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
package org.plain.old.retro.shooter.math.point;

import java.util.stream.Stream;

/**
 * The type Point factory.
 *
 * @param <D> the type parameter
 * @author Alexander A. Kropotin
 * @project plain -old-retro-shooter
 * @created 02.07.2020 15:32 <p>
 */
public class PointFactory<D extends Number> {

    /**
     * Gets instance.
     *
     * @param scalarType     the scalar type
     * @param dimensionCount the dimension count
     * @return the instance
     */
    public Point<D>getInstance(Class<D> scalarType, int dimensionCount) {
        return new Point(
                (D[]) Stream.iterate(0, i -> i < dimensionCount, i -> i + 1)
                        .map(i -> Coordinate.ZERO)
                        .toArray()
        );
    }

    /**
     * Gets instance.
     *
     * @param coordinate the coordinate
     * @return the instance
     */
    public Point<D>getInstance(D... coordinate) {
        return new Point(coordinate);
    }
}
