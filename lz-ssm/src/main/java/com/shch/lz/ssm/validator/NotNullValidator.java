package com.shch.lz.ssm.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import lombok.AllArgsConstructor;

/**
 * Created by Link at 20:57 on 3/30/18.
 */
@AllArgsConstructor
public class NotNullValidator extends ValidatorHandler<String> implements Validator<String> {
    private String fieldName;

    @Override
    public boolean validate(ValidatorContext validatorContext, String string) {
        if (null == string) {
            validatorContext.addError(ValidationError.create(String.format("Field %s must not be null.", fieldName)).setErrorCode(-1).setField(fieldName).setInvalidValue(string));
            return false;
        }
        return true;
    }
}
