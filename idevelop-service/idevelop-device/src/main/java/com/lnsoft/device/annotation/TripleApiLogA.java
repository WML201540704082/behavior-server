

package com.lnsoft.device.annotation;

import com.lnsoft.device.eums.TripleApiLogValueEnum;
import com.lnsoft.device.eums.TripleTypeEnum;

import java.lang.annotation.*;

/**
 * 三方系统操作日志注解
 *
 * @author guozhao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TripleApiLogA {

	/**
	 * 操作类型
	 *
	 * @return {enum}
	 */
	TripleApiLogValueEnum value() default TripleApiLogValueEnum.UNKNOWN;


	/**
	 * 三方系统
	 *
	 * @return {enum}
	 */
	TripleTypeEnum tripleType() default TripleTypeEnum.UNKNOWN;
}
