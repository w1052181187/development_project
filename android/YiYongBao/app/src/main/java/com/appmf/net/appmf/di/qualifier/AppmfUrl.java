package com.appmf.net.appmf.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wyg
 * @date 2018/11/27
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface AppmfUrl {
}
