package com.serverless.config;

import com.serverless.services.BillFileService;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    BillFileService billFileService();
}
