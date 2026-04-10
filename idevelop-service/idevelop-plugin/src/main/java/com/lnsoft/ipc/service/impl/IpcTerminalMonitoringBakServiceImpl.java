package com.lnsoft.ipc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnsoft.ipc.entity.IpcTerminalMonitoringBak;
import com.lnsoft.ipc.mapper.IpcTerminalMonitoringBakMapper;
import com.lnsoft.ipc.service.IpcTerminalMonitoringBakService;
import org.springframework.stereotype.Service;

/**
 * 工控机管控--终端运行状态监控表 服务实现类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
@Service
public class IpcTerminalMonitoringBakServiceImpl extends ServiceImpl<IpcTerminalMonitoringBakMapper, IpcTerminalMonitoringBak> implements IpcTerminalMonitoringBakService {

    @Override
    public IPage<IpcTerminalMonitoringBak> selectIpcTerminalMonitoringBakPage(IPage<IpcTerminalMonitoringBak> page, IpcTerminalMonitoringBak ipcTerminalMonitoringBak) {
        QueryWrapper<IpcTerminalMonitoringBak> wrapper = new QueryWrapper<>();

        // 根据查询条件构建查询语句
        if (ipcTerminalMonitoringBak != null) {
            if (ipcTerminalMonitoringBak.getUserId() != null && !ipcTerminalMonitoringBak.getUserId().isEmpty()) {
                wrapper.eq("user_id", ipcTerminalMonitoringBak.getUserId());
            }
            if (ipcTerminalMonitoringBak.getUserName() != null && !ipcTerminalMonitoringBak.getUserName().isEmpty()) {
                wrapper.like("user_name", ipcTerminalMonitoringBak.getUserName());
            }
            if (ipcTerminalMonitoringBak.getIp() != null && !ipcTerminalMonitoringBak.getIp().isEmpty()) {
                wrapper.eq("ip", ipcTerminalMonitoringBak.getIp());
            }
            if (ipcTerminalMonitoringBak.getDept() != null && !ipcTerminalMonitoringBak.getDept().isEmpty()) {
                wrapper.eq("dept", ipcTerminalMonitoringBak.getDept());
            }
        }

        wrapper.orderByDesc("create_time"); // 按创建时间倒序排列

        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public IpcTerminalMonitoringBak getById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean saveIpcTerminalMonitoringBak(IpcTerminalMonitoringBak ipcTerminalMonitoringBak) {
        return save(ipcTerminalMonitoringBak);
    }

    @Override
    public boolean updateIpcTerminalMonitoringBak(IpcTerminalMonitoringBak ipcTerminalMonitoringBak) {
        return updateById(ipcTerminalMonitoringBak);
    }

    @Override
    public boolean removeByIds(String[] ids) {
        return removeByIds(java.util.Arrays.asList(ids));
    }
}
