package com.appmf.net.appmf.di.module;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class HttpModule_ProvideClientFactory implements Factory<OkHttpClient> {
  private final HttpModule module;
  private final Provider<Builder> builderProvider;

  public HttpModule_ProvideClientFactory(HttpModule module, Provider<Builder> builderProvider) {  
    assert module != null;
    this.module = module;
    assert builderProvider != null;
    this.builderProvider = builderProvider;
  }

  @Override
  public OkHttpClient get() {  
    OkHttpClient provided = module.provideClient(builderProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<OkHttpClient> create(HttpModule module, Provider<Builder> builderProvider) {  
    return new HttpModule_ProvideClientFactory(module, builderProvider);
  }
}

