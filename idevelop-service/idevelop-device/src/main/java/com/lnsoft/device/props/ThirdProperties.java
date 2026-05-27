package com.lnsoft.device.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xueli
 * @CreateTime: 2024/1/22 14:49
 * @Description: 映射配置文件中api实体
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "third")
public class ThirdProperties {

	// 是否开启erp同步 true:同步 false:不同步
	private Boolean apiErp;
	// 是否开启i6000同步 true:同步 false:不同步
	private Boolean apiI6000;
	// 是否使用I6000通用认证方式 true:使用 false:不使用
	private Boolean isGeneralI6000;
	// (新增/修改/删除)是否请求请求I6000  true:请求 false:不请求
	private Boolean isRequestI6000;

	// (数据治理)是否请求获取I6000的资产数据  true:请求 false:不请求
	private Boolean isGovernProperty;
	// (数据治理)是否请求ERP或请求I6000  true:请求ERP false:请求I6000
	private Boolean isErpOrI6000;

	// (仓库/机房/机柜)是否请求获取I6000的仓库/机房/机柜数据
	private Boolean isRoomWarehouseI6000;

	// (xtyth) 可以查看全部设备的地市
	private String regions;

	// 用于信通一体化平台同步管理员同步ERP是否需要新增I6000.
	private Boolean isAddI6000;

	// 根据信通一体化设备编码, 同步I6000系统数据(是否开启终端设备的台式机和笔记本同步, true: 开启, false: 不开启)
	private Boolean isSyncI6000;

	@Override
	public String toString() {
		return "ThirdProperties{" +
			"apiErp=" + apiErp +
			", apiI6000=" + apiI6000 +
			", isGeneralI6000=" + isGeneralI6000 +
			", isRequestI6000=" + isRequestI6000 +
			", isGovernProperty=" + isGovernProperty +
			", isErpOrI6000=" + isErpOrI6000 +
			", isRoomWarehouseI6000=" + isRoomWarehouseI6000 +
			", isAddI6000=" + isAddI6000 +
			'}';
	}
}


