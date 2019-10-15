package io.alensnajder.gatekeeper.di;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.alensnajder.gatekeeper.App;
import io.alensnajder.gatekeeper.data.AppPreferences;
import io.alensnajder.gatekeeper.data.repository.AuthRepository;
import io.alensnajder.gatekeeper.data.repository.GateRepository;
import io.alensnajder.gatekeeper.data.repository.UserRepository;
import io.alensnajder.gatekeeper.data.service.AuthService;
import io.alensnajder.gatekeeper.data.service.GateService;
import io.alensnajder.gatekeeper.data.service.UserService;
import io.alensnajder.gatekeeper.network.TokenAuthenticator;
import io.alensnajder.gatekeeper.network.TokenInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {ViewModelModule.class})
class AppModule {

    private static final String PREFERENCES_NAME = "gatekeeper_prefs";

    @Provides
    @Singleton
    Context provideContext(App application) {
        return application;
    }

    @Provides @Named("HttpClient")
    @Singleton
    OkHttpClient provideHttpClient(TokenInterceptor tokenInterceptor, TokenAuthenticator tokenAuthenticator) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(tokenInterceptor)
                .authenticator(tokenAuthenticator)
                .build();
    }

    @Provides @Named("HttpAuthClient")
    @Singleton
    OkHttpClient provideAuthHttpClient() {
        return new OkHttpClient().newBuilder()
                .build();
    }

    @Provides @Named("Retrofit")
    @Singleton
    Retrofit provideRetrofit(@Named("HttpClient") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.88:3000")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides @Named("AuthRetrofit")
    @Singleton
    Retrofit provideAuthRetrofit(@Named("HttpAuthClient") OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.88:3000")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    AuthService provideAuthService(@Named("AuthRetrofit") Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }

    @Provides
    @Singleton
    AuthRepository provideAuthRepository(AuthService authService) {
        return new AuthRepository(authService);
    }

    @Provides
    @Singleton
    UserService provideUserService(@Named("Retrofit") Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserService userService) {
        return new UserRepository(userService);
    }

    @Provides
    @Singleton
    GateService provideGateService(@Named("Retrofit") Retrofit retrofit) {
        return retrofit.create(GateService.class);
    }

    @Provides
    @Singleton
    GateRepository provideGateRepository(GateService gateService) {
        return new GateRepository(gateService);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    AppPreferences provideAppPreferences(SharedPreferences sharedPreferences) {
        return new AppPreferences(sharedPreferences);
    }

    @Provides
    @Singleton
    TokenInterceptor provideTokenInterceptor(AppPreferences appPreferences) {
        return new TokenInterceptor(appPreferences);
    }

    @Provides
    @Singleton
    TokenAuthenticator provideTokenAuthenticator(AuthService authService, AppPreferences appPreferences, TokenInterceptor tokenInterceptor) {
        return new TokenAuthenticator(authService, appPreferences, tokenInterceptor);
    }
}