package com.lnsoft.device.api.asset.task;

import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.feign.ICmdbClient;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.device.api.asset.dto.OverdueAssetSearchListDTO;
import com.lnsoft.device.api.asset.entity.DeviceAssetCaching;
import com.lnsoft.device.api.asset.service.IDeviceAssetCachingService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;

import com.lnsoft.device.constant.CmdbCientityConstant;
import com.lnsoft.device.eums.Expression;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.constant.CmdbAttrConstant;
import com.lnsoft.device.vo.CiCientitySearchVO;

import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.feign.IDeptClient;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author xyzadmin
 */
@EnableScheduling
@Component
@AllArgsConstructor
public class DeviceAssetTask {
	private ICmdbService cmdbService;
	private IDeviceAssetCachingService deviceAssetCachingService;
	private ICmdbClient cmdbClient;
	private IDeptClient deptClient;
	private CmdbCientityProperties cmdbCientityProperties;
	private final String TYPE = "CORP";


	/**
	 * 任务 老旧设备缓存表添加资产数据
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateAsset() {

		String operation = cmdbCientityProperties.getCientityId(CmdbCientityConstant.IN_OPERATION);
		String warehouse = cmdbCientityProperties.getCientityId(CmdbCientityConstant.RETURN_WAREHOUSE);
		IdevelopUser user = SecureUtil.getUser();
		R<List<Map<String, Object>>> categoryList = cmdbClient.feignGetCiCientityDictList(1097745625841664L);
		List<Map<String, Object>> categoryListData = categoryList.getData();
		//获取单位数据
		List<Dept> deptList = deptClient.list().getData();
		Query query = new Query();
		query.setCurrent(1);
		query.setSize(999999999);
		// 根据单位过滤
		ArrayList<DeviceAssetCaching> list = new ArrayList<>();
		for (Dept dept : deptList) {
			if (TYPE.equals(dept.getType())){
				OverdueAssetSearchListDTO search = new OverdueAssetSearchListDTO();
				// 设备分类过滤
				for (Map<String, Object> category : categoryListData) {
					R<List<Map<String, Object>>> typeData = cmdbClient.feignGetCiEntityDictListByPid(1097745969774592L, Long.valueOf(String.valueOf(category.get("dictKey"))));
					List<Map<String, Object>> typeList = typeData.getData();
					// 设备类型过滤
					for (Map<String, Object> type : typeList) {

						search.setReceiveUnitCode(String.valueOf(dept.getId()));
						search.setDeviceCategoryCode(String.valueOf(category.get("dictKey")));
						search.setDeviceTypeCode(String.valueOf(type.get("dictKey")));
						List<CiCientitySearchVO> searchVOS = CiCientitySearchVO.convertCiCientitySearchVO(search);
						CiCientitySearchVO searchVO = new CiCientitySearchVO();
						searchVO.setAttrName(CmdbAttrConstant.OLD_MARK);
						searchVO.setAttrValue("1");
						searchVO.setExpression(Expression.EQUAL);
						searchVOS.add(searchVO);
						CiCientitySearchVO searchVO1 = new CiCientitySearchVO();
						searchVO1.setAttrName("");
						searchVO1.setExpression(Expression.LIKE);
						searchVO1.setAttrValue(operation+","+warehouse);
						searchVOS.add(searchVO1);
						FeignCiCientity jsonObject = cmdbService.getCiCientityListByClaccify(searchVOS, query);
						List<Map<String, Object>> deviceData = jsonObject.getData();
						Double sum = 0.00;
						for (Map<String, Object> map : deviceData) {
							Object assetOriginal = map.get(CmdbAttrConstant.ASSET_ORIGINAL);
							if (ObjectUtil.isNotEmpty(assetOriginal)){
								Double asset = Double.parseDouble(String.valueOf(assetOriginal));
								sum +=asset;
							}
						}
						DeviceAssetCaching deviceAssetCaching = new DeviceAssetCaching();
						deviceAssetCaching.setDept(String.valueOf(dept.getId()));
						deviceAssetCaching.setRegionCode(dept.getRegionCode());
						deviceAssetCaching.setDeviceTypeCode(String.valueOf(type.get("dictKey")));
						deviceAssetCaching.setDeviceCategoryCode(String.valueOf(category.get("dictKey")));
						deviceAssetCaching.setAssetOriginalSum(sum);
						DeviceAssetCaching assetCaching = deviceAssetCachingService.getOneData(deviceAssetCaching);
						if (assetCaching!=null){
							assetCaching.setId(assetCaching.getId());
							assetCaching.setCreateTime(new Date());
						}
						deviceAssetCaching.setIsDeleted(0);
						deviceAssetCaching.setUpdateTime(new Date());
//						deviceAssetCaching.setCreateUser(user.getUserId());
						list.add(deviceAssetCaching);

					}
				}
			}
		}
		deviceAssetCachingService.saveOrUpdateBatch(list);
	}

}
