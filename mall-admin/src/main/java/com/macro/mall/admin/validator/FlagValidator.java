package com.macro.mall.admin.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 11:36 上午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {

    String[] value() default {};
    String message() default "flag is not found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
