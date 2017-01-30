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

package org.eclipse.microprofile.faulttolerance.spi;

import org.eclipse.microprofile.faulttolerance.AsyncExecutionContext;

/**
 * A callable that manually triggers asynchronous retries or completion via an asynchronous execution.
 *
 * @author Jonathan Halterman
 * @param <T> result type
 */
public interface AsyncCallable<T> {
  /**
   * Handles an asynchronous execution, allowing retries or completion to be performed via the {@code execution}
   * reference.
   */
  T call(AsyncExecutionContext execution) throws Exception;
}
