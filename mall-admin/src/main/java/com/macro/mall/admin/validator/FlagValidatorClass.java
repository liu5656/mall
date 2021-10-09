package com.macro.mall.admin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/10/9 11:38 上午
 * @desc
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Integer> {

    private String[] values;

    @Override
    public void initialize(FlagValidator validator) {
        this.values = validator.value();
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        if (values == null) {
            return true;
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(String.valueOf(integer))) {
                return true;
            }
        }
        return false;
    }
}
