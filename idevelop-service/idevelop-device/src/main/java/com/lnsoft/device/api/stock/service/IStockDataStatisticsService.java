package com.lnsoft.device.api.stock.service;

import com.lnsoft.device.api.stock.vo.CountyVO;
import com.lnsoft.device.api.stock.vo.GovernanceSituationVO;
import com.lnsoft.system.entity.Region;

import java.util.List;

/**
 * @author xyzadmin
 */

public interface IStockDataStatisticsService  {
	/**
	 * 治理情况统计
	 * @return
	 */
	List<GovernanceSituationVO> list(String flush)  throws Exception;

	/**
	 * 准确性统计
	 * @return
	 */
	List<GovernanceSituationVO> accurate();

	/**
	 * 完整性统计
	 * @return
	 */
	List<GovernanceSituationVO> complete();

	/**
	 * 及时率统计
	 * @return
	 */
	List<GovernanceSituationVO> timely();

	/**
	 * 各区县治理情况统计
	 * @param region
	 * @return 市级编码  如3701   3702
	 */
	List<CountyVO> county(String region,String flush) throws Exception;

	/**
	 * 地区枚举
	 * @return
	 */
    List<Region> regionList();
}
