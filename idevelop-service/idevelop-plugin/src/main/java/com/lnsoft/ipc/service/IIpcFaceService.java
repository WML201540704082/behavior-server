package com.lnsoft.ipc.service;

import com.lnsoft.ipc.faceCommon.Response;
import com.lnsoft.ipc.faceCommon.UserRequest;

import javax.servlet.http.HttpServletRequest;

public interface IIpcFaceService {
	/**
	 * 上传用户信息
	 * @param userRequest
	 * @return
	 */
	Response upload(UserRequest userRequest, HttpServletRequest request);

	/**
	 * 上传用户时间信息
	 * @param userRequest
	 * @return
	 */
	Response uploadTime(UserRequest userRequest);

	/**
	 * 客户端上传日志
	 * @param userRequest
	 * @return
	 */
	Response uploadLog(UserRequest userRequest, HttpServletRequest request);

	/**
	 * 客户端获取用户信息
	 * @param userRequest
	 * @return
	 */
	Response downUser(UserRequest userRequest,HttpServletRequest request) throws Exception;

	/**
	 * 客户端获取时间信息
	 * @param userRequest
	 * @return
	 */
	Response downTime(UserRequest userRequest) throws Exception;

	/**
	 * 上传开关机时间
	 * @param userRequest
	 * @return
	 */
	Response uploadTimeList(UserRequest userRequest, HttpServletRequest request);

	/**
	 * 上传网络访问日志
	 * @param userRequest
	 * @param request
	 * @return
	 */
	Response uploadNetwork(UserRequest userRequest, HttpServletRequest request);

	/**
	 * 上传本地应用数据
	 * @param userRequest
	 * @param request
	 * @return
	 */
	Response uploadLocalAppData(UserRequest userRequest, HttpServletRequest request);
}
