package com.lnsoft.ipc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lnsoft.ipc.entity.IpcTicket;

/**
 * 存储ticket和ip的Service接口
 *
 * @author Idevelop
 * @since 2026-04-13
 */
public interface IIpcTicketService extends IService<IpcTicket> {

    /**
     * 保存ticket和ip
     * @param ticket ticket值
     * @param ip 客户端IP
     * @return 是否保存成功
     */
    boolean saveTicket(String ticket, String ip);

}