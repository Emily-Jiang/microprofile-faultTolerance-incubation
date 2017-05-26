package org.eclipse.microprofile.faulttolerance.spi;

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


import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ServiceLoader;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Executor;
import org.eclipse.microprofile.faulttolerance.RetryPolicy;

/**
 *
 */
public abstract class FaultToleranceProviderResolver {
    protected FaultToleranceProviderResolver() {}

    private static volatile FaultToleranceProviderResolver instance = null;

    public abstract RetryPolicy newRetryPolicy();

    public abstract CircuitBreaker newCircuitBreaker();

    public abstract Bulkhead newBulkhead();

    public abstract Executor newExecutor();

    /**
     * Creates a FaultToleranceProviderResolver object
     * 
     */
    public static FaultToleranceProviderResolver instance() {
        if (instance == null) {
            synchronized (FaultToleranceProviderResolver.class) {
                if (instance != null) {
                    return instance;
                }

                ClassLoader cl = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                    @Override
                    public ClassLoader run() {
                        return Thread.currentThread().getContextClassLoader();
                    }
                });

                if (cl == null) {
                    cl = FaultToleranceProviderResolver.class.getClassLoader();
                }

                FaultToleranceProviderResolver newInstance = loadSpi(cl);

                if (newInstance == null) {
                    throw new IllegalStateException("No FaultToleranceProviderResolver implementation found!");
                }

                instance = newInstance;
            }
        }

        return instance;
    }

    private static FaultToleranceProviderResolver loadSpi(ClassLoader cl) {
        if (cl == null) {
            return null;
        }

        // start from the root CL and go back down to the TCCL
        FaultToleranceProviderResolver instance = loadSpi(cl.getParent());

        if (instance == null) {
            ServiceLoader<FaultToleranceProviderResolver> sl = ServiceLoader.load(FaultToleranceProviderResolver.class, cl);
            for (FaultToleranceProviderResolver spi : sl) {
                if (instance != null) {
                    throw new IllegalStateException("Multiple FaultToleranceResolverProvider implementations found: " + spi.getClass().getName()
                                                    + " and " + instance.getClass().getName());
                } else {
                    instance = spi;
                }
            }
        }
        return instance;
    }

    /**
     * Set the instance. It is used by OSGi environment while service loader
     * pattern is not supported.
     *
     * @param resolver
     *            set the instance.
     */
    public static void setInstance(FaultToleranceProviderResolver resolver) {
        instance = resolver;
    }
}
