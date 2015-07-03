package com.adchina.api.validation;

import com.adchina.api.validation.validator.MinLengthValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 最小长度注解
 *
 * @author huangyong
 * @since 1.0.0
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinLengthValidator.class)
public @interface MinLength {

    String message() default "min_length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
