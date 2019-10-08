package com.appmf.net.appmf.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author wyg
 * @date 2018/11/27
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
