package com.lnsoft.device.api.asset.service;

import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.device.api.asset.dto.OverdueAssetSearchListDTO;
import com.lnsoft.device.api.asset.vo.BeOverdueAssetsVo;
import com.lnsoft.device.api.asset.vo.OverdueAssetVO;

import java.util.List;

/**
 * @author xyzadmin
 */
public interface IDeviceOverdueService {

	/**
	 * 老旧设备分类统计
	 * @return
	 */
	List<OverdueAssetVO> overdueDeviceStatistics(OverdueAssetSearchListDTO overdueAssetSearchListDTO);
	/**
	 * 投运年限分布
	 * @param overdueAssetVO
	 * @return
	 */
	List<OverdueAssetVO> oldDeviceAgeStatistics(OverdueAssetVO overdueAssetVO);
	/**
	 * 老旧设备分页查询
	 * @param beOverdueVo
	 * @return
	 */
	FeignCiCientity getList(BeOverdueAssetsVo beOverdueVo, Query query);
}
