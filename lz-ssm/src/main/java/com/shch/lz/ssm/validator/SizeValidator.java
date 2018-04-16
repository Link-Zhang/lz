package com.shch.lz.ssm.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import lombok.AllArgsConstructor;

/**
 * Created by Link at 21:02 on 3/30/18.
 */
@AllArgsConstructor
public class SizeValidator extends ValidatorHandler<Integer> implements Validator<Integer> {
    private int min;
    private int max;
    private String fieldName;

    @Override
    public boolean validate(ValidatorContext validatorContext, Integer integer) {
        if (null == integer || integer.intValue() > max || integer.intValue() < min) {
            validatorContext.addError(ValidationError.create(String.format("The size of field %s must between %s and %s.", fieldName, min, max)).setInvalidValue(-1).setField(fieldName).setInvalidValue(integer));
            return false;
        }
        return true;
    }
}
