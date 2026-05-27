package com.lnsoft.device.api.operation.annotation;

import java.lang.annotation.*;

/**
 * @author xyzadmin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ForUpdate {
	String fieldName() default "";
}
