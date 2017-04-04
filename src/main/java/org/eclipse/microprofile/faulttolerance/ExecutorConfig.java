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
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.microprofile.faulttolerance.spi.ContextualResultListener;

/**
 * Executor configuration.
 *
 * @author Jonathan Halterman
 * @author Emily Jiang
 * @param <R> result type
 * @param <F> executor type - {@link SyncExecutor} or {@link AsyncExecutor}
 */
public interface ExecutorConfig<R, F> {

    /**
     * Registers the {@code listener} to be called when an execution is aborted.
     */
    public F onAbort(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called when an execution is aborted.
     */
    public F onAbort(Consumer<? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called when an execution is aborted.
     */
    public F onAbort(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution is aborted.
     */
    public F onAbortAsync(BiConsumer<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution is aborted.
     */
    public F onAbortAsync(Consumer<? extends Throwable> listener, ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution is aborted.
     */
    public F onAbortAsync(ContextualResultListener<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called when an execution is
     * completed.
     */
    public F onComplete(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called when an execution is
     * completed.
     */
    public F onComplete(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution is completed.
     */
    public F onCompleteAsync(BiConsumer<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution is completed.
     */
    public F onCompleteAsync(ContextualResultListener<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called when an execution attempt
     * fails.
     */
    public F onFailedAttempt(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called when an execution attempt
     * fails.
     */
    public F onFailedAttempt(Consumer<? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called when an execution attempt
     * fails.
     */
    public F onFailedAttempt(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution attempt fails.
     */
    public F onFailedAttemptAsync(BiConsumer<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution attempt fails.
     */
    public F onFailedAttemptAsync(Consumer<? extends Throwable> listener, ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution attempt fails.
     */
    public F onFailedAttemptAsync(
                    ContextualResultListener<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called when an execution fails and
     * cannot be retried.
     */
    public F onFailure(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called when an execution fails and
     * cannot be retried.
     */
    public F onFailure(Consumer<? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called when an execution fails and
     * cannot be retried.
     */
    public F onFailure(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution fails and cannot be retried.
     */
    public F onFailureAsync(BiConsumer<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution fails and cannot be retried.
     */
    public F onFailureAsync(Consumer<? extends Throwable> listener, ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution fails and cannot be retried.
     */
    public F onFailureAsync(ContextualResultListener<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called when an execution fails and
     * the {@link RetryPolicy#withMaxRetries(int) max retry attempts} or
     * {@link RetryPolicy#withMaxDuration(long, java.util.concurrent.TimeUnit)
     * max duration} are exceeded.
     */
    public F onRetriesExceeded(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called when an execution fails and
     * the {@link RetryPolicy#withMaxRetries(int) max retry attempts} or
     * {@link RetryPolicy#withMaxDuration(long, java.util.concurrent.TimeUnit)
     * max duration} are exceeded.
     */
    public F onRetriesExceeded(Consumer<? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution fails and the
     * {@link RetryPolicy#withMaxRetries(int) max retry attempts} or
     * {@link RetryPolicy#withMaxDuration(long, java.util.concurrent.TimeUnit)
     * max duration} are exceeded.
     */
    public F onRetriesExceededAsync(BiConsumer<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution fails and the
     * {@link RetryPolicy#withMaxRetries(int) max retry attempts} or
     * {@link RetryPolicy#withMaxDuration(long, java.util.concurrent.TimeUnit)
     * max duration} are exceeded.
     */
    public F onRetriesExceededAsync(Consumer<? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called before an execution is
     * retried.
     */
    public F onRetry(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called before an execution is
     * retried.
     */
    public F onRetry(Consumer<? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called before an execution is
     * retried.
     */
    public F onRetry(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} before an execution is retried.
     */
    public F onRetryAsync(BiConsumer<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} before an execution is retried.
     */
    public F onRetryAsync(Consumer<? extends Throwable> listener, ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} before an execution is retried.
     */
    public F onRetryAsync(ContextualResultListener<? extends R, ? extends Throwable> listener,
                    ExecutorService executor);

    /**
     * Registers the {@code listener} to be called when an execution is
     * successful.
     */
    public F onSuccess(BiConsumer<? extends R, Execution> listener);

    /**
     * Registers the {@code listener} to be called when an execution is
     * successful.
     */
    public F onSuccess(Consumer<? extends R> listener);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution is successful.
     */
    public F onSuccessAsync(BiConsumer<? extends R, Execution> listener, ExecutorService executor);

    /**
     * Registers the {@code listener} to be called asynchronously on the
     * {@code executor} when an execution is successful.
     */
    public F onSuccessAsync(Consumer<? extends R> listener, ExecutorService executor);

    /**
     * Configures the {@code circuitBreaker} to be used to control the rate of
     * event execution.
     *
     * @throws NullPointerException
     *             if {@code circuitBreaker} is null
     * @throws IllegalStateException
     *             if a circuit breaker is already configured
     */
    public F with(CircuitBreaker circuitBreaker);

    /**
     * Configures the {@code retryPolicy} to be used for retrying failed
     * executions.
     *
     * @throws NullPointerException
     *             if {@code retryPolicy} is null
     * @throws IllegalStateException
     *             if a retry policy is already configured
     */
    public F with(RetryPolicy retryPolicy);

    /**
     * Configures the {@code fallback} action to be executed if execution fails.
     *
     * @throws NullPointerException
     *             if {@code fallback} is null
     * @throws IllegalStateException
     *             if {@code withFallback} method has already been called
     */
    public F withFallback(Callable<? extends R> fallback);

    /**
     * Configures the {@code fallback} action to be executed if execution fails.
     *
     * @throws NullPointerException
     *             if {@code fallback} is null
     * @throws IllegalStateException
     *             if {@code withFallback} method has already been called
     */
    public F withFallback(BiConsumer<? extends R, ? extends Throwable> fallback);

    /**
     * Configures the {@code fallback} action to be executed if execution fails.
     *
     * @throws NullPointerException
     *             if {@code fallback} is null
     * @throws IllegalStateException
     *             if {@code withFallback} method has already been called
     */
    public F withFallback(BiFunction<? extends R, ? extends Throwable, ? extends R> fallback);

    /**
     * Configures the {@code fallback} action to be executed if execution fails.
     *
     * @throws NullPointerException
     *             if {@code fallback} is null
     * @throws IllegalStateException
     *             if {@code withFallback} method has already been called
     */
    public F withFallback(Consumer<? extends Throwable> fallback);

    /**
     * Configures the {@code fallback} action to be executed if execution fails.
     *
     * @throws NullPointerException
     *             if {@code fallback} is null
     * @throws IllegalStateException
     *             if {@code withFallback} method has already been called
     */
    public F withFallback(Function<? extends Throwable, ? extends R> fallback);

    /**
     * Configures the {@code fallback} action to be executed if execution fails.
     *
     * @throws NullPointerException
     *             if {@code fallback} is null
     * @throws IllegalStateException
     *             if {@code withFallback} method has already been called
     */
    public F withFallback(Runnable fallback);

    /**
     * Configures the {@code fallback} result to be returned if execution fails.
     *
     * @throws NullPointerException
     *             if {@code fallback} is null
     * @throws IllegalStateException
     *             if {@code withFallback} method has already been called
     */
    public F withFallback(R fallback);

}