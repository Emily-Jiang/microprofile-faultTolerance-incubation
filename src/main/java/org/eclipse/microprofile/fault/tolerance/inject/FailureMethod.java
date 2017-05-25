package org.eclipse.microprofile.fault.tolerance.inject;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * A qualifier to identify a particular method so that the ExecutionFailureEvent can be filtered.
 * @author <a href="mailto:emijiang@uk.ibm.com">Emily Jiang</a>
 *
 */
@Qualifier
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface FailureMethod {
	String value();
}
