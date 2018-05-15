package org.gatlin.util.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentityValidator implements ConstraintValidator<Identity, String> {

	@Override
	public void initialize(Identity constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return null == value ? true : Validator.isIdentity(value);
	}
}
