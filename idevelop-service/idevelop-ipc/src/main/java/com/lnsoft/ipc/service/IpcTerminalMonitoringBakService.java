package com.lnsoft.ipc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnsoft.ipc.entity.IpcTerminalMonitoringBak;
import com.lnsoft.core.tool.api.R;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 工控机管控--终端运行状态监控表 服务类
 *
 * @author Idevelop
 * @since 2025-11-17
 */
public interface IpcTerminalMonitoringBakService extends IService<IpcTerminalMonitoringBak> {

    /**
     * 自定义分页查询
     *
     * @param page 分页参数
     * @param ipcTerminalMonitoringBak 查询条件
     * @return 分页结果
     */
    IPage<IpcTerminalMonitoringBak> selectIpcTerminalMonitoringBakPage(IPage<IpcTerminalMonitoringBak> page, IpcTerminalMonitoringBak ipcTerminalMonitoringBak);

    /**
     * 根据ID获取详情
     *
     * @param id 主键ID
     * @return 详情信息
     */
    IpcTerminalMonitoringBak getById(String id);

    /**
     * 新增
     *
     * @param ipcTerminalMonitoringBak 实体对象
     * @return 是否成功
     */
    boolean saveIpcTerminalMonitoringBak(IpcTerminalMonitoringBak ipcTerminalMonitoringBak);

    /**
     * 修改
     *
     * @param ipcTerminalMonitoringBak 实体对象
     * @return 是否成功
     */
    boolean updateIpcTerminalMonitoringBak(IpcTerminalMonitoringBak ipcTerminalMonitoringBak);

    /**
     * 删除
     *
     * @param ids 主键ID数组
     * @return 是否成功
     */
    boolean removeByIds(String[] ids);
}
