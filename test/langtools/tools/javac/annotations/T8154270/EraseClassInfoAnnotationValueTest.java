/*
 * Copyright (c) 2016 Google Inc. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/*
 * @test
 * @bug 8154270
 * @summary javac wrongly rejects some class literals as annotation element values
 * @compile EraseClassInfoAnnotationValueTest.java
 * @compile -implicit:none Other.java
 * @run main EraseClassInfoAnnotationValueTest
 */
public class EraseClassInfoAnnotationValueTest {

  @Retention(RetentionPolicy.RUNTIME)
  public @interface A {
    Class<?> value();
  }

  static class ParametricType<T> {

    @A(Inner.class)
    public static class Nested {}

    public class Inner {}
  }

  public static void main(String[] args) {
    Class<?> clazz = ParametricType.Nested.class.getAnnotation(A.class).value();
    if (!clazz.equals(ParametricType.Inner.class)) {
      throw new AssertionError(clazz);
    }
  }
}
