package com.lnsoft.device.props;

import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.constant.CmdbCientityConstant;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: CmdbCiIdProperties
 * @description:
 * @author: zhangs
 * @create: 2024-04-20 13:45
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "cmdb-enum-cientity-id")
public class CmdbCientityProperties {

	// 电压等级 直流220V
	private String dc220v;
	// 字典-是
	private String yesNo;
	// 字典-否
	private String noNo;
	// 设备状态：库存备用
	private String deviceStatus0;
	// 设备状态：在运
	private String inOperation;
	// 设备状态：已报废
	private String scarpStatus;
	// 设备状态：退运在库
	private String returnWarehouse;
	// ERP转资状态 已转资
	private String erpTransferStatus1;
	// ERP转资状态 未转资
	private String erpTransferStatus2;
	// ERP转资状态 转资中
	private String erpTransferStatus3;
	// ERP转资状态 转资失败
	private String erpTransferStatus4;
	// 设备来源 统一纳管
	private String deviceSource;
	// 设备来源 非统一纳管
	private String noDeviceSource;
	// 交换机网络设备用途接入层
	private String switchesType;
	// 网络交换机
	private String T10302;
	// 字典枚举ID-存储交换机
	private String T10205;
	// 字典枚举ID-光纤交换机
	private String T10307;
	// 台式机
	private String T10501;
	// 手持终端
	private String T10502;
	// 笔记本电脑
	private String T10503;
	// 工作站
	private String T10504;
	// 平板电脑
	private String T10505;
	// 打印机
	private String T10701;
	// 数字式绘图仪
	private String T10702;
	// 复印机
	private String T10703;
	// 传真机
	private String T10704;
	// 影印一体机
	private String T10705;
	// 扫描仪
	private String T10706;
	// 投影仪
	private String T10707;
	// 屏幕设备
	private String T10708;
	// 分拣装置
	private String T10712;
	// 自助缴费终端
	private String T10525;
	// 日志审计
	private String T10427;
	// 标准化抢修库房管理系统
	private String T10713;
	// 移动工作站
	private String T10528;
	// IAD终端
	private String T10721;
	// 其他终端
	private String T10507;
	// 计量终端
	private String T10715;
	// 排队机
	private String T10523;
	// 查询机
	private String T10520;
	// 视频会议终端
	private String T10527;
	// 考勤机
	private String T10720;
	// 计量周转柜
	private String T10723;
	// 图像监控
	private String T10722;
	//其他外部设备
	private String T10709;
	// POS终端
	private String T10521;
	// 云终端
	private String T10508;
	// IP电话
	private String T10716;
	// 刀片机
	private String T10102;
	// 小型机
	private String T10103;
	// PC服务器
	private String T10101;
	// 路由器
	private String T10301;
	// 媒体控制器
	private String T10605;
	// 门禁设备
	private String T10608;
	// 图形采集设备
	private String T10609;
	// kvm
	private String T10604;
	// 机柜
	private String T10603;
	// 温湿度传感器
	private String T10610;
	// UPS
	private String T10901;
	//蓄电池组
	private String T10902;
	// 机房空调
	private String T10903;
	// 入侵监测设备
	private String T10403;
	// 其他安全设备
	private String T10409;
	// 入侵防御设备
	private String T10404;
	// 防病毒网关设备
	private String T10402;
	// 网络隔离设备
	private String T10408;
	// 防火墙
	private String T10401;
	// 智能钥匙柜
	private String T10724;
	// 漏洞扫描设备
	private String T10406;
	// 流量监测设备
	private String T10405;
	// 主机设备
	private String T101;
	// 存储设备
	private String T102;
	// 网络设备
	private String T103;
	// 安全设备
	private String T104;
	// 终端设备
	private String T105;
	// 辅助设备
	private String T106;
	// 办公设备
	private String T107;
	// 备品备件
	private String T108;
	// 基础设施
	private String T109;
	// ERP转资状态 已转资
	private String finishTransfer;
	// 工厂区域 县公司
	private String countyFactoryArea;
	// 工厂区域 省/市公司
	private String cityFactoryArea;
	// 是否治理 - 否
	private String governNo;
	// 是否治理 - 是
	private String governYes;
	// 是否治理 - 非治理数据
	// private String governMistake;
	// 采购方式 - 国网集采
	private String procureType0;
	// 采购方式 - 单位自购
	private String procureType1;
	// CPU架构 - arm架构
	private String cpu_arm;
	// CPU架构 - x86架构
	private String cpu_x86;
	// 计量单位 - 台
	private String jldw_tai;
	// 产权状态 正常
	private String propStatus0;
	// 运维等级 三级
	private String operationLevel0;
	// 售后状态 正常
	private String afterStatus0;
	// CPU品牌 - 华为麒麟
	private String cpuBrand0;
	// CPU品牌 - 飞腾
	private String cpuBrand1;
	// CPU品牌 - 海光
	private String cpuBrand2;
	// CPU品牌 - 兆芯
	private String cpuBrand3;
	// CPU品牌 - 其他
	private String cpuBrand4;

	// 枚举值类型 - 国网库(I6000平台) (prod)
	private String dictValueType0;
	// 枚举值类型 - 自建库(一体化平台) (prod)
	private String dictValueType1;

	// 操作系统发行版本 - 统信系统
	private String releaseVersion0;
	// 操作系统发行版本 - 银河麒麟 (prod)
	private String releaseVersion1;

	// 所属网络 - 外网-统一出口外网
	private String network0;
	// 所属网络 - 外网-集体企业外网
	private String network1;
	// 所属网络 - 内网
	private String network2;
	// 所属网络 - 未联网
	private String network3;

	// 资产台账来源系统 - 信通一体化-入库管理 - 01
	private String sourceSystem1;
	// 资产台账来源系统 - 信通一体化-设备投运 - 02
	private String sourceSystem2;

	// 制造国家和地区 - 中国 - 01
	private String countryArea1;

	// 线站标识 - 00000000000000000 - 01
	private String lineStation01;

	private static final Map<String, String> CMDB_CIENTITY_MAP = new HashMap<>();

	@PostConstruct
	private void init() {
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.LINE_STATION_SIGN_01, this.lineStation01);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.RELEASE_VERSION_0, this.releaseVersion0);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.RELEASE_VERSION_1, this.releaseVersion1);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.DICT_VALUE_TYPE_0, this.dictValueType0);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.DICT_VALUE_TYPE_1, this.dictValueType1);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.CPU_BRAND_0, this.cpuBrand0);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.CPU_BRAND_1, this.cpuBrand1);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.CPU_BRAND_2, this.cpuBrand2);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.CPU_BRAND_3, this.cpuBrand3);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.CPU_BRAND_4, this.cpuBrand4);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.AFTER_STATUS_0, this.afterStatus0);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.OPERATION_LEVEL_0, this.operationLevel0);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.PROP_STATUS_0, this.propStatus0);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.CPU_ARM, this.cpu_arm);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.CPU_X86, this.cpu_x86);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.DC_220V, this.dc220v);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.YES, this.yesNo);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.NO_NO, this.noNo);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.DEVICE_STATUS_0, this.deviceStatus0);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.ERP_STATUS_1, this.erpTransferStatus1);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.ERP_STATUS_2, this.erpTransferStatus2);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10501, this.T10501);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10502, this.T10502);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10503, this.T10503);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10504, this.T10504);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10505, this.T10505);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10701, this.T10701);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10702, this.T10702);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10703, this.T10703);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10704, this.T10704);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10705, this.T10705);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10706, this.T10706);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10707, this.T10707);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10708, this.T10708);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10302, this.T10302);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T101, this.T101);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T102, this.T102);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T103, this.T103);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T104, this.T104);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T105, this.T105);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T106, this.T106);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T107, this.T107);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T108, this.T108);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T109, this.T109);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.IN_OPERATION, this.inOperation);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.RETURN_WAREHOUSE, this.returnWarehouse);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.DEVICE_SOURCE, this.deviceSource);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.NO_DEVICE_SOURCE, this.noDeviceSource);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.SWITCHES_TYPE, this.switchesType);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10205, this.T10205);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10307, this.T10307);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10712, this.T10712);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10525, this.T10525);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10427, this.T10427);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10713, this.T10713);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10528, this.T10528);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10721, this.T10721);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10507, this.T10507);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10715, this.T10715);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10523, this.T10523);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10520, this.T10520);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10527, this.T10527);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10720, this.T10720);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10723, this.T10723);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10722, this.T10722);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10521, this.T10521);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10508, this.T10508);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10716, this.T10716);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10102, this.T10102);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10103, this.T10103);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10101, this.T10101);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10301, this.T10301);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10605, this.T10605);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10603, this.T10603);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10608, this.T10608);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10609, this.T10609);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10604, this.T10604);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10610, this.T10610);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10901, this.T10901);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10903, this.T10903);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10403, this.T10403);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10409, this.T10409);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10404, this.T10404);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10402, this.T10402);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10408, this.T10408);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10401, this.T10401);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10724, this.T10724);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10406, this.T10406);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.T10405, this.T10405);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.FINISH_TRANSFER, this.finishTransfer);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.SCRAP_STATUS, this.scarpStatus);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.COUNTY_FACTORY_AREA, this.countyFactoryArea);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.CITY_FACTORY_AREA, this.cityFactoryArea);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.GOVERN_NO, this.governNo);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.GOVERN_YES, this.governYes);
		// CMDB_CIENTITY_MAP.put(CmdbCientityConstant.GOVERN_MISTAKE, this.governMistake);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.PROCURE_TYPE_0, this.procureType0);
		CMDB_CIENTITY_MAP.put(CmdbCientityConstant.PROCURE_TYPE_1, this.procureType1);
	}

	/**
	 * 获取模型枚举项ID
	 */
	public String getCientityId(String constant) {

		String cmdbAttrId = CMDB_CIENTITY_MAP.get(constant);
		if (StringUtils.isEmpty(cmdbAttrId)) {
			throw new ServiceException("未查询到需要的枚举项ID");
		}

		return cmdbAttrId;
	}

	/**
	 * 获取指定模型枚举项ID
	 *
	 * @param modelFlag 查询模型标识
	 * @return List
	 */
	public List<String> getModelIdList(String modelFlag) {
		List<String> modelIdList = new ArrayList<>();
		if (Constants.SWITCHES_TYPE.equals(modelFlag)) {
			modelIdList.add(this.T10302);
			modelIdList.add(this.T10205);
			modelIdList.add(this.T10307);
		}
		if (Constants.DEVICE_SAFE_ACCESS_TYPE.equals(modelFlag)) {
			modelIdList.add(this.T10701);
			modelIdList.add(this.T10503);
			modelIdList.add(this.T10501);
			modelIdList.add(this.T10712);
			modelIdList.add(this.T10525);
			modelIdList.add(this.T10427);
			modelIdList.add(this.T10713);
			modelIdList.add(this.T10528);
			modelIdList.add(this.T10721);
			// 其他终端
			modelIdList.add(this.T10507);
			modelIdList.add(this.T10715);
			modelIdList.add(this.T10523);
			modelIdList.add(this.T10520);
			modelIdList.add(this.T10527);
			modelIdList.add(this.T10504);
			modelIdList.add(this.T10720);
			modelIdList.add(this.T10723);
			modelIdList.add(this.T10722);
			modelIdList.add(this.T10521);
			modelIdList.add(this.T10508);
			modelIdList.add(this.T10716);
			modelIdList.add(this.T10102);
			modelIdList.add(this.T10103);
			modelIdList.add(this.T10101);
			// 网络路由器
			modelIdList.add(this.T10301);
			modelIdList.add(this.T10708);
			modelIdList.add(this.T10703);
			modelIdList.add(this.T10705);
			modelIdList.add(this.T10605);
			modelIdList.add(this.T10608);
			// 图形采集设备
			modelIdList.add(this.T10609);
			// KVM切换器
			modelIdList.add(this.T10604);
			modelIdList.add(this.T10610);
			// 信息UPS
			modelIdList.add(this.T10901);
			// T10903
			modelIdList.add(this.T10903);
			modelIdList.add(this.T10403);
			modelIdList.add(this.T10409);
			modelIdList.add(this.T10404);
			// T10402
			modelIdList.add(this.T10402);
			modelIdList.add(this.T10408);
			modelIdList.add(this.T10401);
			modelIdList.add(this.T10724);
			modelIdList.add(this.T10406);
			modelIdList.add(this.T10405);
			// T10709
			modelIdList.add(this.T10709);
		}
		if (CollectionUtils.isEmpty(modelIdList)) {
			throw new ServiceException("未查询到需要的枚举项ID");
		}
		return modelIdList;
	}
}
