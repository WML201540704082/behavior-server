package com.lnsoft.ipc.faceCommon;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
@lombok.Data
public class Data {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// 对应 "data" 对象中的 "userList" 字段
	@JsonProperty("userList")
	private List<FaceUser> userList;
	@JsonProperty("time")
	private List<FaceTime> time;
	@JsonProperty("logList")
	private List<FaceLog> logList;
	@JsonProperty("timeList")
	private List<FaceOnOffTime> timeList;
}
