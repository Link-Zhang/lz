package com.shch.lz.ssm.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import lombok.AllArgsConstructor;

/**
 * Created by Link at 20:41 on 3/30/18.
 */
@AllArgsConstructor
public class LengthValidator extends ValidatorHandler<String> implements Validator<String> {
    private int min;
    private int max;
    private String fieldName;

    @Override
    public boolean validate(ValidatorContext validatorContext, String string) {
        if (null == string || string.length() > max || string.length() < min) {
            validatorContext.addError(ValidationError.create(String.format("The length of field %s must between %s and %s.", fieldName, min, max)).setErrorCode(-1).setField(fieldName).setInvalidValue(string));
            return false;
        }
        return true;
    }
}
