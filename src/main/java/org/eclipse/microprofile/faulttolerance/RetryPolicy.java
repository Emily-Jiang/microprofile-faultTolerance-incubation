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

import java.time.Duration;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * A policy that defines when retries should be performed.
 *
 * <p>
 * The {@code retryOn} methods describe when a retry should be performed for a particular failure. The {@code retryWhen}
 * method describes when a retry should be performed for a particular result. If multiple {@code retryOn} or
 * {@code retryWhen} conditions are specified, any matching condition can allow a retry. The {@code abortOn},
 * {@code abortWhen} and {@code abortIf} methods describe when retries should be aborted.
 *
 * @author Jonathan Halterman
 */
public interface RetryPolicy {

    /**
     * Specifies that retries should be aborted if the
     * {@code completionPredicate} matches the completion result.
     *
     * @throws NullPointerException
     *             if {@code completionPredicate} is null
     */
    public <T> RetryPolicy abortIf(BiPredicate<T, ? extends Throwable> completionPredicate);

    /**
     * Specifies that retries should be aborted if the {@code resultPredicate}
     * matches the result.
     *
     * @throws NullPointerException
     *             if {@code resultPredicate} is null
     */
    public <T> RetryPolicy abortIf(Predicate<T> resultPredicate);

    /**
     * Specifies when retries should be aborted. Any failure that is assignable
     * from the {@code failures} will be result in retries being aborted.
     *
     * @throws NullPointerException
     *             if {@code failures} is null
     * @throws IllegalArgumentException
     *             if failures is empty
     */
    public RetryPolicy abortOn(Class<? extends Throwable>... failures);

    /**
     * Specifies when retries should be aborted. Any failure that is assignable
     * from the {@code failures} will be result in retries being aborted.
     *
     * @throws NullPointerException
     *             if {@code failures} is null
     * @throws IllegalArgumentException
     *             if failures is null or empty
     */
    public RetryPolicy abortOn(List<Class<? extends Throwable>> failures);

    /**
     * Specifies that retries should be aborted if the {@code failurePredicate}
     * matches the failure.
     *
     * @throws NullPointerException
     *             if {@code failurePredicate} is null
     */
    public RetryPolicy abortOn(Predicate<? extends Throwable> failurePredicate);

    /**
     * Specifies that retries should be aborted if the execution result matches
     * the {@code result}.
     */
    public RetryPolicy abortWhen(Object result);

    /**
     * Returns whether the policy allows retries according to the configured
     * {@link #withMaxRetries(int) maxRetries} and
     * {@link #withMaxDuration(Duration) maxDuration}.
     *
     * @see #withMaxRetries(int)
     * @see #withMaxDuration(Duration)
     */
    public boolean allowsRetries();

    /**
     * Returns whether an execution result can be aborted given the configured
     * abort conditions.
     *
     * @see #abortIf(BiPredicate)
     * @see #abortIf(Predicate)
     * @see #abortOn(Class...)
     * @see #abortOn(List)
     * @see #abortOn(Predicate)
     * @see #abortWhen(Object)
     */
    public boolean canAbortFor(Object result, Throwable failure);

    /**
     * Returns whether an execution result can be retried given the configured
     * abort conditions.
     *
     * @see #retryIf(BiPredicate)
     * @see #retryIf(Predicate)
     * @see #retryOn(Class...)
     * @see #retryOn(List)
     * @see #retryOn(Predicate)
     * @see #retryWhen(Object)
     */
    public boolean canRetryFor(Object result, Throwable failure);

    /**
     * Returns a copy of this RetryPolicy.
     */
    public RetryPolicy copy();

    /**
     * Returns the delay between retries. Defaults to {@link Duration#NONE}.
     *
     * @see #withDelay(Duration)
     * @see #withBackoff(Duration, Duration)
     * @see #withBackoff(Duration, Duration, double)
     */
    public Duration getDelay();

    /**
     * Returns the delay factor for backoff retries.
     *
     * @see #withBackoff(Duration, Duration, double)
     */
    public double getDelayFactor();

    /**
     * Returns the jitter, else {@code null} if none has been configured.
     *
     * @see #withJitter(Duration)
     */
    public Duration getJitter();

    /**
     * Returns the jitter factor, else {@code 0.0} is none has been configured.
     *
     * @see #withJitter(double)
     */
    public double getJitterFactor();

    /**
     * Returns the max delay between backoff retries.
     *
     * @see #withBackoff(Duration, Duration)
     */
    public Duration getMaxDelay();

    /**
     * Returns the max duration to perform retries for.
     *
     * @see #withMaxDuration(Duration)
     */
    public Duration getMaxDuration();

    /**
     * Returns the max retries. Defaults to {@code 100}, which retries forever.
     *
     * @see #withMaxRetries(int)
     */
    public int getMaxRetries();

    /**
     * Specifies that a retry should occur if the {@code completionPredicate}
     * matches the completion result and the retry policy is not exceeded.
     *
     * @throws NullPointerException
     *             if {@code completionPredicate} is null
     */
    public <T> RetryPolicy retryIf(BiPredicate<T, ? extends Throwable> completionPredicate);

    /**
     * Specifies that a retry should occur if the {@code resultPredicate}
     * matches the result and the retry policy is not exceeded.
     *
     * @throws NullPointerException
     *             if {@code resultPredicate} is null
     */
    public <T> RetryPolicy retryIf(Predicate<T> resultPredicate);

    /**
     * Specifies the failures to retry on. Any failure that is assignable from
     * the {@code failures} will be retried.
     *
     * @throws NullPointerException
     *             if {@code failures} is null
     * @throws IllegalArgumentException
     *             if failures is empty
     */
    public RetryPolicy retryOn(Class<? extends Throwable>... failures);

    /**
     * Specifies the failures to retry on. Any failure that is assignable from
     * the {@code failures} will be retried.
     *
     * @throws NullPointerException
     *             if {@code failures} is null
     * @throws IllegalArgumentException
     *             if failures is null or empty
     */
    public RetryPolicy retryOn(List<Class<? extends Throwable>> failures);

    /**
     * Specifies that a retry should occur if the {@code failurePredicate}
     * matches the failure and the retry policy is not exceeded.
     *
     * @throws NullPointerException
     *             if {@code failurePredicate} is null
     */
    public RetryPolicy retryOn(Predicate<? extends Throwable> failurePredicate);

    /**
     * Specifies that a retry should occur if the execution result matches the
     * {@code result} and the retry policy is not exceeded.
     */
    public RetryPolicy retryWhen(Object result);

    /**
     * Sets the {@code delay} between retries, exponentially backing off to the
     * {@code maxDelay} and multiplying successive delays by a factor of 2.
     *
     * @throws NullPointerException
     *             if {@code delay} or {@code duration} are null
     * @throws IllegalStateException
     *             if {@code delay} is >= the
     *             {@link RetryPolicy#withMaxDuration(Duration)
     *             maxDuration}
     * @throws IllegalArgumentException
     *             if {@code delay} is <= 0 or {@code delay} is >=
     *             {@code maxDelay}
     */
    public RetryPolicy withBackoff(Duration delay, Duration maxDelay);

    /**
     * Sets the {@code delay} between retries, exponentially backing off to the
     * {@code maxDelay} and multiplying successive delays by the
     * {@code delayFactor}.
     *
     * @throws NullPointerException
     *             if {@code delay} or {@code duration} are null
     * @throws IllegalStateException
     *             if {@code delay} is >= the
     *             {@link RetryPolicy#withMaxDuration(Duration)
     *             maxDuration}
     * @throws IllegalArgumentException
     *             if {@code delay} <= 0, {@code delay} is >= {@code maxDelay},
     *             or the {@code delayFactor} is <= 1
     */
    public RetryPolicy withBackoff(Duration delay, Duration maxDelay, double delayFactor);

    /**
     * Sets the {@code delay} between retries.
     *
     * @throws NullPointerException
     *             if {@code duration} is null
     * @throws IllegalArgumentException
     *             if {@code delay} <= 0
     * @throws IllegalStateException
     *             if {@code delay} is >= the
     *             {@link RetryPolicy#withMaxDuration(Duration)
     *             maxDuration}
     */
    public RetryPolicy withDelay(Duration delay);

    /**
     * Sets the {@code jitterFactor} to randomly vary retry delays by. For each
     * retry delay, a random portion of the delay multiplied by the
     * {@code jitterFactor} will be added or subtracted to the delay. For
     * example: a retry delay of {@code 100} milliseconds and a
     * {@code jitterFactor} of {@code .25} will result in a random retry delay
     * between {@code 75} and {@code 125} milliseconds.
     * <p>
     * Jitter should be combined with {@link #withDelay(Duration) fixed}
     * or {@link #withBackoff(Duration, Duration) exponential backoff} delays.
     *
     * @throws IllegalArgumentException
     *             if {@code duration} is <= 0 or > 1
     * @throws IllegalStateException
     *             if no delay has been configured or
     *             {@link #withJitter(Duration)} has already been called
     */
    public RetryPolicy withJitter(double jitterFactor);

    /**
     * Sets the {@code jitter} to randomly vary retry delays by. For each retry
     * delay, a random portion of the {@code jitter} will be added or subtracted
     * to the delay. For example: a {@code jitter} of {@code 100} milliseconds
     * will randomly add between {@code -100} and {@code 100} milliseconds to
     * each retry delay.
     * <p>
     * Jitter should be combined with {@link #withDelay(Duration) fixed}
     * or {@link #withBackoff(Duration, Duration) exponential backoff} delays.
     *
     * @throws NullPointerException
     *             if {@code jitter} is null
     * @throws IllegalArgumentException
     *             if {@code jitter} is <= 0
     * @throws IllegalStateException
     *             if no delay has been configured or
     *             {@link #withJitter(double)} has already been called
     */
    public RetryPolicy withJitter(Duration jitter);

    /**
     * Sets the max duration to perform retries for, else the execution will be
     * failed.
     *
     * @throws NullPointerException
     *             if {@code maxDuration} is null
     * @throws IllegalStateException
     *             if {@code maxDuration} is <= the
     *             {@link RetryPolicy#withDelay(Duration) delay}
     */
    public RetryPolicy withMaxDuration(Duration maxDuration);

    /**
     * Sets the max number of retries to perform. {@code -1} indicates to retry
     * forever.
     *
     * @throws IllegalArgumentException
     *             if {@code maxRetries} < -1
     */
    public RetryPolicy withMaxRetries(int maxRetries);

}