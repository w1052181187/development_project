package com.appmf.net.appmf.di.component;

import android.app.Activity;
import com.appmf.net.appmf.base.activity.AbstractRootActivity;
import com.appmf.net.appmf.base.activity.AbstractRootActivity_MembersInjector;
import com.appmf.net.appmf.base.activity.BaseActivity;
import com.appmf.net.appmf.base.activity.BaseWebviewActivity;
import com.appmf.net.appmf.di.module.ActivityModule;
import com.appmf.net.appmf.di.module.ActivityModule_ProvideActivityFactory;
import com.appmf.net.appmf.presenter.MainPresenter;
import com.appmf.net.appmf.presenter.MainPresenter_Factory;
import com.appmf.net.appmf.presenter.SplashPresenter;
import com.appmf.net.appmf.presenter.SplashPresenter_Factory;
import com.appmf.net.appmf.view.AdActivity;
import com.appmf.net.appmf.view.MainActivity;
import com.appmf.net.appmf.view.MainActivity_MembersInjector;
import com.appmf.net.appmf.view.SplashActivity;
import com.appmf.net.appmf.view.SplashActivity_MembersInjector;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerActivityComponent implements ActivityComponent {
  private Provider<Activity> provideActivityProvider;
  private Provider<MainPresenter> mainPresenterProvider;
  private MembersInjector<AbstractRootActivity<MainPresenter>> abstractRootActivityMembersInjector;
  private MembersInjector<BaseActivity<MainPresenter>> baseActivityMembersInjector;
  private MembersInjector<BaseWebviewActivity<MainPresenter>> baseWebviewActivityMembersInjector;
  private MembersInjector<MainActivity> mainActivityMembersInjector;
  private Provider<SplashPresenter> splashPresenterProvider;
  private MembersInjector<AbstractRootActivity<SplashPresenter>> abstractRootActivityMembersInjector1;
  private MembersInjector<BaseActivity<SplashPresenter>> baseActivityMembersInjector1;
  private MembersInjector<SplashActivity> splashActivityMembersInjector;

  private DaggerActivityComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideActivityProvider = ScopedProvider.create(ActivityModule_ProvideActivityFactory.create(builder.activityModule));
    this.mainPresenterProvider = MainPresenter_Factory.create((MembersInjector) MembersInjectors.noOp());
    this.abstractRootActivityMembersInjector = AbstractRootActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), mainPresenterProvider);
    this.baseActivityMembersInjector = MembersInjectors.delegatingTo(abstractRootActivityMembersInjector);
    this.baseWebviewActivityMembersInjector = MembersInjectors.delegatingTo(baseActivityMembersInjector);
    this.mainActivityMembersInjector = MainActivity_MembersInjector.create(baseWebviewActivityMembersInjector, mainPresenterProvider);
    this.splashPresenterProvider = SplashPresenter_Factory.create((MembersInjector) MembersInjectors.noOp());
    this.abstractRootActivityMembersInjector1 = AbstractRootActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), splashPresenterProvider);
    this.baseActivityMembersInjector1 = MembersInjectors.delegatingTo(abstractRootActivityMembersInjector1);
    this.splashActivityMembersInjector = SplashActivity_MembersInjector.create(baseActivityMembersInjector1, splashPresenterProvider);
  }

  @Override
  public Activity getActivity() {  
    return provideActivityProvider.get();
  }

  @Override
  public void inject(MainActivity mainActivity) {  
    mainActivityMembersInjector.injectMembers(mainActivity);
  }

  @Override
  public void inject(SplashActivity splashActivity) {  
    splashActivityMembersInjector.injectMembers(splashActivity);
  }

  @Override
  public void inject(AdActivity adActivity) {  
    MembersInjectors.noOp().injectMembers(adActivity);
  }

  public static final class Builder {
    private ActivityModule activityModule;
    private AppComponent appComponent;
  
    private Builder() {  
    }
  
    public ActivityComponent build() {  
      if (activityModule == null) {
        throw new IllegalStateException("activityModule must be set");
      }
      if (appComponent == null) {
        throw new IllegalStateException("appComponent must be set");
      }
      return new DaggerActivityComponent(this);
    }
  
    public Builder activityModule(ActivityModule activityModule) {  
      if (activityModule == null) {
        throw new NullPointerException("activityModule");
      }
      this.activityModule = activityModule;
      return this;
    }
  
    public Builder appComponent(AppComponent appComponent) {  
      if (appComponent == null) {
        throw new NullPointerException("appComponent");
      }
      this.appComponent = appComponent;
      return this;
    }
  }
}

