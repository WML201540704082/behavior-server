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
package com.lnsoft.device.api.asset.service;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.cmdb.vo.HardwareBasicVO;
import com.lnsoft.device.api.asset.dto.I6000RequestDTO;
import com.lnsoft.device.api.asset.dto.WarehouseDetailDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 资产台账模型树管理表 服务类
 *
 * @author Idevelop
 * @since 2024-02-22
 */
public interface IHardwareBasicService {

	/**
	 * 自定义分页
	 *
	 * @param hardwareBasicVO
	 * @return
	 */
	JSONObject selectHardwareBasicTreePage(HardwareBasicVO hardwareBasicVO);

	/**
	 * 详情
	 *
	 * @param feignCmdbCientityGet
	 * @return
	 */
	JSONObject getDetailOne(FeignCmdbCientityGet feignCmdbCientityGet);

	/**
	 * 批量导入信创终端设备 [ls临时]
	 *
	 * @param file
	 * @return
	 */
	Boolean importByExcel(MultipartFile file, String deviceTypeName, String attrCode);

	/**
	 * 根据条件获取 I6000 相关数据信息(数据治理修改功能)
	 *
	 * @param selectI6000Info
	 * @return
	 */
	Map<String, Object> selectInfoByI6000(I6000RequestDTO selectI6000Info);
	/**
	 * 批量填充信创终端设备 [ls临时]
	 *
	 * @param assetCodeErpList
	 * @return
	 */
    Boolean xcUpdate(List<String> assetCodeErpList,String attrCode);

	/**
	 * 设备出入库记录
	 * @param deviceCode
	 * @return
	 */
	List<WarehouseDetailDTO> warehouse(String deviceCode);

	/**
	 * 设备申请记录
	 * @param deviceCode
	 * @return
	 */
	Object apply(String deviceCode);

	/**
	 * 设备投运记录
	 * @param deviceCode
	 * @return
	 */
	Object operation(String deviceCode);

	/**
	 * 设备变更记录
	 * @param deviceCode
	 * @return
	 */
	Object change(String deviceCode);

	/**
	 * 设备报修记录
	 * @param deviceCode
	 * @return
	 */
	Object repair(String deviceCode);
}
