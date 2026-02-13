package com.lnsoft.ipc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lnsoft.core.tool.constant.IdevelopConstant;
import com.lnsoft.core.tool.utils.CollectionUtil;
import com.lnsoft.core.tool.utils.ObjectUtil;
import com.lnsoft.core.tool.utils.crypto.sm4.SM4Util;
import com.lnsoft.ipc.contant.MethodConstant;
import com.lnsoft.ipc.contant.OperationTypeConstant;
import com.lnsoft.ipc.contant.ResponseCodeConstant;
import com.lnsoft.ipc.entity.*;
import com.lnsoft.ipc.faceCommon.*;
import com.lnsoft.ipc.mapper.*;
import com.lnsoft.ipc.service.IIpcFaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IpcFaceServiceImpl implements IIpcFaceService {
	@Resource
	private IpcUserMapper ipcUserMapper;
	@Resource
	private IpcUserTimeMapper ipcUserTimeMapper;
	@Resource
	private IpcFaceLoginLogMapper ipcFaceLoginLogMapper;
	@Resource
	private IpcTerminalMapper ipcTerminalMapper;
	@Resource
	private IpcBusinessSystemMapper ipcBusinessSystemMapper;
	@Resource
	private IpcTerminalMonitoringMapper ipcTerminalMonitoringMapper;
	@Resource
	private IpcNetworkLogMapper ipcNetworkLogMapper;
	@Resource
	private IpcTerminalMonitoringBakMapper ipcTerminalMonitoringBakMapper;
	@Resource
	private IpcLocalAppLogMapper ipcLocalAppLogMapper;
	// 定义时间格式（与客户端约定的格式，可根据实际情况调整）
	private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	// 一天的总秒数（24*60*60）
	private static final long SECONDS_PER_DAY = 86400L;
	@Override
	public Response upload(UserRequest userRequest, HttpServletRequest request) {
		Data data = new Data();
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		String method = userRequest.getMethod();
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getUserList())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		String type = null;
		LambdaQueryWrapper<IpcUser> queryWrapper = new LambdaQueryWrapper<>();
		if (MethodConstant.UPLOAD_ADMIN.equals(method)) {
			//管理员信息
			type = "管理员";
		} else if (MethodConstant.UPLOAD_TEMP.equals(method)) {
			//临时用户信息
			type = "临时用户";
		}

		List<FaceUser> userList = userRequest.getData().getUserList();
		for (FaceUser faceUser : userList) {
			IpcUser ipcUser = new IpcUser();
			ipcUser.setId(faceUser.getId());
			ipcUser.setName(faceUser.getUsername());
			ipcUser.setUserType(type);
			ipcUser.setDepartment(faceUser.getDepartment());
			ipcUser.setUpdateTime(faceUser.getOperationTime());
			ipcUser.setFace(faceUser.getFaceData());
			ipcUser.setTerminal(ip);
			//对密码进行加密 todo
			String userPwd = faceUser.getUserPwd();

			ipcUser.setPassword(faceUser.getUserPwd());
			ipcUser.setIsSync("0");
			if (OperationTypeConstant.DELETE.equals(faceUser.getType())) {
				int delete = ipcUserMapper.deleteById(ipcUser);
				if (delete == 0) {
					//todo 失败机制

				}
			} else if (OperationTypeConstant.ADD.equals(faceUser.getType())) {
				String id = faceUser.getId();
				IpcUser user = ipcUserMapper.selectById(id);
				if (ObjectUtil.isNotEmpty(user)) {
					continue;
				}
				int insert = ipcUserMapper.insert(ipcUser);
				if (insert == 0) {
					//todo 失败机制

				}
			} else if (OperationTypeConstant.UPDATE.equals(faceUser.getType())) {
				IpcUser select = ipcUserMapper.selectById(ipcUser);
				if (ObjectUtil.isEmpty(select)) {
					int insert = ipcUserMapper.insert(ipcUser);
					if (insert == 0) {
						//todo 失败机制

					}
				} else {
					int update = ipcUserMapper.updateById(ipcUser);
					if (update == 0) {
						//todo 失败机制

					}
				}
			}
		}
		//查询本地需要同步的数据
		IpcUser user = new IpcUser();
		user.setTerminal(ip);
		user.setUserType(type);
		user.setIsSync("1");
		List<IpcUser> ipcUsers = ipcUserMapper.getList(user);
		List<FaceUser> faceUsers = new ArrayList<>();
		for (IpcUser ipcUser : ipcUsers) {
			FaceUser faceUser = new FaceUser();
			faceUser.setFaceData(ipcUser.getFace());
			faceUser.setDepartment(ipcUser.getDepartment());
			faceUser.setUsername(ipcUser.getName());
			faceUser.setId(ipcUser.getId());
			faceUser.setOperationTime(new Date());
			faceUser.setType(ipcUser.getIsDeleted() == 1 ? "delete" : "update");
			faceUser.setUserPwd(ipcUser.getPassword());
			faceUsers.add(faceUser);
			ipcUser.setIsSync("0");
			ipcUserMapper.editById(ipcUser);
		}
		data.setUserList(faceUsers);
		return Response.data(data);
	}

	@Override
	public Response uploadTime(UserRequest userRequest) {
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getTime())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		List<FaceTime> faceTimeList = userRequest.getData().getTime();
		for (FaceTime faceTime : faceTimeList) {
			IpcUserTime ipcUserTime = new IpcUserTime();
			ipcUserTime.setId(faceTime.getId());
			ipcUserTime.setUserId(faceTime.getUserId());
			ipcUserTime.setLoginBeginTime(faceTime.getStartTime());
			ipcUserTime.setLoginEndTime(faceTime.getEndTime());
			ipcUserTime.setUpdateTime(faceTime.getOperationTime());
			ipcUserTime.setIsSync("0");
			if (OperationTypeConstant.DELETE.equals(faceTime.getType())) {
				int delete = ipcUserTimeMapper.deleteById(ipcUserTime);
				if (delete == 0) {
					//todo 失败机制

				}
			} else if (OperationTypeConstant.ADD.equals(faceTime.getType())) {
				int insert = ipcUserTimeMapper.insert(ipcUserTime);
				if (insert == 0) {
					//todo 失败机制

				}
			} else if (OperationTypeConstant.UPDATE.equals(faceTime.getType())) {
				IpcUserTime select = ipcUserTimeMapper.selectById(ipcUserTime);
				if (ObjectUtil.isEmpty(select)) {
					int insert = ipcUserTimeMapper.insert(ipcUserTime);
					if (insert == 0) {
						//todo 失败机制

					}
				} else {
					int update = ipcUserTimeMapper.updateById(ipcUserTime);
					if (update == 0) {
						//todo 失败机制

					}
				}
			}
		}
		//查询需要同步的数据
		IpcUserTime userTime = new IpcUserTime();
		userTime.setIsSync("1");
		List<IpcUserTime> ipcUserTimes = ipcUserTimeMapper.getList(userTime);
		List<FaceTime> faceTimes = new ArrayList<>();
		for (IpcUserTime ipcUserTime : ipcUserTimes) {
			FaceTime faceTime = new FaceTime();
			faceTime.setId(ipcUserTime.getId());
			faceTime.setUserId(ipcUserTime.getUserId());
			faceTime.setStartTime(ipcUserTime.getLoginBeginTime());
			faceTime.setEndTime(ipcUserTime.getLoginEndTime());
			faceTime.setType(ipcUserTime.getIsDeleted() == 1 ? "delete" : "update");
			faceTime.setOperationTime(new Date());
			faceTimes.add(faceTime);
			ipcUserTime.setIsSync("0");
			ipcUserTimeMapper.updateById(ipcUserTime);
		}
		Data data = new Data();
		data.setTime(faceTimes);
		return Response.data(data);
	}

	@Override
	public Response uploadLog(UserRequest userRequest, HttpServletRequest request) {
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		//根据ip获取终端信息
		LambdaQueryWrapper<IpcTerminal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcTerminal::getIp, ip).eq(IpcTerminal::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getLogList())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		List<FaceLog> logList = userRequest.getData().getLogList();

//		for (FaceLog faceLog : logList) {
//			IpcTerminalMonitoring monitoring = new IpcTerminalMonitoring();
//			monitoring.setIp(ip);
//			monitoring.setUserName(faceLog.getUsername());
//			monitoring.setOpenTime(faceLog.getLoginTime());
//			monitoring.setShowdownTime(faceLog.getLogoutTime());
//			long between = ChronoUnit.SECONDS.between(faceLog.getLoginTime(), faceLog.getLogoutTime());
//			monitoring.setActivityLength(between);
//			monitoring.setOnlineLength(between);
//			monitoring.setCreateTime(new Date());
//			ipcTerminalMonitoringMapper.insert(monitoring);
//		}

		for (FaceLog faceLog : logList) {
			IpcFaceLoginLog ipcFaceLoginLog = new IpcFaceLoginLog();
			ipcFaceLoginLog.setLoginTime(faceLog.getLoginTime());
			ipcFaceLoginLog.setLogoutTime(faceLog.getLogoutTime());
			ipcFaceLoginLog.setUsername(faceLog.getUsername());
			ipcFaceLoginLog.setDepartment(faceLog.getDepartment());
			ipcFaceLoginLogMapper.insert(ipcFaceLoginLog);
		}
		return Response.code(ResponseCodeConstant.SUCCESS);
	}

	@Override
	public Response downUser(UserRequest userRequest,HttpServletRequest request) throws Exception {
		String method = userRequest.getMethod();
		String type = null;
		if (MethodConstant.DOWN_ADMIN.equals(method)) {
			//管理员信息
			type = "管理员";
		} else if (MethodConstant.DOWN_TEMP.equals(method)) {
			//临时用户信息
			type = "临时用户";
		}
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		//查询本地需要同步的数据
		IpcUser user = new IpcUser();
		user.setTerminal(ip);
		user.setUserType(type);
		user.setIsSync("1");
		List<IpcUser> ipcUsers = ipcUserMapper.getList(user);
		List<FaceUser> faceUsers = new ArrayList<>();
		for (IpcUser ipcUser : ipcUsers) {
			FaceUser faceUser = new FaceUser();
			faceUser.setFaceData(ipcUser.getFace());
			faceUser.setDepartment(ipcUser.getDepartment());
			faceUser.setUsername(ipcUser.getName());
			faceUser.setId(ipcUser.getId());
			faceUser.setOperationTime(new Date());
			faceUser.setUserPwd(ipcUser.getPassword());
			faceUser.setType(ipcUser.getIsDeleted() == 1 ? "delete" : "update");
			faceUsers.add(faceUser);
			ipcUser.setIsSync("0");
			ipcUserMapper.editById(ipcUser);
		}
		Data data = new Data();
		data.setUserList(faceUsers);
		return Response.data(data);
	}

	@Override
	public Response downTime(UserRequest userRequest) throws Exception {
		//查询需要同步的数据
		LambdaQueryWrapper<IpcUserTime> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcUserTime::getIsSync, "1")
			.and(i -> i.eq(IpcUserTime::getIsDeleted, 0).or().eq(IpcUserTime::getIsDeleted, 1));
		List<IpcUserTime> ipcUserTimes = ipcUserTimeMapper.selectList(queryWrapper);
		List<FaceTime> faceTimes = new ArrayList<>();
		for (IpcUserTime ipcUserTime : ipcUserTimes) {
			FaceTime faceTime = new FaceTime();
			faceTime.setId(ipcUserTime.getId());
			faceTime.setUserId(ipcUserTime.getUserId());
			faceTime.setStartTime(ipcUserTime.getLoginBeginTime());
			faceTime.setEndTime(ipcUserTime.getLoginEndTime());
			faceTime.setType(ipcUserTime.getIsDeleted() == 1 ? "delete" : "update");
			faceTime.setOperationTime(new Date());
			faceTimes.add(faceTime);
			ipcUserTime.setIsSync("0");
			ipcUserTimeMapper.editById(ipcUserTime);
		}
		Data data = new Data();
		data.setTime(faceTimes);
		return Response.data(data);
	}

	@Override
	public Response uploadTimeList(UserRequest userRequest,HttpServletRequest request) {
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
//		String ip = fingerPrint;
		//根据ip获取终端信息
		LambdaQueryWrapper<IpcTerminal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcTerminal::getIp, ip).eq(IpcTerminal::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getTimeList())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		List<FaceOnOffTime> timeList = userRequest.getData().getTimeList();
		//新增跨天时间不处理直接入表
		for (FaceOnOffTime faceOnOffTime : timeList) {
			IpcTerminalMonitoringBak ipcTerminalMonitoringBak = new IpcTerminalMonitoringBak();
			ipcTerminalMonitoringBak.setIp(ip);
			ipcTerminalMonitoringBak.setOpenTime(faceOnOffTime.getPowerOnTime());
			ipcTerminalMonitoringBak.setShowdownTime(faceOnOffTime.getPowerOffTime());
			long time = Duration.between(faceOnOffTime.getPowerOnTime(), faceOnOffTime.getPowerOffTime()).getSeconds();
			ipcTerminalMonitoringBak.setOnlineLength(time);
			ipcTerminalMonitoringBak.setActivityLength(time);
			ipcTerminalMonitoringBak.setCreateTime(new Date());
			ipcTerminalMonitoringBakMapper.insert(ipcTerminalMonitoringBak);

			//处理跨天时间
			IpcTerminalMonitoring monitoring = new IpcTerminalMonitoring();
			monitoring.setIp(ip);
			List<IpcTerminalMonitoring> monitoringList = splitCrossDayUsage(faceOnOffTime.getPowerOnTime(), faceOnOffTime.getPowerOffTime());
			if (CollectionUtil.isNotEmpty(monitoringList)){
				for (IpcTerminalMonitoring terminalMonitoring : monitoringList) {
					//临时增加2026-1-12 去重
					LambdaQueryWrapper<IpcTerminalMonitoring> wrapper = new LambdaQueryWrapper<>();
					wrapper.eq(IpcTerminalMonitoring::getOpenTime,faceOnOffTime.getPowerOnTime())
						.eq(IpcTerminalMonitoring::getShowdownTime,faceOnOffTime.getPowerOffTime())
						.eq(IpcTerminalMonitoring::getIp,ip);
					List<IpcTerminalMonitoring> ipcTerminalMonitorings = ipcTerminalMonitoringMapper.selectList(wrapper);
					if (ObjectUtil.isNotEmpty(ipcTerminalMonitorings)){
						//有重复数据加入，不处理
						continue;
					}
					terminalMonitoring.setIp(ip);
					ipcTerminalMonitoringMapper.insert(terminalMonitoring);

				}
			}
		}
		return Response.code(ResponseCodeConstant.SUCCESS);
	}

	@Override
	public Response uploadNetwork(UserRequest userRequest, HttpServletRequest request) {
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		//根据ip获取终端信息
		LambdaQueryWrapper<IpcTerminal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcTerminal::getIp, ip).eq(IpcTerminal::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getNetworkDataList())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		List<networkData> networkDataList = userRequest.getData().getNetworkDataList();
		for (networkData networkData : networkDataList) {
			IpcNetworkLog ipcNetworkLog = new IpcNetworkLog();
			ipcNetworkLog.setIp(ip);
			ipcNetworkLog.setUrl(networkData.getRemoteAddr()+":"+networkData.getRemotePort());
			ipcNetworkLog.setStartTime(networkData.getStartTime());
			ipcNetworkLog.setEndTime(networkData.getEndTime());
			ipcNetworkLog.setUserName(networkData.getUsername());
			ipcNetworkLog.setUserDept(networkData.getDepartment());
			ipcNetworkLog.setCreateTime(new Date());
			long secondsDiff = ChronoUnit.SECONDS.between(networkData.getStartTime(), networkData.getEndTime());
			ipcNetworkLog.setAccessLength(secondsDiff);
			//业务系统名称
			LambdaQueryWrapper<IpcBusinessSystem> wrapper = new LambdaQueryWrapper<>();
			wrapper.like(IpcBusinessSystem::getUrl,networkData.getRemoteAddr()).eq(IpcBusinessSystem::getIsDeleted,IdevelopConstant.DB_NOT_DELETED);
			List<IpcBusinessSystem> list = ipcBusinessSystemMapper.selectList(wrapper);
			if (CollectionUtil.isNotEmpty(list)){
				String businessName = list.get(0).getBusinessName();
				ipcNetworkLog.setBusinessName(businessName);
			}
			ipcNetworkLogMapper.insert(ipcNetworkLog);
		}
		return Response.code(ResponseCodeConstant.SUCCESS);
	}

	@Override
	public Response uploadLocalAppData(UserRequest userRequest, HttpServletRequest request) {
		//所属终端字段，获取请求头中数据
		String fingerPrint = request.getHeader("fingerPrint");
		String nonce = request.getHeader("nonce");
		String ip = SM4Util.decryptCBC(fingerPrint, nonce, "ad01c2b36421cc77ff120a0dec7b0e0f");
		//根据ip获取终端信息
		LambdaQueryWrapper<IpcTerminal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IpcTerminal::getIp, ip).eq(IpcTerminal::getIsDeleted, IdevelopConstant.DB_NOT_DELETED);
		if (ObjectUtil.isEmpty(userRequest.getData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		if (ObjectUtil.isEmpty(userRequest.getData().getLocalAppData())) {
			return Response.code(ResponseCodeConstant.REQUEST_DATA_EMPTY);
		}
		List<LocalAppData> localAppDataList = userRequest.getData().getLocalAppData();
		// 获取用户名和部门信息
		String username = userRequest.getData().getUsername();
		String department = userRequest.getData().getDepartment();
		for (LocalAppData localAppData : localAppDataList) {
			IpcLocalAppLog ipcLocalAppLog = new IpcLocalAppLog();
			ipcLocalAppLog.setIp(ip);
			ipcLocalAppLog.setAppName(localAppData.getLocalAppName());
			ipcLocalAppLog.setAppStatus(localAppData.getStatus());
			ipcLocalAppLog.setStartTime(localAppData.getStartTime());
			ipcLocalAppLog.setEndTime(localAppData.getEndTime());
			ipcLocalAppLog.setUserName(username);
			ipcLocalAppLog.setUserDept(department);
			ipcLocalAppLog.setCreateTime(new Date());
			long secondsDiff = ChronoUnit.SECONDS.between(localAppData.getStartTime(), localAppData.getEndTime());
			ipcLocalAppLog.setAccessLength(secondsDiff);
			ipcLocalAppLogMapper.insert(ipcLocalAppLog);
		}
		return Response.code(ResponseCodeConstant.SUCCESS);
	}
	/**
	 * 拆分任意跨天的使用时长记录（支持跨1天/多天）
	 * @param startTime 开始时间（格式：yyyy-MM-dd HH:mm:ss）
	 * @param endTime   结束时间（格式：yyyy-MM-dd HH:mm:ss）
	 * @return 拆分后的使用记录列表，每个记录包含日期、起止时间、时长（秒）
	 * @throws DateTimeParseException 时间格式解析失败时抛出
	 */
	public static List<IpcTerminalMonitoring> splitCrossDayUsage(LocalDateTime startTime, LocalDateTime endTime) {

		List<IpcTerminalMonitoring> usageRecords = new ArrayList<>();

		// 2. 获取开始/结束时间的日期
		LocalDate startDate = startTime.toLocalDate();
		LocalDate endDate = endTime.toLocalDate();

		// 3. 判断是否跨天
		if (startDate.equals(endDate)) {
			// 不跨天：直接计算时长
			long durationSeconds = Duration.between(startTime, endTime).getSeconds();
			IpcTerminalMonitoring monitoring = new IpcTerminalMonitoring();
			monitoring.setOpenTime(startTime);
			monitoring.setShowdownTime(endTime);
			monitoring.setActivityLength(durationSeconds);
			monitoring.setOnlineLength(durationSeconds);
			monitoring.setCreateTime(new Date());
			usageRecords.add(monitoring);
		} else {
			// 跨天：第一步 - 处理开始日（开始时间 → 开始日23:59:59）
			LocalDateTime startDayEnd = LocalDateTime.of(startDate, LocalTime.of(23, 59, 59));
			long startDayDuration = Duration.between(startTime, startDayEnd).getSeconds();
			IpcTerminalMonitoring monitoring = new IpcTerminalMonitoring();
			monitoring.setOpenTime(startTime);
			monitoring.setShowdownTime(startDayEnd);
			monitoring.setActivityLength(startDayDuration);
			monitoring.setOnlineLength(startDayDuration);
			monitoring.setCreateTime(new Date());
			usageRecords.add(monitoring);

			// 跨天：第二步 - 处理中间的完整日期（若有）
			LocalDate currentDate = startDate.plusDays(1); // 从开始日的次日开始
			while (currentDate.isBefore(endDate)) {
				IpcTerminalMonitoring monitoring_a = new IpcTerminalMonitoring();
				// 完整日期的开始时间：00:00:00
				LocalDateTime fullDayStart = LocalDateTime.of(currentDate, LocalTime.MIN);
				// 完整日期的结束时间：23:59:59
				LocalDateTime fullDayEnd = LocalDateTime.of(currentDate, LocalTime.of(23, 59, 59));
				monitoring_a.setOpenTime(fullDayStart);
				monitoring_a.setShowdownTime(fullDayEnd);
				monitoring_a.setActivityLength(SECONDS_PER_DAY);
				monitoring_a.setOnlineLength(SECONDS_PER_DAY);
				monitoring_a.setCreateTime(new Date());
				// 完整日期的时长固定为86400秒
				usageRecords.add(monitoring_a);
				// 日期往后推一天
				currentDate = currentDate.plusDays(1);
			}

			// 跨天：第三步 - 处理结束日（结束日00:00:00 → 结束时间）
			LocalDateTime endDayStart = LocalDateTime.of(endDate, LocalTime.MIN);
			long endDayDuration = Duration.between(endDayStart, endTime).getSeconds();
			IpcTerminalMonitoring monitoring_b = new IpcTerminalMonitoring();
			monitoring_b.setOpenTime(endDayStart);
			monitoring_b.setShowdownTime(endTime);
			monitoring_b.setActivityLength(endDayDuration);
			monitoring_b.setOnlineLength(endDayDuration);
			monitoring_b.setCreateTime(new Date());
			usageRecords.add(monitoring_b);
		}

		return usageRecords;
	}
}
