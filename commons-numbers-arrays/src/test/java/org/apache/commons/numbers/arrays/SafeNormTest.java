/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.numbers.arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test cases for the {@link SafeNorm} class.
 */
class SafeNormTest {

    @Test
    void testTiny() {
        final double s = 1e-320;
        final double[] v = new double[] {s, s};
        Assertions.assertEquals(Math.sqrt(2) * s, SafeNorm.value(v));
    }

    @Test
    void testBig() {
        final double s = 1e300;
        final double[] v = new double[] {s, s};
        Assertions.assertEquals(Math.sqrt(2) * s, SafeNorm.value(v));
    }

    @Test
    void testOne3D() {
        final double s = 1;
        final double[] v = new double[] {s, s, s};
        Assertions.assertEquals(Math.sqrt(3), SafeNorm.value(v));
    }

    @Test
    void testUnit3D() {
        Assertions.assertEquals(1, SafeNorm.value(new double[] {1, 0, 0}));
        Assertions.assertEquals(1, SafeNorm.value(new double[] {0, 1, 0}));
        Assertions.assertEquals(1, SafeNorm.value(new double[] {0, 0, 1}));
    }

    @Test
    void testSimple() {
        final double[] v = new double[] {-0.9, 8.7, -6.5, -4.3, -2.1, 0, 1.2, 3.4, -5.6, 7.8, 9.0};
        final double expected = direct(v);
        Assertions.assertEquals(expected, SafeNorm.value(v));
    }

    @Test
    void testZero() {
        final double[] v = new double[] {0, 0, 0, 0, 0};
        Assertions.assertEquals(0d, SafeNorm.value(v));
    }

    @Test
    void testTinyAndSmallNormal() {
        // Ensure the sum of the squared values for 'normal' values (1e-19*1e-19)
        // is less than largest tiny value (1e-20)
        final double[] v = new double[] {1e-20, 1e-19};
        Assertions.assertEquals(Math.sqrt(101) * 1e-20, SafeNorm.value(v));
    }

    /**
     * Direct computation.
     *
     * @param v Array.
     * @return the norm using direct summation.
     */
    private double direct(double[] v) {
        double n = 0;
        for (int i = 0; i < v.length; i++) {
            n += v[i] * v[i];
        }
        return Math.sqrt(n);
    }
}
