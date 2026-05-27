/**
 * .
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
package com.lnsoft.device.api.safeaccess.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.asset.wrapper.HardwareBasicWrapper;
import com.lnsoft.device.api.res.constants.Constants;
import com.lnsoft.device.api.safeaccess.dto.DatchSyncRadiusDTO;
import com.lnsoft.device.api.safeaccess.dto.SafeaccessUserAccessSaveDTO;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessEditSubnet;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSubnetMapper;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessUserAccessDisableMapper;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessUserAccessMapper;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessService;
import com.lnsoft.device.api.warehouse.dto.SwitcherDeviceListDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnUserAccess;
import com.lnsoft.device.api.warehouse.service.IDSwitcherSyncService;
import com.lnsoft.device.api.warehouse.service.ISyncSdnService;
import com.lnsoft.device.dto.SafeaccessUserAccessDTO;
import com.lnsoft.device.entity.SafeaccessSubnet;
import com.lnsoft.device.entity.SafeaccessUserAccess;
import com.lnsoft.device.entity.SafeaccessUserAccessDisable;
import com.lnsoft.device.props.CmdbCientityProperties;
import com.lnsoft.device.props.CmdbDictProperties;
import com.lnsoft.device.so.SafeaccessUserAccessSO;
import com.lnsoft.device.utils.DBManagerToMysql;
import com.lnsoft.device.utils.JobUtil;
import com.lnsoft.system.entity.Dept;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 终端用户入网信息表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-09
 */
@Service
@Slf4j
public class SafeaccessUserAccessServiceImpl extends BaseServiceImpl<SafeaccessUserAccessMapper, SafeaccessUserAccess> implements ISafeaccessUserAccessService {

    @Resource
    private IRadiusBizcImpl radiusBizc;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private CmdbDictProperties cmdbDictProperties;
    @Resource
    private CmdbCientityProperties cmdbCientityProperties;
    @Resource
    private SafeaccessUserAccessDisableMapper safeaccessUserAccessDisableMapper;
    @Resource
    private SafeaccessUserAccessMapper safeaccessUserAccessMapper;
    @Resource
    private IDSwitcherSyncService idSwitcherSyncService;
    @Resource
    private SafeaccessSubnetMapper safeaccessSubnetMapper;

    @Resource
    private ISyncSdnService syncSdnService;


    @Override
    public List<SafeaccessUserAccess> findByConditionSubnetId(Map<String, Object> map) {
        return baseMapper.selectByMap(map);
    }

    @Override
    public void saveEditNet(SafeaccessEditSubnet csTEditNet) {
        baseMapper.saveEditNet(csTEditNet);
    }

    @Override
    public void updateUserAccess(Map<String, String> userAccessMap) {
        baseMapper.updateUserAccess(userAccessMap);
    }

    @Override
    public IPage<SafeaccessUserAccess> selectSafeaccessUserAccessPage(SafeaccessUserAccess safeaccessUserAccess, Query query) {
        LambdaQueryWrapper<SafeaccessUserAccess> queryWrapper = new LambdaQueryWrapper<SafeaccessUserAccess>().eq(SafeaccessUserAccess::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
        queryWrapper.like(StringUtil.isNotBlank(safeaccessUserAccess.getDeviceCode()), SafeaccessUserAccess::getDeviceCode, safeaccessUserAccess.getDeviceCode());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getDeptCode()), SafeaccessUserAccess::getDeptCode, safeaccessUserAccess.getDeptCode());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getMacAddress()), SafeaccessUserAccess::getMacAddress, safeaccessUserAccess.getMacAddress());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getIpAddress()), SafeaccessUserAccess::getIpAddress, safeaccessUserAccess.getIpAddress());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getApproveuUser()), SafeaccessUserAccess::getApproveuUser, safeaccessUserAccess.getApproveuUser());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getIsAccess()), SafeaccessUserAccess::getIsAccess, safeaccessUserAccess.getIsAccess());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getSubnetId()), SafeaccessUserAccess::getSubnetId, safeaccessUserAccess.getSubnetId());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getMiUser()), SafeaccessUserAccess::getMiUser, safeaccessUserAccess.getMiUser());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getMiChargeUser()), SafeaccessUserAccess::getMiChargeUser, safeaccessUserAccess.getMiChargeUser());
        queryWrapper.eq(StringUtil.isNotBlank(safeaccessUserAccess.getDeviceType()), SafeaccessUserAccess::getDeviceType, safeaccessUserAccess.getDeviceType());
        queryWrapper.orderByDesc(SafeaccessUserAccess::getCreateTime);
        return baseMapper.selectPage(Condition.getPage(query), queryWrapper);
    }

    @Override
    public SafeaccessUserAccessDTO fingUserAccessByIpAddress(String ipAddress) {
        if (StringUtil.isBlank(ipAddress)) {
            throw new ServiceException("ip地址不能为空");
        }
        SafeaccessUserAccess userAccess = new SafeaccessUserAccess();
        userAccess.setIpAddress(ipAddress);
        IdevelopUser sysUser = SecureUtil.getUser();
        userAccess.setRegionCode(sysUser.getRegionCode());
        return baseMapper.customGetOne(userAccess);
    }

    @Override
    public boolean syncRadius(String id) {
        String uacessql = "select t.*,"
                + "(select o.full_name from sys_t_corp_shortname o where o.org_no=t.company) as COMPANYZW, "
                + "(select b.name from isc_baseorg b where  b.id=t.department) as DEPARTMENTZW, "
                + "(select n.type_name from DEV_T_HARDWARE_TYPE n where n.type_code=t.device_id) as DEVICE_IDZW "
                + "  from CS_T_USER_ACCESS t where t.id = ?";
        Map<String, String> map = baseMapper.findRadiusMap(id);
        if (map == null || map.size() <= 0) {
            return false;
        }

        // 同步数据到中间表 0：成功 ，1：地市不支持sdn ，2：失败
        String syncSdn = syncRadiusToH3ZJB(map);
        log.warn("syncSdn:{}", syncSdn);
        // 同步radius
        boolean syncRadius = syncToRadius(map);
        log.warn("syncRadius:{}", syncRadius);
        // 两个都同步成功才能算成功，没有sdn功能的除外
        if (!"2".equals(syncSdn) && syncRadius) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public R<Object> batchSyncRadius(DatchSyncRadiusDTO datchSyncRadiusDTO) {

        List<String> ids = datchSyncRadiusDTO.getIds();
        String type = datchSyncRadiusDTO.getType();
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("数据不能为空");
        }

        List<SafeaccessUserAccess> safeaccessUserAccesses = safeaccessUserAccessMapper.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(safeaccessUserAccesses)) {
            throw new ServiceException("未查询到用户入网数据, 数据不能为空");
        }

        for (SafeaccessUserAccess item : safeaccessUserAccesses) {
            String deviceCode = item.getDeviceCode();
            try {

                String regionCode = item.getRegionCode().length() > 4 ? item.getRegionCode().substring(0, 4) : item.getRegionCode();

                String switcherKey = CacheNames.DEVICE_SYNC_SDN_SWITCHER + regionCode;
                if (redisUtil.hasKey(switcherKey)) {
                    throw new ServiceException("当前地市存在正在新增或者同步Radius流程, 请一分钟后尝试! ");
                }

                redisUtil.set(switcherKey, "1", 3, TimeUnit.MINUTES);

                // 设备投运推送数据同步服务
                List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
                List<DeviceSdnUserAccess> deviceSdnUserAccessList = new ArrayList<>();

                switcherDeviceListDTOList.add(SwitcherDeviceListDTO.builder()
                        .deviceCategory("终端设备")
                        .deviceType(item.getDeviceTypeName())
                        .deviceIp(item.getIpAddress())
                        .deviceMac(item.getMacAddress())
                        .authAccount(item.getApproveuUser())
                        .authPassword(item.getApproveuPassword())
                        .deviceSubnet(item.getSubnetId())
                        .deviceCode(item.getDeviceCode())
                        .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" :
                                Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" :
                                        Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                        .build());

                SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String company = item.getCompany();
                if (company == null || company.isEmpty()) {
                    throw new ServiceException("公司为空, 请联系项目组处理! ");
                }
                String department = item.getDepartment();
                if (department == null || department.isEmpty()) {
                    throw new ServiceException("部门为空, 请联系项目组处理! ");
                }
                Dept companyDept = HardwareBasicWrapper.build().getDeptById(company);
                Dept departmentDept = HardwareBasicWrapper.build().getDeptById(department);

                LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<SafeaccessSubnet>()
                        .likeRight(SafeaccessSubnet::getRegionCode, item.getRegionCode())
                        .in(SafeaccessSubnet::getId, item.getSubnetId());
                List<SafeaccessSubnet> safeAccessSubnetList = safeaccessSubnetMapper.selectList(queryWrapper);
                deviceSdnUserAccessList.add(DeviceSdnUserAccess.builder()
                        .company(companyDept.getFullName())
                        .department(departmentDept.getFullName())
                        .address(item.getAddress())
                        .phone(item.getPhone())
                        .deviceId(item.getDeviceTypeName())
                        .subnetId(item.getSubnetId())
                        .authUser(item.getApproveuUser())
                        .authPassword(item.getApproveuPassword())
                        .macAddress(item.getMacAddress())
                        .ipAddress(item.getIpAddress())
                        .sbbm(item.getDeviceCode())
                        // 如果非临时使用，入网时间是当前时间，如果是临时使用，入网时间是开始时间
                        .startTime(DATE_FORMAT_TIME.format(new Date()))
                        // 如果非临时使用，允许入网时长默认0，如果是临时使用，允许入网时长通过时间计算
                        .allowDays("0")
                        .is802(Constants.NO_AUTHENTICATION.equals(item.getIs802()) ? "0" :
                                Constants.I802_AUTHENTICATION.equals(item.getIs802()) ? "1" :
                                        Constants.MAC_AUTHENTICATION.equals(item.getIs802()) ? "2" : "3")
                        .fullUserName(item.getMiUser())
                        .isAccess("0")
                        .syncTime(DATE_FORMAT_TIME.format(new Date()))
                        .syncSign(type)
                        .readState("0")
                        .dataFrom("0")
                        .vlanId(safeAccessSubnetList.stream().filter(safeAccessSubnet ->
                                safeAccessSubnet.getId().equals(item.getSubnetId())).findFirst().orElse(new SafeaccessSubnet()).getVlanId())
                        .region(regionCode)
                        .build());

                if (CollectionUtils.isNotEmpty(switcherDeviceListDTOList)) {
                    System.out.println("switcherDeviceListDTOList" + JSONObject.toJSONString(switcherDeviceListDTOList));
                    idSwitcherSyncService.insertDSwitcherSync(switcherDeviceListDTOList, item.getRegionCode(), "0");
                }
                if (CollectionUtils.isNotEmpty(deviceSdnUserAccessList)) {
                    System.out.println("deviceSdnUserAccessList" + JSONObject.toJSONString(deviceSdnUserAccessList));
                    idSwitcherSyncService.deviceSdnUserAccess(deviceSdnUserAccessList);
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                redisUtil.del(switcherKey);
            } catch (Exception e) {
                throw new RuntimeException(deviceCode + " 同步Radius数据推送失败, 失败原因: " + e.getMessage());
            }
        }
        return R.success("同步Radius数据成功");
    }


    @Override
    public IPage<SafeaccessUserAccessDTO> customPage(SafeaccessUserAccessSO so, Query query, IdevelopUser sysUser) {
        Page<SafeaccessUserAccessDTO> page = new Page<>(query.getCurrent(), query.getSize());

        so.setRegionCode(sysUser.getRegionCode());
        IPage<SafeaccessUserAccessDTO> iPage = baseMapper.customPage(page, so);
        List<SafeaccessUserAccessDTO> records = iPage.getRecords();
		/*if (CollectionUtil.isNotEmpty(records)) {
			records = records.stream().map(record -> {
				if (StringUtil.isNotBlank(record.getDeviceType())) {
					String redisKey = CacheNames.CMDB_DICT_STORAGE + cmdbDictProperties.getDeviceType();
					Map<String, String> cache = (Map<String, String>) redisUtil.get(redisKey);
					record.setDeviceTypeName(cache.get(record.getDeviceType()));

//					R<List<Map<String, Object>>> deviceType = CmdbCiAttrWrapper.build().getCiCientityDictList(1097745969774592L);
//					record.setDeviceTypeName(CmdbDictUtil.getValue(deviceType, record.getDeviceType()));
				}
				return record;
			}).collect(Collectors.toList());
		}*/
        return iPage.setRecords(records);
    }

    @Override
    public List<SafeaccessUserAccessDTO> customList(SafeaccessUserAccessSO safeaccessUserAccess, IdevelopUser sysUser) {
        safeaccessUserAccess.setRegionCode(sysUser.getRegionCode());
        List<SafeaccessUserAccessDTO> safeaccessUserAccessDTOS = baseMapper.customList(safeaccessUserAccess);
        return safeaccessUserAccessDTOS;
    }

    @Override
    public void saveUserAccess(SafeaccessUserAccessSaveDTO dto) {
        IdevelopUser sysUser = SecureUtil.getUser();
        SafeaccessUserAccess entity = BeanUtil.copy(dto, SafeaccessUserAccess.class);
        entity.setCreateDept(sysUser.getDeptId());
        entity.setCreateTime(new Date());
        entity.setCreateUser(sysUser.getUserId());
        entity.setDeptCode(sysUser.getDeptId());
        entity.setRegionCode(sysUser.getRegionCode());
        entity.setFillMan(sysUser.getUserName());
        entity.setFillTime(new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()));
        this.save(entity);
    }

    @Override
    public SafeaccessUserAccessDTO customGetOne(SafeaccessUserAccess safeaccessUserAccess) {
        IdevelopUser sysUser = SecureUtil.getUser();
        String regionCode = sysUser.getRegionCode();

        if (!StringUtils.equals(regionCode, "37")) {
            safeaccessUserAccess.setRegionCode(regionCode);
        }
        return baseMapper.customGetOne(safeaccessUserAccess);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<Integer> accessSafeAccess(String id) {
        IdevelopUser user = SecureUtil.getUser();
        SafeaccessUserAccess safeaccessUserAccess = baseMapper.selectById(id);
        if (Objects.isNull(safeaccessUserAccess)) {
            return R.fail("用户入网信息不存在");
        }
        if (!safeaccessUserAccess.getDisableStatus().equals("1")) {
            return R.fail("当前设备正常入网，无需恢复");
        }
        SafeaccessUserAccessDisable safeaccessUserAccessDisable = safeaccessUserAccessDisableMapper.selectOne(new LambdaQueryWrapper<SafeaccessUserAccessDisable>()
                .eq(SafeaccessUserAccessDisable::getDeviceCode, safeaccessUserAccess.getDeviceCode()));
        if (Objects.isNull(safeaccessUserAccessDisable)) {
            return R.fail("用户入网临时表信息不存在");
        }
        baseMapper.update(null, new LambdaUpdateWrapper<SafeaccessUserAccess>()
                .eq(SafeaccessUserAccess::getId, safeaccessUserAccess.getId())
                .set(SafeaccessUserAccess::getDisableStatus, 2)
                .set(SafeaccessUserAccess::getMacAddress, safeaccessUserAccessDisable.getMac())
                .set(SafeaccessUserAccess::getUpdateUser, user.getUserId())
                .set(SafeaccessUserAccess::getUpdateTime, new Date()));
        safeaccessUserAccessDisableMapper.deleteById(safeaccessUserAccessDisable.getId());
        return R.success(ResultCode.SUCCESS);
    }


    private boolean syncToRadius(Map<String, String> map) {
        IdevelopUser sysUser = SecureUtil.getUser();
        // 同步radius
        try {
            SafeaccessSubnet findSubnetById = baseMapper.findSubnetById(map.get("subnet_id"));
            if (findSubnetById == null) {
                return false;
            }
            String groupname = findSubnetById.getUserGroup();
            String pid = radiusBizc.queryRadcheckPid(map.get("approveu_user"), sysUser);
            if (pid == null) {
                return false;
            }
            if ("".equals(pid)) {
                pid = map.get("id");
            }
            int resultDeleGroup = radiusBizc.deleteRadusergroup(pid, sysUser);
            int resultDeleCheck = radiusBizc.deleteRadcheck(pid, sysUser);
            if (resultDeleGroup != -1 && resultDeleCheck != -1) {
                int resultCheck = radiusBizc.updateOrsaveRadcheck(pid, map.get("approveu_user"),
                        map.get("approveu_password"), map.get("ip_address"),
                        map.get("mac_address"), map.get("is802"), sysUser);
                int resultGroup = radiusBizc.updateOrsaveRadusergroup(pid, map.get("approveu_user"),
                        groupname, sysUser);
                if (resultGroup != -1 && resultCheck != -1) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    private String syncRadiusToH3ZJB(Map<String, String> map) {
        IdevelopUser sysUser = SecureUtil.getUser();
        //插入用户入网中间表
        try {
            String compCode = sysUser.getRegionCode();
            String cityCode = "";
            log.info("开始同步数据到华3的中间表,compCode——" + compCode);
            DBManagerToMysql dbManager = null;
            if (compCode != null && !"".equals(compCode)) {
                //城市编码
                cityCode = compCode.substring(0, 4);
                if (JobUtil.cityCodeArray.indexOf(cityCode) > -1) {
                    dbManager = new DBManagerToMysql(cityCode);
                } else {
                    log.error("城市编码的城市中间表不存在！！");
                    //地市不支持sdn
                    return "1";
                }
            } else {
                log.error("部门编码为空");
                //失败
                return "2";
            }

            //判断是否有数据库连接信息
            if (dbManager == null) {
                log.error("没有获取到数据库的连接信息！");
                //失败
                return "2";
            }


            /** 设备编码 */
            String sbbm = map.get("device_code") == null ? "" : map.get("device_code");
            /** 公司名称 */
            String company = map.get("companyzw") == null ? "" : map.get("companyzw");
            /** 部门名称 */
            String department = map.get("departmentzw") == null ? "" : map.get("departmentzw");
            /** 地址 */
            String address = map.get("address") == null ? "" : map.get("address");
            /** 联系电话 */
//			String phone = map.get("PHONE") == null ? "" : map.get("PHONE").toString();
            /** 设备类型 */
            String deviceId = map.get("device_idzw") == null ? "" : map.get("device_idzw");
            /** 认证用户 */
            String authUser = map.get("approveu_user") == null ? "" : map.get("approveu_user");
            /** 认证密码 */
            String authPassword = map.get("approveu_password") == null ? "" : map.get("approveu_password");
            /** mac地址 */
            String macAddress = map.get("mac_address") == null ? "" : map.get("mac_address");
            /** 所属子网id */
            String subnetId = map.get("subnet_id") == null ? "" : map.get("subnet_id");
            /** ip地址 */
            String ipAddress = map.get("ip_address") == null ? "" : map.get("ip_address");
            /** 是否启用802.1X接入认证（0 不认证，1 802.1X，2 mac）  */
            String is802point1x = map.get("is802") == null ? "" : map.get("is802");
            /** 用户全名 */
//			Map<String,String> userinfo = Common.getUserInfo();
//			String fullName = userinfo.get("userName");
            String fullName = map.get("full_username") == null ? "" : map.get("full_username");
            /** 是否认证成功（0 未认证，1 已认证） */
//			String isAccess = "0";
            String isAccess = map.get("is_access") == null ? "" : map.get("is_access");
            /** 同步标识（A 新增，U 更新，D删除，C 变更） */


            // lf!2021.4.15为新sdn增加vlanid
            String vlanId = "";
            if (!subnetId.equals("")) {
                String vsql = "select s.vlan_id from idevelop_safeaccess_subnet s WHERE s.id = ?";
                List<Map<String, Object>> ret = jdbcTemplate.queryForList(vsql, subnetId);
                Map<String, Object> retMap = new HashMap<>();
                if (ret != null && ret.size() > 0) {
                    retMap = ret.get(0);
                    vlanId = retMap.get("vlan_id") == null ? "" : retMap.get("vlan_id").toString();
                    log.info("vlanId:{}", vlanId);
                }
            }

            //查询设备出入网申请工单表中的申请工作类型，并判断同步标识的状态
            //根据处理时间排序，取最新的那一条的
//			String accessSql = "select n.apply_job_type,n.phone_no,n.SOURCR,n.application_id from " +
//							   "cs_t_access_network n " +
//							   "where " +
//							   "n.application_id in (select t.application_id from cs_t_equipment t where t.device_no = '"+sbbm+"') " +
//							   	"order by n.accept_date desc";
            Map<String, Object> params = new HashMap<>();
            params.put("deviceNo", sbbm);
            //开始执行查询
            // todo 查询投运工单
            List<Map<String, Object>> accessNetwords = baseMapper.getApplyTypeByDeviceNoList(params);
            Map<String, Object> jobTypeMap = new HashMap<>();
            if (accessNetwords != null && accessNetwords.size() > 0) {
                jobTypeMap = accessNetwords.get(0);
            }
            // 联系电话
            String phone = jobTypeMap.get("phone_no") == null ? "" : jobTypeMap.get("phone_no").toString();
            // 申请工作类型 变更/入网/退网
            String jobType = jobTypeMap.get("apply_job_type") == null ? "" : jobTypeMap.get("apply_job_type").toString();
            //同步标识
            String syncSign = "A";
            if ("入网".equals(jobType)) {
                syncSign = "A";
            } else if ("变更".equals(jobType)) {
                syncSign = "U";
            } else if ("退网".equals(jobType)) {
                syncSign = "D";
            }

            // 数据读取状态（0 未读，1 已读）
            String readState = "0";
            // 数据来源（0 一体化平台，1 SDN第三方）
            String dataFrom = "0";


            //判断是插入还是修改
            Object[] params1 = new Object[]{authUser, sbbm};
            String isUpdateSql = "SELECT 1 from user_access t WHERE (AUTH_USER= ? or SBBM = ?)";
            List<Map<String, Object>> isUpdateSqlList = dbManager.queryForListWithSql(isUpdateSql, params1);

            if (isUpdateSqlList != null && isUpdateSqlList.size() > 0) {
                log.info("开始执行中间表的修改操作！");
                //中间表里有数据，修改操作
                //同步标识：M is802字段为2时，变更了mac地址时标识， T is802字段从2变为1时标识， F is802字段从1变为2时标识
                //优先级   T=F>M
                String is802MacSql = "SELECT t.IS802,t.MAC_ADDRESS,t.SYNC_SIGN from user_access t " +
                        "WHERE " +
                        "(t.AUTH_USER= ? " +
                        "or t.SBBM = ?) " +
                        "AND t.SYNC_SIGN!='D'";
                List<Map<String, Object>> is802List = dbManager.queryForListWithSql(is802MacSql, params1);
                if (is802List != null && is802List.size() > 0) {
                    log.info("查询mysql数据库成功");
                    Map<String, Object> map2 = is802List.get(0);
                    String mysqlIs802 = map2.get("IS802") == null ? "" : map2.get("IS802").toString();
                    String mysqlMac = map2.get("MAC_ADDRESS") == null ? "" : map2.get("MAC_ADDRESS").toString();
                    String syncSignMysql = map2.get("SYNC_SIGN") == null ? "" : map2.get("SYNC_SIGN").toString();

                    if ("2".equals(mysqlIs802) && "1".equals(is802point1x)) {
                        //is802字段从2变为1时   T
                        syncSign = "T";
                    } else if ("1".equals(mysqlIs802) && "2".equals(is802point1x)) {
                        //is802字段从1变为2时  F
                        syncSign = "F";
                    } else if ("2".equals(mysqlIs802) && "2".equals(is802point1x) && !mysqlMac.equals(macAddress)) {
                        //is802字段为2时，变更了mac地址时 M
                        syncSign = "M";
                    } else if ("C".equals(syncSignMysql)) {
                        syncSign = "U";
                    } else {
                        syncSign = "";
                    }
                } else {
                    log.error("中间表无数据");
                    //失败
                    return "2";
                }

                //存在主键，执行修改操作
                List<Object> params2 = new ArrayList<>();
                params2.add(company);
                String updateSql = "UPDATE user_access SET  COMPANY=?, ";
                if ("0506".equals(cityCode)) {
                    //青岛sdn
                    StringBuffer sqlAdd = new StringBuffer();
                    /** 设备来源，01：单位自购，02：公司配发 */
                    String sourcr = jobTypeMap.get("sourcr") == null ? "" : jobTypeMap.get("sourcr").toString();
                    String applicationId = jobTypeMap.get("application_id") == null ? "" : jobTypeMap.get("application_id").toString();
//					String sqlequipment = "select * from cs_t_equipment t where t.device_no = '"+sbbm+"' and t.application_id = '"+applicationId+"'";
                    Map<String, Object> param = new HashMap<>();
                    param.put("deviceNo", sbbm);
                    param.put("applicationId", applicationId);
                    List<Map<String, Object>> equipments = baseMapper.getEquipmentByDeviceNoAndIdList(param);
                    /** 设备原Ip */
                    String oldIpAddress = "";
                    if (equipments != null && equipments.size() > 0) {
                        oldIpAddress = equipments.get(0).get("device_old_ip") == null ? "" : equipments.get(0).get("device_old_ip").toString();
                        if (ipAddress.equals(oldIpAddress)) {
                            //保证中间表里的原ip和ip不一样
                            oldIpAddress = "";
                        }
                    }
                    if (StringUtil.isNotBlank(sbbm)) {
                        params2.add(sbbm);
                        sqlAdd.append("SBBM=?, ");
                    }
                    if (StringUtil.isNotBlank(department)) {
                        params2.add(department);
                        sqlAdd.append("DEPARTMENT=?, ");
                    }
                    if (StringUtil.isNotBlank(address)) {
                        params2.add(address);
                        sqlAdd.append("ADDRESS=?, ");
                    }
                    if (StringUtil.isNotBlank(phone)) {
                        params2.add(phone);
                        sqlAdd.append("PHONE=?, ");
                    }
                    if (StringUtil.isNotBlank(deviceId)) {
                        params2.add(deviceId);
                        sqlAdd.append("DEVICE_ID=?, ");
                    }
//					if (StringUtils.isNotEmpty(authUser)) {
//						sqlAdd.append("AUTH_USER='"+authUser+"', ");认证用户不会变化
//					}
//					if (StringUtils.isNotEmpty(authPassword)) {
//						sqlAdd.append("AUTH_PASSWORD='"+authPassword+"', ");
//					}
                    if (StringUtil.isNotBlank(macAddress)) {
                        params2.add(macAddress);
                        sqlAdd.append("MAC_ADDRESS=?, ");
                    }
                    if (StringUtil.isNotBlank(subnetId)) {
                        params2.add(subnetId);
                        sqlAdd.append("SUBNET_ID=?, ");
                    }
                    if (StringUtil.isNotBlank(ipAddress)) {
                        params2.add(ipAddress);
                        sqlAdd.append("IP_ADDRESS=?, ");
                    }
                    if (StringUtil.isNotBlank(is802point1x)) {
                        params2.add(is802point1x);
                        sqlAdd.append("IS802=?, ");
                    }
                    if (StringUtil.isNotBlank(fullName)) {
                        params2.add(fullName);
                        sqlAdd.append("FULL_USERNAME=?, ");
                    }
                    if (StringUtil.isNotBlank(isAccess)) {
                        params2.add(isAccess);
                        sqlAdd.append("IS_ACCESS=?, ");
                    }
                    if ("T".equals(syncSign) || "F".equals(syncSign) || "M".equals(syncSign) || "U".equals(syncSign)) {
                        params2.add(syncSign);
                        sqlAdd.append("SYNC_SIGN=?, ");
                    }
                    // lf!2021.4.15为新sdn增加vlanid
                    if (StringUtil.isNotBlank(vlanId)) {
                        params2.add(vlanId);
                        sqlAdd.append("VLAN_ID=?, ");
                    }
                    if (StringUtil.isNotBlank(oldIpAddress)) {
                        params2.add(oldIpAddress);
                        sqlAdd.append("OLD_IP_ADDRESS=?, ");
                    }
                    if (StringUtil.isNotBlank(sourcr)) {
                        params2.add(sourcr);
                        sqlAdd.append("SOURCR=?, ");
                    }
                    params2.add(readState);
                    params2.add(dataFrom);
                    params2.add(authUser);
                    params2.add(sbbm);
                    sqlAdd.append("READ_STATE=?, ");
                    //根据认证用户和不删除状态的
                    sqlAdd.append("DATA_FROM=? WHERE (AUTH_USER=? or SBBM = ?)");
                    updateSql += sqlAdd.toString();
                } else {
                    StringBuffer sqlAdd = new StringBuffer();
                    if (StringUtil.isNotBlank(sbbm)) {
                        params2.add(sbbm);
                        sqlAdd.append("SBBM=?, ");
                    }
                    if (StringUtil.isNotBlank(department)) {
                        params2.add(department);
                        sqlAdd.append("DEPARTMENT=?, ");
                    }
                    if (StringUtil.isNotBlank(address)) {
                        params2.add(address);
                        sqlAdd.append("ADDRESS=?, ");
                    }
                    if (StringUtil.isNotBlank(phone)) {
                        params2.add(phone);
                        sqlAdd.append("PHONE=?, ");
                    }
                    if (StringUtil.isNotBlank(deviceId)) {
                        params2.add(deviceId);
                        sqlAdd.append("DEVICE_ID=?, ");
                    }
//					if (StringUtils.isNotEmpty(authUser)) {
//						sqlAdd.append("AUTH_USER='"+authUser+"', ");认证用户不会变化
//					}
//					if (StringUtils.isNotEmpty(authPassword)) {
//						sqlAdd.append("AUTH_PASSWORD='"+authPassword+"', ");
//					}
                    if (StringUtil.isNotBlank(macAddress)) {
                        params2.add(macAddress);
                        sqlAdd.append("MAC_ADDRESS=?, ");
                    }
                    if (StringUtil.isNotBlank(subnetId)) {
                        params2.add(subnetId);
                        sqlAdd.append("SUBNET_ID=?, ");
                    }
                    if (StringUtil.isNotBlank(ipAddress)) {
                        params2.add(ipAddress);
                        sqlAdd.append("IP_ADDRESS=?, ");
                    }
                    if (StringUtil.isNotBlank(is802point1x)) {
                        params2.add(is802point1x);
                        sqlAdd.append("IS802=?, ");
                    }
                    if (StringUtil.isNotBlank(fullName)) {
                        params2.add(fullName);
                        sqlAdd.append("FULL_USERNAME=?, ");
                    }
                    if (StringUtil.isNotBlank(isAccess)) {
                        params2.add(isAccess);
                        sqlAdd.append("IS_ACCESS=?, ");
                    }
                    if ("T".equals(syncSign) || "F".equals(syncSign) || "M".equals(syncSign) || "U".equals(syncSign)) {
                        params2.add(syncSign);
                        sqlAdd.append("SYNC_SIGN=?, ");
                    }
                    // lf!2021.4.15为新sdn增加vlanid
                    if (StringUtil.isNotBlank(vlanId)) {
                        params2.add(vlanId);
                        sqlAdd.append("VLAN_ID=?, ");
                    }
                    params2.add(readState);
                    params2.add(dataFrom);
                    params2.add(authUser);
                    params2.add(sbbm);
                    sqlAdd.append("READ_STATE=?, ");
                    //根据认证用户和不删除状态的
                    sqlAdd.append("DATA_FROM=? WHERE (AUTH_USER=? or SBBM = ?) ");
                    updateSql += sqlAdd.toString();
                }

                Object[] params3 = params2.toArray();
                int mysql = dbManager.updateWithSql(updateSql, params3);
                if (mysql > 0) {
                    log.info("修改中间表数据成功！");
                    //成功
                    return "0";
                }

            } else {
                log.info("开始执行中间表的插入操作！！");
                //中间表里没有数据，插入操作
                //把数据插入到中间表中
                String insertSql = "";
                Object[] params3 = null;
                if ("0506".equals(cityCode)) {
                    //青岛sdn
                    /** 设备来源，01：单位自购，02：公司配发 */
                    String sourcr = jobTypeMap.get("sourcr") == null ? "" : jobTypeMap.get("sourcr").toString();
                    String applicationId = jobTypeMap.get("application_id") == null ? "" : jobTypeMap.get("application_id").toString();
//					String sqlequipment = "select * from cs_t_equipment t where t.device_no = '"+sbbm+"' and t.application_id = '"+applicationId+"'";
//					List<Map<String, String>> equipments = hibernateDao.queryForListWithSql(sqlequipment);
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("deviceNo", sbbm);
                    param.put("applicationId", applicationId);
                    List<Map<String, Object>> equipments = baseMapper.getEquipmentByDeviceNoAndIdList(param);
                    /** 设备原Ip */
                    String oldIpAddress = "";
                    if (equipments != null && equipments.size() > 0) {
                        oldIpAddress = equipments.get(0).get("DEVICE_OLD_IP") == null ? "" : equipments.get(0).get("DEVICE_OLD_IP").toString();
                        if (ipAddress.equals(oldIpAddress)) {
                            //保证中间表里的原ip和ip不一样
                            oldIpAddress = "";
                        }
                    }

                    params3 = new Object[]{company, department, address, phone, deviceId, subnetId, authUser, authPassword, macAddress, ipAddress, sbbm,
                            is802point1x, fullName, isAccess, syncSign, readState, dataFrom, oldIpAddress, sourcr};
                    insertSql = "INSERT INTO user_access " +
                            "(ID, COMPANY, DEPARTMENT, " +
                            "ADDRESS, PHONE, DEVICE_ID, " +
                            "SUBNET_ID, AUTH_USER, AUTH_PASSWORD, " +
                            "MAC_ADDRESS, IP_ADDRESS, SBBM, " +
                            "START_TIME, ALLOW_DAYS, IS802, " +
                            "FULL_USERNAME, IS_ACCESS, SYNC_TIME, " +
                            "SYNC_SIGN, READ_STATE, DATA_FROM, OLD_IP_ADDRESS, SOURCR) " +
                            "VALUES " +
                            "('" + UUID.randomUUID().toString().replaceAll("-", "") + "', ?, ?, " +
                            "?, ?, ?, " +
                            "?, ?, ?, " +
                            "?, ?, ?, " +
                            "NULL, NULL, ?, " +
                            "?, ?, DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), " +
                            "?, ?, ?, ?, ?)";
                } else {
                    log.info("开始执行中间表的插入操作！！");
                    params3 = new Object[]{company, department, address, phone, deviceId, subnetId, authUser, authPassword, macAddress, ipAddress, sbbm,
                            is802point1x, fullName, isAccess, syncSign, readState, dataFrom, vlanId};
                    insertSql = "INSERT INTO user_access " +
                            "(ID, COMPANY, DEPARTMENT, " +
                            "ADDRESS, PHONE, DEVICE_ID, " +
                            "SUBNET_ID, AUTH_USER, AUTH_PASSWORD, " +
                            "MAC_ADDRESS, IP_ADDRESS, SBBM, " +
                            "START_TIME, ALLOW_DAYS, IS802, " +
                            "FULL_USERNAME, IS_ACCESS, SYNC_TIME, " +
                            "SYNC_SIGN, READ_STATE, DATA_FROM, VLAN_ID) " +
                            "VALUES " +
                            "('" + UUID.randomUUID().toString().replaceAll("-", "") + "', ?, ?, " +
                            "?, ?, ?, " +
                            "?, ?, ?, " +
                            "?, ?, ?, " +
                            "NULL, NULL, ?, " +
                            "?, ?, DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'), " +
                            "?, ?, ?, ?)";
                }

//					DBManagerToMysql dbManager = new DBManagerToMysql(compCode.substring(0, 4));
                int mysql = dbManager.updateWithSql(insertSql, params3);
                if (mysql > 0) {
                    //插入中间表成功，总数+1
                    log.info("插入中间表数据成功！");
                    //成功
                    return "0";
                } else {
                    log.info("插入中间表数据失败！");
                }
            }
        } catch (Exception e) {
            log.error("插入中间表出现错误，原因是：" + e.getMessage(), e);
        }
        //失败
        return "2";
    }

}
