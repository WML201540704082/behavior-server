package com.lnsoft.device.api.cmdb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.device.dto.CmdbDictDeleteDTO;
import com.lnsoft.device.entity.CmdbDict;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.vo.CmdbDictVO;

import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/1 16:15
 * @Description: ICmdbDictService
 */
public interface ICmdbDictService {


	/**
	 * 查询 字典配置项(CMDB)
	 *
	 * @param cmdbDict
	 * @return
	 */
	IPage<CmdbDictVO> cientitySelectDict(CmdbDict cmdbDict);


	/**
	 * 新增 字典配置项(CMDB)
	 *
	 * @param cmdbDict
	 * @param transactionActionType
	 * @return
	 */
	Map<String, Object> cientityBatchsave(CmdbDict cmdbDict, TransactionActionType transactionActionType);

	/**
	 * 修改 字典配置项(CMDB)
	 *
	 * @param cmdbDict
	 * @param transactionActionType
	 * @return
	 */
	Map<String, Object> cientityBatchupdate(CmdbDict cmdbDict, TransactionActionType transactionActionType);


	/**
	 * 删除 字典配置项(CMDB)
	 *
	 * @param cmdbDictDeleteDTOList
	 * @param transactionActionType
	 * @return
	 */
	Boolean cientityDelete(List<CmdbDictDeleteDTO> cmdbDictDeleteDTOList, TransactionActionType transactionActionType);
}
