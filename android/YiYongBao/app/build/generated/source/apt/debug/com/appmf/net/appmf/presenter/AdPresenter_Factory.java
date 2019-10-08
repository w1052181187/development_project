package com.appmf.net.appmf.presenter;

import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AdPresenter_Factory implements Factory<AdPresenter> {
  private final MembersInjector<AdPresenter> membersInjector;

  public AdPresenter_Factory(MembersInjector<AdPresenter> membersInjector) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
  }

  @Override
  public AdPresenter get() {  
    AdPresenter instance = new AdPresenter();
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<AdPresenter> create(MembersInjector<AdPresenter> membersInjector) {  
    return new AdPresenter_Factory(membersInjector);
  }
}

