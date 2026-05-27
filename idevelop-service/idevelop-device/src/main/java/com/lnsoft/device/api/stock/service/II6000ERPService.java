package com.lnsoft.device.api.stock.service;

import com.lnsoft.device.api.cmdb.entity.DeviceCodeStencil;
import com.lnsoft.device.dto.I6000SrynDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface II6000ERPService {


    /**
     * 根据信通一体化设备编码, 同步I6000和ERP系统数据
     *
     * @param file
     */
    void importSyncI6000Detail(MultipartFile file);



    /**
     * 根据信通一体化设备编码, 同步I6000系统数据
     *
     * @param deviceCodeStencils
     * @return
     */
    String importSyncI6000Detail(List<DeviceCodeStencil> deviceCodeStencils, Integer type);


    /**
     * 根据信通一体化设备编码, 同步I6000系统数据111
     *
     * @param deviceCodeList
     * @return
     */
    void importSyncI6000Detail(List<String> deviceCodeList);


    /**
     * 用户自主导入资产数据, 同步I6000系统和ERP系统数据
     *
     * @param deviceCodeStencils
     * @return
     */
    String importSyncI6000ErpDetail(List<String> deviceCodeStencils, String result, Boolean isBatch);


    /**
     * (task)根据ERP资产编码和ERP设备编码, 信通一体化同步I6000系统数据
     *
     * @param i6000SrynDTOS
     * @return
     */
    String syncI6000DetailTask(I6000SrynDTO i6000SrynDTOS);

    /**
     * 根据信通一体化设备编码, 同步I6000和ERP系统数据(单条同步)
     *
     * @param deviceCode
     * @return
     */
    String importSyncI6000DetailOne(String deviceCode);
}
