/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2018, Google and/or its affiliates. All rights reserved.
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

package MyPackage;

/**
 * @test
 * @summary Verifies the JVMTI Heap Monitor API
 * @requires vm.jvmti
 * @build Frame HeapMonitor
 * @compile HeapMonitorTest.java
 * @run main/othervm/native -agentlib:HeapMonitorTest MyPackage.HeapMonitorTest
 */

public class HeapMonitorTest {

  private static native boolean framesAreNotLive(Frame[] frames);

  public static void main(String[] args) {
    if (args.length > 0) {
      // For interpreter mode, have a means to reduce the default iteration count.
      HeapMonitor.setAllocationIterations(Integer.parseInt(args[0]));
    }

    HeapMonitor.allocateAndCheckFrames();

    HeapMonitor.disableSamplingEvents();
    HeapMonitor.resetEventStorage();
    if (!HeapMonitor.eventStorageIsEmpty()) {
      throw new RuntimeException("Storage is not empty after reset.");
    }

    HeapMonitor.allocate();
    if (!HeapMonitor.eventStorageIsEmpty()) {
      throw new RuntimeException("Storage is not empty after allocation while disabled.");
    }
  }
}
