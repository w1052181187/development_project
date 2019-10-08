package com.appmf.net.appmf.app;

import android.app.Application;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class MyApp_MembersInjector implements MembersInjector<MyApp> {
  private final MembersInjector<Application> supertypeInjector;
  private final Provider<Builder> mClientBuilderProvider;
  private final Provider<Retrofit.Builder> mRetrofitBuilderProvider;

  public MyApp_MembersInjector(MembersInjector<Application> supertypeInjector, Provider<Builder> mClientBuilderProvider, Provider<Retrofit.Builder> mRetrofitBuilderProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mClientBuilderProvider != null;
    this.mClientBuilderProvider = mClientBuilderProvider;
    assert mRetrofitBuilderProvider != null;
    this.mRetrofitBuilderProvider = mRetrofitBuilderProvider;
  }

  @Override
  public void injectMembers(MyApp instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mClientBuilder = mClientBuilderProvider.get();
    instance.mRetrofitBuilder = mRetrofitBuilderProvider.get();
  }

  public static MembersInjector<MyApp> create(MembersInjector<Application> supertypeInjector, Provider<Builder> mClientBuilderProvider, Provider<Retrofit.Builder> mRetrofitBuilderProvider) {  
      return new MyApp_MembersInjector(supertypeInjector, mClientBuilderProvider, mRetrofitBuilderProvider);
  }
}

