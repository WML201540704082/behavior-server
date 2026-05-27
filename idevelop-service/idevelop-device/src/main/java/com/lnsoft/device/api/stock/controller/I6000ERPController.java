package com.lnsoft.device.api.stock.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.api.stock.service.II6000ERPService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author: xuel
 * @CreateTime: 2025/11/2 13:18
 * @Description: I6000ERPController
 */


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/i6000/erp")
@Api(value = "i6000和ERP同步接口", tags = "i6000和ERP同步接口")
public class I6000ERPController {

    private static final Logger LOGGER = LoggerFactory.getLogger(I6000ERPController.class);

    @Resource
    private II6000ERPService i6000ERPService;


    /**
     * 根据信通一体化设备编码, 同步I6000和ERP系统数据
     *
     * @param file
     * @return
     */
    @PostMapping("/import/sync")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "根据信通一体化设备编码, 同步I6000和ERP系统数据", notes = "导入模板同步表格")
    public void importSyncI6000Detail(MultipartFile file) {
        try {
            i6000ERPService.importSyncI6000Detail(file);;
        } catch (Exception e) {
            LOGGER.info("数据同步异常: " + e);
            throw new ServiceException("数据同步异常:" + e.getMessage());
        }
    }

    /**
     * 根据信通一体化设备编码, 同步I6000和ERP系统数据 单条同步
     *
     * @param deviceCode
     * @return
     */
    @GetMapping("/import/sync/one")
    @ApiOperationSupport(order = 11)
    @ApiOperation(value = "根据信通一体化设备编码, 同步I6000和ERP系统数据(单条同步)", notes = "设备编码")
    public R<String> importSyncI6000DetailOne(String deviceCode) {
        try {
            String msg = i6000ERPService.importSyncI6000DetailOne(deviceCode);
            return R.success(msg);
        } catch (Exception e) {
            LOGGER.info("数据同步异常: " + e);
            throw new ServiceException("数据同步异常:" + e.getMessage());
        }
    }


}
