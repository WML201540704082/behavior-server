package com.lnsoft.device.api.i6000.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xuel
 * @CreateTime: 2025/2/22 15:42
 * @Description: I6000RoomDTP
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class I6000RoomDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private String name;
	private String citypeId;
	private String runCorpCode;
	private String runCorpCodeName;

}
