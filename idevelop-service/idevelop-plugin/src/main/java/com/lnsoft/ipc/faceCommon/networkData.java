package com.lnsoft.ipc.faceCommon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class networkData {
	/**
	 * 网络协议类型
	 */
	private String protocol;
	/**
	 * 本地地址
	 */
	private String localAddr;
	/**
	 * 本地端口号
	 */
	private String localPort;
	/**
	 * 远程地址
	 */
	private String remoteAddr;
	/**
	 * 远程端口号
	 */
	private String remotePort;
	/**
	 * 连接状态
	 */
	private String status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;
	private String username;
	private String department;
}
