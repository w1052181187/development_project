package com.appmf.net.appmf.view;

import com.appmf.net.appmf.base.activity.BaseActivity;
import com.appmf.net.appmf.presenter.SplashPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class SplashActivity_MembersInjector implements MembersInjector<SplashActivity> {
  private final MembersInjector<BaseActivity<SplashPresenter>> supertypeInjector;
  private final Provider<SplashPresenter> mPresenterProvider;

  public SplashActivity_MembersInjector(MembersInjector<BaseActivity<SplashPresenter>> supertypeInjector, Provider<SplashPresenter> mPresenterProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mPresenterProvider != null;
    this.mPresenterProvider = mPresenterProvider;
  }

  @Override
  public void injectMembers(SplashActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mPresenter = mPresenterProvider.get();
  }

  public static MembersInjector<SplashActivity> create(MembersInjector<BaseActivity<SplashPresenter>> supertypeInjector, Provider<SplashPresenter> mPresenterProvider) {  
      return new SplashActivity_MembersInjector(supertypeInjector, mPresenterProvider);
  }
}

