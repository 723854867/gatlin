package org.gatlin.web.util;

import java.io.File;
import java.io.IOException;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);
	
	public static final String save(MultipartFile file, String path, String category, String newFileName) {
		return save(file, path, category, newFileName, null);
	}
	
	public static final String save(MultipartFile file, String path, String category, String newFileName, String oldFileName) {
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
			throw new CodeException("资源上传失败");
		}
		if (StringUtil.hasText(oldFileName)) {			// 删除老文件
			File old = new File(path + File.separator + oldFileName);
			if (old.exists())
				old.delete();
		}
		return category + "/" + newFileName + ext;
	}
}
