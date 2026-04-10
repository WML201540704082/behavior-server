package com.lnsoft.ipc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.ipc.entity.IpcTerminal;
import com.lnsoft.ipc.entity.IpcBusinessSystem;
import com.lnsoft.ipc.service.IIpcTerminalService;
import com.lnsoft.ipc.service.IIpcBusinessSystemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 插件接口控制器
 *
 * @author Idevelop
 * @since 2026-04-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/plugin")
@Api(value = "插件接口", tags = "插件接口")
public class IpcPluginController {

    private IIpcTerminalService ipcTerminalService;
    private IIpcBusinessSystemService ipcBusinessSystemService;

    /**
     * 获取公司导航数据
     */
    @GetMapping("/companyNav")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "获取公司导航数据", notes = "根据客户端IP获取对应的业务系统数据，支持appName搜索")
    public R<List<IpcBusinessSystem>> companyNav(@RequestParam(value = "appName", required = false) String appName) {
        // 获取客户端IP
        String clientIp = getClientIp();
        if (Func.isBlank(clientIp)) {
            return R.fail("无法获取客户端IP");
        }

        // 根据IP查询终端信息，获取deptId
        LambdaQueryWrapper<IpcTerminal> terminalWrapper = new LambdaQueryWrapper<>();
        terminalWrapper.eq(IpcTerminal::getIp, clientIp).eq(IpcTerminal::getIsDeleted, 0);
        IpcTerminal terminal = ipcTerminalService.getOne(terminalWrapper);

        if (terminal == null) {
            return R.fail("未找到对应终端信息");
        }

        String deptId = terminal.getDeptId();
        if (Func.isBlank(deptId)) {
            return R.fail("终端未关联部门");
        }

        // 根据deptId查询业务系统数据，支持appName搜索
        LambdaQueryWrapper<IpcBusinessSystem> businessWrapper = new LambdaQueryWrapper<>();
        businessWrapper.eq(IpcBusinessSystem::getDeptId, deptId).eq(IpcBusinessSystem::getIsDeleted, 0);
        if (Func.isNotBlank(appName)) {
            businessWrapper.like(IpcBusinessSystem::getAppName, appName);
        }
        List<IpcBusinessSystem> businessSystems = ipcBusinessSystemService.list(businessWrapper);

        return R.data(businessSystems);
    }

    /**
     * 获取客户端真实IP
     */
    private String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (Func.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (Func.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
