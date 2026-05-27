package com.lnsoft.device.annotation;

import java.lang.annotation.*;

/**
 * @author zhang
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDictImport {

	String type();

}
