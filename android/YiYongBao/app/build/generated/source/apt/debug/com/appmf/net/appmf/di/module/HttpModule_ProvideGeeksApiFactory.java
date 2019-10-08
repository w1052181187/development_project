package com.appmf.net.appmf.di.module;

import com.appmf.net.appmf.core.http.api.GeeksApis;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class HttpModule_ProvideGeeksApiFactory implements Factory<GeeksApis> {
  private final HttpModule module;
  private final Provider<Retrofit> retrofitProvider;

  public HttpModule_ProvideGeeksApiFactory(HttpModule module, Provider<Retrofit> retrofitProvider) {  
    assert module != null;
    this.module = module;
    assert retrofitProvider != null;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public GeeksApis get() {  
    GeeksApis provided = module.provideGeeksApi(retrofitProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<GeeksApis> create(HttpModule module, Provider<Retrofit> retrofitProvider) {  
    return new HttpModule_ProvideGeeksApiFactory(module, retrofitProvider);
  }
}

