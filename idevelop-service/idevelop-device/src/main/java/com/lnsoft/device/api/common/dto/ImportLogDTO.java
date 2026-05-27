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
package com.lnsoft.device.api.common.dto;

import com.lnsoft.device.api.common.entity.ImportLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 信通一体化平台导入文件结果下载日志数据传输对象实体类
 *
 * @author Idevelop
 * @since 2026-03-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImportLogDTO extends ImportLog {
	private static final long serialVersionUID = 1L;

}
