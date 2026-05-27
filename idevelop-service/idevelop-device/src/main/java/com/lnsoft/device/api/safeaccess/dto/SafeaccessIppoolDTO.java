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
package com.lnsoft.device.api.safeaccess.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * IP地址池数据传输对象实体类
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SafeaccessIppoolDTO extends SafeaccessIppool {
	private static final long serialVersionUID = 1L;

	@TableField(exist = false)
	@ApiModelProperty("部门名称")
	private String orgName;

	@TableField(exist = false)
	@ApiModelProperty("子网名称")
	private String subnetName;

	@TableField(exist = false)
	private String ipPoolName;

	@TableField(exist = false)
	private String mac;

	@TableField(exist = false)
	private String deviceType;

	@TableField(exist = false)
	private String[] ids;

	@TableField(exist = false)
	private String online;

	@TableField(exist = false)
	private String is802;

	@TableField(exist = false)
	private String level;

	@ApiModelProperty("排除ip")
	private List<String> excludeIps;
}
