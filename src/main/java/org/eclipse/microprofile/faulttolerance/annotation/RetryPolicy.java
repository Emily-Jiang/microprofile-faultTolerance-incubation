package org.eclipse.microprofile.faulttolerance.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates an executable method that should be executed with a RetryPolicy.
 * 
 * @author Jonathan Halterman
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryPolicy {
  /**
   * The name of the produced RetryPolicy to execute the annotated method with.
   */
  String name() default "";
}