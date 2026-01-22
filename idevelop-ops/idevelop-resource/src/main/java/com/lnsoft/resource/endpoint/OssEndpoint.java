
package com.lnsoft.resource.endpoint;


import com.lnsoft.core.oss.AliossTemplate;
import com.lnsoft.core.oss.model.IdevelopFile;
import com.lnsoft.core.oss.model.OssFile;
import com.lnsoft.core.tool.api.R;
import com.lnsoft.core.tool.utils.Func;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 对象存储端点
 *
 * @author guozhao
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oss/endpoint")
@Api(value = "对象存储端点", tags = "对象存储端点")
@Slf4j
public class OssEndpoint {

	private AliossTemplate aliossTemplate;


	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return Bucket
	 */
	@SneakyThrows
	@PostMapping("/make-bucket")
	public R makeBucket(@RequestParam String bucketName) {
		aliossTemplate.makeBucket(bucketName);
		return R.success("创建成功");
	}

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-bucket")
	public R removeBucket(@RequestParam String bucketName) {
		aliossTemplate.removeBucket(bucketName);
		return R.success("删除成功");
	}

	/**
	 * 拷贝文件
	 *
	 * @param fileName       存储桶对象名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/copy-file")
	public R copyFile(@RequestParam String fileName, @RequestParam String destBucketName, String destFileName) {
		aliossTemplate.copyFile(fileName, destBucketName, destFileName);
		return R.success("操作成功");
	}

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶对象名称
	 * @return InputStream
	 */
	@SneakyThrows
	@GetMapping("/stat-file")
	public R<OssFile> statFile(@RequestParam String fileName) {
		return R.data(aliossTemplate.statFile(fileName));
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-path")
	public R<String> filePath(@RequestParam String fileName) {
		return R.data(aliossTemplate.filePath(fileName));
	}


	/**
	 * 获取文件外链
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-link")
	public R<String> fileLink(@RequestParam String fileName) {
		return R.data(aliossTemplate.fileLink(fileName));
	}

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@PostMapping("/put-file")
	@ApiOperation(value = "上传文件", notes = "传入文件")
	public R<IdevelopFile> putFile(@RequestParam MultipartFile file) {
		//校验文件大小
		long size = file.getSize();
		if (size > (1024 * 1024 * 1024)) {
			R.fail("文件大小不超过" + 1024 + "M");
		}
		// 文件类型
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		InputStream inputStream = null;
		IdevelopFile idevelopFile = null;
		try {
			inputStream = file.getInputStream();
			idevelopFile = aliossTemplate.putFile(file.getOriginalFilename(), inputStream);
			idevelopFile.setFileType(extension);
		}catch (Exception e) {
			log.error("上传文件失败：{}", e.getMessage(), e);
		}finally {
			if (inputStream !=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("流关闭失败");
				}
			}
		}
		return R.data(idevelopFile);
	}

	/**
	 * 转换路径
	 *
	 * @param originalPath
	 * @return
	 */
	@GetMapping("/convertToCurrentModePath")
	public String convertToCurrentModePath(@RequestParam("originalPath") String originalPath) {
		return aliossTemplate.convertToCurrentModePath(originalPath);
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@PostMapping("/put-file-by-name")
	public R<IdevelopFile> putFile(@RequestParam String fileName, @RequestParam MultipartFile file){
		//文件名长度
		int fileNamelength = file.getOriginalFilename().length();
		//校验文件大小
		long size = file.getSize();
		if (size > (1024 * 1024 * 1024)) {
			R.fail("文件大小不超过" + 1024 + "M");
		}
		// 校验文件类型
		// 文件类型
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		InputStream inputStream = null;
		IdevelopFile idevelopFile = null;
		try {
			inputStream = file.getInputStream();
			idevelopFile = aliossTemplate.putFile(file.getOriginalFilename(), inputStream);
			idevelopFile.setFileType(extension);
		}catch (Exception e) {
			log.error("上传文件失败：{}", e.getMessage(), e);
		}finally {
			if (inputStream !=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("流关闭失败");
				}

			}
		}
		idevelopFile.setFileType(extension);
		return R.data(idevelopFile);
	}

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-file")
	@ApiOperation(value = "删除文件", notes = "删除文件")
	public R removeFile(@RequestBody IdevelopFile fileName) {
		aliossTemplate.removeFile(fileName.getOriginalName());
		return R.success("操作成功");
	}

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-files")
	public R removeFiles(@RequestParam String fileNames) {
		aliossTemplate.removeFiles(Func.toStrList(fileNames));
		return R.success("操作成功");
	}

}
