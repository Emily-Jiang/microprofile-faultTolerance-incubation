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
package org.eclipse.microprofile.faulttolerance.spi;

import org.eclipse.microprofile.faulttolerance.AsyncExecution;

/**
 * A runnable that manually triggers asynchronous retries or completion via an asynchronous execution.
 *
 * @author Jonathan Halterman
 */
public interface AsyncRunnable {
  /**
   * Handles an asynchronous execution, allowing retries or completion to be performed via the {@code execution}
   * reference.
   */
  void run(AsyncExecution execution) throws Exception;
}
