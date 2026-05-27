package com.lnsoft.device.annotation;


import java.lang.annotation.*;

/**
 * @author zhang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelValid {

	String column() default "";

	boolean required() default false;

//	Class sourceClass() ;
}
