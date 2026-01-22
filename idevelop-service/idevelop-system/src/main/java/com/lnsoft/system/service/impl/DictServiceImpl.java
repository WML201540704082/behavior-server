
package com.lnsoft.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnsoft.system.entity.Dict;
import com.lnsoft.system.service.IDictService;
import com.lnsoft.system.vo.DictVO;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.node.ForestNodeMerger;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.core.tool.utils.StringPool;
import com.lnsoft.system.mapper.DictMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lnsoft.common.cache.CacheNames.DICT_LIST;
import static com.lnsoft.common.cache.CacheNames.DICT_VALUE;

/**
 * 服务实现类
 *
 * @author guozhao
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

	@Override
	public IPage<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict) {
		return page.setRecords(baseMapper.selectDictPage(page, dict));
	}

	@Override
	public List<DictVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	@Cacheable(cacheNames = DICT_VALUE, key = "#code+'_'+#dictKey")
	public String getValue(String code, Integer dictKey) {
		return Func.toStr(baseMapper.getValue(code, dictKey), StringPool.EMPTY);
	}

	@Override
	@Cacheable(cacheNames = DICT_LIST, key = "#code")
	public List<Dict> getList(String code) {
		return baseMapper.getList(code);
	}

	@Override
	@CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE}, allEntries = true)
	public boolean submit(Dict dict) {
		LambdaQueryWrapper<Dict> lqw = Wrappers.<Dict>query().lambda().eq(Dict::getCode, dict.getCode()).eq(Dict::getDictKey, dict.getDictKey());
		Long cnt = baseMapper.selectCount((Func.isEmpty(dict.getId())) ? lqw : lqw.notIn(Dict::getId, dict.getId()));
		if (cnt > 0) {
			throw new ServiceException("当前字典键值已存在!");
		}
		return saveOrUpdate(dict);
	}

	@Override
	public String getKey(String code, String value) {
		return Func.toStr(baseMapper.getKey(code, value), StringPool.EMPTY);
	}
}
