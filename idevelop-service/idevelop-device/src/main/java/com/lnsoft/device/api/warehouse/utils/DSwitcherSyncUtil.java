package com.lnsoft.device.api.warehouse.utils;

import com.alibaba.fastjson.JSONObject;
import com.lnsoft.common.cache.CacheNames;
import com.lnsoft.core.tool.api.ResultCode;
import com.lnsoft.core.tool.utils.RedisUtil;
import com.lnsoft.device.api.safeaccess.dto.SdnUserAccessDTO;
import com.lnsoft.device.api.warehouse.dto.DSwitcherSyncDTO;
import com.lnsoft.device.api.warehouse.dto.ServerInfoDTO;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnQingDao;
import com.lnsoft.device.api.warehouse.entity.DeviceSdnUserAccess;
import com.lnsoft.device.api.warehouse.entity.SyncSdn;
import com.lnsoft.device.api.warehouse.mapper.SyncSdnMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DSwitcherSyncUtil {

    @Value(value = "${sync.boolean}")
    private boolean switcherSyncBoolean;

    @Value(value = "${sync.http-port}")
    private String switcherSyncUrlPort;

    @Value(value = "${sync.switcher}")
    private String switcherSyncUrl;

    @Value(value = "${sync.return}")
    private String returnSyncUrl;

    @Value(value = "${sdn.boolean}")
    private boolean sdnSyncBoolean;

    @Value(value = "${sdn.http-port}")
    private String sdnSyncUrlPort;

    @Value(value = "${sdn.url}")
    private String sdnSyncUrl;
    @Value(value = "${sdn.qingdaoUrl}")
    private String qingdaoUrl;

    @Resource
    private SyncSdnMapper syncSdnMapper;
    @Resource
    private RedisUtil redisUtil;


    /**
     * 设备投运推送数据同步服务
     *
     * @param dSwitcherSyncList 推送数据信息
     */
    public void insertDSwitcherSync(List<DSwitcherSyncDTO> dSwitcherSyncList, String switcherType, Boolean isTask) throws Exception {

        DSwitcherSyncDTO dSwitcherSyncDTO = dSwitcherSyncList.get(0);
        ServerInfoDTO serverInfo = dSwitcherSyncDTO.getServerInfo();

        String regionCode = serverInfo.getCode().length() > 4 ? serverInfo.getCode().substring(0, 4) : serverInfo.getCode();
        String switcherKey = CacheNames.DEVICE_SYNC_SDN_SWITCHER + regionCode;

        if (redisUtil.hasKey(switcherKey) && !isTask) {
            SyncSdn syncSdn = new SyncSdn();
            syncSdn.setSwitcherType(switcherType);
            syncSdn.setRegionCode(regionCode);
            syncSdn.setRequestType("Sync");
            syncSdn.setRequest(JSONObject.toJSONString(dSwitcherSyncList));
            int save = syncSdnMapper.insert(syncSdn);
            log.info("当前地市存在正在新增或者同步Radius流程, 将数据插入表中：{}", save);
        } else {
            if (switcherSyncBoolean) {
                redisUtil.set(switcherKey, "1", 3, TimeUnit.MINUTES);
                RestTemplate restTemplate = new RestTemplate();
                try {
                    ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(switcherSyncUrlPort + switcherSyncUrl, dSwitcherSyncList, String.class);
                    log.info("设备投运推送数据同步返回结果：" + JSONObject.toJSONString(stringResponseEntity));
                    if (HttpStatus.OK.value() == stringResponseEntity.getStatusCode().value()) {
                        JSONObject jsonObject = JSONObject.parseObject(stringResponseEntity.getBody());
                        if (ResultCode.SUCCESS.getCode() != (int) jsonObject.get("code")) {
                            log.info("设备投运推送数据同步返回结果：" + JSONObject.toJSONString(jsonObject));
                        }
                    } else {
                        log.info("设备投运推送数据同步失败：" + JSONObject.toJSONString(stringResponseEntity));
                    }
                } catch (Exception e) {
                    log.info("调用数据同步服务失败" + e.getMessage());
                    throw new Exception("调用数据同步服务失败" + e.getMessage());
                } finally {
                    redisUtil.del(switcherKey);
                }
            }
        }
    }

    /**
     * 设备退运推送数据同步服务
     *
     * @param dSwitcherSyncList 推送数据信息
     */
    public void delDSwitcherSync(List<DSwitcherSyncDTO> dSwitcherSyncList, String switcherType, Boolean isTask) throws Exception {

        DSwitcherSyncDTO dSwitcherSyncDTO = dSwitcherSyncList.get(0);
        ServerInfoDTO serverInfo = dSwitcherSyncDTO.getServerInfo();

        String regionCode = serverInfo.getCode().length() > 4 ? serverInfo.getCode().substring(0, 4) : serverInfo.getCode();

        String switcherKey = CacheNames.DEVICE_SYNC_SDN_SWITCHER + regionCode;
        if (redisUtil.hasKey(switcherKey) && !isTask) {
            SyncSdn syncSdn = new SyncSdn();
            syncSdn.setSwitcherType(switcherType);
            syncSdn.setRegionCode(regionCode);
            syncSdn.setRequestType("Sync");
            syncSdn.setRequest(JSONObject.toJSONString(dSwitcherSyncList));
            int save = syncSdnMapper.insert(syncSdn);
            log.info("当前地市存在正在新增或者同步Radius流程, 将数据插入表中：{}", save);
        } else {
            if (switcherSyncBoolean) {
                RestTemplate restTemplate = new RestTemplate();
                try {
                    ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(switcherSyncUrlPort + returnSyncUrl, dSwitcherSyncList, String.class);
                    log.info("推送数据同步返回结果：" + stringResponseEntity.toString());
                    if (HttpStatus.OK.value() == stringResponseEntity.getStatusCode().value()) {
                        JSONObject jsonObject = JSONObject.parseObject(stringResponseEntity.getBody());
                        if (ResultCode.SUCCESS.getCode() != (int) jsonObject.get("code")) {
                            log.info("设备退运推送数据同步返回结果：" + jsonObject.toString());
                        }
                    } else {
                        log.info("设备退运推送数据同步失败：" + stringResponseEntity.toString());
                    }
                } catch (Exception e) {
                    log.info("调用数据同步服务失败");
                    throw new Exception("调用数据同步服务失败");
                }
            }
        }

    }

    /**
     * 同步SDN
     *
     * @param deviceSdnUserAccessList 同步数据
     */
    public void deviceSdnUserAccess(List<DeviceSdnUserAccess> deviceSdnUserAccessList) throws Exception {
        if (sdnSyncBoolean) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sdnSyncUrlPort + sdnSyncUrl, deviceSdnUserAccessList, String.class);
                log.info("推送SDN同步返回结果：" + stringResponseEntity.toString());
                if (HttpStatus.OK.value() == stringResponseEntity.getStatusCode().value()) {
                    JSONObject jsonObject = JSONObject.parseObject(stringResponseEntity.getBody());
                    if (ResultCode.SUCCESS.getCode() != (int) jsonObject.get("code")) {
                        log.info("推送SDN数据返回结果：" + jsonObject.toString());
                    }
                } else {
                    log.info("推送SDN数据同步失败：" + stringResponseEntity.toString());
                }
            } catch (Exception e) {
                log.info("调用SDN数据同步服务失败");
                throw new Exception("调用SDN数据同步服务失败");
            }
        }
    }

    /**
     * 同步SDN青岛
     *
     * @param deviceSdnQingDao 同步数据
     */
    public void deviceSdnQingDao(DeviceSdnQingDao deviceSdnQingDao) throws Exception {
        if (sdnSyncBoolean) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sdnSyncUrlPort + qingdaoUrl, deviceSdnQingDao, String.class);
                log.info("推送SDN同步返回结果：" + stringResponseEntity.toString());
                if (HttpStatus.OK.value() == stringResponseEntity.getStatusCode().value()) {
                    JSONObject jsonObject = JSONObject.parseObject(stringResponseEntity.getBody());
                    if (ResultCode.SUCCESS.getCode() != (int) jsonObject.get("code")) {
                        log.info("推送SDN数据返回结果：" + jsonObject.toString());
                    }
                } else {
                    log.info("推送SDN数据同步失败：" + stringResponseEntity.toString());
                }
            } catch (Exception e) {
                log.info("调用SDN数据同步服务失败");
                throw new Exception("调用SDN数据同步服务失败");
            }
        }
    }

    /**
     * 同步SDN
     *
     * @param sdnUserAccessDTO 同步数据
     */
    public Map<String, Object> getUserAccessList(SdnUserAccessDTO sdnUserAccessDTO) throws Exception {
        if (sdnSyncBoolean) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sdnSyncUrlPort + "/sdn/user/access", sdnUserAccessDTO, String.class);
                log.info("推送SDN同步返回结果：" + stringResponseEntity.toString());
                if (HttpStatus.OK.value() == stringResponseEntity.getStatusCode().value()) {
                    JSONObject jsonObject = JSONObject.parseObject(stringResponseEntity.getBody());
                    Map<String, Object> innerMap = jsonObject.getInnerMap();
                    log.info("推送SDN数据返回结果：{}", jsonObject);
                    int code = (int) innerMap.get("code");
                    if (HttpStatus.OK.value() == code) {
                        List<Map<String, Object>> maps = (List<Map<String, Object>>) innerMap.get("data");
                        return maps.get(0);
                    } else {
                        return innerMap;
                    }
                } else {
                    log.info("推送SDN数据同步失败：" + stringResponseEntity.toString());
                }
            } catch (Exception e) {
                log.info("调用SDN数据同步服务失败");
                throw new Exception("调用SDN数据同步服务失败");
            }
        }
		return new HashMap<>();
    }

    public Map<String, Object> delUserAccess(SdnUserAccessDTO sdnUserAccessDTO) throws Exception {
        if (sdnSyncBoolean) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sdnSyncUrlPort + "/sdn/del/user/access", sdnUserAccessDTO, String.class);
                log.info("推送SDN同步返回结果：" + stringResponseEntity.toString());
                if (HttpStatus.OK.value() == stringResponseEntity.getStatusCode().value()) {
                    JSONObject jsonObject = JSONObject.parseObject(stringResponseEntity.getBody());
                    Map<String, Object> innerMap = jsonObject.getInnerMap();
                    log.info("推送SDN数据返回结果：{}", jsonObject);
                    int code = (int) innerMap.get("code");
                    if (HttpStatus.OK.value() == code) {
                        List<Map<String, Object>> maps = (List<Map<String, Object>>) innerMap.get("data");
                        return maps.get(0);
                    } else {
                        return innerMap;
                    }
                } else {
                    log.info("推送SDN数据同步失败：" + stringResponseEntity.toString());
                }
            } catch (Exception e) {
                log.info("调用SDN数据同步服务失败");
                throw new Exception("调用SDN数据同步服务失败");
            }
        }
        return new HashMap<>();
    }

    public Map<String, Object> updateUserAccess(SdnUserAccessDTO sdnUserAccessDTO) throws Exception {
        if (sdnSyncBoolean) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(sdnSyncUrlPort + "/sdn/update/user/access", sdnUserAccessDTO, String.class);
                log.info("推送SDN同步返回结果：" + stringResponseEntity.toString());
                if (HttpStatus.OK.value() == stringResponseEntity.getStatusCode().value()) {
					JSONObject jsonObject = JSONObject.parseObject(stringResponseEntity.getBody());
					Map<String, Object> innerMap = jsonObject.getInnerMap();
                    log.info("推送SDN数据返回结果：{}", jsonObject);
                    int code = (int) innerMap.get("code");
                    if (HttpStatus.OK.value() == code) {
                        List<Map<String, Object>> maps = (List<Map<String, Object>>) innerMap.get("data");
                        return maps.get(0);
                    } else {
                        return innerMap;
                    }
                } else {
                    log.info("推送SDN数据同步失败：" + stringResponseEntity.toString());
                }
            } catch (Exception e) {
                log.info("调用SDN数据同步服务失败");
                throw new Exception("调用SDN数据同步服务失败");
            }
        }
        return new HashMap<>();
    }
}
