/**
 .
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lnsoft.device.api.stock.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据治理，点击保存时使用
 *
 * @author zhang
 */
@Data
public class SimpleSafeAccessSwitchesDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	@ApiModelProperty(value = "管理用户")
	private String telUser;
	@ApiModelProperty(value = "管理密码")
	private String telPass;
	@ApiModelProperty(value = "交换机密码(NASSECRET)")
	private String swPass;
	@ApiModelProperty(value = "SNMP读字符串")
	private String snmpReadStr;
	@ApiModelProperty(value = "SNMP写字符串")
	private String snmpWriteStr;
	@ApiModelProperty(value = "SNMP版本号")
	private String snmpVersion;
	@ApiModelProperty(value = "工作vlans号")
	private String vlans;
	@ApiModelProperty(value = "配置密码")
	private String configPass;


}
