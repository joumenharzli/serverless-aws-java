package com.serverless.config;

import com.serverless.services.BillFileService;
import com.serverless.services.ClientService;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    BillFileService billFileService();

    ClientService clientService();
}
