package com.lnsoft.ipc.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lnsoft.ipc.entity.IpcLocalAppLog;
import com.lnsoft.ipc.vo.IpcNetworkLogVO;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 工控机管控-本地应用访问记录表 Mapper 接口
 *
 * @author Idevelop
 * @since 2026-02-12
 */
public interface IpcLocalAppLogMapper extends BaseMapper<IpcLocalAppLog> {


}
