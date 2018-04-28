package org.gatlin.web.util;

import java.io.File;
import java.io.IOException;

import org.gatlin.core.Gatlin;
import org.gatlin.core.bean.enums.Env;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.soa.config.api.ConfigService;
import org.gatlin.soa.resource.bean.entity.Resource;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;
import org.gatlin.util.callback.Callback;
import org.gatlin.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Conditional(UploadCondition.class)
public class Uploader {
	
	private static final Logger logger = LoggerFactory.getLogger(Uploader.class);
	
	@javax.annotation.Resource
	private Gatlin gatlin;
	@javax.annotation.Resource
	private ConfigService configService;
	
	public <T> T upload(MultipartFile file, String category, Callback<Resource, T> callback) {
		String path = _path();
		String suffix = _save(file, path, category, IDWorker.INSTANCE.nextSid());
		String filePath = path + File.separator + suffix;
		try {
			String prefix = configService.config(WebConsts.Options.RESOURCE_PREFIX);
			Resource resource = new Resource();
			resource.setId(IDWorker.INSTANCE.nextId());
			resource.setPath(filePath);
			resource.setUrl(prefix + suffix);
			resource.setBytes(file.getSize());
			resource.setCreated(DateUtil.current());
			resource.setUpdated(resource.getCreated());
			return callback.invoke(resource);
		} catch (Exception e) {
			File newFile = new File(filePath);
			if (newFile.exists())
				newFile.delete();
			throw e;
		}
	}
	
	private String _path() {
		Env env = gatlin.env();
		switch (env) {
		case LOCAL:
			return WebUtil.getRequest().getServletContext().getRealPath("/");
		default:
			return configService.config(WebConsts.Options.RESOURCE_PATH);
		}
	}
	
	private String _save(MultipartFile file, String path, String category, String newFileName) {
		return _save(file, path, category, newFileName, null);
	}
	
	private String _save(MultipartFile file, String path, String category, String newFileName, String oldFileName) {
		File dir = new File(path + category);
		if (!dir.exists())
			dir.mkdirs();
		String fileName = file.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf("."));
		File dest = new File(dir.getPath() + File.separator + newFileName + ext);
		try {
			file.transferTo(dest);
		} catch (IllegalStateException | IOException e) {
			logger.error("资源 - {} 上传失败！", dest.getPath(), e);
			throw new CodeException(WebCode.UPLOAD_FAILURE);
		}
		if (StringUtil.hasText(oldFileName)) {			// 删除老文件
			File old = new File(path + File.separator + oldFileName);
			if (old.exists())
				old.delete();
		}
		return category + "/" + newFileName + ext;
	}
}
