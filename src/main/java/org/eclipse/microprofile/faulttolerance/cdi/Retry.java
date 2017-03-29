/*
 *******************************************************************************
 * Copyright (c) 2017 IBM Corp. and others
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

package org.eclipse.microprofile.faulttolerance.cdi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * The Retry annotation to define the number of the retries and the fallback method on reaching the
 * retry counts.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Retry {

    /**
     *
     * @return The max number of retries.
     */
    int maxRetries() default 3;

    /**
     * The delay between retries. Defaults to {@link Duration#NONE}.
     * @return
     */
    int delay() default 0;

    /**
     *
     * @return the delay unit
     */

    TimeUnit delayUnit() default TimeUnit.SECONDS;

    /**
     * @return the maximum duration for the execution.
     */
    int maxDuration() default 20;

    /**
     *
     * @return the duration unit
     */
    TimeUnit durationUnit() default TimeUnit.SECONDS;

    /**
     *
     * @return
     */
    int jitter() default 2;

    int jitterFactor() default 2;

    TimeUnit jitterDelayUnit() default TimeUnit.SECONDS;

    int backOff() default 2;

    int backOffFactor() default 2;

    TimeUnit bakeOffUnit() default TimeUnit.SECONDS;

    Class<? extends Throwable>[] retryOn() default { Throwable.class };

    Class<? extends Throwable>[] aboartOn() default { Throwable.class };

    /**
     * The fallback method
     * @return the fallback method
     */
    String fallback() default "";

}
