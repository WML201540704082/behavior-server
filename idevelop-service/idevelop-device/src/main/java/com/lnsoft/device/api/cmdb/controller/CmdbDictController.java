package com.lnsoft.device.api.cmdb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.cmdb.service.ICmdbDictService;
import com.lnsoft.device.dto.CmdbDictDeleteDTO;
import com.lnsoft.device.entity.CmdbDict;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.vo.CmdbDictVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuel
 * @CreateTime: 2024/7/1 15:59
 * @Description: CmdbDictController
 */

@RestController
@AllArgsConstructor
@RequestMapping("/cmdb/dict")
@Api(value = "CMDB字典配置项接口", tags = "CMDB字典配置项接口")
public class CmdbDictController {


	private ICmdbDictService iCmdbDictService;

	/**
	 * 查询 字典配置项(CMDB)
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询 字典配置项(CMDB)", notes = "传入cmdbDict")
	public R<IPage<CmdbDictVO>> list(CmdbDict cmdbDict) {
		try {
			// 查询 字典配置项
			IPage<CmdbDictVO> returnMap = iCmdbDictService.cientitySelectDict(cmdbDict);
			return R.data(returnMap);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}

	/**
	 * 新增 字典配置项(CMDB)
	 */
	@PostMapping("/add")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "新增 字典配置项(CMDB)", notes = "传入cmdbDict")
	public R<Map<String, Object>> add(@Valid @RequestBody CmdbDict cmdbDict) {
		try {
			Map<String, Object> returnMap = iCmdbDictService.cientityBatchsave(cmdbDict, TransactionActionType.INSERT);
			return R.data(returnMap);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}

	/**
	 * 修改 字典配置项(CMDB)
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改 字典配置项(CMDB)", notes = "传入cmdbDict")
	public R<Map<String, Object>> update(@Valid @RequestBody CmdbDict cmdbDict) {
		try {
			Map<String, Object> returnMap = iCmdbDictService.cientityBatchupdate(cmdbDict, TransactionActionType.UPDATE);
			return R.data(returnMap);
		} catch (Exception e) {
			return R.fail(e.getMessage());
		}
	}


	/**
	 * 删除 字典配置项(CMDB)
	 */
	@PostMapping("/delete")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "删除 字典配置项(CMDB)", notes = "传入ids")
	public R<Boolean> delete(@RequestBody List<CmdbDictDeleteDTO> cmdbDictDeleteDTOList) {

		// 删除 字典配置项
		Boolean result = iCmdbDictService.cientityDelete(cmdbDictDeleteDTOList, TransactionActionType.DELETE);
		return R.data(result);
	}

}
