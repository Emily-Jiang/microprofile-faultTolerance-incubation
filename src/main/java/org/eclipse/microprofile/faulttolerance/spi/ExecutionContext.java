/*
 *******************************************************************************
 * Copyright (c) 2016 IBM Corp. and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *******************************************************************************/

package org.eclipse.microprofile.faulttolerance.spi;

import java.time.Duration;

/**
 * Contextual execution information.
 *
 * @author Jonathan Halterman
 */
public class ExecutionContext {
    final Duration startTime;
    /** Number of execution attempts */
    volatile int executions;

    ExecutionContext(Duration startTime) {
        this.startTime = startTime;
    }

    ExecutionContext(ExecutionContext context) {
        this.startTime = context.startTime;
        this.executions = context.executions;
    }

    /**
     * Returns the elapsed time since initial execution began.
     */
    public Duration getElapsedTime() {
        return Duration.ofNanos(System.nanoTime() - startTime.toNanos());
    }

    /**
     * Gets the number of executions so far.
     */
    public int getExecutions() {
        return executions;
    }

    /**
     * Returns the time that the initial execution started.
     */
    public Duration getStartTime() {
        return startTime;
    }

    ExecutionContext copy() {
        return new ExecutionContext(this);
    }
}
