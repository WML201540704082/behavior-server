package com.lnsoft.ipc.faceCommon;


@lombok.Data
public class UserRequest {

	// 对应 JSON 中的 "method" 字段
	private String method;

	// 对应 JSON 中的 "data" 字段
	private Data data;

}
