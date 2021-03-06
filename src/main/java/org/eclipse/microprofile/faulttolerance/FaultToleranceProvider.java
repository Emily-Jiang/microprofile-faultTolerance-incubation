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

import org.eclipse.microprofile.faulttolerance.spi.FaultToleranceProviderResolver;

public final class FaultToleranceProvider {
    private static final FaultToleranceProviderResolver INSTANCE = FaultToleranceProviderResolver.instance();

    public static RetryPolicy newRetryPolicy() {
        return INSTANCE.newRetryPolicy();
    }

    public static CircuitBreakerPolicy newCircuitBreaker() {
        return INSTANCE.newCircuitBreaker();
    }

    public static Bulkhead newBulkhead() {
        return INSTANCE.newBulkhead();
    }

    public static Executor newExecutor() {
        return INSTANCE.newExecutor();
    }

    public static SyncExecutor<?> newExecutor(RetryPolicy retryPolicy) {
        return newExecutor().with(retryPolicy);
    }

    public static SyncExecutor<?> newExecutor(CircuitBreakerPolicy circuitBreakerPolicy) {
        return newExecutor().with(circuitBreakerPolicy);
    }

    public static SyncExecutor<?> newExecutor(Bulkhead bulkhead) {
        return newExecutor().with(bulkhead);
    }

    public static AsyncExecutor<?> newExecutor(RetryPolicy retryPolicy, ScheduledExecutorService executorService) {
        return newExecutor(retryPolicy).with(executorService);
    }

    public static AsyncExecutor<?> newExecutor(CircuitBreakerPolicy circuitBreakerPolicy, ScheduledExecutorService executorService) {
        return newExecutor(circuitBreakerPolicy).with(executorService);
    }

    public static AsyncExecutor<?> newExecutor(Bulkhead bulkhead, ScheduledExecutorService executorService) {
        return newExecutor(bulkhead).with(executorService);
    }
}
