package com.android.aksiem.eizzy.di;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;

import dagger.Module;
import dagger.Provides;

@Module
public class MqttClientModule {

    private String serverUri;

    private String clientId;

    public MqttClientModule(String serverUri, String clientId) {
        this.serverUri = serverUri;
        this.clientId = clientId;
    }

    @AppScope
    @Provides
    MqttAndroidClient provideMqttAndroidClient(@ApplicationContext Context context) {
        MqttAndroidClient mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        return mqttAndroidClient;
    }
}
