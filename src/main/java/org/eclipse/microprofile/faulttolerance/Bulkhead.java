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

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * A Bulkhead to limit the number of concurrent calls to a component by using a fixed number
 * of threads in a pool.
 *
 * @author Jonathan Halterman
 * @author Emily Jiang
 */
public interface Bulkhead {

    /**
     * Configure the bulkhead pattern via thread isolation
     * @param executor
     * @return
     */
    Bulkhead withThread(ThreadPoolExecutor executor);

    /**
     * Configure the bulkhead pattern via semaphore isolation
     * @param semaphore
     * @return
     */
    Bulkhead WithSempahore(Semaphore semaphore);
}
