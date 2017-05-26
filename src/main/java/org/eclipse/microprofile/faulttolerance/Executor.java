/*
 * Copyright (c) 2016,2017 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.microprofile.faulttolerance;

/**
 * Performs executions with failures handled according to a configured fault tolerance strategy.
 *
 * @author Jonathan Halterman
 * @author Emily Jiang
 */
public interface Executor {
    /**
     * Creates and returns a new SyncExecution instance that will perform executions and retries synchronously according
     * to the {@code retryPolicy}.
     *
     * @param <T> result type
     * @throws NullPointerException if {@code retryPolicy} is null
     */
    <T> SyncExecutor<T> with(RetryPolicy retryPolicy);

    /**
     * Creates and returns a new SyncExecution instance that will perform executions and retries synchronously according
     * to the {@code circuitBreaker}.
     *
     * @param <T> result type
     * @throws NullPointerException if {@code circuitBreaker} is null
     */
    <T> SyncExecutor<T> with(CircuitBreaker circuitBreaker);

    /**
     * Creates and returns a new SyncExecution instance that will perform executions within a defined bulkhead pattern.
     *
     * @param <T> result type
     * @throws NullPointerException if {@code Bulkhead} is null
     */
    <T> SyncExecutor<T> with(Bulkhead bulkhead);

}