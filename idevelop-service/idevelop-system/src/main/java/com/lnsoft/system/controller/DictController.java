
package com.lnsoft.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.secure.annotation.PreAuth;
import com.lnsoft.core.tool.utils.CollectionUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.lnsoft.system.entity.Dict;
import com.lnsoft.system.vo.DictVO;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.system.service.IDictService;
import com.lnsoft.system.wrapper.DictWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.lnsoft.common.cache.CacheNames.DICT_LIST;
import static com.lnsoft.common.cache.CacheNames.DICT_VALUE;

/**
 * 控制器
 *
 * @author guozhao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典", tags = "字典")
public class DictController extends IdevelopController {

	private IDictService dictService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入dict")
	public R<DictVO> detail(Dict dict) {
		Dict detail = dictService.getOne(Condition.getQueryWrapper(dict));
		return R.data(DictWrapper.build().entityVO(detail));
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", value = "字典编号", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "dictValue", value = "字典名称", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入dict")
	@PreAuth("hasPerm('system:common:all')")
	public R<List<DictVO>> list(@ApiIgnore @RequestParam Map<String, Object> dict) {
		List<Dict> list = dictService.list(Condition.getQueryWrapper(dict, Dict.class).lambda().orderByAsc(Dict::getSort));
		return R.data(DictWrapper.build().listNodeVO(list));
	}

	/**
	 * 获取字典树形结构
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "树形结构", notes = "树形结构")
	public R<List<DictVO>> tree() {
		List<DictVO> tree = dictService.tree();
		return R.data(tree);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入dict")
	public R submit(@Valid @RequestBody Dict dict) {
		LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Dict::getDictKey,dict.getDictKey()).eq(Dict::getCode,dict.getCode());
		List<Dict> list = dictService.list(wrapper);
		if (CollectionUtil.isNotEmpty(list)){
			return R.fail("该键值已存在！");
		}
		return R.status(dictService.submit(dict));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE}, allEntries = true)
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		List<Long> longs = Func.toLongList(ids);
		for (Long id : longs) {
			LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(Dict::getParentId,id);
			List<Dict> list = dictService.list(queryWrapper);
			if (CollectionUtil.isNotEmpty(list)){
				return R.fail("该节点下存在子节点，请删除子节点后重试！");
			}
		}
		return R.status(dictService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 获取字典
	 *
	 * @return
	 */
	@GetMapping("/dictionary")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取字典", notes = "获取字典")
	public R<List<Dict>> dictionary(String code) {
		List<Dict> tree = dictService.getList(code);
		return R.data(tree);
	}


}
