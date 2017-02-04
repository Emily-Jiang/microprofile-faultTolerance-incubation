package org.eclipse.microprofile.faulttolerance;

/**
 * Thrown when a synchronous execution fails with a checked Exception. Use {@link Throwable#getCause()} to learn the
 * cause of the failure.
 * 
 * @author Jonathan Halterman
 */
public class ExecutionException extends RuntimeException {
  public ExecutionException() {
  }

  public ExecutionException(Throwable t) {
    super(t);
  }
}
