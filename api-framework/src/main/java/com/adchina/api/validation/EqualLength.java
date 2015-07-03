package com.adchina.api.validation;

import com.adchina.api.validation.validator.EqualLengthValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 等于长度注解
 *
 * @author huangyong
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualLengthValidator.class)
public @interface EqualLength {

    String message() default "equal_length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
