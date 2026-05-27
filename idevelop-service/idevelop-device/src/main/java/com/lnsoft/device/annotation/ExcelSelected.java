package com.lnsoft.device.annotation;

import java.lang.annotation.*;

/**
 * @author zhang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelSelected {

	String[] source() default {};

	String ciId() default "";

	Class<? extends ExcelDynamicSelect>[] sourceClass() default {};

	Class<? extends ExcelDynamicSelectNew>[] sourceClassDept() default {};

//	Class<? extends ExcelDynamicSelectWbs>[] sourceClassWbs() default {};

	Class<? extends ExcelDynamicSelectTran>[] sourceClassTran() default {};

	Class<? extends ExcelDynamicSelectMaintain>[] sourceClassMain() default {};

	int firstRow() default 1;

	int lastRow() default 0x10000;
}
