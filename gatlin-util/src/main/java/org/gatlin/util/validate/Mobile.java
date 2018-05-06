package org.gatlin.util.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidator.class)
@Documented
public @interface Mobile {

	String message() default "{validate.message.mobile}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}