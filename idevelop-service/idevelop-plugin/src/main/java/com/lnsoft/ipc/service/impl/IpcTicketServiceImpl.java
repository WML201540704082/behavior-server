package com.lnsoft.ipc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lnsoft.ipc.entity.IpcTicket;
import com.lnsoft.ipc.mapper.IpcTicketMapper;
import com.lnsoft.ipc.service.IIpcTicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 存储ticket和ip的Service实现类
 *
 * @author Idevelop
 * @since 2026-04-13
 */
@Service
public class IpcTicketServiceImpl extends ServiceImpl<IpcTicketMapper, IpcTicket> implements IIpcTicketService {

    @Override
    public boolean saveTicket(String ticket, String ip) {
        // 先查询是否存在相同的IP
        LambdaQueryWrapper<IpcTicket> ipWrapper = new LambdaQueryWrapper<>();
        ipWrapper.eq(IpcTicket::getIp, ip);
        IpcTicket existingByIp = this.getOne(ipWrapper);

        if (existingByIp != null) {
            // 如果存在相同的IP，则更新记录的ticket和updateTime
            existingByIp.setTicket(ticket);
            existingByIp.setUpdateTime(LocalDateTime.now());
            return this.updateById(existingByIp);
        } else {
            // 再查询是否存在相同的ticket
            LambdaQueryWrapper<IpcTicket> ticketWrapper = new LambdaQueryWrapper<>();
            ticketWrapper.eq(IpcTicket::getTicket, ticket);
            IpcTicket existingByTicket = this.getOne(ticketWrapper);

            if (existingByTicket != null) {
                // 如果存在相同的ticket，则更新记录的ip和updateTime
                existingByTicket.setIp(ip);
                existingByTicket.setUpdateTime(LocalDateTime.now());
                return this.updateById(existingByTicket);
            } else {
                // 如果都不存在，则插入新记录
                IpcTicket ipcTicket = new IpcTicket();
                ipcTicket.setTicket(ticket);
                ipcTicket.setIp(ip);
                ipcTicket.setCreateTime(LocalDateTime.now());
                ipcTicket.setUpdateTime(LocalDateTime.now());
                ipcTicket.setIsDeleted(0);
                return this.save(ipcTicket);
            }
        }
    }

}