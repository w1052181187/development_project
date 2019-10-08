package com.appmf.net.appmf.presenter;

import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class SplashPresenter_Factory implements Factory<SplashPresenter> {
  private final MembersInjector<SplashPresenter> membersInjector;

  public SplashPresenter_Factory(MembersInjector<SplashPresenter> membersInjector) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
  }

  @Override
  public SplashPresenter get() {  
    SplashPresenter instance = new SplashPresenter();
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<SplashPresenter> create(MembersInjector<SplashPresenter> membersInjector) {  
    return new SplashPresenter_Factory(membersInjector);
  }
}

