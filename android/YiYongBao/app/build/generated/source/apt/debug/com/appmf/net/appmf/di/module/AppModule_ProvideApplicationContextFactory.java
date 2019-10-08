package com.appmf.net.appmf.di.module;

import com.appmf.net.appmf.app.MyApp;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideApplicationContextFactory implements Factory<MyApp> {
  private final AppModule module;

  public AppModule_ProvideApplicationContextFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public MyApp get() {  
    MyApp provided = module.provideApplicationContext();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<MyApp> create(AppModule module) {  
    return new AppModule_ProvideApplicationContextFactory(module);
  }
}

