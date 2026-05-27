package com.lnsoft.device.api.safeaccess.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: xuel
 * @CreateTime: 2025/12/18 15:57
 * @Description: DatchSyncRadiusDTO
 */

@Data
public class DatchSyncRadiusDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    private List<String> ids;

    private String type;

}
