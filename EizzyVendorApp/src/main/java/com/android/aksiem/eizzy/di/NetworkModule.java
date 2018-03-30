package com.android.aksiem.eizzy.di;

import com.android.aksiem.eizzy.api.AppService;
import com.android.aksiem.eizzy.app.AppGlobal;
import com.android.aksiem.eizzy.util.LiveDataCallAdapterFactory;
import com.android.aksiem.eizzy.util.Logger;
import com.android.aksiem.eizzy.util.TLSSocketFactory;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.inject.Singleton;
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

    NetworkModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Singleton
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

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (loggingInterceptor != null) {
            client.addInterceptor(loggingInterceptor);
        }

        //adding SSL certificate trust managers
        X509TrustManager trustManager = getTrustManager();
        TrustManager[] byPassTrustManagers = new TrustManager[]{trustManager};
        try {
            client.sslSocketFactory(new TLSSocketFactory(byPassTrustManagers), trustManager);
        } catch (NoSuchAlgorithmException e) {
            Logger.tag(TAG_API).e(e, e.getMessage());
        } catch (KeyManagementException e) {
            Logger.tag(TAG_API).e(e, e.getMessage());
        }

        return client.build();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        if (AppGlobal.APP_ENV.isHttpLoggerEnabled()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Logger.tag(TAG_API).d(message));
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return httpLoggingInterceptor;
        }
        return null;
    }

    private X509TrustManager getTrustManager() {
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
