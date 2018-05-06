package org.gatlin.util.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.gatlin.util.PhoneUtil;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
	
	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		return PhoneUtil.isMobile(arg0);
	}
}