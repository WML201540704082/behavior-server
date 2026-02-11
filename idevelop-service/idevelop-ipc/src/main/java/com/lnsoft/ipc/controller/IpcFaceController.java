package com.lnsoft.ipc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lnsoft.core.mp.support.Condition;
import com.lnsoft.core.oss.AliossTemplate;
import com.lnsoft.core.oss.model.IdevelopFile;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.ipc.dto.Face;
import com.lnsoft.ipc.entity.IpcBusinessSystem;
import com.lnsoft.ipc.faceCommon.FaceUser;
import com.lnsoft.ipc.faceCommon.Response;
import com.lnsoft.ipc.faceCommon.UserRequest;
import com.lnsoft.ipc.service.IIpcFaceService;
import com.lnsoft.ipc.service.impl.IpcFaceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

/**
 * 人脸识别客户端对接
 */
@RestController
@AllArgsConstructor
@RequestMapping("/face")
@Api(value = "工控机管控--人脸识别客户端对接", tags = "工控机管控--人脸识别客户端对接")
@Slf4j
public class IpcFaceController {
	private AliossTemplate aliossTemplate;

	@Resource
	private IIpcFaceService iIpcFaceService;

	@PostMapping("/base64")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "测试", notes = "")
	public R detail(@RequestBody Face face) throws IOException {
		String base64Data;
		String base64String = face.getImg();
		if (base64String.contains(",")) {
			base64Data = base64String.split(",")[1];
		} else {
			base64Data = base64String;
		}
		byte[] decode = Base64.getDecoder().decode(base64Data);
		InputStream inputStream = new ByteArrayInputStream(decode);
		IdevelopFile idevelopFile = null;
		try {
			idevelopFile = aliossTemplate.putFile("ces" + ".jpg", inputStream);
			idevelopFile.setFileType("jpg");
		} catch (Exception e) {
			log.error("上传文件失败：{}", e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("流关闭失败");
				}
			}
		}
		String link = idevelopFile.getLink();
//		ByteArrayResource resource = new ByteArrayResource(decode);
//		return ResponseEntity.ok()
//			.contentType(MediaType.IMAGE_PNG) // 通用二进制流类型
//			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image_" + "123" + ".png\"") // 建议文件名
//			.body(resource);
		return R.success(link);
	}

	@GetMapping("/time")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "对时接口", notes = "")
	public long getTime() {
		long timeMillis = System.currentTimeMillis();
		System.out.println(timeMillis);
		return timeMillis;
	}

	@PostMapping("/uploadUser")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "客户端上传用户信息", notes = "")
	public Response upload(@RequestBody UserRequest userRequest, HttpServletRequest request) {
		Response response = iIpcFaceService.upload(userRequest,request);
		return response;
	}
	@PostMapping("/uploadTime")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "客户端上传用户可登录时间信息", notes = "")
	public Response uploadTime(@RequestBody UserRequest userRequest) {
		Response response = iIpcFaceService.uploadTime(userRequest);
		return response;
	}
	@PostMapping("/uploadLog")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "客户端上传日志信息", notes = "")
	public Response uploadLog(@RequestBody UserRequest userRequest, HttpServletRequest request) {
		Response response = iIpcFaceService.uploadLog(userRequest,request);
		return response;
	}
	@PostMapping("/downUser")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "客户端获取用户信息", notes = "")
	public Response downUser(@RequestBody UserRequest userRequest,HttpServletRequest request) throws Exception {
		Response response = iIpcFaceService.downUser(userRequest,request);
		return response;
	}
	@PostMapping("/downTime")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "客户端获取时间信息", notes = "")
	public Response downTime(@RequestBody UserRequest userRequest) throws Exception {
		Response response = iIpcFaceService.downTime(userRequest);
		return response;
	}
	//   nonce: 5f8d7e6c4b3a2918070654321fedcba9
	//   iv: 8a7b6c5d4e3f2a1b0c9d8e7f6a5b4c3d
	@PostMapping("/uploadTimeList")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "客户端获取时间信息", notes = "")
	public Response uploadTimeList(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception {
		Response response = iIpcFaceService.uploadTimeList(userRequest,request);
		return response;
	}
	@PostMapping("/uploadNetworkData")
	public Response uploadNetwork(@RequestBody UserRequest userRequest, HttpServletRequest request){
		Response response  = iIpcFaceService.uploadNetwork(userRequest,request);
		return response;
	}
}
