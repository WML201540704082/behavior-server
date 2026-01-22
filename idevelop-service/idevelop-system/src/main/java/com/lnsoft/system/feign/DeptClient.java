package com.lnsoft.system.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.StringUtil;
import com.lnsoft.system.entity.Dept;
import com.lnsoft.system.service.IDeptService;
import com.lnsoft.system.vo.DeptVO;
import com.lnsoft.system.wrapper.DeptWrapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xyzadmin
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class DeptClient implements IDeptClient {

    private IDeptService deptService;

    @Override
    @GetMapping(API_PREFIX + "/getList")
    public R<List<Dept>> list() {
        List<Dept> depts = deptService.getAllCorp();
        return R.data(depts);
    }

    @Override
    @GetMapping(API_PREFIX + "/getTreeList")
    public R<List<DeptVO>> getTreeList(String parentId) {
        List<Dept> list = deptService.getTreeList(parentId);
        List<Dept> corpList = list.stream().filter(s -> "CORP".equals(s.getType())).collect(Collectors.toList());
        List<DeptVO> corpVOList = DeptWrapper.build().listNodeVO(corpList);
        return R.data(corpVOList);
    }

    @Override
    @GetMapping(API_PREFIX + "/getDeptList")
    public R<List<DeptVO>> getDeptList(String parentId) {
        List<Dept> list = deptService.getTreeList(parentId);
        List<Dept> corpList = list.stream().filter(s -> "DEPT".equals(s.getType())).collect(Collectors.toList());
        List<DeptVO> corpVOList = DeptWrapper.build().listNodeVO(corpList);
        return R.data(corpVOList);
    }

    @Override
    @GetMapping(API_PREFIX + "/getByI6000Code")
    public R<Dept> getByI6000Code(String i6000UnitCode) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getI6000UnitCode, i6000UnitCode).eq(Dept::getType, "CORP");
        return R.data(deptService.getOne(queryWrapper));
    }


    @Override
    @GetMapping(API_PREFIX + "/getCodeByName")
    public R<Dept> getCodeByName(String name, String pid) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getDeptName, name).or().eq(Dept::getFullName, name);
        if (StringUtil.isNotBlank(pid)) {
            queryWrapper.like(Dept::getAncestors, pid);
        }
        List<Dept> list = deptService.list(queryWrapper);
        if (list != null && list.size() > 0) {
            //存在异常数据
            return R.data(list.get(0));
        }
        return R.data(null);
    }

    @Override
    public R<List<Dept>> getByRegionCode(String regionCode) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getRegionCode, regionCode).eq(Dept::getType, "CORP");
        List<Dept> list = deptService.list(queryWrapper);
        if (list == null) {
            return R.fail("查询erpUnitCode失败！");
        }
        return R.data(list);
    }

    @Override
    public R<List<Dept>> getByRegionCodeControl(String regionCode) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(Dept::getRegionCode, regionCode).eq(Dept::getType, "CORP");
        List<Dept> list = deptService.list(queryWrapper);
        if (list == null) {
            return R.fail("查询erpUnitCode失败！");
        }
        return R.data(list);
    }

    @Override
    public R<List<Dept>> getByPidAndFullName(String parentId, String fullName) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getParentId, parentId).like(Dept::getFullName, fullName);
        List<Dept> list = deptService.list(queryWrapper);
        if (list == null) {
            return R.fail("查询单位部门失败！");
        }
        return R.data(list);
    }

    @Override
    public R<Dept> getById(String id) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getId, id).eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
        Dept dept = deptService.getOne(queryWrapper);
        return R.data(dept);
    }

    @Override
    public R<List<Dept>> getByI6000Unit(String i6000UnitCode) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getI6000UnitCode, i6000UnitCode).eq(Dept::getType, "CORP");
        Dept one = deptService.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(one)) {
            return R.fail("获取单位失败");
        }
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dept::getParentId, one.getId()).eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED).eq(Dept::getType, "DEPT");
        List<Dept> deptList = deptService.list(wrapper);
        return R.data(deptList);
    }

    @Override
    public R<Boolean> update(Dept dept) {
        return R.status(deptService.updateById(dept));
    }

    @Override
    @PostMapping(API_PREFIX + "/detail")
    public R<Dept> detail(Dept dept) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getType, "CORP")
                .eq(StringUtil.isNotBlank(dept.getFullName()), Dept::getDeptName, dept.getFullName())
                .eq(StringUtil.isNotBlank(dept.getRegionCode()), Dept::getRegionCode, dept.getRegionCode())
                .eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
        return R.data(deptService.getOne(queryWrapper));
    }

    @Override
    @PostMapping(API_PREFIX + "/region/detail")
    public R<Map<Long, Dept>> getRegionDetail(Dept dept) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getType, "CORP")
                .eq(StringUtil.isNotBlank(dept.getFullName()), Dept::getDeptName, dept.getFullName())
                .eq(StringUtil.isNotBlank(dept.getRegionCode()), Dept::getRegionCode, dept.getRegionCode())
                .eq(Dept::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);

        List<Dept> deptList = deptService.list(queryWrapper);
        Map<Long, Dept> deptMap = deptList.stream().collect(Collectors.toMap(Dept::getId, item -> item));

        return R.data(deptMap);
    }

    @Override
    @GetMapping(API_PREFIX + "getByErpUnitCode")
    public R<Dept> getByErpUnitCode(String erpUnitCode) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dept::getErpUnitCode, erpUnitCode);
        List<Dept> list = deptService.list(queryWrapper);
        if (ObjectUtil.isEmpty(list)) {
            Dept dept = new Dept();
            return R.data(dept);
        } else {
            Dept dept = list.get(0);
            return R.data(dept);
        }
    }

}
