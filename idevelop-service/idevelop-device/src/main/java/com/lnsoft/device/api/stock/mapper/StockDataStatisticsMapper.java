package com.lnsoft.device.api.stock.mapper;

import com.lnsoft.system.entity.Region;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xyzadmin
 */
@Mapper
public interface StockDataStatisticsMapper {


	Integer getDeviceType(@Param("category") String dictKey, @Param("region") String category);

	/**
	 * 根据市编码获取区县编码
	 * @param region
	 * @return
	 */
    List<Map<String,String>> getRegion(String region);

	/**
	 * 获取地市编码
	 * @return
	 */
	List<Map<String, String>> getRegionCity();

	/**
	 * 地区枚举
	 * @return
	 */
    List<Region> getRegionList();

}
