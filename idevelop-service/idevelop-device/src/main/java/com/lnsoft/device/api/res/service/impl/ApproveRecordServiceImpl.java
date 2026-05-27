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
package com.lnsoft.device.api.res.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.entity.ApproveRecord;
import com.lnsoft.device.api.res.mapper.ApproveRecordMapper;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.vo.ApproveRecordVO;
import com.lnsoft.device.constant.CommonConstant;
import com.lnsoft.device.props.ErpMassageProperties;
import com.lnsoft.system.entity.Dict;
import com.lnsoft.system.feign.IDictClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 审批流程记录表 服务实现类
 *
 * @author Idevelop
 * @since 2024-02-21
 */
@Service
@AllArgsConstructor
public class ApproveRecordServiceImpl extends BaseServiceImpl<ApproveRecordMapper, ApproveRecord> implements IApproveRecordService {

	private final IDictClient dictClient;
	private ErpMassageProperties erpProperties;

	@Override
	public IPage<ApproveRecordVO> selectApproveRecordPage(IPage<ApproveRecordVO> page, ApproveRecordVO approveRecord) {
		return page.setRecords(baseMapper.selectApproveRecordPage(page, approveRecord));
	}

	@Override
	public Boolean approveRecordAdd(String nodeId, String nodeName, String optRole, String optTitle, String optOpinion, String filingNo) {
		ApproveRecord approveRecord = new ApproveRecord();
		approveRecord.setNodeId(nodeId);
		approveRecord.setNodeName(nodeName);
		approveRecord.setOptRole(optRole);
		approveRecord.setOptTitle(optTitle);
		approveRecord.setOptOpinion(optOpinion);
		approveRecord.setFilingNo(filingNo);
		//获取用户数据
		IdevelopUser user = SecureUtil.getUser();
		if (user != null) {
			approveRecord.setOptBy((user.getUserId()).toString());
			approveRecord.setOptName(user.getRealName());
			approveRecord.setOptRole(user.getRoleName());
			approveRecord.setCreateUser(user.getUserId());
			approveRecord.setUpdateUser(user.getUserId());
		}
		//字典值转换
		R<List<Dict>> list = dictClient.getList(approveRecord.getWbsId());
		if (list.getData().size() > 0) {
			Map<String, String> dictMap = list.getData().stream().collect(Collectors.toMap(Dict::getDictValue, Dict::getDictKey));
			approveRecord.setWbsId(dictMap.get(approveRecord.getWbsId()));
		}
		approveRecord.setCreateTime(new Date());
		approveRecord.setUpdateTime(new Date());
		approveRecord.setOptDate(new Date());
		approveRecord.setIsDeleted(1);
		approveRecord.setIsDeleted(0);
		return baseMapper.insert(approveRecord) == 1;
	}

	@Override
	public String recordDeviceSave(ApproveRecord approveRecord) {
		IdevelopUser user = SecureUtil.getUser();
		//设置用户信息
		if (user != null) {
			approveRecord.setNodeId("1");
			approveRecord.setNodeName(CommonConstant.DEVICERECORD);
			approveRecord.setOptRole(user.getRoleName());
			approveRecord.setOptName(user.getRealName());
			approveRecord.setOptBy(user.getUserId().toString());
		}
		approveRecord.setOptType(CommonConstant.DEVICERECORD);
		approveRecord.setOptDate(new Date());
		baseMapper.insert(approveRecord);
		return approveRecord.getId().toString();
	}

	/**
	 * 新增审核记录流程
	 * 传参中的 status 的注释
	 * status = 0 : ERP审核
	 * status = 1 : 系统归档
	 * status = null || status = '' : 人员操作审核记录
	 *
	 * @param approveRecord 审核记录流程
	 */
	@Override
	public void commonRecord(ApproveRecord approveRecord) {
		IdevelopUser user = SecureUtil.getUser();
		if (Objects.nonNull(approveRecord.getStatus()) && approveRecord.getStatus() == 0) {
			approveRecord.setStatus(0);
			approveRecord.setCreateUser(user.getUserId());
			approveRecord.setOptBy(erpProperties.getUserId().toString());
			if (StringUtil.isBlank(approveRecord.getOptName())) {
				approveRecord.setOptName("ERP审核");
			}
		} else if (Objects.nonNull(approveRecord.getStatus()) && approveRecord.getStatus() == 1) {
			approveRecord.setCreateUser(user.getUserId());
			approveRecord.setOptBy("");
			approveRecord.setStatus(2);
		} else {
			if (Objects.isNull(user)) {
				user = approveRecord.getUser();
			}
			approveRecord.setCreateUser(user.getUserId());
			approveRecord.setCreateDept(user.getDeptId());
			approveRecord.setStatus(1);
			approveRecord.setOptBy(user.getUserId().toString());
			approveRecord.setOptName(user.getRealName());
		}
		approveRecord.setCreateTime(new Date());
		approveRecord.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		approveRecord.setOptDate(new Date());
		baseMapper.insert(approveRecord);
	}

	/**
	 * 新增审核记录流程
	 * 传参中的 status 的注释
	 * status = 0 : ERP审核
	 * status = 1 : 系统归档
	 * status = null || status = '' : 人员操作审核记录
	 *
	 * @param approveRecord 审核记录流程
	 */
	@Override
	public void commonRecordDate(ApproveRecord approveRecord, IdevelopUser user) {
		approveRecord.setCreateUser(user.getUserId());
		approveRecord.setOptBy("");
		approveRecord.setStatus(2);
		approveRecord.setCreateTime(new Date());
		approveRecord.setIsDeleted(IdevelopConstant.DB_NOT_DELETED);
		approveRecord.setOptDate(new Date());
		baseMapper.insert(approveRecord);
	}

	/**
	 * 审批流程记录表
	 *
	 * @param page          page
	 * @param approveRecord 查询条件
	 * @return R
	 */
	@Override
	public R<List<ApproveRecord>> approveRecordList(IPage<ApproveRecord> page, ApproveRecord approveRecord) {
		List<ApproveRecord> approveRecordList = baseMapper.selectList(new LambdaQueryWrapper<ApproveRecord>().eq(ApproveRecord::getFilingNo, approveRecord.getFilingNo())
			.orderByAsc(ApproveRecord::getCreateTime));
		Map<String, List<ApproveRecord>> collect = approveRecordList.stream().collect(Collectors.groupingBy(ApproveRecord::getNodeId));
		List<ApproveRecord> approveRecords = new ArrayList<>();
		for (Map.Entry<String, List<ApproveRecord>> stringListEntry : collect.entrySet()) {
			ApproveRecord record = stringListEntry.getValue().stream().sorted(Comparator.comparing(ApproveRecord::getCreateTime).reversed()).collect(Collectors.toList()).get(0);
			approveRecords.add(record);
		}
		return R.data(approveRecords);
	}

}
