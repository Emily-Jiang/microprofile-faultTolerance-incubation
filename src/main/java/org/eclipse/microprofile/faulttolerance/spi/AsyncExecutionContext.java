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
 */
package org.eclipse.microprofile.faulttolerance.spi;

public interface AsyncExecutionContext extends ExecutionContext {

  /**
   * Completes the execution and the associated {@code Future}.
   *
   * @throws IllegalStateException if the execution is already complete
   */
  void complete();

  /**
   * Attempts to complete the execution and the associated {@code Future} with the {@code result}. Returns true on
   * success, else false if completion failed and the execution should be retried via {@link #retry()}.
   *
   * @throws IllegalStateException if the execution is already complete
   */
  boolean complete(Object result);

  /**
   * Attempts to complete the execution and the associated {@code Future} with the {@code result} and
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
   * Records an execution and returns true if a retry has been scheduled for else returns returns false and completes
   * the execution and associated {@code Future}.
   *
   * @throws IllegalStateException if a retry method has already been called or the execution is already complete
   */
  boolean retry();

  /**
   * Records an execution and returns true if a retry has been scheduled for the {@code result}, else returns false and
   * marks the execution and associated {@code Future} as complete.
   *
   * @throws IllegalStateException if a retry method has already been called or the execution is already complete
   */
  boolean retryFor(Object result);

  /**
   * Records an execution and returns true if a retry has been scheduled for the {@code result} or {@code failure}, else
   * returns false and marks the execution and associated {@code Future} as complete.
   * 
   * @throws IllegalStateException if a retry method has already been called or the execution is already complete
   */
  boolean retryFor(Object result, Throwable failure);

  /**
   * Records an execution and returns true if a retry has been scheduled for the {@code failure}, else returns false and
   * marks the execution and associated {@code Future} as complete.
   *
   * @throws NullPointerException if {@code failure} is null
   * @throws IllegalStateException if a retry method has already been called or the execution is already complete
   */
  boolean retryOn(Throwable failure);

}