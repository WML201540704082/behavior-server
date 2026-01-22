package com.lnsoft.auth.domain;

import lombok.Data;

@Data
public class LoginParam {

	private String account;

	private String password;

	private String grantType;

	private String refreshToken;

	private String tenantId = "000000";




}
