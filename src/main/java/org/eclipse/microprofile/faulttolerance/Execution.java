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

package org.eclipse.microprofile.faulttolerance;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Simple, sophisticated failure handling.
 *
 * @author Jonathan Halterman
 * @author Emily Jiang
 */
public interface Execution<R, T> extends ExecutionConfig<R, T> {

    /**
     * Records an execution and returns true if a retry can be performed for the
     * {@code result}, else returns false and marks the execution as complete.
     *
     * @throws IllegalStateException
     *             if the execution is already complete
     */
    public boolean canRetryFor(Object result);

    /**
     * Records an execution and returns true if a retry can be performed for the
     * {@code result} or {@code failure}, else returns false and marks the
     * execution as complete.
     *
     * @throws IllegalStateException
     *             if the execution is already complete
     */
    public boolean canRetryFor(Object result, Throwable failure);

    /**
     * Records an execution and returns true if a retry can be performed for the
     * {@code failure}, else returns false and marks the execution as complete.
     *
     * @throws NullPointerException
     *             if {@code failure} is null
     * @throws IllegalStateException
     *             if the execution is already complete
     */
    public boolean canRetryOn(Throwable failure);

    /**
     * Records and completes the execution.
     *
     * @throws IllegalStateException
     *             if the execution is already complete
     */
    public void complete();

    /**
     * Records and attempts to complete the execution with the {@code result}.
     * Returns true on success, else false if completion failed and execution
     * should be retried.
     *
     * @throws IllegalStateException
     *             if the execution is already complete
     */
    public boolean complete(Object result);

    /**
     * Records a failed execution and returns true if a retry can be performed
     * for the {@code failure}, else returns false and completes the execution.
     *
     * <p>
     * Alias of {@link #canRetryOn(Throwable)}
     *
     * @throws NullPointerException
     *             if {@code failure} is null
     * @throws IllegalStateException
     *             if the execution is already complete
     */
    public boolean recordFailure(Throwable failure);

    @Override
    public T with(RetryPolicy retryPolicy);

    @Override
    public T with(CircuitBreaker circuitBreaker);

    public T with(ThreadPoolExecutor pool);

}