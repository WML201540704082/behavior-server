/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.lnsoft.device.api.stock.vo;

import com.lnsoft.device.api.stock.entity.I6000ImportLog;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员批量导入I6000数据记录表视图实体类
 *
 * @author Idevelop
 * @since 2025-11-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "I6000ImportLogVO对象", description = "管理员批量导入I6000数据记录表")
public class I6000ImportLogVO extends I6000ImportLog {
	private static final long serialVersionUID = 1L;

}
