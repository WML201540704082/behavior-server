
package com.lnsoft.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author guozhao
 */
@Getter
@AllArgsConstructor
public enum IdevelopUserEnum {

	/**
	 * web
	 */
	WEB("web", 1),

	/**
	 * app
	 */
	APP("app", 2),
	;

	final String name;
	final int category;

}
