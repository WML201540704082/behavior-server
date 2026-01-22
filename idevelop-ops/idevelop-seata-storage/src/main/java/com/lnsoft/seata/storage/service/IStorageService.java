package com.lnsoft.seata.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnsoft.seata.storage.entity.Storage;

/**
 * IStorageService
 *
 * @author guozhao
 */
public interface IStorageService extends IService<Storage> {

	/**
	 * 减库存
	 *
	 * @param commodityCode 商品代码
	 * @param count         数量
	 * @return boolean
	 */
	int deduct(String commodityCode, int count);

}
