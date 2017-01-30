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

import org.eclipse.microprofile.faulttolerance.spi.Ratio;

/**
 * A circuit breaker that temporarily halts execution when configurable thresholds are exceeded.
 *
 * @author Jonathan Halterman
 * @author Emily Jiang
 */
public interface CircuitBreaker {

    /**
     * The state of the circuit.
     */
    public enum State {
        /*
         * The circuit is closed and fully functional, allowing executions to
         * occur.
         */
        CLOSED,
        /* The circuit is opened and not allowing executions to occur. */
        OPEN,
        /* The circuit is temporarily allowing executions to occur. */
        HALF_OPEN;
    }

    /**
     * Returns whether the circuit allows execution, possibly triggering a state
     * transition.
     */
    public boolean allowsExecution();

    /**
     * Closes the circuit.
     */
    public void close();

    /**
     * Specifies that a failure should be recorded if the
     * {@code completionPredicate} matches the completion result.
     *
     * @throws NullPointerException
     *             if {@code completionPredicate} is null
     */
    public <T> CircuitBreaker failIf(BiPredicate<T, ? extends Throwable> completionPredicate);

    /**
     * Specifies that a failure should be recorded if the
     * {@code resultPredicate} matches the result.
     *
     * @throws NullPointerException
     *             if {@code resultPredicate} is null
     */
    public <T> CircuitBreaker failIf(Predicate<T> resultPredicate);

    /**
     * Specifies the types to fail on. Applies to any type that is assignable
     * from the {@code failures}.
     *
     * @throws NullPointerException
     *             if {@code failures} is null
     * @throws IllegalArgumentException
     *             if failures is empty
     */
    public CircuitBreaker failOn(Class<? extends Throwable>... failures);

    /**
     * Specifies the types to fail on. Applies to any type that is assignable
     * from the {@code failures}.
     *
     * @throws NullPointerException
     *             if {@code failures} is null
     * @throws IllegalArgumentException
     *             if failures is empty
     */
    public CircuitBreaker failOn(List<Class<? extends Throwable>> failures);

    /**
     * Specifies that a failure should be recorded if the
     * {@code failurePredicate} matches the failure.
     *
     * @throws NullPointerException
     *             if {@code failurePredicate} is null
     */
    public CircuitBreaker failOn(Predicate<? extends Throwable> failurePredicate);

    /**
     * Specifies that a failure should be recorded if the execution result
     * matches the {@code result}.
     */
    public CircuitBreaker failWhen(Object result);

    /**
     * Returns the delay before allowing another execution on the circuit.
     * Defaults to {@link Duration#NONE}.
     *
     * @see #withDelay(Duration)
     */
    public Duration getDelay();

    /**
     * Gets the ratio of successive failures that must occur when in a closed
     * state in order to open the circuit else {@code null} if none has been
     * configured.
     *
     * @see #withFailureThreshold(int)
     * @see #withFailureThreshold(int, int)
     */
    public Ratio getFailureThreshold();

    /**
     * Gets the state of the circuit.
     */
    public State getState();

    /**
     * Gets the ratio of successive successful executions that must occur when
     * in a half-open state in order to close the circuit else {@code null} if
     * none has been configured.
     *
     * @see #withSuccessThreshold(int)
     * @see #withSuccessThreshold(int, int)
     */
    public Ratio getSuccessThreshold();

    /**
     * Returns timeout for executions else {@code null} if none has been
     * configured.
     *
     * @see #withTimeout(Duration)
     */
    public Duration getTimeout();

    /**
     * Half-opens the circuit.
     */
    public void halfOpen();

    /**
     * Returns whether the circuit is closed.
     */
    public boolean isClosed();

    /**
     * Returns whether the circuit breaker considers the {@code result} or
     * {@code throwable} a failure based on the configured conditions, or if
     * {@code failure} is not null it is not checked by any configured
     * condition.
     *
     * @see #failIf(BiPredicate)
     * @see #failIf(Predicate)
     * @see #failOn(Class...)
     * @see #failOn(List)
     * @see #failOn(Predicate)
     * @see #failWhen(Object)
     */
    public boolean isFailure(Object result, Throwable failure);

    /**
     * Returns whether the circuit is half open.
     */
    public boolean isHalfOpen();

    /**
     * Returns whether the circuit is open.
     */
    public boolean isOpen();

    /**
     * Calls the {@code runnable} when the circuit is closed.
     */
    public void onClose(Runnable runnable);

    /**
     * Calls the {@code runnable} when the circuit is half-opened.
     */
    public void onHalfOpen(Runnable runnable);

    /**
     * Calls the {@code runnable} when the circuit is opened.
     */
    public void onOpen(Runnable runnable);

    /**
     * Opens the circuit.
     */
    public void open();

    /**
     * Records an execution {@code failure} as a success or failure based on the
     * failure configuration as determined by
     * {@link #isFailure(Object, Throwable)}.
     *
     * @see #isFailure(Object, Throwable)
     */
    public void recordFailure(Throwable failure);

    /**
     * Records an execution {@code result} as a success or failure based on the
     * failure configuration as determined by
     * {@link #isFailure(Object, Throwable)}.
     *
     * @see #isFailure(Object, Throwable)
     */
    public void recordResult(Object result);

    /**
     * Records an execution success.
     */
    public void recordSuccess();

    @Override
    public String toString();

    /**
     * Sets the {@code delay} to wait in open state before transitioning to
     * half-open.
     *
     * @throws NullPointerException
     *             if {@code delay} is null
     * @throws IllegalArgumentException
     *             if {@code delay} <= 0
     */
    public CircuitBreaker withDelay(Duration delay);

    /**
     * Sets the number of successive failures that must occur when in a closed
     * state in order to open the circuit.
     *
     * @throws IllegalArgumentException
     *             if {@code failureThresh} < 1
     */
    public CircuitBreaker withFailureThreshold(int failureThreshold);

    /**
     * Sets the ratio of successive failures that must occur when in a closed
     * state in order to open the circuit. For example: 5, 10 would open the
     * circuit if 5 out of the last 10 executions result in a failure. The
     * circuit will not be opened until at least {@code executions} executions
     * have taken place.
     *
     * @param failures
     *            The number of failures that must occur in order to open the
     *            circuit
     * @param executions
     *            The number of executions to measure the {@code failures}
     *            against
     * @throws IllegalArgumentException
     *             if {@code failures} < 1, {@code executions} < 1, or
     *             {@code failures} is < {@code executions}
     */
    public CircuitBreaker withFailureThreshold(int failures, int executions);

    /**
     * Sets the number of successive successful executions that must occur when
     * in a half-open state in order to close the circuit, else the circuit is
     * re-opened when a failure occurs.
     *
     * @throws IllegalArgumentException
     *             if {@code successThreshold} < 1
     */
    public CircuitBreaker withSuccessThreshold(int successThreshold);

    /**
     * Sets the ratio of successive successful executions that must occur when
     * in a half-open state in order to close the circuit. For example: 5, 10
     * would close the circuit if 5 out of the last 10 executions were
     * successful. The circuit will not be closed until at least
     * {@code executions} executions have taken place.
     *
     * @param successes
     *            The number of successful executions that must occur in order
     *            to open the circuit
     * @param executions
     *            The number of executions to measure the {@code successes}
     *            against
     * @throws IllegalArgumentException
     *             if {@code successes} < 1, {@code executions} < 1, or
     *             {@code successes} is < {@code executions}
     */
    public CircuitBreaker withSuccessThreshold(int successes, int executions);

    /**
     * Sets the {@code timeout} for executions. Executions that exceed this
     * timeout are not interrupted, but are recorded as failures once they
     * naturally complete.
     *
     * @throws NullPointerException
     *             if {@code timeout} is null
     * @throws IllegalArgumentException
     *             if {@code timeout} <= 0
     */
    public CircuitBreaker withTimeout(Duration timeout);

}