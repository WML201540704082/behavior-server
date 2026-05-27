package com.lnsoft.device.api.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DSwitcherSyncDTO implements Serializable {
	private static final long serialVersionUID = 3105715323438899069L;

	private ServerInfoDTO serverInfo;
	private ConfDataDTO confData;

}
