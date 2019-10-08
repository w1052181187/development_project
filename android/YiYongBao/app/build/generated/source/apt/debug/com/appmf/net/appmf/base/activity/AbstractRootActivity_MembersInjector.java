package com.appmf.net.appmf.base.activity;

import com.appmf.net.appmf.base.presenter.AbstractPresenter;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AbstractRootActivity_MembersInjector<T extends AbstractPresenter> implements MembersInjector<AbstractRootActivity<T>> {
  private final MembersInjector<AbstractSimpleActivity> supertypeInjector;
  private final Provider<T> mPresenterProvider;

  public AbstractRootActivity_MembersInjector(MembersInjector<AbstractSimpleActivity> supertypeInjector, Provider<T> mPresenterProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mPresenterProvider != null;
    this.mPresenterProvider = mPresenterProvider;
  }

  @Override
  public void injectMembers(AbstractRootActivity<T> instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mPresenter = mPresenterProvider.get();
  }

  public static <T extends AbstractPresenter> MembersInjector<AbstractRootActivity<T>> create(MembersInjector<AbstractSimpleActivity> supertypeInjector, Provider<T> mPresenterProvider) {  
      return new AbstractRootActivity_MembersInjector<T>(supertypeInjector, mPresenterProvider);
  }
}

