package com.lnsoft.device.api.asset.service;

import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.device.api.asset.dto.DevicePatentDistributionDTO;
import com.lnsoft.device.api.asset.dto.DevicePatentOnlineDTO;
import com.lnsoft.device.api.asset.vo.*;

import java.util.List;
import java.util.Map;

public interface IDevicePatentService {

	/**
	 * 获取信创设备列表
	 *
	 * @param deviceInfoVo
	 * @return
	 */
	FeignCiCientity deviceList(DeviceInfoVo deviceInfoVo);

	/**
	 * 设备数量计算
	 *
	 * @return
	 */
	Map<String, DevicePatentNumberVO> number();

	/**
	 * 采购方式概览
	 *
	 * @return
	 */
	List<DevicePatentPurchaseVO> purchase();

	/**
	 * 数据概览-分发情况
	 *
	 * @return
	 */
	List<DevicePatentDistributionVO> distribution(DevicePatentDistributionDTO devicePatentDistributionDTO);

	/**
	 * 数据概览-在线情况
	 *
	 * @return
	 */
	List<DevicePatentOnlineVO> online();

	/**
	 * 在线情况统计
	 *
	 * @param patentOnlineDTO
	 * @return
	 */
	List<DevicePatentOnlineVO> onlineStatistics(DevicePatentOnlineDTO patentOnlineDTO);

	/**
	 * 在线情况统计 - 单位
	 *
	 * @return
	 */
	List<Map<String, String>> onlineStatisticsUnit(DevicePatentOnlineDTO patentOnlineDTO);

	/**
	 * 软硬件分布 - 操作系统
	 *
	 * @return
	 */
	List<DevicePatentOperatingVO> operating();

	/**
	 * 软硬件分布-芯片架构(ARM)
	 *
	 * @return
	 */
	DevicePatentOperatingVO frameworkArm();

	/**
	 * 软硬件分布-芯片架构(X86)
	 *
	 * @return
	 */
	DevicePatentOperatingVO frameworkX86();

	/**
	 * 软硬件分布-品牌分布
	 *
	 * @return
	 */
	List<DevicePatentOperatingVO> brand();

	/**
	 * 7日内每日在线数量趋势
	 *
	 * @return
	 */
	List<DevicePatentOperatingVO> onlineTrend();

	/**
	 * 设备替代数据
	 *
	 * @return
	 */
	Map<String, DevicePatentReplaceVO> replace();

	/**
	 * 数据概览-最后同步时间
	 *
	 * @return
	 */
	Map<String, String> onlineTime();

}
