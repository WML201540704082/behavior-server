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

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lnsoft.core.log.exception.ServiceException;
import com.lnsoft.core.mp.base.BaseServiceImpl;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.secure.utils.SecureUtil;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.safeaccess.dto.SafeaccessIppoolDTO;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessEditSubnet;
import com.lnsoft.device.api.safeaccess.entity.SafeaccessIppool;
import com.lnsoft.device.api.safeaccess.mapper.SafeaccessSubnetMapper;
import com.lnsoft.device.api.safeaccess.service.*;
import com.lnsoft.device.api.warehouse.dto.DSwitcherSyncDTO;
import com.lnsoft.device.api.warehouse.dto.SwitcherDeviceListDTO;
import com.lnsoft.device.api.warehouse.service.IDSwitcherSyncService;
import com.lnsoft.device.entity.SafeaccessSubnet;
import com.lnsoft.device.entity.SafeaccessUserAccess;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.utils.Common;
import com.lnsoft.device.utils.SubnetDivision;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 子网管理表 服务实现类
 *
 * @author Idevelop
 * @since 2024-03-08
 */
@Service
@Slf4j
public class SafeaccessSubnetServiceImpl extends BaseServiceImpl<SafeaccessSubnetMapper, SafeaccessSubnet> implements ISafeaccessSubnetService {

    private static final Pattern pattern = Pattern.compile("^\\w+$");

    @Resource
    private IRadiusBizc radiusBizc;
    @Resource
    private ISafeaccessIppoolService ippoolService;
    @Resource
    private ISafeaccessSwitcheService switcheService;
    @Resource
    private ISafeaccessUserAccessService userAccessService;
    @Resource
    private ICmdbService cmdbService;
    @Resource
    private IDSwitcherSyncService idSwitcherSyncService;

    @Override
    public IPage<SafeaccessSubnet> selectSafeaccessSubnetPage(SafeaccessSubnet so, Query query, IdevelopUser sysUser) {
        LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtil.isNotBlank(so.getSubnetName()), SafeaccessSubnet::getSubnetName, so.getSubnetName());
        queryWrapper.like(StringUtil.isNotBlank(so.getSpName()), SafeaccessSubnet::getSpName, so.getSpName());
        queryWrapper.like(StringUtil.isNotBlank(so.getSubnetAddress()), SafeaccessSubnet::getSubnetAddress, so.getSubnetAddress());
        queryWrapper.eq(StringUtil.isNotBlank(so.getCode()), SafeaccessSubnet::getCode, so.getCode());
        //判断如果是公用网络
        if ("1".equals(so.getIsPublic())) {
            if (sysUser.getRegionCode().length() > 4) {
                //区县  SafeaccessSubnet::getRegionCode, sysUser.getRegionCode()).or().eq(SafeaccessSubnet::getRegionCode,sysUser.getRegionCode().substring(0,4))
                queryWrapper.nested(i -> i.eq(SafeaccessSubnet::getRegionCode, sysUser.getRegionCode()).or().eq(SafeaccessSubnet::getRegionCode, sysUser.getRegionCode().substring(0, 4)));
            } else {
                //地市
                queryWrapper.likeRight(SafeaccessSubnet::getRegionCode, sysUser.getRegionCode());
            }
        } else if ("0".equals(so.getIsPublic())) {
            queryWrapper.eq(StringUtil.isNotBlank(so.getInstitutionCode()), SafeaccessSubnet::getInstitutionCode, so.getInstitutionCode());
            queryWrapper.likeRight(SafeaccessSubnet::getRegionCode, sysUser.getRegionCode());
        } else {
            queryWrapper.eq(StringUtil.isNotBlank(so.getInstitutionCode()), SafeaccessSubnet::getInstitutionCode, so.getInstitutionCode());
            if (sysUser.getRegionCode().length() > 4) {
                //区县
                queryWrapper.nested(i -> i.eq(SafeaccessSubnet::getRegionCode, sysUser.getRegionCode()).or().eq(SafeaccessSubnet::getRegionCode, sysUser.getRegionCode().substring(0, 4)));
            } else {
                //地市
                queryWrapper.likeRight(SafeaccessSubnet::getRegionCode, sysUser.getRegionCode());
            }
        }
        queryWrapper.eq(StringUtil.isNotBlank(so.getVlanId()), SafeaccessSubnet::getVlanId, so.getVlanId());
        queryWrapper.eq(StringUtil.isNotBlank(so.getIsPublic()), SafeaccessSubnet::getIsPublic, so.getIsPublic());
        queryWrapper.eq(StringUtil.isNotBlank(so.getNetworkType()), SafeaccessSubnet::getNetworkType, so.getNetworkType());

        queryWrapper.orderByDesc(SafeaccessSubnet::getCreateTime);
        IPage<SafeaccessSubnet> page = baseMapper.selectPage(Condition.getPage(query), queryWrapper);
        List<SafeaccessSubnet> records = page.getRecords();
        // records = records.stream().map(subnet -> {
        //     SafeaccessSwitche switche = switcheService.getOne(new LambdaQueryWrapper<SafeaccessSwitche>().eq(SafeaccessSwitche::getId, subnet.getSwitchboard()).eq(SafeaccessSwitche::getIsDeleted, IdevelopConstant.DB_NOT_DELETED));
        //     if (switche != null) {
        //         subnet.setSwitchboardName(switche.getSwName());
        //     }
        //     return subnet;
        // }).collect(Collectors.toList());
        page.setRecords(records);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SafeaccessSubnet> customSave(SafeaccessSubnet safeaccessSubnet) {
        List<SafeaccessSubnet> voList = new ArrayList<>();
        IdevelopUser sysUser = SecureUtil.getUser();
        String compCode4 = getCompCode4(sysUser);

        if (safeaccessSubnet.getVlanId() != null) {
            if (safeaccessSubnet.getVlanId() == null) {
                return voList;
            }
            if (!pattern.matcher(safeaccessSubnet.getVlanId()).matches()) {
                return voList;
            }
            if (StringUtil.isBlank(safeaccessSubnet.getId())) {
                String uuid = UUID.randomUUID().toString();
                safeaccessSubnet.setUserGroup(uuid);
                safeaccessSubnet.setRegionCode(sysUser.getRegionCode());
                safeaccessSubnet.setDeptCode(sysUser.getDeptId());
                voList.add(safeaccessSubnet);
                this.save(safeaccessSubnet);
                // 数据同步
                try {
                    log.info("数据同步");
                    List<SwitcherDeviceListDTO> updateList = getSafeaccessSubnetSyncUpdateList(safeaccessSubnet, safeaccessSubnet.getRegionCode());
                    idSwitcherSyncService.insertDSwitcherSync(updateList, safeaccessSubnet.getRegionCode(), "2");
                } catch (Exception e) {
                    log.error("数据同步失败:{}", e.getMessage(), e);
                }
//				insert(safeaccessSubnet, sysUser);
                if (!createIpPool(String.valueOf(safeaccessSubnet.getId()), compCode4)) {
                    log.error("生成ip地址池失败:{}", safeaccessSubnet);
                    throw new ServiceException("生成ip地址池失败请联系管理员!");
                }
                voList.add(safeaccessSubnet);
            } else {
                SafeaccessSubnet csSubnet2 = this.getById(safeaccessSubnet.getId());
                safeaccessSubnet.setFillMan(sysUser.getUserName());
                baseMapper.updateById(safeaccessSubnet);
//				update(String.valueOf(safeaccessSubnet.getId()), safeaccessSubnet, sysUser);
                if (StringUtil.isNotBlank(safeaccessSubnet.getSubnetGateway()) || StringUtil.isNotBlank(safeaccessSubnet.getSubnetMask())) {
                    if (!csSubnet2.getSubnetGateway().equals(safeaccessSubnet.getSubnetGateway()) || !csSubnet2.getSubnetMask().equals(safeaccessSubnet.getSubnetMask())) {
                        boolean suc = createIpPool(safeaccessSubnet.getId(), safeaccessSubnet.getSubnetMask(), compCode4);
                        if (!suc) {
                            log.error("生成ip地址池失败:{}", safeaccessSubnet);
                            throw new ServiceException("操作失败请联系管理员!");
                        }
                    }
                }
                voList.add(safeaccessSubnet);
            }
        }
        return voList;
    }

    /**
     * 子网修改-同步数据
     *
     * @param safeaccessSubnet
     */
    @Override
    public void syncUpdateBySafeaccessSubnetId(SafeaccessSubnet safeaccessSubnet) {
        Long[] id = new Long[1];
        id[0] = Long.parseLong(safeaccessSubnet.getId());
        List<SafeaccessSubnet> safeaccessSubnetListByIds = getSafeaccessSubnetListByIds(id);
        if (!safeaccessSubnetListByIds.isEmpty()) {
            // 数据同步
            try {
                SafeaccessSubnet subnet = safeaccessSubnetListByIds.get(0);
                List<SwitcherDeviceListDTO> updateList = getSafeaccessSubnetSyncUpdateList(subnet, subnet.getRegionCode());
                idSwitcherSyncService.insertDSwitcherSync(updateList, subnet.getRegionCode(), "2");
            } catch (Exception e) {
                log.error("数据同步失败:{}", e.getMessage(), e);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initIpPoolData() {
        // todo
		/*
		 先用户入网数据，再子网管理数据，再初始化ip地址池
		 注意：用户入网表id

		 根据公司查询迁移子网列表
		 循环根据子网查询ip资源池

		 莱芜数据需要单独处理
		 */
        log.error("开始迁移用户入网数据》》》》》》");
        // 初始化用户入网数据
        baseMapper.initUserAccess();
        // 更新用户区域编码 15w 440s+ 如何优化
        baseMapper.updateUserAccessRegionCode();
        // 更新单位编码
        baseMapper.updateUserAccessCompany();
        // 更新部门编码
        baseMapper.updateUserAccessDepartment();
        log.error("开始迁移子网管理数据》》》》》》");
        // 初始化子网管理数据
        baseMapper.initSubnet();
        // 更新子网管理区域编码
        baseMapper.updateSubnetRegionCode();
        // 更新子网管理所属单位编码
        baseMapper.updateSubnetInstitutionCode();
        // 更新子网管理所属单位名称
        baseMapper.updateSubnetInstitutionName();
        log.error("开始迁移交换机管理数据》》》》》》");
        baseMapper.initSwitches();
        baseMapper.updateRegionCode();
        baseMapper.updateDeptCode();
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initIpPoolInfo(String deptCode) {
        List<SafeaccessSubnet> list = baseMapper.selectAll(deptCode);
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }
        list.stream().forEach(subnet -> {
            log.error("当前迁移的子网为>>{}", subnet.getSubnetName());
            String subnetId = subnet.getId();
            String compCode4 = getCompCode4(subnet);
            SafeaccessIppoolDTO saTIppool = new SafeaccessIppoolDTO();
            saTIppool.setSubnet(subnetId);
            // 初始化地址池
            clearIpPool(saTIppool, compCode4);
            createIpPool(saTIppool, compCode4);
            // 更新地址池地址使用情况
            List<String> userAccessList = ippoolService.selectUserAccess(saTIppool.getSubnet());
            List<String> switches = switcheService.selectSwitches(saTIppool.getSubnet());
            userAccessList.addAll(switches);
            for (String ip : userAccessList) {
                if (ip == null) {
                    continue;
                }
                saTIppool.setIp(ip);
                saTIppool.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);
                ippoolService.updateIpPool(saTIppool);
            }
        });
        return true;
    }

    @Override
    public List<SafeaccessSubnet> getByRegon(String regionCode) {
        LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SafeaccessSubnet::getRegionCode, regionCode).eq(SafeaccessSubnet::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 数据迁移用
     *
     * @param saTIppool
     * @param compCode4
     * @return
     */
    private int clearIpPool(SafeaccessIppoolDTO saTIppool, String compCode4) {
        String orgNo = getDeptId();
        saTIppool.setOrgNo(orgNo);
        saTIppool.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);
        int deleteNum = ippoolService.deleteIpPool(saTIppool);
        return deleteNum;
    }

    private String getCompCode4(SafeaccessSubnet subnet) {
        String orgNo = subnet.getRegionCode();
        log.error("安全准入-数据迁移-获取regionCode:{}", orgNo);
        return orgNo.length() >= 4 ? orgNo.substring(0, 4) : orgNo;
    }


    /**
     * 子网新增、修改
     *
     * @param safeaccessSubnet
     * @param regionCode
     * @return
     */
    private List<SwitcherDeviceListDTO> getSafeaccessSubnetSyncUpdateList(SafeaccessSubnet safeaccessSubnet, String regionCode) {
        List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
        SwitcherDeviceListDTO switcherDeviceListDTO = new SwitcherDeviceListDTO();
        switcherDeviceListDTO.setSubnet(safeaccessSubnet.getSubnetAddress());
        switcherDeviceListDTO.setNetmask(safeaccessSubnet.getSubnetMask());
        switcherDeviceListDTO.setOptionBroadcastAddress(safeaccessSubnet.getBroadcastAddress());
        switcherDeviceListDTO.setOptionRouters(safeaccessSubnet.getSubnetGateway());
        switcherDeviceListDTO.setOptionSubnetMask(safeaccessSubnet.getSubnetMask());
        String dnsip = safeaccessSubnet.getDnsip();
        String dnsip2 = safeaccessSubnet.getDnsip2();
        String dns = null;
        if (dnsip != null && !dnsip.isEmpty()) {
            dns = dnsip;
        }
        if (dns == null) {
            dns = dnsip2;
        } else {
            if (dnsip2 != null && !dnsip2.isEmpty()) {
                dns = dns + "," + dnsip2;
            }
        }
        switcherDeviceListDTO.setOptionDomainNameServers(dns);
        switcherDeviceListDTO.setDefaultLeaseTime(Integer.parseInt(safeaccessSubnet.getDefaultLeaseTime()));
        switcherDeviceListDTOList.add(switcherDeviceListDTO);
        return switcherDeviceListDTOList;
    }


    /***
     * 子网删除
     * @param safeaccessSubnet
     * @param regionCode
     */
    private List<SwitcherDeviceListDTO> getSafeaccessSubnetSyncDeleteList(SafeaccessSubnet safeaccessSubnet, String regionCode) {
        // 子网数据
        List<SwitcherDeviceListDTO> switcherDeviceListDTOList = new ArrayList<>();
        SwitcherDeviceListDTO switcherDeviceListDTO = new SwitcherDeviceListDTO();
        if (safeaccessSubnet.getSubnetAddress() == null || safeaccessSubnet.getSubnetMask() == null) {
            log.warn("subnetAddress或subnetMask码为空");
            return new ArrayList<>();
        }
        switcherDeviceListDTO.setSubnet(safeaccessSubnet.getSubnetAddress());
        switcherDeviceListDTO.setNetmask(safeaccessSubnet.getSubnetMask());
        switcherDeviceListDTOList.add(switcherDeviceListDTO);
        return switcherDeviceListDTOList;
    }


    /**
     * 查询子网
     *
     * @param ids
     * @return
     */
    private List<SafeaccessSubnet> getSafeaccessSubnetListByIds(Long[] ids) {
        // 子网数据
        List<SafeaccessSubnet> safeaccessSubnets = new ArrayList<>();
        LambdaQueryWrapper<SafeaccessSubnet> queryWrapper = new LambdaQueryWrapper<SafeaccessSubnet>();
        queryWrapper.in(SafeaccessSubnet::getId, ids);
        baseMapper.selectList(queryWrapper);
        return safeaccessSubnets;
    }


    @Override
    public List getSubnetInfo(String id, String gateway, String mask) {
        String[] info = SubnetDivision.getSubnetInfo(gateway, mask);
        if (!mask.equals("255.255.255.254") && !mask.equals("255.255.255.255")) {
            if (info[0].equals("")) {
                // 子网网关与子网掩码格式错误
                info[0] = "-1";
            } else if (gateway.equals(info[0]) || gateway.equals(info[1])) {
                // 子网网关与子网地址或广播地址相同
                info[0] = "-2";
            }
        }
        return Arrays.asList(info);
    }

    @Override
    public boolean hasTerminal(String subnetId) {
        Map<String, Object> map = new HashMap<>();
        map.put("subnetId", subnetId);
        return baseMapper.hasTerminal(map) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<Map<String, Object>>> customRemove(List<SafeaccessSubnet> subnetList) {
        Map<String, List<Map<String, Object>>> map = Maps.newHashMap();
        IdevelopUser sysUser = SecureUtil.getUser();

        //增加ip被占用则无法删除的判断
        String[] ids = new String[subnetList.size()];
        Long[] longId = new Long[subnetList.size()];
        List<Map<String, Object>> list = new ArrayList<>();
        String orgCode = getCompCode4(sysUser);
        for (int i = 0; i < subnetList.size(); i++) {
            SafeaccessSubnet subnet = subnetList.get(i);
            ids[i] = subnet.getId();
            longId[i] = Long.parseLong(subnet.getId());
            //查询ip地址池内该网段是否有ip被占用
            Map<String, String> paramMap = Maps.newHashMap();
            String tableName = SafeaccessIppool.getTablename() + orgCode;
            paramMap.put("tableName", tableName);
            paramMap.put("subnetId", subnet.getId());
            List<String> ipList = baseMapper.queryUsedIpBySubnet(paramMap);
            if (ipList.size() > 0) {
                Map<String, Object> rstMap = Maps.newHashMap();
                rstMap.put("subnetName", subnet.getSubnetName());
                rstMap.put("ipList", ipList);
                list.add(rstMap);
            } else {
                //查询用户入网是否有被占用的子网
                List<String> ipListFromAccess = baseMapper.queryUsedIpFromUserAccess(subnet.getId());
                if (ipListFromAccess.size() > 0) {
                    Map<String, Object> rstMap = Maps.newHashMap();
                    rstMap.put("subnetName", subnet.getSubnetName());
                    rstMap.put("ipList", ipListFromAccess);
                    list.add(rstMap);
                }
            }
        }
        if (list.size() > 0) {
            map.put("list", list);
            return map;
        }
        //删除主表
        this.deleteLogic(Arrays.asList(longId));
        // 数据同步
        List<SafeaccessSubnet> safeaccessSubnetListByIds = getSafeaccessSubnetListByIds(longId);
        List<DSwitcherSyncDTO> syncDeleteList = new ArrayList<>();
        for (SafeaccessSubnet subnet : safeaccessSubnetListByIds) {
            // 按区域编码组装数据
            List<SwitcherDeviceListDTO> safeaccessSubnetSyncDeleteList = getSafeaccessSubnetSyncDeleteList(subnet, subnet.getRegionCode());
            syncDeleteList.addAll(idSwitcherSyncService.getDSwitcherSyncList(safeaccessSubnetSyncDeleteList, subnet.getRegionCode(), "2"));
        }
        if (!syncDeleteList.isEmpty()) {
            try {
                // 删除多条传集合，接口只请求一次
                idSwitcherSyncService.run(syncDeleteList, "3");
            } catch (Exception e) {
                log.error("数据同步失败:{}", e.getMessage(), e);
            }
        }
        for (SafeaccessSubnet subnet : subnetList) {
//			int flag = radiusBizc.deleteRadgroupreply(subnet.getId(), sysUser);
//			if (flag == -1) {
//				subnet.setRdsState("-1");
//				setFlag(subnet);
//				Map<String, Object> rstMap = Maps.newHashMap();
//				rstMap.put("data", subnet.getSubnetName() + "的radius中间库删除失败");
//				list.add(rstMap);
//				map.put("msg", list);
//				return map;
//			}
            clearIpPool(String.valueOf(subnet.getId()), sysUser);
        }

        map.put("list", new ArrayList<>());
        return map;

    }

    @Override
    public boolean initIpPool(String subnetId) {
        if (subnetId == null) {
            return false;
        }
        SafeaccessSubnet subnet = getById(subnetId);
        if (subnet == null) {
            throw new ServiceException("无法找到资源");
        }
        IdevelopUser sysUser = SecureUtil.getUser();

        // 地市编码
        String compCode4 = getCompCode4(sysUser);
        SafeaccessIppoolDTO saTIppool = new SafeaccessIppoolDTO();
        saTIppool.setSubnet(subnetId);
        // 初始化地址池
        clearIpPool(saTIppool, sysUser);
        // boolean ipPool = createIpPool(saTIppool, compCode4);
        // log.info("ipPool: {}", ipPool);

        // // 更新地址池地址使用情况
        // List<String> list = ippoolService.selectUserAccess(saTIppool.getSubnet());
        // log.info("selectUserAccess: {}", list);
        // List<String> switches = switcheService.selectSwitches(saTIppool.getSubnet());
        // list.addAll(switches);
        // for (String ip : list) {
        //     if (ip == null) {
        //         continue;
        //     }
        //     saTIppool.setIp(ip);
        //     saTIppool.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);
        //     ippoolService.updateIpPool(saTIppool);
        // }
        return createIpPool(saTIppool, compCode4);
    }

    /**
     * 初始化并更新单个子网的地址池
     *
     * @param subnetId
     * @return
     */
    @Override
    public boolean initIpPool1(String subnetId) {
        if (subnetId == null) {
            return false;
        }
        SafeaccessSubnet subnet = getById(subnetId);
        if (subnet == null) {
            throw new ServiceException("无法找到资源");
        }
        IdevelopUser sysUser = SecureUtil.getUser();

        // 地市编码
        String compCode4 = getCompCode4(sysUser);
        SafeaccessIppoolDTO saTIppool = new SafeaccessIppoolDTO();
        saTIppool.setSubnet(subnetId);
        // // 初始化地址池
        // clearIpPool(saTIppool, sysUser);
        // boolean ipPool = createIpPool(saTIppool, compCode4);
        // log.info("ipPool: {}", ipPool);

        // 更新地址池地址使用情况
        List<String> list = ippoolService.selectUserAccess(saTIppool.getSubnet());
        log.info("selectUserAccess: {}", list);
        List<String> switches = switcheService.selectSwitches(saTIppool.getSubnet());
        list.addAll(switches);
        for (String ip : list) {
            if (ip == null) {
                continue;
            }
            saTIppool.setIp(ip);
            saTIppool.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);
            ippoolService.updateIpPool(saTIppool);
        }
        return true;
    }

    @Override
    public boolean syncSubnet(String id) {
        IdevelopUser sysUser = SecureUtil.getUser();
        SafeaccessSubnet csTSubnet = this.getById(id);
        if (csTSubnet == null) {
            return false;
        }
        return syncInsert(id, csTSubnet.toMap(), sysUser);
    }

    @Override
    public boolean selectSwitches(String subnetId) {
        List<String> switches = baseMapper.selectSwitches(subnetId);
        return switches.size() > 0;
    }

    @Override
    public List<SafeaccessEditSubnet> updateSubnet(ArrayList<SafeaccessEditSubnet> list) {
        ArrayList<SafeaccessEditSubnet> voList = Lists.newArrayList();
        IdevelopUser sysUser = SecureUtil.getUser();

        for (SafeaccessEditSubnet csTEditNet : list) {
            if (StringUtil.isNotBlank(csTEditNet.getId())) {
                SafeaccessEditSubnet vo = update(csTEditNet);
                voList.add(vo);
            } else {
                SafeaccessEditSubnet csTEditnetVO = save(csTEditNet);
                syncData(csTEditNet.getSubnetOld(), csTEditNet.getSubnetNew(), sysUser);
                voList.add(csTEditnetVO);
            }
        }
        return voList;
    }

    @Override
    public boolean setCommonSubNet(SafeaccessSubnet subnet) {
        return this.updateById(subnet);
    }

    @Override
    public List<String> ipPoolIdSegmentList(String subnetId) {
        int num = 0;
        int cou = 0;
        IdevelopUser sysUser = SecureUtil.getUser();

        String code = getCompCode4(sysUser);
        String tableName = SafeaccessIppool.getTablename() + code;
        Map<String, String> map = new HashMap<>();
        map.put("subnet", subnetId);
        map.put("ipPoolName", tableName);
        //查询所有ip
        List<SafeaccessIppoolDTO> list = ippoolService.search(map);
        for (SafeaccessIppoolDTO saTIppool : list) {
            num++;
            //截取ip查询是否存在于中间表中
            int number = saTIppool.getIp().lastIndexOf('.');
            String subsection = saTIppool.getIp().substring(0, number + 1);
            int count = baseMapper.selectSubnetIppoolByIppoolSegment(subsection);
            //如果ip前三段不存在与中间表则添加相应的信息
            if (count == 0) {
                Map<String, String> subnetIppool = new HashMap<>();
                subnetIppool.put("id", IdUtil.getSnowflakeNextIdStr());
                subnetIppool.put("subnetId", saTIppool.getSubnet());
                subnetIppool.put("ippoolId", saTIppool.getIpId());
                subnetIppool.put("ippoolSegment", subsection);
                subnetIppool.put("code", code);
                baseMapper.insertSubnetIppool(subnetIppool);
                cou++;
            }
        }
        //前端按照此返回值进行分页展示与查询
        Map<String, String> param = new HashMap<>();
        param.put("subnetId", subnetId);
        return baseMapper.selectSubnetIppoolList(param);
    }

    @Override
    public List<Map<String, String>> queryByParam(String institutionCode, String isPublic) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("institutionCode", institutionCode);
        map.put("isPublic", isPublic);
        IdevelopUser sysUser = SecureUtil.getUser();
        map.put("regionCode", sysUser.getRegionCode());
        return baseMapper.findByCondition(map);
    }


    private int syncData(String subnetOld, String subnetNew, IdevelopUser sysUser) {
        try {
            String compCode4 = getCompCode4(sysUser);
            String orgNo = getDeptId();
            List ipList = new ArrayList();
            Map<String, Object> map = new HashMap<>();
            map.put("subnetId", subnetOld);

            List<SafeaccessUserAccess> userList = userAccessService.findByConditionSubnetId(map);

            List<SafeaccessIppool> ipPoolList = getIpPools(subnetNew, "0", sysUser);

            SafeaccessUserAccess userAccess;
            SafeaccessIppool ippool;
            for (int i = 0; i < userList.size(); i++) {
                SafeaccessEditSubnet csTEditNet = new SafeaccessEditSubnet();
                userAccess = userList.get(i);
                ippool = ipPoolList.get(i);
                String oldIp = userAccess.getIpAddress();
                String newIp = ippool.getIp();
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                csTEditNet.setId(uuid);
                csTEditNet.setSubnetNew(subnetNew);
                csTEditNet.setSubnetOld(subnetOld);
                csTEditNet.setOldIp(oldIp);
                csTEditNet.setNewIp(newIp);
                csTEditNet.setCreateTime(new Date());
                userAccessService.saveEditNet(csTEditNet);
                Map tmap = new HashMap<String, String>();
                tmap.put("oldIp", oldIp);
                tmap.put("newIp", newIp);
                tmap.put("orgNo", orgNo);
                ipList.add(tmap);
                tmap.put("ipPoolName", SafeaccessIppool.getTablename() + compCode4);
                // 同步IP地址池,新子网
                ippoolService.updateIpPoolNewSubnet(tmap);

                // 同步台帐
                String deviceCode = userAccess.getDeviceCode();
                if (StringUtil.isNotBlank(deviceCode)) {
                    Map<Long, Map<String, Object>> pushErpMap = new HashMap<>();
                    Map<String, Object> hardWareMap = new HashMap<>();
                    hardWareMap.put("IP", newIp);
                    hardWareMap.put("uuid", userAccess.getDeviceUuid());
                    hardWareMap.put("ciId", userAccess.getDeviceCiId());
                    pushErpMap.put(Long.valueOf(userAccess.getDeviceId()), hardWareMap);
                    // 调用cmdb
                    cmdbService.cientityBatchupdate(pushErpMap, TransactionActionType.UPDATE);
                }
                // 同步认证用户表
                Map<String, String> userAccessMap = new HashMap<>();
                userAccessMap.put("newIp", newIp);
                userAccessMap.put("subnetNew", subnetNew);
                userAccessMap.put("id", userAccess.getId());
                userAccessService.updateUserAccess(userAccessMap);
            }

            SafeaccessSubnet csTSubnetOld = this.getById(subnetOld);
            SafeaccessSubnet csTSubnetNew = this.getById(subnetNew);

            // 同步radius
            // RadiusBizc.changeSubnet(groupnameOld, groupnameNew, ipList);
            // 同步IP地址池,原子网
            SafeaccessIppoolDTO ippoolDTO = new SafeaccessIppoolDTO();
            ippoolDTO.setOrgNo(orgNo);
            ippoolDTO.setSubnet(subnetOld);
            ippoolDTO.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);
            ippoolService.updateIpPoolOld(ippoolDTO);
        } catch (Exception e) {
            log.error("同步数据错误{}", e);
        }

        return 1;
    }


    private List<SafeaccessIppool> getIpPools(String subnet, String isUsed, IdevelopUser sysUser) {
        // 获取登陆账户的地市编码
        String compCode4 = getCompCode4(sysUser);
        String orgNo = getDeptId();
        Map<String, Object> map = new HashMap<>();
        map.put("subnet", subnet);
        map.put("orgNo", orgNo);
        map.put("isUse", isUsed);
        map.put("ipPoolName", SafeaccessIppool.getTablename() + compCode4);
        return ippoolService.selectIpPools(map);
    }

    private SafeaccessEditSubnet save(SafeaccessEditSubnet csTEditNet) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        csTEditNet.setId(uuid);
        baseMapper.saveEditNet(csTEditNet);
        return csTEditNet;
    }

    private SafeaccessEditSubnet update(SafeaccessEditSubnet CsTEditNet) {
        try {
            baseMapper.updateCsTEditNet(CsTEditNet);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return CsTEditNet;
    }

    public boolean syncInsert(String id, Map<String, String> map, IdevelopUser sysUser) {
        String groupname = map.get("userGroup");
        String value = map.get("vlanId");
        String broadcast = map.get("broadcastAddress");
        String subnet = map.get("subnetAddress");
        String route = map.get("subnetGateway");
        String mask = map.get("subnetMask");
        String dns = map.get("dnsip");
        String default_lease_time = map.get("defaultLeaseTime");
        String attribute1 = "Tunnel-Type";
        String attribute2 = "Tunnel-Medium-Type";
        String attribute3 = "Tunnel-Private-Group-ID";
        String op = ":=";
        String value1 = "VLAN";
        String value2 = "IEEE-802";
        if (radiusBizc.deleteRadgroupreply(id, sysUser) == -1) {
            return false;
        }
        radiusBizc.addRadgroupreply(groupname, attribute1, op, value1, broadcast, subnet, route, mask, dns, id, default_lease_time, sysUser);
        radiusBizc.addRadgroupreply(groupname, attribute2, op, value2, broadcast, subnet, route, mask, dns, id, default_lease_time, sysUser);
        radiusBizc.addRadgroupreply(groupname, attribute3, op, value, broadcast, subnet, route, mask, dns, id, default_lease_time, sysUser);
        return true;
    }

    private String getCompCode4(IdevelopUser sysUser) {
        String orgNo = sysUser.getRegionCode();
        if (orgNo.length() < 4) {
            throw new ServiceException("当前登录账号不是具体地市账号,无法使用该功能");
        }
        return orgNo.length() >= 4 ? orgNo.substring(0, 4) : orgNo;
    }

    private String getDeptId() {
        IdevelopUser sysUser = SecureUtil.getUser();
        if (sysUser != null) {
            return sysUser.getDeptId();
        } else {
            throw new ServiceException("无法获取用户信息");
        }
    }


    /**
     * 向radius插数据
     *
     * @param csTSubnet
     * @param sysUser
     * @return
     */
    private boolean insert(SafeaccessSubnet csTSubnet, IdevelopUser sysUser) {
        String id = String.valueOf(csTSubnet.getId());
        String groupname = csTSubnet.getUserGroup();
        String value = csTSubnet.getVlanId();
        String broadcast = csTSubnet.getBroadcastAddress();
        String subnet = csTSubnet.getSubnetAddress();
        String route = csTSubnet.getSubnetGateway();
        String mask = csTSubnet.getSubnetMask();
        String dns = csTSubnet.getDnsip();
        String default_lease_time = csTSubnet.getDefaultLeaseTime();
        String attribute1 = "Tunnel-Type";
        String attribute2 = "Tunnel-Medium-Type";
        String attribute3 = "Tunnel-Private-Group-ID";
        String op = ":=";
        String value1 = "VLAN";
        String value2 = "IEEE-802";

        int conn = radiusBizc.addRadgroupreply(groupname, attribute1, op, value1, broadcast, subnet, route, mask, dns, id, default_lease_time, sysUser);
        if (conn == -1) {
            csTSubnet.setRdsState("-1");
            setFlag(csTSubnet);
            return false;
        }
        radiusBizc.addRadgroupreply(groupname, attribute2, op, value2, broadcast, subnet, route, mask, dns, id, default_lease_time, sysUser);
        radiusBizc.addRadgroupreply(groupname, attribute3, op, value, broadcast, subnet, route, mask, dns, id, default_lease_time, sysUser);
        return true;
    }


    private boolean update(String id, SafeaccessSubnet csTSubnet, IdevelopUser sysUser) {
        SafeaccessSubnet csSubnet2 = this.getById(csTSubnet.getId());
        String groupname = csSubnet2.getUserGroup();
        String value = csSubnet2.getVlanId();
        String broadcast = csSubnet2.getBroadcastAddress();
        String subnet = csSubnet2.getSubnetAddress();
        String route = csSubnet2.getSubnetGateway();
        String mask = csSubnet2.getSubnetMask();
        String dns = csSubnet2.getDnsip();
        String default_lease_time = csSubnet2.getDefaultLeaseTime();
        String attribute1 = "Tunnel-Type";
        String attribute2 = "Tunnel-Medium-Type";
        String attribute3 = "Tunnel-Private-Group-ID";
        String op = ":=";
        String value1 = "VLAN";
        String value2 = "IEEE-802";

        int conn = radiusBizc.updateRadgroupreply(id, groupname, value1, attribute1, broadcast, subnet, route, mask, dns, default_lease_time, sysUser);
        if (conn == -1) {
            // bakcol7 同步radius失败
            csTSubnet.setRdsState("-1");
            setFlag(csTSubnet);
            return false;
        }
        radiusBizc.updateRadgroupreply(id, groupname, value2, attribute2, broadcast, subnet, route, mask, dns, default_lease_time, sysUser);
        radiusBizc.updateRadgroupreply(id, groupname, value, attribute3, broadcast, subnet, route, mask, dns, default_lease_time, sysUser);
        return true;
    }

    /**
     * 改变radius状态
     *
     * @param csTSubnet
     */
    private void setFlag(SafeaccessSubnet csTSubnet) {
        baseMapper.updateById(csTSubnet);
    }


    /**
     * 创建子网的ip地址池
     *
     * @param subnetId
     * @param code
     * @return
     */
    private boolean createIpPool(String subnetId, String code) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", subnetId);
        List<Map<String, String>> list = baseMapper.findByCondition(map);
        String gateway = list.get(0).get("subnetGateway");
        String mask = list.get(0).get("subnetMask");
        String subnetAddress = list.get(0).get("subnetAddress");
        return createIpPool(subnetId, gateway, mask, subnetAddress, code);
    }

    private boolean createIpPool(String subnetId, String mask, String code) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", subnetId);
        List<Map<String, String>> list = baseMapper.findByCondition(map);
        String gateway = list.get(0).get("subnetGateway");
        String subnetAddress = list.get(0).get("subnetAddress");
        return createIpPool(subnetId, gateway, mask, subnetAddress, code);
    }

    private boolean createIpPool(SafeaccessIppool saTIppool, String code) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", saTIppool.getSubnet());
        List<Map<String, String>> findByCondition = baseMapper.findByCondition(map);
        String gateway = findByCondition.get(0).get("subnetGateway");
        String mask = findByCondition.get(0).get("subnetMask");
        String subnetAddress = findByCondition.get(0).get("subnetAddress");
        return createIpPool(saTIppool.getSubnet(), gateway, mask, subnetAddress, code);
    }


    private boolean createIpPool(String subnetId, String gateway, String mask, String subnetAddress, String compCode4) {
        if (!Common.isSubnetMask(mask)) {
            return false;
        }
        List<String> list = SubnetDivision.getIpsInSubnet(gateway, mask);
        log.info("createIpPool1: {}", list);
        Map<String, String> ipMap = new HashMap<>();
        ipMap.put("subnetId", subnetId);
        ipMap.put("tableName", SafeaccessIppool.getTablename() + compCode4);
        List<String> ipListAll = baseMapper.queryIpBySubnet(ipMap);
        log.info("createIpPool2: {}", ipListAll);
        if (list.size() < ipListAll.size() + 1) {
            log.error("新生成的IP段小于已有IP段,请重新选择!");
            throw new ServiceException("新生成的IP段小于已有IP段,请重新选择!");
        }
        list.removeAll(ipListAll);
        int count = baseMapper.queryNewIpBySubnet(list, SafeaccessIppool.getTablename() + compCode4);
        log.info("createIpPool3: {}", count);
        if (count != 0) {
            log.error("新生成的IP与已有IP重复,请删除重复IP!");
            throw new ServiceException("新生成的IP与已有IP重复,请删除重复IP");
        }
        List<Map<String, String>> ipList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String ip = list.get(i);
            //如果ip与子网地址相同，则跳过--xfb
            if (!StringUtil.isBlank(subnetAddress) && ip.equals(subnetAddress)) {
                continue;
            }
            String id = IdUtil.getSnowflakeNextIdStr();
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            // 删除ip地址池时用
            map.put("orgNo", compCode4);
            map.put("subnetId", subnetId);
            map.put("ip", ip);
            map.put("orderNo", i + "");
            ipList.add(map);
        }
        log.info("createIpPool4: {}", ipList);
        if (ipList.size() == 0) {
            return false;
        }
        ippoolService.insertIpPool(SafeaccessIppool.getTablename() + compCode4, ipList);
        // 设置网关
        Map<String, String> map = new HashMap<>();
        map.put("subnet", subnetId);
        map.put("gateway", gateway);
        map.put("ipPoolName", SafeaccessIppool.getTablename() + compCode4);
        ippoolService.updateGatewayByIp(map);
        return true;
    }

    /**
     * 清空子网的ip地址池
     *
     * @param subnetId
     * @param sysUser
     * @return
     */
    public int clearIpPool(String subnetId, IdevelopUser sysUser) {
        String compCode4 = getCompCode4(sysUser);
        String orgNo = getDeptId();
        SafeaccessIppoolDTO saTIppool = new SafeaccessIppoolDTO();
        saTIppool.setOrgNo(orgNo);
        saTIppool.setSubnet(subnetId);
        saTIppool.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);
        return ippoolService.deleteIpPool(saTIppool);
    }

    public int clearIpPool(SafeaccessIppoolDTO saTIppool, IdevelopUser sysUser) {
        String compCode4 = getCompCode4(sysUser);
        saTIppool.setOrgNo(compCode4);
        saTIppool.setIpPoolName(SafeaccessIppool.getTablename() + compCode4);
        int deleteNum = ippoolService.deleteIpPool(saTIppool);
        return deleteNum;
    }
}
