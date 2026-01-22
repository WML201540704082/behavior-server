
package com.lnsoft.system.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * GrantTreeVO
 *
 * @author guozhao
 */
@Data
public class GrantTreeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<MenuVO> menu;

	private List<MenuVO> dataScope;

}
