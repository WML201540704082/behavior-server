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
package com.lnsoft.device.api.res.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseService;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.api.res.vo.ApproveRecordVO;

import java.util.List;

/**
 * 审批流程记录表 服务类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
public interface IApproveRecordService extends BaseService<ApproveRecord> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param approveRecord
	 * @return
	 */
	IPage<ApproveRecordVO> selectApproveRecordPage(IPage<ApproveRecordVO> page, ApproveRecordVO approveRecord);

	/**
	 * 新增 审批流程记录
	 *
	 * @param nodeId
	 * @param nodeName
	 * @param optRole
	 * @param optTitle
	 * @param optOpinion
	 * @return
	 */
	Boolean approveRecordAdd(String nodeId, String nodeName, String optRole, String optTitle, String optOpinion, String filingNo);

	/**
	 * 新增 审批流程记录表
	 *
	 * @param approveRecord
	 */
	String recordDeviceSave(ApproveRecord approveRecord);

	/**
	 * 新增审核记录流程
	 *
	 * @param approveRecord 审核记录流程
	 */
	void commonRecord(ApproveRecord approveRecord);

	/**
	 * 新增审核记录流程
	 *
	 * @param approveRecord 审核记录流程
	 */
	void commonRecordDate(ApproveRecord approveRecord, IdevelopUser user);

	/**
	 * 审批流程记录表
	 *
	 * @param page          page
	 * @param approveRecord 查询条件
	 * @return R
	 */
	R<List<ApproveRecord>> approveRecordList(IPage<ApproveRecord> page, ApproveRecord approveRecord);
}
