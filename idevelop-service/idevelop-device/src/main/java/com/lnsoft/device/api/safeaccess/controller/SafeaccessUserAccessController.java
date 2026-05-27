package com.lnsoft.device.api.safeaccess.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.boot.ctrl.IdevelopController;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.safeaccess.dto.DatchSyncRadiusDTO;
import com.lnsoft.device.api.safeaccess.dto.SafeUserAccessExportDTO;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessService;
import com.lnsoft.device.dto.SafeaccessUserAccessDTO;
import com.lnsoft.device.entity.SafeaccessUserAccess;
import com.lnsoft.device.so.SafeaccessUserAccessSO;
import com.lnsoft.device.utils.EasyExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户入网信息表 控制器
 *
 * @author Idevelop
 * @since 2024-03-09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/safe/access/user")
@Api(value = "用户入网信息表", tags = "用户入网信息接口")
public class SafeaccessUserAccessController extends IdevelopController {

    private ISafeaccessUserAccessService safeaccessUserAccessService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入safeaccessUserAccess")
    public R<SafeaccessUserAccessDTO> detail(SafeaccessUserAccess safeaccessUserAccess) {
        SafeaccessUserAccessDTO detail = safeaccessUserAccessService.customGetOne(safeaccessUserAccess);
        return R.data(detail);
    }

    /**
     * 分页 终端用户入网信息表
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入safeaccessUserAccess")
    public R<IPage<SafeaccessUserAccessDTO>> list(SafeaccessUserAccessSO safeaccessUserAccess, Query query) {
        // 数据权限
        IdevelopUser sysUser = SecureUtil.getUser();
        IPage<SafeaccessUserAccessDTO> pages = safeaccessUserAccessService.customPage(safeaccessUserAccess, query, sysUser);
        return R.data(pages);
    }

    /**
     * 新增 终端用户入网信息表
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "新增", notes = "传入safeaccessUserAccess")
    public R save(@Valid @RequestBody SafeaccessUserAccess safeaccessUserAccess) {
        return R.status(safeaccessUserAccessService.save(safeaccessUserAccess));
    }

    /**
     * 修改 终端用户入网信息表
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "修改", notes = "传入safeaccessUserAccess")
    public R update(@Valid @RequestBody SafeaccessUserAccess safeaccessUserAccess) {
        return R.status(safeaccessUserAccessService.updateById(safeaccessUserAccess));
    }


    /**
     * 删除 终端用户入网信息表
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestBody List<Long> ids) {
        return R.status(safeaccessUserAccessService.deleteLogic(ids));
    }

    /**
     * 根据ip地址查询
     */
    @GetMapping("/fingUserAccessByIpAddress")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "根据ip地址查询", notes = "传入ipAddress")
    public R<SafeaccessUserAccessDTO> fingUserAccessByIpAddress(@ApiParam(value = "ip地址", required = true) @RequestParam String ipAddress) {
        return R.data(safeaccessUserAccessService.fingUserAccessByIpAddress(ipAddress));
    }

    /**
     * 同步radius数据
     */
//	@GetMapping("/getRadiusState")
//	@ApiOperationSupport(order = 7)
//	@ApiOperation(value = "同步radius数据", notes = "传入id")
//	public R syncRadius(@ApiParam(value = "id", required = true) @RequestParam String id) {
//		return R.data(safeaccessUserAccessService.syncRadius(id));
//	}


    /**
     * 批量同步radius数据
     */
    @PostMapping("/batchSyncRadius")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "批量同步radius数据", notes = "传入ids")
    public R batchSyncRadius(@RequestBody DatchSyncRadiusDTO datchSyncRadiusDTO) {
        return safeaccessUserAccessService.batchSyncRadius(datchSyncRadiusDTO);
    }

    @PostMapping("/access")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "用户入网恢复", notes = "传入id")
    public R<Integer> accessSafeAccess(@RequestBody SafeaccessUserAccess safeaccessUserAccess) {
        return safeaccessUserAccessService.accessSafeAccess(safeaccessUserAccess.getId());
    }

    @PostMapping("/export")
    public void export(@RequestBody SafeaccessUserAccessSO safeaccessUserAccess, HttpServletResponse response) {
        // 数据权限
        IdevelopUser sysUser = SecureUtil.getUser();
        List<SafeaccessUserAccessDTO> records = safeaccessUserAccessService.customList(safeaccessUserAccess, sysUser);
        List<SafeUserAccessExportDTO> list = new ArrayList<>();
        for (SafeaccessUserAccessDTO record : records) {
            SafeUserAccessExportDTO safeUserAccessExport = new SafeUserAccessExportDTO();
            BeanUtils.copyProperties(record, safeUserAccessExport);
            if (StringUtils.isNotEmpty(safeUserAccessExport.getCode())) {
                safeUserAccessExport.setCode(safeUserAccessExport.getCode().equals("1") ? "运行" : "未运行");
            }
            if (StringUtils.isNotEmpty(safeUserAccessExport.getIs802())) {
                safeUserAccessExport.setIs802(safeUserAccessExport.getIs802().equals("0") ? "不认证" : safeUserAccessExport.getIs802().equals("1") ? "802.1x" : "MAC认证");
            }
            if (StringUtils.isNotEmpty(safeUserAccessExport.getIsAccess())) {
                safeUserAccessExport.setIsAccess(safeUserAccessExport.getIsAccess().equals("0") ? "未认证" : "已认证");
            }
            safeUserAccessExport.setSbbm(record.getDeviceCode());
            list.add(safeUserAccessExport);
        }
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            String fileName = URLEncoder.encode("导出", StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), SafeUserAccessExportDTO.class)
                    .registerWriteHandler(new HorizontalCellStyleStrategy(EasyExcelUtils.headStyle(), EasyExcelUtils.contentStyle()))
                    .sheet("入网数据")
                    .doWrite(list);
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
