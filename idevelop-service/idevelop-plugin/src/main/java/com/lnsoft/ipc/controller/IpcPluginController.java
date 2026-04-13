package com.lnsoft.ipc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.mp.support.Query;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import com.lnsoft.ipc.entity.IpcTerminal;
import com.lnsoft.ipc.entity.IpcBusinessSystem;
import com.lnsoft.ipc.entity.IpcDesktopApp;
import com.lnsoft.ipc.service.IIpcTerminalService;
import com.lnsoft.ipc.service.IIpcBusinessSystemService;
import com.lnsoft.ipc.service.IIpcDesktopAppService;
import com.lnsoft.ipc.service.IIpcTicketService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import javax.validation.Valid;

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
    private IIpcDesktopAppService ipcDesktopAppService;
    private IIpcTicketService ipcTicketService;

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
     * 详情
     */
    @GetMapping("/message/detail")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "详情", notes = "传入ipcDesktopApp")
    public R<IpcDesktopApp> detail(IpcDesktopApp ipcDesktopApp) {
        IpcDesktopApp detail = ipcDesktopAppService.getOne(Condition.getQueryWrapper(ipcDesktopApp));
        return R.data(detail);
    }

    /**
     * 分页 消息列表
     */
    @GetMapping("/message/list")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入ipcDesktopApp")
    public R<IPage<IpcDesktopApp>> list(IpcDesktopApp ipcDesktopApp, Query query) {
        IPage<IpcDesktopApp> pages = ipcDesktopAppService.page(Condition.getPage(query), Condition.getQueryWrapper(ipcDesktopApp));
        return R.data(pages);
    }

    // /**
    //  * 新增 消息
    //  */
    // @PostMapping("/message/save")
    // @ApiOperationSupport(order = 4)
    // @ApiOperation(value = "新增", notes = "传入ipcDesktopApp")
    // public R save(@Valid @RequestBody IpcDesktopApp ipcDesktopApp) {
    //     return R.status(ipcDesktopAppService.save(ipcDesktopApp));
    // }

    // /**
    //  * 修改 消息
    //  */
    // @PostMapping("/update")
    // @ApiOperationSupport(order = 5)
    // @ApiOperation(value = "修改", notes = "传入ipcDesktopApp")
    // public R update(@Valid @RequestBody IpcDesktopApp ipcDesktopApp) {
    //     return R.status(ipcDesktopAppService.updateById(ipcDesktopApp));
    // }

    // /**
     * 删除 工控机管控--桌面应用维护表
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "逻辑删除", notes = "传入id")
    public R remove(@RequestBody IpcDesktopApp ipcDesktopApp) {
        return R.status(ipcDesktopAppService.removeById(ipcDesktopApp.getId()));
    }

    /**
     * 保存ticket和ip
     */
    @PostMapping("/saveTicket")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "保存ticket和ip", notes = "传入ticket")
    public R saveTicket(@RequestParam("ticket") String ticket) {
        // 获取客户端IP
        String clientIp = getClientIp();
        if (Func.isBlank(clientIp)) {
            return R.fail("无法获取客户端IP");
        }

        if (Func.isBlank(ticket)) {
            return R.fail("ticket不能为空");
        }

        boolean result = ipcTicketService.saveTicket(ticket, clientIp);
        return R.status(result);
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
