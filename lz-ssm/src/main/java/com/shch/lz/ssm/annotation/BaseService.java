package com.shch.lz.ssm.annotation;

import java.lang.annotation.*;

/**
 * Created by Link at 21:18 on 3/29/18.
 */
// auto implements java.lang.annotation.Annotation
@Documented // include into javadoc
@Retention(RetentionPolicy.RUNTIME) // annotation in class, can reflection
@Target({ElementType.TYPE}) // interface, class, enum, annotation
public @interface BaseService {
}
