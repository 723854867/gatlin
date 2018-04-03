package org.gatlin.web.util.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class UploadValidator implements ConstraintValidator<Upload, MultipartFile> {
	
	private int maxmium;

	@Override
	public void initialize(Upload upload) {
		this.maxmium = upload.value();
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		return null == value ? true : value.getSize() <= maxmium;
	}
}