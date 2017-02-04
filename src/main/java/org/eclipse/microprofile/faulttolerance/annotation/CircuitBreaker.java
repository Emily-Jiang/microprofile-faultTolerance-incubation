package org.eclipse.microprofile.faulttolerance.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates an executable method that should be executed with a CircuitBreaker.
 * 
 * @author Jonathan Halterman
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CircuitBreaker {
  /**
   * The name of the produced CircuitBreaker to execute the annotated method with.
   */
  String name() default "";
}