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

import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.eclipse.microprofile.faulttolerance.spi.ContextualResultListener;
import org.eclipse.microprofile.faulttolerance.spi.Scheduler;

/**
 * Async executor configuration.
 * <p>
 * Async execution event listeners are called asynchronously on the {@link Scheduler} or
 * {@link ScheduledExecutorService} associated with the execution.
 *
 * @author Jonathan Halterman
 * @author Emily Jiang
 * @param <R> result type
 * @param <F> executor type - {@link SyncExecutor} or {@link AsyncExecutor}
 */
public interface AsyncExecutorConfig<R, F> extends ExecutorConfig<R, F> {

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler when an
       * execution is aborted according to the retry policy.
       */
    public F onAbortAsync(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler when an
       * execution is aborted according to the retry policy.
       */
    public F onAbortAsync(Consumer<? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler when an
       * execution is aborted according to the retry policy.
       */
    public F onAbortAsync(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler when an
       * execution is completed.
       */
    public F onCompleteAsync(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler when an
       * execution is completed.
       */
    public F onCompleteAsync(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler after a
       * failed execution attempt.
       */
    public F onFailedAttemptAsync(
                    ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler after a
       * failed execution attempt.
       */
    public F onFailedAttemptAsync(Consumer<? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler after a
       * failed execution attempt.
       */
    public F onFailedAttemptAsync(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler after a
       * failure occurs that cannot be retried.
       */
    public F onFailureAsync(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler after a
       * failure occurs that cannot be retried.
       */
    public F onFailureAsync(Consumer<? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler after a
       * failure occurs that cannot be retried.
       */
    public F onFailureAsync(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler when an
       * execution fails and the max retry attempts or duration are exceeded.
       */
    public F onRetriesExceededAsync(Consumer<? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler when an
       * execution fails and the max retry attempts or duration are exceeded.
       */
    public F onRetriesExceededAsync(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler before a
       * retry is attempted.
       */
    public F onRetryAsync(ContextualResultListener<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler before a
       * retry is attempted.
       */
    public F onRetryAsync(Consumer<? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler before a
       * retry is attempted.
       */
    public F onRetryAsync(BiConsumer<? extends R, ? extends Throwable> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler after a
       * successful execution.
       */
    public F onSuccessAsync(BiConsumer<? extends R, Execution> listener);

    /**
       * Registers the {@code listener} to be called asynchronously on the configured executor or Scheduler after a
       * successful execution.
       */
    public F onSuccessAsync(Consumer<? extends R> listener);

}