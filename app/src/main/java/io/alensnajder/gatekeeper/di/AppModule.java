package io.alensnajder.gatekeeper.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.alensnajder.gatekeeper.data.repository.AuthRepository;
import io.alensnajder.gatekeeper.data.service.AuthService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ViewModelModule.class})
class AppModule {

    @Provides
    @Singleton
    OkHttpClient provideHttpClient() {
        return new OkHttpClient().newBuilder()
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.88:3000")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    AuthService provideAuthService(Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }

    @Provides
    @Singleton
    AuthRepository provideAuthRepository(AuthService authService) {
        return new AuthRepository(authService);
    }
}