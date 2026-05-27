package com.lnsoft.device.api.i6000.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.JsonObject;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.i6000.dto.I6000ExternalDTO;
import com.lnsoft.device.api.i6000.dto.I6000OriViewDTO;
import com.lnsoft.device.api.i6000.dto.I6000ReqManualDTO;
import com.lnsoft.device.api.i6000.dto.I6000RoomAndCabinetsDTO;
import com.lnsoft.device.api.i6000.entity.I6000External;
import com.lnsoft.system.entity.Tenant;

import java.util.List;
import java.util.Map;

/**
 * @author xyzadmin
 */
public interface II6000ExternalService extends BaseService<I6000External> {
	/**
	 * 手动新增外部数据
	 * @param i6000ExternalDTO
	 * @return
	 */
	Boolean saveExternal(I6000ExternalDTO i6000ExternalDTO);

	/**
	 * 查看详情
	 * @param i6000External
	 * @return
	 */
	I6000External detail(I6000External i6000External);


	/**
	 * 分页
	 * @param page
	 * @param i6000External
	 * @return
	 */
	IPage<I6000External> seleteI6000ExternalPage(IPage<I6000External> page, I6000External i6000External);

	/**
	 * 物理删除
	 * @param idList
	 */
	void delete(List<Long> idList);
	/**
	 * 手动新增外部数据
	 * @param i6000ExternalDTO
	 * @return
	 */
	Boolean insertExternal(I6000ExternalDTO i6000ExternalDTO);
	/**
	 * 手动新增外部数据
	 * @param i6000ExternalDTO
	 * @return
	 */
	Boolean addExternal(I6000ExternalDTO i6000ExternalDTO);


	boolean addCabinets(Map<String, Map<String,Object>> map);

	boolean insertCabinets(I6000ExternalDTO i6000ExternalDTO);

	List<I6000External> checkData(List<I6000External> i6000Externals);
	/**
	 * 获取i6000数据并新增
	 * @param i6000OriViewDTO
	 * @return
	 */
	Boolean getI6000AndAdd(I6000OriViewDTO i6000OriViewDTO);

	/**
	 * 机柜导入cmdb
	 * @return
	 */
	R inCmdb();

	/**
	 * 机柜导入cmdb测试
	 * @param id
	 * @return
	 */
	R inCmdbTest(String id);

	R inCmdbTry();

	/**
	 * 临时刷新机柜重复数据
	 * @param cabinetsNames
	 * @return
	 */
	R refresh(List<String> cabinetsNames);

	/**
	 * 刷新区域
	 * @param cabinetCodes
	 * @return
	 */
	R updateRegion(List<String> cabinetCodes);
}
