package com.appmf.net.appmf.view;

import com.appmf.net.appmf.base.activity.BaseWebviewActivity;
import com.appmf.net.appmf.presenter.MainPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final MembersInjector<BaseWebviewActivity<MainPresenter>> supertypeInjector;
  private final Provider<MainPresenter> mainPresenterProvider;

  public MainActivity_MembersInjector(MembersInjector<BaseWebviewActivity<MainPresenter>> supertypeInjector, Provider<MainPresenter> mainPresenterProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mainPresenterProvider != null;
    this.mainPresenterProvider = mainPresenterProvider;
  }

  @Override
  public void injectMembers(MainActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mainPresenter = mainPresenterProvider.get();
  }

  public static MembersInjector<MainActivity> create(MembersInjector<BaseWebviewActivity<MainPresenter>> supertypeInjector, Provider<MainPresenter> mainPresenterProvider) {  
      return new MainActivity_MembersInjector(supertypeInjector, mainPresenterProvider);
  }
}

