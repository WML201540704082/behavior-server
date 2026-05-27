package com.lnsoft.device.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCorpSelected {

	String[] source() default {};

	int firstRow() default 1;

	int lastRow() default 0x10000;
}
