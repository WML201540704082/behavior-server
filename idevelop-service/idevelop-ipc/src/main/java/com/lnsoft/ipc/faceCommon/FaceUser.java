package com.lnsoft.ipc.faceCommon;

import lombok.Data;

import java.util.Date;
@Data
public class FaceUser {
	private String id;

	private String type;

	private String username;

	private String faceData;

	private String userPwd;

	private String department;

	private Date operationTime;
}
