/*
 *******************************************************************************
 * Copyright (c) 2016, 2017 IBM Corp. and others
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
/**
 * Tracks asynchronous executions and allows retries to be scheduled according to a {@link RetryPolicy}.
 *
 * @author Jonathan Halterman
 * @author Emily Jiang
 */
package org.eclipse.microprofile.faulttolerance;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.eclipse.microprofile.faulttolerance.spi.AsyncCallable;
import org.eclipse.microprofile.faulttolerance.spi.AsyncRunnable;
import org.eclipse.microprofile.faulttolerance.spi.ContextualCallable;

/**
 * Performs asynchronous executions with failures handled according to a configured {@link #with(RetryPolicy) retry
 * policy}, {@link #with(CircuitBreaker) circuit breaker} and
 * {@link #withFallback(java.util.function.BiFunction) fallback}.
 * 
 * @author Jonathan Halterman
 * @param <R> result type
 */
public interface AsyncExecution<R> extends ExecutionConfig<R, AsyncExecution<R>> {

  /**
   * Completes the execution and the associated {@code FutureResult}.
   *
   * @throws IllegalStateException if the execution is already complete
   */
  void complete();

  /**
   * Attempts to complete the execution and the associated {@code FutureResult} with the {@code result}. Returns true on
   * success, else false if completion failed and the execution should be retried via {@link #retry()}.
   *
   * @throws IllegalStateException if the execution is already complete
   */
  boolean complete(Object result);

  /**
   * Attempts to complete the execution and the associated {@code FutureResult} with the {@code result} and
   * {@code failure}. Returns true on success, else false if completion failed and the execution should be retried via
   * {@link #retry()}.
   * <p>
   * Note: the execution may be completed even when the {@code failure} is not {@code null}, such as when the
   * RetryPolicy does not allow retries for the {@code failure}.
   *
   * @throws IllegalStateException if the execution is already complete
   */
  boolean complete(Object result, Throwable failure);
  
    /**
     * Executes the {@code callable} asynchronously until the resulting future
     * is successfully completed or the configured {@link RetryPolicy} is
     * exceeded.
     * <p>
     * Supported on Java 8 and above.
     *
     * @throws NullPointerException
     *             if the {@code callable} is null
     * @throws CircuitBreakerOpenException
     *             if a configured circuit breaker is open
     */
    public abstract <T> java.util.concurrent.CompletableFuture<T> future(
                    Callable<java.util.concurrent.CompletableFuture<T>> callable);

    /**
     * Executes the {@code callable} asynchronously until the resulting future
     * is successfully completed or the configured {@link RetryPolicy} is
     * exceeded.
     * <p>
     * Supported on Java 8 and above.
     *
     * @throws NullPointerException
     *             if the {@code callable} is null
     * @throws CircuitBreakerOpenException
     *             if a configured circuit breaker is open
     */
    public abstract <T> java.util.concurrent.CompletableFuture<T> future(
                    ContextualCallable<java.util.concurrent.CompletableFuture<T>> callable);

    /**
     * Executes the {@code callable} asynchronously until the resulting future
     * is successfully completed or the configured {@link RetryPolicy} is
     * exceeded. This method is intended for integration with asynchronous code.
     * Retries must be manually scheduled via one of the
     * {@code AsyncExecution.retry} methods.
     * <p>
     * Supported on Java 8 and above.
     *
     * @throws NullPointerException
     *             if the {@code callable} is null
     * @throws CircuitBreakerOpenException
     *             if a configured circuit breaker is open
     */
    public abstract <T> java.util.concurrent.CompletableFuture<T> futureAsync(
                    AsyncCallable<java.util.concurrent.CompletableFuture<T>> callable);

    /**
     * Executes the {@code callable} asynchronously until a successful result is
     * returned or the configured {@link RetryPolicy} is exceeded.
     *
     * @throws NullPointerException
     *             if the {@code callable} is null
     * @throws CircuitBreakerOpenException
     *             if a configured circuit breaker is open
     */
    public abstract <T> Future<T> get(Callable<T> callable);

    /**
     * Executes the {@code callable} asynchronously until a successful result is
     * returned or the configured {@link RetryPolicy} is exceeded.
     *
     * @throws NullPointerException
     *             if the {@code callable} is null
     * @throws CircuitBreakerOpenException
     *             if a configured circuit breaker is open
     */
    public abstract <T> Future<T> get(ContextualCallable<T> callable);

    /**
     * Executes the {@code callable} asynchronously until a successful result is
     * returned or the configured {@link RetryPolicy} is exceeded. This method
     * is intended for integration with asynchronous code. Retries must be
     * manually scheduled via one of the {@code AsyncExecution.retry} methods.
     *
     * @throws NullPointerException
     *             if the {@code callable} is null
     * @throws CircuitBreakerOpenException
     *             if a configured circuit breaker is open
     */
    public abstract <T> Future<T> getAsync(AsyncCallable<T> callable);

    /**
     * Records an execution and returns true if a retry has been scheduled for else returns returns false and completes
     * the execution and associated {@code FutureResult}.
     *
     * @throws IllegalStateException if a retry method has already been called or the execution is already complete
     */
    boolean retry();

    /**
     * Records an execution and returns true if a retry has been scheduled for the {@code result}, else returns false and
     * marks the execution and associated {@code FutureResult} as complete.
     *
     * @throws IllegalStateException if a retry method has already been called or the execution is already complete
     */
    boolean retryFor(Object result);

    /**
     * Records an execution and returns true if a retry has been scheduled for the {@code result} or {@code failure}, else
     * returns false and marks the execution and associated {@code FutureResult} as complete.
     * 
     * @throws IllegalStateException if a retry method has already been called or the execution is already complete
     */
    boolean retryFor(Object result, Throwable failure);

    /**
     * Records an execution and returns true if a retry has been scheduled for the {@code failure}, else returns false and
     * marks the execution and associated {@code FutureResult} as complete.
     *
     * @throws NullPointerException if {@code failure} is null
     * @throws IllegalStateException if a retry method has already been called or the execution is already complete
     */
    boolean retryOn(Throwable failure);

    
    /**
     * Executes the {@code runnable} asynchronously until successful or until
     * the configured {@link RetryPolicy} is exceeded.
     *
     * @throws NullPointerException
     *             if the {@code runnable} is null
     * @throws CircuitBreakerOpenException
     *             if a configured circuit breaker is open
     */
    public abstract Future<Void> run(Runnable runnable);

    /**
     * Executes the {@code runnable} asynchronously until successful or until
     * the configured {@link RetryPolicy} is exceeded. This method is intended
     * for integration with asynchronous code. Retries must be manually
     * scheduled via one of the {@code AsyncExecution.retry} methods.
     *
     * @throws NullPointerException
     *             if the {@code runnable} is null
     * @throws CircuitBreakerOpenException
     *             if a configured circuit breaker is open
     */
    public abstract Future<Void> runAsync(AsyncRunnable runnable);

}