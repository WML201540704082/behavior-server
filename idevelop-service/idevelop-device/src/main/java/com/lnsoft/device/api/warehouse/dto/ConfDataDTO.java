package com.lnsoft.device.api.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfDataDTO implements Serializable {
	private static final long serialVersionUID = -4286154321476926575L;

	private List<ClientsConfDTO> clientsConfList;
	private List<SharedNetworkConfDTO> sharedNetworkConfList;
	private List<HostConfDTO> hostConfList;
	private List<UsersConfDTO> usersConfList;

}
