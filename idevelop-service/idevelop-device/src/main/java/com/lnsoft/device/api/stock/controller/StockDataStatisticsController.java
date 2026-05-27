package com.lnsoft.device.api.stock.controller;

import com.alibaba.excel.EasyExcel;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.stock.service.IStockDataStatisticsService;
import com.lnsoft.device.api.stock.vo.CountyVO;
import com.lnsoft.device.api.stock.vo.GovernanceSituationVO;
import com.lnsoft.system.entity.Region;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author xyzadmin
 */
@RestController
@AllArgsConstructor
@RequestMapping("/staatistics")
@Api(value = "资产台账治理", tags = "资产台账治理接口")
public class StockDataStatisticsController extends IdevelopController {

	@Resource
	private IStockDataStatisticsService stockDataStatisticsService;
	/**
	 * 治理情况统计列表查询
	 */
	@GetMapping("/governance/situation")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "治理情况统计列表查询", notes = "")
	public R<List<GovernanceSituationVO>> list(String flush)  throws Exception{

		List<GovernanceSituationVO> governanceSituationVOList = stockDataStatisticsService.list(flush);
		return R.data(governanceSituationVOList);

	}
	@PostMapping("/governance/export")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "导出",notes = "")
	public void export(@RequestBody List<GovernanceSituationVO> list, HttpServletResponse response){
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("信息设备台账存量数据治理整体进度评价", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), GovernanceSituationVO.class).sheet("统计列表").doWrite(list);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	 * 治理情况准确率查询
	 */
	@GetMapping("/governance/accurate")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "治理情况准确率查询", notes = "")
	public R<List<GovernanceSituationVO>> accurate() {
		List<GovernanceSituationVO> governanceSituationVOList = stockDataStatisticsService.accurate();
		return R.data(governanceSituationVOList);

	}
	/**
	 * 治理情况完整率查询
	 */
	@GetMapping("/governance/complete")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "治理情况准确率查询", notes = "")
	public R<List<GovernanceSituationVO>> complete() {
		List<GovernanceSituationVO> governanceSituationVOList = stockDataStatisticsService.complete();
		return R.data(governanceSituationVOList);

	}
	/**
	 * 治理情况及时率查询
	 */
	@GetMapping("/governance/timely")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "治理情况准确率查询", notes = "")
	public R<List<GovernanceSituationVO>> timely() {
		List<GovernanceSituationVO> governanceSituationVOList = stockDataStatisticsService.timely();
		return R.data(governanceSituationVOList);

	}


	/**
	 * 地市区县治理情况统计接口
	 */
	@GetMapping("/governance/county")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "地市区县治理情况统计接口", notes = "")
	public R<List<CountyVO>> county(String region,String flush) throws Exception {
		List<CountyVO> map = stockDataStatisticsService.county(region,flush);
		return R.data(map);

	}

	/**
	 * 地区枚举
	 */
	@GetMapping("/governance/region")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "地区枚举", notes = "")
	public R<List<Region>> county() {
		List<Region> list = stockDataStatisticsService.regionList();
		return R.data(list);

	}
}
