package com.appmf.net.appmf.di.module;

import dagger.internal.Factory;
import javax.annotation.Generated;
import retrofit2.Retrofit.Builder;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class HttpModule_ProvideRetrofitBuilderFactory implements Factory<Builder> {
  private final HttpModule module;

  public HttpModule_ProvideRetrofitBuilderFactory(HttpModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Builder get() {  
    Builder provided = module.provideRetrofitBuilder();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Builder> create(HttpModule module) {  
    return new HttpModule_ProvideRetrofitBuilderFactory(module);
  }
}

