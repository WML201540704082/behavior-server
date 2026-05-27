/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.erp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.api.erp.entity.ErpKostl;
import com.lnsoft.device.api.erp.entity.ErpMaintain;
import com.lnsoft.device.api.erp.mapper.ErpKostlMapper;
import com.lnsoft.device.api.erp.service.IErpKostlService;
import com.lnsoft.device.api.erp.service.IErpMaintainService;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.erp.vo.ErpKostlVO;
import com.lnsoft.device.api.erp.vo.ErpMaintainKostlVO;
import com.lnsoft.device.api.erp.vo.ErpXtythMaintainVO;
import com.lnsoft.device.constant.ErpConstant;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * erp成本中心 服务实现类
 *
 * @author Idevelop
 * @since 2024-04-02
 */
@Service
@AllArgsConstructor
public class ErpKostlServiceImpl extends BaseServiceImpl<ErpKostlMapper, ErpKostl> implements IErpKostlService {

	private IErpService iErpService;
	private IErpMaintainService iErpMaintainService;

	@Override
	public IPage<ErpKostlVO> selectErpKostlPage(IPage<ErpKostlVO> page, ErpKostlVO erpKostl) {
		return page.setRecords(baseMapper.selectErpKostlPage(page, erpKostl));
	}

	/**
	 * 懒加载 ERP维护工厂(对应单位)
	 *
	 * @return
	 */
	@Override
	public List<ErpXtythMaintainVO> lazyMaintain() {
		List<ErpMaintain> erpMaintainList = iErpMaintainService.list(Condition.getQueryWrapper(new ErpMaintain()));
		List<ErpXtythMaintainVO> erpXtythMaintainVOS = erpMaintainList.stream().map(maintain ->
			ErpXtythMaintainVO.builder()
				.code(maintain.getCode())
				.name(maintain.getName())
				.unitSort(1)
				.build()
		).collect(Collectors.toList());

		return erpXtythMaintainVOS;
	}


	/**
	 * 懒加载 ERP维护工厂(对应单位)和成本中心
	 *
	 * @return
	 */
	@Override
	public ErpMaintainKostlVO lazyMaintainKostl(String unitCode) {

		ErpMaintain erpMaintain = new ErpMaintain();
		erpMaintain.setCode(unitCode);
		ErpMaintain erpMaintainOne = iErpMaintainService.getOne(Condition.getQueryWrapper(erpMaintain));

		ErpKostl erpKostl = new ErpKostl();
		erpKostl.setSwerk(unitCode);
		List<ErpKostl> erpKostlList = this.list(Condition.getQueryWrapper(erpKostl));

		List<ErpMaintainKostlVO.Children> childrenList = erpKostlList.stream().map(kostl ->
				ErpMaintainKostlVO.Children.builder()
					.code(kostl.getKostl())
					.name(kostl.getKostlT())
					.deptSort(2)
					.build())
			.collect(Collectors.toList());

		ErpMaintainKostlVO erpMaintainKostlVO = ErpMaintainKostlVO.builder()
			.code(erpMaintainOne.getCode())
			.name(erpMaintainOne.getName())
			.childrenList(childrenList)
			.unitSort(1)
			.build();

		return erpMaintainKostlVO;
	}


	/**
	 * 同步ERP成本中心
	 * 先删除后新增
	 *
	 * @param swerk
	 * @return
	 */
	@Override
	public boolean synchronousRefresh(String swerk) {
		if (StringUtils.hasLength(swerk)) {
			List<ErpKostl> erpKostlList = getErpKostls(swerk);
			ErpKostl erpKostl = new ErpKostl();
			erpKostl.setSwerk(swerk);
			Set<String> swerkSet = new HashSet<>();
			swerkSet.add(swerk);
			baseMapper.delectErpKostlList(swerkSet);
			return this.saveBatch(erpKostlList);
		}
		Query query = new Query();
		query.setCurrent(0);
		query.setSize(10000);
		IPage<ErpMaintain> page = iErpMaintainService.page(Condition.getPage(query), Condition.getQueryWrapper(new ErpMaintain()));
		List<ErpKostl> erpKostlListAll = new ArrayList<>();
		Set<String> swerkSet = new HashSet<>();
		for (ErpMaintain record : page.getRecords()) {
			List<ErpKostl> erpKostlList = getErpKostls(record.getCode());
			erpKostlListAll.addAll(erpKostlList);
			swerkSet.add(record.getCode());
		}
		baseMapper.delectErpKostlList(swerkSet);
		return this.saveBatch(erpKostlListAll);
	}

	@NotNull
	private List<ErpKostl> getErpKostls(String record) {
		List<Map<String, String>> kostl = iErpService.getKostl(record);
		return kostl.stream().map(item -> {
			ErpKostl erpKostl = new ErpKostl();
			erpKostl.setKostl(item.get(ErpConstant.KOSTL));
			erpKostl.setKostlT(item.get(ErpConstant.KOSTL_T));
			erpKostl.setKostlLt(item.get(ErpConstant.KOSTL_LT));
			erpKostl.setSwerk(item.get(ErpConstant.SWERK));
			erpKostl.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
			erpKostl.setKoslStatus(0);
			return erpKostl;
		}).collect(Collectors.toList());
	}


}
