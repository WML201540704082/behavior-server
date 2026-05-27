package com.lnsoft.device.api.safeaccess.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.device.api.safeaccess.dto.SafeaccessSwitcheDTO;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSwitcheService;
import com.lnsoft.device.entity.SafeaccessSwitche;
import com.lnsoft.device.so.SafeaccessSwitcheSO;
import com.lnsoft.device.utils.EasyExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * 交换机管理 控制器
 *
 * @author Idevelop
 * @since 2024-03-07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/safe/access/switche")
@Api(value = "交换机管理", tags = "交换机管理接口")
public class SafeaccessSwitcheController extends IdevelopController {

	private ISafeaccessSwitcheService safeaccessSwitcheService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入safeaccessSwitche")
	public R<SafeaccessSwitche> detail(SafeaccessSwitche safeaccessSwitche) {
		SafeaccessSwitche detail = safeaccessSwitcheService.getOne(Condition.getQueryWrapper(safeaccessSwitche));
		return R.data(detail);
	}

	/**
	 * 详情
	 */
	@GetMapping("/change/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情1", notes = "传入safeaccessSwitche")
	public R<SafeaccessSwitche> detail1(SafeaccessSwitche safeaccessSwitche) {

		IdevelopUser sysUser = SecureUtil.getUser();
		QueryWrapper<SafeaccessSwitche> queryWrapper = Condition.getQueryWrapper(safeaccessSwitche);
		queryWrapper.lambda().likeRight(SafeaccessSwitche::getRegionCode, sysUser.getRegionCode());
		queryWrapper.lambda().orderByDesc(SafeaccessSwitche::getCreateTime);

		SafeaccessSwitche detail = safeaccessSwitcheService.getOne(Condition.getQueryWrapper(safeaccessSwitche));
		if (Objects.isNull(detail)) {
			return R.fail("该设备未查询网络设备入网信息，请联系项目组处理！");
		}
		return R.data(detail);
	}

	/**
	 * 分页 交换机管理
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入safeaccessSwitche")
	public R<IPage<SafeaccessSwitche>> list(SafeaccessSwitcheSO safeaccessSwitche, Query query) {
		IPage<SafeaccessSwitche> pages = safeaccessSwitcheService.selectSafeaccessSwitchePage(safeaccessSwitche, query);
		return R.data(pages);
	}


	/**
	 * 新增 交换机管理
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入safeaccessSwitche")
	public R save(@Valid @RequestBody SafeaccessSwitche safeaccessSwitche) {
		return R.status(safeaccessSwitcheService.save(safeaccessSwitche));
	}

	/**
	 * 修改 交换机管理
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入safeaccessSwitche")
	public R update(@Valid @RequestBody SafeaccessSwitche safeaccessSwitche) {
		return R.status(safeaccessSwitcheService.updateById(safeaccessSwitche));
	}


	/**
	 * 删除 交换机管理
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(safeaccessSwitcheService.deleteLogic(Func.toLongList(ids)));
	}


	/**
	 * 交换机同步radius
	 */
	@GetMapping("/getRadiusState")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "交换机同步radius", notes = "传入id")
	public R<Boolean> getRadiusState(@ApiParam(value = "交换机id", required = true) @RequestParam String id) {
		return R.data(safeaccessSwitcheService.getRadiusState(id));
	}


	@PostMapping("/export")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "交换机信息导出", notes = "传入safeaccessSwitche")
	public void export(@RequestBody SafeaccessSwitche safeaccessSwitche, HttpServletResponse response) {
		// 数据权限
		IdevelopUser sysUser = SecureUtil.getUser();
		QueryWrapper<SafeaccessSwitche> queryWrapper = Condition.getQueryWrapper(safeaccessSwitche);
		queryWrapper.lambda().likeRight(SafeaccessSwitche::getRegionCode, sysUser.getRegionCode());
		queryWrapper.lambda().orderByDesc(SafeaccessSwitche::getCreateTime);

		List<SafeaccessSwitche> safeaccessSwitches = safeaccessSwitcheService.list(queryWrapper);
		try {
			List<SafeaccessSwitcheDTO> safeaccessSwitcheDTOS = Convert.convert(new TypeReference<List<SafeaccessSwitcheDTO>>() {
			}, safeaccessSwitches);

			safeaccessSwitcheDTOS.forEach(item -> {

				// 网络设备用途类型
				if (StringUtils.containsAny(item.getIs3(), "01","1131178741792770")) {
					item.setIs3("核心");
				}
				if (StringUtils.containsAny(item.getIs3(), "02","1131178741792774")) {
					item.setIs3("汇聚");
				}
				// 接入
				if (StringUtils.containsAny(item.getIs3(), "03","1131178741792784")) {
					item.setIs3("接入");
				}
				// 工作状态
				item.setSwState(StringUtils.contains(item.getSwState(), "1")? "运行" : "停用");

			});

			//  接入
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			String fileName = URLEncoder.encode("导出", StandardCharsets.UTF_8.name());
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			EasyExcel.write(response.getOutputStream(), SafeaccessSwitcheDTO.class)
					.registerWriteHandler(new HorizontalCellStyleStrategy(EasyExcelUtils.headStyle(), EasyExcelUtils.contentStyle()))
					.sheet("交换机入网数据")
					.doWrite(safeaccessSwitcheDTOS);
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
