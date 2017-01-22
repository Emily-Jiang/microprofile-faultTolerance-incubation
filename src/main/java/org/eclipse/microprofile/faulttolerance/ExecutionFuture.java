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

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * The future result of an asynchronous Failsafe execution.
 *
 * @author Jonathan Halterman
 * @param <T> result type
 */
public interface ExecutionFuture<T> {

    /**
     * Attempts to cancel this execution. This attempt will fail if the
     * execution has already completed, has already been cancelled, or could not
     * be cancelled for some other reason. If successful, and this execution has
     * not started when {@code cancel} is called, this execution should never
     * run. If the execution has already started, then the
     * {@code mayInterruptIfRunning} parameter determines whether the thread
     * executing this task should be interrupted in an attempt to stop the
     * execution.
     *
     * <p>
     * After this method returns, subsequent calls to {@link #isDone} will
     * always return {@code true}. Subsequent calls to {@link #isCancelled} will
     * always return {@code true} if this method returned {@code true}.
     *
     * @param mayInterruptIfRunning
     *            {@code true} if the thread executing this execution should be
     *            interrupted; otherwise, in-progress executions are allowed to
     *            complete
     * @return {@code false} if the execution could not be cancelled, typically
     *         because it has already completed normally; {@code true} otherwise
     */
    public boolean cancel(boolean mayInterruptIfRunning);

    /**
     * Waits if necessary for the execution to complete, and then returns its
     * result.
     *
     * @return the execution result
     * @throws CancellationException
     *             if the execution was cancelled
     * @throws ExecutionException
     *             if the execution threw an exception
     * @throws InterruptedException
     *             if the current thread was interrupted while waiting
     */
    public T get() throws InterruptedException, ExecutionException;

    /**
     * Waits if necessary for at most the given time for the execution to
     * complete, and then returns its result, if available.
     *
     * @param timeout
     *            the maximum time to wait
     * @param unit
     *            the time unit of the timeout argument
     * @return the execution result
     * @throws CancellationException
     *             if the execution was cancelled
     * @throws ExecutionException
     *             if the execution threw an exception
     * @throws InterruptedException
     *             if the current thread was interrupted while waiting
     * @throws TimeoutException
     *             if the wait timed out
     * @throws NullPointerException
     *             if {@code unit} is null
     * @throws IllegalArgumentException
     *             if {@code timeout} is < 0
     */
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
                    TimeoutException;

    /**
     * Returns {@code true} if this execution was cancelled before it completed
     * normally.
     *
     * @return {@code true} if this execution was cancelled before it completed
     */
    public boolean isCancelled();

    /**
     * Returns {@code true} if this execution completed.
     *
     * Completion may be due to normal termination, an exception, or
     * cancellation -- in all of these cases, this method will return
     * {@code true}.
     *
     * @return {@code true} if this execution completed
     */
    public boolean isDone();

}