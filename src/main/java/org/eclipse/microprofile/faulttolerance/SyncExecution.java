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

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.microprofile.faulttolerance.spi.ContextualCallable;
import org.eclipse.microprofile.faulttolerance.spi.Scheduler;

/**
 * Performs synchronous executions with failures handled according to a configured {@link #with(RetryPolicy) retry
 * policy}, {@link #with(CircuitBreaker) circuit breaker} and
 * {@link #withFallback(net.jodah.failsafe.function.BiFunction) fallback}.
 *
 * @author Jonathan Halterman
 * @author Emily Jiang
 * @param <R> listener result type
 */
public interface SyncExecution<R> extends ExecutionConfig<R, SyncExecution<R>> {

    /**
     * Executes the {@code callable} until a successful result is returned or
     * the configured {@link RetryPolicy} is exceeded.
     *
     * @throws NullPointerException
     *             if the {@code callable} is null
     * @throws FailsafeException
     *             if the {@code callable} fails with a checked Exception or if
     *             interrupted while waiting to perform a retry.
     * @throws CircuitBreakerOpenException
     *             if a configured circuit is open.
     */
    public abstract <T> T get(Callable<T> callable);

    /**
     * Executes the {@code callable} until a successful result is returned or
     * the configured {@link RetryPolicy} is exceeded.
     *
     * @throws NullPointerException
     *             if the {@code callable} is null
     * @throws FailsafeException
     *             if the {@code callable} fails with a checked Exception or if
     *             interrupted while waiting to perform a retry.
     * @throws CircuitBreakerOpenException
     *             if a configured circuit is open.
     */
    public abstract <T> T get(ContextualCallable<T> callable);

    /**
     * Executes the {@code runnable} until successful or until the configured
     * {@link RetryPolicy} is exceeded.
     *
     * @throws NullPointerException
     *             if the {@code runnable} is null
     * @throws FailsafeException
     *             if the {@code runnable} fails with a checked Exception or if
     *             interrupted while waiting to perform a retry.
     * @throws CircuitBreakerOpenException
     *             if a configured circuit is open.
     */
    public abstract void run(Runnable runnable);

    /**
     * Creates and returns a new AsyncFailsafe instance that will perform
     * executions and retries asynchronously via the {@code executor}.
     *
     * @throws NullPointerException
     *             if {@code executor} is null
     */
    public abstract AsyncExecution<R> with(ScheduledExecutorService executor);

    /**
     * Creates and returns a new AsyncFailsafe instance that will perform
     * executions and retries asynchronously via the {@code scheduler}.
     *
     * @throws NullPointerException
     *             if {@code scheduler} is null
     */
    public abstract AsyncExecution<R> with(Scheduler scheduler);

}