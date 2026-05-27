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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.device.api.erp.dto.ErpTranstplnrDTO;
import com.lnsoft.device.api.erp.entity.ErpTranstplnr;
import com.lnsoft.device.api.erp.mapper.ErpTranstplnrMapper;
import com.lnsoft.device.api.erp.service.IErpService;
import com.lnsoft.device.api.erp.service.IErpTranstplnrService;
import com.lnsoft.device.api.erp.utils.TranstplnrCodeUtil;
import com.lnsoft.device.api.erp.vo.ErpTranstplnrVO;
import com.lnsoft.device.api.i6000.dto.I6000FuncDTO;
import com.lnsoft.device.api.i6000.service.II6000Service;
import com.lnsoft.device.constant.ErpConstant;
import com.lnsoft.device.constant.I6000Constant;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * ERP功能位置 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-23
 */
@Service

public class ErpTranstplnrServiceImpl extends BaseServiceImpl<ErpTranstplnrMapper, ErpTranstplnr> implements IErpTranstplnrService {
	@Resource
	private IErpService iErpService;
	@Resource
	private II6000Service ii6000Service;
	@Value(value = "${third.api-erp}")
	private boolean erpPush;

	@Override
	public IPage<ErpTranstplnrVO> selectErpTranstplnrPage(IPage<ErpTranstplnrVO> page, ErpTranstplnrVO erpTranstplnr) {
		IdevelopUser sysUser = SecureUtil.getUser();
		if (!StringUtils.hasLength(erpTranstplnr.getSwerk()) && !StringUtils.pathEquals(sysUser.getRegionCode(), "37")) {
			throw new ServiceException("需要当前用户维护工厂编码");
		}
		return page.setRecords(baseMapper.selectErpTranstplnrPage(page, erpTranstplnr));
	}

	/**
	 * 新增或修改 ERP功能位置
	 *
	 * @param erpTranstplnrDTO
	 * @return
	 */
	@Override
	public Map<String, String> saveOrUpdateNew(ErpTranstplnrDTO erpTranstplnrDTO) {
		// 电压等级编码 默认 07
		erpTranstplnrDTO.setZsbdydj("07");

		String swerk = erpTranstplnrDTO.getSwerk();

		String beber = swerk.startsWith("00") ? "003" : "004";
		erpTranstplnrDTO.setBeber(beber);

		String tplma = String.format("%s-%s-%s", "06", swerk, "T");
		erpTranstplnrDTO.setTplma(tplma);

		String operation = ErpConstant.C;
		ErpTranstplnr erpTranstplnr = this.getOne(new LambdaQueryWrapper<ErpTranstplnr>()
			.eq(ErpTranstplnr::getTrlnr, erpTranstplnrDTO.getTrlnr())
			.eq(ErpTranstplnr::getTplma, tplma)
			.eq(ErpTranstplnr::getSwerk, swerk));
		if (Objects.nonNull(erpTranstplnr)) {
			operation = ErpConstant.M;
		}
		erpTranstplnrDTO.setOperation(operation);
		Map<String, String> resultMap = new HashMap<>();
		if (erpPush){
			resultMap = iErpService.transTplnr(erpTranstplnrDTO);
		}
		if (StringUtils.pathEquals("S", resultMap.get("code"))) {
			erpTranstplnrDTO.setErpStatus(0);
			this.saveOrUpdate(erpTranstplnrDTO);
			// 组装I6000参数
			Map<String, Object> i6000ResultMap = new HashMap<>();
			try {
				I6000FuncDTO i6000FuncDTO = getI6000FuncDTO(erpTranstplnrDTO, operation);
				i6000ResultMap = ii6000Service.saveOrUpdateFunc(i6000FuncDTO);
				if (!CollectionUtils.isEmpty(i6000ResultMap) && StringUtils.pathEquals(i6000ResultMap.get(I6000Constant.CODE).toString(), "0")) {
					erpTranstplnrDTO.setI6000Status(0);
					erpTranstplnrDTO.setI6000Code(i6000FuncDTO.getCode());
				} else {
					erpTranstplnrDTO.setI6000Status(1);
					erpTranstplnrDTO.setI6000Message(String.valueOf(i6000ResultMap.get(I6000Constant.ERROR_MSG)));
				}
			} catch (Exception e) {
				erpTranstplnrDTO.setI6000Status(1);
				erpTranstplnrDTO.setI6000Message(String.valueOf(i6000ResultMap.get(I6000Constant.ERROR_MSG)));
			}
			this.saveOrUpdate(erpTranstplnrDTO);
		} else {
			erpTranstplnrDTO.setErpStatus(1);
			erpTranstplnrDTO.setI6000Status(1);
			this.saveOrUpdate(erpTranstplnrDTO);
		}
		return resultMap;
	}

	/**
	 * 组装I6000参数
	 *
	 * @param erpTranstplnrDTO
	 * @param operation
	 * @return
	 */
	@NotNull
	private static I6000FuncDTO getI6000FuncDTO(ErpTranstplnrDTO erpTranstplnrDTO, String operation) {
		I6000FuncDTO i6000FuncDTO = new I6000FuncDTO();

		// 功能位置编码
		i6000FuncDTO.setErpCode(erpTranstplnrDTO.getTrlnr());
		// 功能位置名称
		i6000FuncDTO.setName(erpTranstplnrDTO.getPltxt());
		// 工厂区域
		i6000FuncDTO.setDomain(erpTranstplnrDTO.getBeber());
		// 维护工厂
		i6000FuncDTO.setMaintenance(erpTranstplnrDTO.getSwerk());
		// 上级功能位置编码
		i6000FuncDTO.setUpflcode(erpTranstplnrDTO.getTplma());
		// 电压等级编码
		i6000FuncDTO.setVoltageClass(erpTranstplnrDTO.getZsbdydj());
		// 操作标识
		i6000FuncDTO.setOperation(operation);

		String uuid = new String();
		if (StringUtils.pathEquals(operation, ErpConstant.C)) {
			// 同步新增I6000
			uuid = "xtyth" + UUID.randomUUID().toString().replace("-", "");
		} else {
			// 同步修改I6000
			uuid = erpTranstplnrDTO.getI6000Code();
		}
		i6000FuncDTO.setCode(uuid);
		i6000FuncDTO.setObjId(uuid);
		return i6000FuncDTO;
	}

	/**
	 * 删除 ERP功能位置
	 *
	 * @param idList
	 */
	@Override
	public void deleteByTrlnr(List<String> idList) {
		try {
			for (String trlnr : idList) {
				ErpTranstplnr erpTranstplnr = this.getOne(new LambdaQueryWrapper<ErpTranstplnr>()
					.eq(ErpTranstplnr::getTrlnr, trlnr));
				if (StringUtils.pathEquals(ErpConstant.D, erpTranstplnr.getOperation())) {
					throw new RuntimeException("当前功能位置已经在ERP中删除, 请联系运维人员处理! ");
				}
				erpTranstplnr.setOperation(ErpConstant.D);
				ErpTranstplnrDTO erpTranstplnrDTO = new ErpTranstplnrDTO();
				BeanUtils.copyProperties(erpTranstplnr, erpTranstplnrDTO);
				// 删除ERP
				Map<String, String> map = new HashMap<>();
				if (erpPush){
					map = iErpService.transTplnr(erpTranstplnrDTO);
				}
				if (StringUtils.pathEquals("S", map.get("code"))) {
					erpTranstplnr.setIsDeleted(IdevelopConstant.DB_IS_DELETED);
					erpTranstplnr.setErpStatus(0);
					// 删除I6000
					Map<String, Object> i6000ResultMap = new HashMap<>();
					if (StringUtils.hasLength(erpTranstplnr.getI6000Code())) {
						try {
							i6000ResultMap = ii6000Service.deleteFunc(erpTranstplnr.getI6000Code());
							if (!CollectionUtils.isEmpty(i6000ResultMap) && StringUtils.pathEquals(i6000ResultMap.get(I6000Constant.CODE).toString(), "0")) {
								erpTranstplnrDTO.setI6000Status(0);
							} else {
								erpTranstplnr.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
								erpTranstplnrDTO.setI6000Status(1);
								erpTranstplnrDTO.setI6000Message(String.valueOf(i6000ResultMap.get(I6000Constant.ERROR_MSG)));
							}
						} catch (Exception e) {
							erpTranstplnr.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
							erpTranstplnrDTO.setI6000Status(1);
							erpTranstplnrDTO.setI6000Message(String.valueOf(i6000ResultMap.get(I6000Constant.ERROR_MSG)));
						}
					}
					// 删除信通一体化.
					this.updateById(erpTranstplnr);
				} else {
					erpTranstplnr.setErpStatus(0);
					erpTranstplnr.setI6000Status(0);
					this.updateById(erpTranstplnr);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("功能位置删除失败: " + e.getMessage());
		}
	}

	@Override
	public ErpTranstplnr getDetail(ErpTranstplnr erpTranstplnr) {
		return baseMapper.getDetail(erpTranstplnr);
	}


	/**
	 * 随机生成功能位置编码
	 *
	 * @param swerk 维护工厂
	 * @return
	 */
	@Override
	public String buildCode(String swerk) {
		if (!StringUtils.hasLength(swerk)) {
			throw new RuntimeException("维护工厂不能为空");
		}
		String code = TranstplnrCodeUtil.generateUniqueCode();
		String trlnr = String.format("%s-%s-%s-%s", "06", swerk, "T", code);
		String tplma = String.format("%s-%s-%s", "06", swerk, "T");

		ErpTranstplnrDTO erpTranstplnrDTO = new ErpTranstplnrDTO();
		erpTranstplnrDTO.setTrlnr(trlnr);
		erpTranstplnrDTO.setTplma(tplma);
		erpTranstplnrDTO.setSwerk(swerk);

		Boolean result = Boolean.TRUE;
		while (result) {
			ErpTranstplnr erpTranstplnr = this.getOne(new LambdaQueryWrapper<ErpTranstplnr>()
				.eq(ErpTranstplnr::getTrlnr, trlnr)
				.eq(ErpTranstplnr::getTplma, tplma)
				.eq(ErpTranstplnr::getSwerk, swerk));
			if (Objects.isNull(erpTranstplnr)) {
				result = Boolean.FALSE;
			}
		}

		return trlnr;
	}
}
