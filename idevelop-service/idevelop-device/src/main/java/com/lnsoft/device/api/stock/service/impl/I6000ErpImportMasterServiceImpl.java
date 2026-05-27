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
package com.lnsoft.device.api.stock.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.device.api.stock.dto.I6000ErpImportMasterDTO;
import com.lnsoft.device.api.stock.entity.I6000ErpImportMaster;
import com.lnsoft.device.api.stock.mapper.I6000ErpImportMasterMapper;
import com.lnsoft.device.api.stock.service.II6000ErpImportMasterService;
import com.lnsoft.device.api.stock.vo.I6000ErpImportMasterVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员批量导入I6000数据记录表-主表 服务实现类
 *
 * @author Idevelop
 * @since 2025-11-02
 */
@Service
public class I6000ErpImportMasterServiceImpl extends BaseServiceImpl<I6000ErpImportMasterMapper, I6000ErpImportMaster> implements II6000ErpImportMasterService {

    @Override
    public IPage<I6000ErpImportMasterVO> selectI6000ErpImportMasterPage(IPage<I6000ErpImportMasterVO> page, I6000ErpImportMasterVO i6000ErpImportMaster) {
        IdevelopUser user = SecureUtil.getUser();
        i6000ErpImportMaster.setCreateUser(user.getUserId());
        return page.setRecords(baseMapper.selectI6000ErpImportMasterPage1(page, i6000ErpImportMaster));
    }

    @Override
    public List<I6000ErpImportMasterVO> selectI6000ErpImportMasterPageByUser(I6000ErpImportMasterDTO i6000ErpImportMasterDTO) {
        Integer importStatus = i6000ErpImportMasterDTO.getImportStatus();
        Long userId = i6000ErpImportMasterDTO.getCreateUser();
        return baseMapper.selectI6000ErpImportMasterPageByUser(userId, importStatus);
    }


}
