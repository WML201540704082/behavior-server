
package com.lnsoft.develop.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lnsoft.develop.entity.Code;

/**
 * 服务类
 *
 * @author guozhao
 */
public interface ICodeService extends IService<Code> {

	/**
	 * 提交
	 *
	 * @param code
	 * @return
	 */
	boolean submit(Code code);

}
