package com.lnsoft.device.feign;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lnsoft.cmdb.entity.FeignCiCientity;
import com.lnsoft.cmdb.entity.FeignCmdbCientityGet;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.pojo.IdevelopUser;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.BeanUtil;
import com.lnsoft.device.api.cmdb.dto.CmdbStockDTO;
import com.lnsoft.device.api.cmdb.service.ICmdbDictService;
import com.lnsoft.device.api.cmdb.service.ICmdbService;
import com.lnsoft.device.api.oss.service.IOssFileService;
import com.lnsoft.device.api.res.service.IApproveRecordService;
import com.lnsoft.device.api.res.service.ILogOptService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessSubnetService;
import com.lnsoft.device.api.safeaccess.service.ISafeaccessUserAccessService;
import com.lnsoft.device.api.stock.service.IHardwareBasicCmdbService;
import com.lnsoft.device.api.stock.service.II6000ERPService;
import com.lnsoft.device.api.warehouse.service.IDeviceScrapService;
import com.lnsoft.device.api.warehouse.service.IDeviceStorageService;
import com.lnsoft.device.api.warehouse.service.IDeviceTransferDetailService;
import com.lnsoft.device.api.warehouse.service.IDeviceTransferService;
import com.lnsoft.device.dto.*;
import com.lnsoft.device.entity.*;
import com.lnsoft.device.eums.TransactionActionType;
import com.lnsoft.device.so.SafeaccessUserAccessSO;
import com.lnsoft.device.vo.FeignCiCientityVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DeviceClient
 * @description:
 * @author: zhangs
 * @create: 2024-04-15 14:51
 **/
@ApiIgnore
@RestController
@AllArgsConstructor
public class DeviceClient implements IDeviceClient {

    private ICmdbService iCmdbService;
    private IDeviceScrapService deviceScrapService;
    private IDeviceStorageService deviceStorageService;
    private IDeviceTransferService deviceTransferService;
    private IDeviceTransferDetailService deviceTransferDetailService;
    private ISafeaccessUserAccessService safeaccessUserAccessService;
    private ISafeaccessSubnetService safeaccessSubnetService;
    private ICmdbDictService iCmdbDictService;
    private II6000ERPService i6000ERPService;
    private IHardwareBasicCmdbService iHardwareBasicCmdbService;
    private ILogOptService logOptService;
    private IApproveRecordService approveRecordService;
    private IOssFileService ossFileService;


    @Override
    public R<Map<String, Object>> feignCiEntityBatchSave(AutomaticSaveCmdbDTO save) {
        Map<String, Object> stringObjectMap = iCmdbService.cientityBatchsave(save.getCiId(), save.getSaveList(), TransactionActionType.INSERT);
        return R.data(stringObjectMap);
    }

    /**
     * 修改资产台账
     *
     * @param update
     * @return
     */
    @Override
    public R<Map<String, Object>> feignCiEntityBatchUpdate(Map<Long, Map<String, Object>> update) {
        Map<String, Object> stringObjectMap = iCmdbService.cientityBatchupdate(update, TransactionActionType.UPDATE);
        return R.data(stringObjectMap);
    }

    /**
     * 根据模型ID获取配置项信息,后端列转行 只供字典表使用
     *
     * @param ciId
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getcicientitydictList")
    public R<List<Map<String, Object>>> feignGetCiCientityDictList(Long ciId) {
        List<Map<String, Object>> ciCientityList = iCmdbService.getCiCientityList(ciId);
        return R.data(ciCientityList);
    }

    /**
     * 支持查询设备分类下获取分页查询资产台账
     *
     * @param cientitySearch
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getCiCientityListByClaccifys")
    public R<FeignCiCientityVO> getCiCientityListByClaccify(CiCientitySearch cientitySearch) {
        FeignCiCientity feignCiCientity = iCmdbService.getCiCientityListByCondition(cientitySearch);
        FeignCiCientityVO convert = Convert.convert(new TypeReference<FeignCiCientityVO>() {
        }, feignCiCientity);
        return R.data(convert);
    }

    /**
     * 获取设备报废实体list数据;
     *
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getDeviceScrapList")
    public R<FeignCiCientityVO> getDeviceScrapList() {
        List<Map<String, Object>> deviceScrapLs = deviceScrapService.listMaps();
        FeignCiCientityVO feignCiCientityVO = new FeignCiCientityVO();
        feignCiCientityVO.setData(deviceScrapLs);
        return R.data(feignCiCientityVO);
    }

    /**
     * 获取设备入库实体list数据;
     *
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getDeviceStorageList")
    public R<FeignCiCientityVO> getDeviceStorageList() {
        List<Map<String, Object>> deviceStorageLs = deviceStorageService.listMaps();
        FeignCiCientityVO feignCiCientityVO = new FeignCiCientityVO();
        feignCiCientityVO.setData(deviceStorageLs);
        return R.data(feignCiCientityVO);
    }

    /**
     * 根据nameplateNo获取设备报废实体list数据;
     *
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getDeviceScrapListByNameplateNo")
    public R<FeignCiCientityVO> getDeviceScrapListByNameplateNo(QueryWrapper param) {
        List<Map<String, Object>> deviceStorageLs = deviceStorageService.listMaps(param);
        FeignCiCientityVO feignCiCientityVO = new FeignCiCientityVO();
        feignCiCientityVO.setData(deviceStorageLs);
        return R.data(feignCiCientityVO);
    }

    /**
     * 获取设备转资实体list数据;
     *
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getDeviceTransferList")
    public R<FeignCiCientityVO> getDeviceTransferList() {
        List<Map<String, Object>> deviceTransferLs = deviceTransferService.listMaps();
        FeignCiCientityVO feignCiCientityVO = new FeignCiCientityVO();
        feignCiCientityVO.setData(deviceTransferLs);
        return R.data(feignCiCientityVO);
    }

    /**
     * 获取设备转资明细实体list数据;
     *
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getDeviceTransferDetailList")
    public R<FeignCiCientityVO> getDeviceTransferDetailList() {
        List<Map<String, Object>> deviceTransferDetailLs = deviceTransferDetailService.listMaps();
        FeignCiCientityVO feignCiCientityVO = new FeignCiCientityVO();
        feignCiCientityVO.setData(deviceTransferDetailLs);
        return R.data(feignCiCientityVO);
    }

    /**
     * 用于数据治理修改接口
     *
     * @param cmdbStockDTO
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/cientityBatchupdateStock")
    public R<Map<String, Object>> feignCientityBatchupdateStock(FeignCmdbStockDTO cmdbStockDTO) {
        CmdbStockDTO stockDTO = BeanUtil.copy(cmdbStockDTO, CmdbStockDTO.class);
        return R.data(iHardwareBasicCmdbService.cientityBatchupdateStock(stockDTO));
    }

    /**
     * 配置项详情 列转行
     *
     * @param feignDeviceCientityGet
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getCientityDetail")
    public R<Map<String, Object>> getCientityDetail(FeignDeviceCientityGet feignDeviceCientityGet) {
        FeignCmdbCientityGet feignCmdbCientityGet = BeanUtil.copy(feignDeviceCientityGet, FeignCmdbCientityGet.class);
        return R.data(iCmdbService.getCientityDetail(feignCmdbCientityGet));
    }


    /**
     * 终端用户入网信息表
     *
     * @param feignSafeaccessSwitcheSO
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getcustompage")
    public R<FeignSafeaccessUserAccessDTO> selectSafeaccessSwitchePage(FeignSafeaccessUserAccessSO feignSafeaccessSwitcheSO) {
        SafeaccessUserAccessSO safeaccessUserAccessSO = feignSafeaccessSwitcheSO.getSafeaccessUserAccessSO();
        Query query = feignSafeaccessSwitcheSO.getQuery();
        IdevelopUser sysUser = feignSafeaccessSwitcheSO.getSysUser();

        IPage<SafeaccessUserAccessDTO> safeaccessSwitcheIPage = safeaccessUserAccessService.customPage(safeaccessUserAccessSO, query, sysUser);

        List<SafeaccessUserAccessDTO> records = safeaccessSwitcheIPage.getRecords();
        long total = safeaccessSwitcheIPage.getTotal();

        FeignSafeaccessUserAccessDTO feignSafeaccessUserAccessDTO = new FeignSafeaccessUserAccessDTO();
        feignSafeaccessUserAccessDTO.setRecords(records);
        feignSafeaccessUserAccessDTO.setTotal(total);

        return R.data(feignSafeaccessUserAccessDTO);
    }


    /**
     * 分页 子网管理表
     *
     * @param feignSafeaccessSwitcheSO
     * @return
     */
    @Override
    @PostMapping(API_PREFIX + "/getswitchepage")
    public R<FeignSafeaccessSubnetDTO> selectSafeaccessSubnetPage(FeignSafeaccessSubnet feignSafeaccessSwitcheSO) {
        SafeaccessSubnet safeaccessSubnet = feignSafeaccessSwitcheSO.getSafeaccessSubnet();
        Query query = feignSafeaccessSwitcheSO.getQuery();
        IdevelopUser sysUser = feignSafeaccessSwitcheSO.getSysUser();

        IPage<SafeaccessSubnet> selectSafeaccessSubnetPage = safeaccessSubnetService.selectSafeaccessSubnetPage(safeaccessSubnet, query, sysUser);
        List<SafeaccessSubnet> records = selectSafeaccessSubnetPage.getRecords();
        long total = selectSafeaccessSubnetPage.getTotal();

        FeignSafeaccessSubnetDTO feignSafeaccessSubnetDTO = new FeignSafeaccessSubnetDTO();
        feignSafeaccessSubnetDTO.setRecords(records);
        feignSafeaccessSubnetDTO.setTotal(total);

        return R.data(feignSafeaccessSubnetDTO);
    }

    @Override
    @PostMapping(API_PREFIX + "/dict/addDict")
    public R<Map<String, Object>> addDict(CmdbDict cmdbDict) {
        Map<String, Object> map = iCmdbDictService.cientityBatchsave(cmdbDict, TransactionActionType.INSERT);
        return R.data(map);
    }

    @Override
    @PostMapping(API_PREFIX + "/cmdb/i6000/abutment")
    public R<String> cmdbI6000Abutment(I6000SrynDTO i6000SrynDTO) {
        return R.data(i6000ERPService.syncI6000DetailTask(i6000SrynDTO));
    }

    /**
     * 业务流程增加操作记录
     *
     * @param logOpt
     */
    @Override
    @PostMapping(API_PREFIX + "/common/log/opt")
    public R<Boolean> commonLogOpt(LogOpt logOpt) {
        logOptService.commonLogOpt(logOpt);
        return R.data(Boolean.TRUE);
    }

    /**
     * 新增审核记录流程
     *
     * @param approveRecord
     */
    @Override
    @PostMapping(API_PREFIX + "/common/record")
    public R<Boolean> commonRecord(ApproveRecord approveRecord) {
        approveRecordService.commonRecord(approveRecord);
        return R.data(Boolean.TRUE);
    }

    /**
     * 业务流程修改审核记录流程
     *
     * @param approveRecord
     */
    @Override
    @PostMapping(API_PREFIX + "/common/record/update")
    public R<Boolean> commonRecordUpdate(ApproveRecord approveRecord) {

        LambdaUpdateWrapper<ApproveRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ApproveRecord::getOptOpinion, approveRecord.getOptOpinion())
				.set(ApproveRecord::getOptTitle, approveRecord.getOptTitle())
				.set(ApproveRecord::getUpdateTime, new Date())
				.eq(ApproveRecord::getFilingNo, approveRecord.getFilingNo())
				.eq(ApproveRecord::getNodeId, approveRecord.getNodeId());
		boolean update = approveRecordService.update(updateWrapper);
		return R.data(update);
    }

    /**
     * 信通一体化通用OSS上传文件对外接口(新增)
     *
     * @param ossFile
     */
    @Override
    public R<Boolean> saveOss(OssFile ossFile) {
        return R.data(ossFileService.save(ossFile));
    }

    /**
     * 信通一体化通用OSS上传文件对外接口(查询)
     *
     * @param ossFile
     */
    @Override
    public R<OssFile> getOss(OssFile ossFile) {
        QueryWrapper<OssFile> queryWrapper = Condition.getQueryWrapper(ossFile);
        OssFile ossFileOne = ossFileService.getOne(queryWrapper);
        return R.data(ossFileOne);
    }


}
