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
@Constraint(validatedBy = IdentityValidator.class)
@Documented
public @interface Identity {

	String message() default "{validate.message.identity}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
