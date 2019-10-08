package com.appmf.net.appmf.di.module;

import dagger.internal.Factory;
import javax.annotation.Generated;
import okhttp3.OkHttpClient.Builder;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class HttpModule_ProvideOkHttpBuilderFactory implements Factory<Builder> {
  private final HttpModule module;

  public HttpModule_ProvideOkHttpBuilderFactory(HttpModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Builder get() {  
    Builder provided = module.provideOkHttpBuilder();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Builder> create(HttpModule module) {  
    return new HttpModule_ProvideOkHttpBuilderFactory(module);
  }
}

