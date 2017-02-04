package org.eclipse.microprofile.faulttolerance.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates an executable method that should be executed with a fault tolerant executor.
 * 
 * @author Jonathan Halterman
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Executable {
  /**
   * The name of the produced executor to execute the annotated method with.
   */
  String name() default "";

  /**
   * The name of the fallback method to invoke if execution fails.
   */
  String fallbackMethod() default "";
}