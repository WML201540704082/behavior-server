package com.lnsoft.ipc.faceCommon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LocalAppData {
    /**
     * 应用名称
     */
    private String localAppName;
    /**
     * 连接状态
     */
    private String status;
    /**
     * 连接开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 连接结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}