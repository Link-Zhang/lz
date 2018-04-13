package com.shch.lz.ssm.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Link at 10:44 on 3/29/18.
 */
@AllArgsConstructor
public class BaseResult {
    @Getter
    @Setter
    public int code;

    @Getter
    @Setter
    public String message;

    @Getter
    @Setter
    public Object data;
}
