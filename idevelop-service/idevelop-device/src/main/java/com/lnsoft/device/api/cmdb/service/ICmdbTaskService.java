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
package com.lnsoft.device.api.cmdb.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * cmdb通用 服务类
 *
 * @author xuel
 * @since 2024-02-21
 */
public interface ICmdbTaskService {

	/**
	 * 刷新投运年限
	 *
	 * @param format
	 */
	Boolean refreshUseAge(String format);

	/**
	 * 刷新转资到期
	 *
	 * @param format
	 */
	Boolean refreshBecomeDueAssets(String format);

	/**
	 * 手动触发异步 刷新固定值 任务
	 *
	 * @param fixedValueMap
	 */
	Boolean refreshFixedValue(Map<String, Object> fixedValueMap);

	/**
	 * 手动触发异步 地市需求字段 任务
	 * @param fixedValueMap
	 * @return
	 */
	Boolean refreshUnitDept(JSONObject fixedValueMap);

}
