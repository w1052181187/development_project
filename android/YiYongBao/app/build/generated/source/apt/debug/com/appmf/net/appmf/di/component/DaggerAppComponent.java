package com.appmf.net.appmf.di.component;

import com.appmf.net.appmf.app.MyApp;
import com.appmf.net.appmf.app.MyApp_MembersInjector;
import com.appmf.net.appmf.di.module.AppModule;
import com.appmf.net.appmf.di.module.AppModule_ProvideApplicationContextFactory;
import com.appmf.net.appmf.di.module.HttpModule;
import com.appmf.net.appmf.di.module.HttpModule_ProvideOkHttpBuilderFactory;
import com.appmf.net.appmf.di.module.HttpModule_ProvideRetrofitBuilderFactory;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerAppComponent implements AppComponent {
  private Provider<OkHttpClient.Builder> provideOkHttpBuilderProvider;
  private Provider<Retrofit.Builder> provideRetrofitBuilderProvider;
  private MembersInjector<MyApp> myAppMembersInjector;
  private Provider<MyApp> provideApplicationContextProvider;

  private DaggerAppComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideOkHttpBuilderProvider = ScopedProvider.create(HttpModule_ProvideOkHttpBuilderFactory.create(builder.httpModule));
    this.provideRetrofitBuilderProvider = ScopedProvider.create(HttpModule_ProvideRetrofitBuilderFactory.create(builder.httpModule));
    this.myAppMembersInjector = MyApp_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), provideOkHttpBuilderProvider, provideRetrofitBuilderProvider);
    this.provideApplicationContextProvider = ScopedProvider.create(AppModule_ProvideApplicationContextFactory.create(builder.appModule));
  }

  @Override
  public void inject(MyApp app) {  
    myAppMembersInjector.injectMembers(app);
  }

  @Override
  public MyApp getContext() {  
    return provideApplicationContextProvider.get();
  }

  public static final class Builder {
    private AppModule appModule;
    private HttpModule httpModule;
  
    private Builder() {  
    }
  
    public AppComponent build() {  
      if (appModule == null) {
        throw new IllegalStateException("appModule must be set");
      }
      if (httpModule == null) {
        this.httpModule = new HttpModule();
      }
      return new DaggerAppComponent(this);
    }
  
    public Builder appModule(AppModule appModule) {  
      if (appModule == null) {
        throw new NullPointerException("appModule");
      }
      this.appModule = appModule;
      return this;
    }
  
    public Builder httpModule(HttpModule httpModule) {  
      if (httpModule == null) {
        throw new NullPointerException("httpModule");
      }
      this.httpModule = httpModule;
      return this;
    }
  }
}

