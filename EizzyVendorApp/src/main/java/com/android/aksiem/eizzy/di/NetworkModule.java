package com.android.aksiem.eizzy.di;

import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.api.DispatcherService;
import com.android.aksiem.eizzy.app.AppGlobal;
import com.android.aksiem.eizzy.util.LiveDataCallAdapterFactory;
import com.android.aksiem.eizzy.util.Logger;
import com.android.aksiem.eizzy.util.TLSSocketFactory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String TAG_API = "ApiLog";

    private String mBaseUrl;
    private String mDispatcherServiceUrl;

    NetworkModule(String baseUrl, String dispatcherServiceUrl) {
        this.mBaseUrl = baseUrl;
        this.mDispatcherServiceUrl = dispatcherServiceUrl;
    }

    @AppScope
    @Provides
    AppService provideAppService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(AppService.class);
    }

    @AppScope
    @Provides
    DispatcherService provideDispatcherService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(mDispatcherServiceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(DispatcherService.class);
    }

    @Provides
    @AppScope
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor,
                                     TLSSocketFactory tlsSocketFactory,
                                     X509TrustManager trustManager) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (loggingInterceptor != null) {
            client.addInterceptor(loggingInterceptor);
        }

        //adding SSL certificate trust managers
        client.sslSocketFactory(tlsSocketFactory, trustManager);

        return client.build();
    }

    @AppScope
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        if (AppGlobal.APP_ENV.isHttpLoggerEnabled()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Logger.tag(TAG_API).d(message));
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return httpLoggingInterceptor;
        }
        return null;
    }

    @AppScope
    @Provides
    TLSSocketFactory provideTLSSocketFactory(X509TrustManager trustManager) {
        try {
            return new TLSSocketFactory(new TrustManager[]{trustManager});
        } catch (NoSuchAlgorithmException e) {
            Logger.tag(TAG_API).e(e, e.getMessage());
        } catch (KeyManagementException e) {
            Logger.tag(TAG_API).e(e, e.getMessage());
        }
        return null;
    }

    @AppScope
    @Provides
    X509TrustManager provideX509TrustManager() {
        return new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                try {
                    chain[0].checkValidity();
                } catch (Exception e) {
                    throw new CertificateException("Certificate not valid or trusted.");
                }
            }
        };
    }
}
