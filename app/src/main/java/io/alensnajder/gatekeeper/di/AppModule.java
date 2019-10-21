package io.alensnajder.gatekeeper.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.alensnajder.gatekeeper.App;
import io.alensnajder.gatekeeper.data.AppPreferences;
import io.alensnajder.gatekeeper.data.repository.AuthRepository;
import io.alensnajder.gatekeeper.data.repository.GateRepository;
import io.alensnajder.gatekeeper.data.repository.RecordRepository;
import io.alensnajder.gatekeeper.data.repository.UserRepository;
import io.alensnajder.gatekeeper.data.service.AuthService;
import io.alensnajder.gatekeeper.data.service.GateService;
import io.alensnajder.gatekeeper.data.service.RecordService;
import io.alensnajder.gatekeeper.data.service.UserService;
import io.alensnajder.gatekeeper.network.HostInterceptor;
import io.alensnajder.gatekeeper.network.TokenAuthenticator;
import io.alensnajder.gatekeeper.network.TokenInterceptor;
import io.alensnajder.gatekeeper.utils.AccountUtils;
import io.alensnajder.gatekeeper.utils.BooleanTypeAdapter;
import io.alensnajder.gatekeeper.utils.HostUtils;
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
    OkHttpClient provideHttpClient(TokenInterceptor tokenInterceptor, TokenAuthenticator tokenAuthenticator, HostInterceptor hostInterceptor) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(tokenInterceptor)
                .authenticator(tokenAuthenticator)
                .addInterceptor(hostInterceptor)
                .build();
    }

    @Provides @Named("HttpAuthClient")
    @Singleton
    OkHttpClient provideAuthHttpClient(HostInterceptor hostInterceptor) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(hostInterceptor)
                .build();
    }

    @Provides @Named("Retrofit")
    @Singleton
    Retrofit provideRetrofit(@Named("HttpClient") OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.1")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides @Named("AuthRetrofit")
    @Singleton
    Retrofit provideAuthRetrofit(@Named("HttpAuthClient") OkHttpClient httpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.1")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
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
    RecordService provideRecordService(@Named("Retrofit") Retrofit retrofit) {
        return retrofit.create(RecordService.class);
    }

    @Provides
    @Singleton
    RecordRepository provideRecordRepository(RecordService recordService) {
        return new RecordRepository(recordService);
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
    HostInterceptor provideHostInterceptor() {
        return new HostInterceptor();
    }

    @Provides
    @Singleton
    TokenInterceptor provideTokenInterceptor(AppPreferences appPreferences) {
        return new TokenInterceptor(appPreferences);
    }

    @Provides
    @Singleton
    AccountUtils provideAccountUtils(AppPreferences appPreferences, TokenInterceptor tokenInterceptor) {
        return new AccountUtils(appPreferences, tokenInterceptor);
    }

    @Provides
    @Singleton
    HostUtils provideHostUtils(AppPreferences appPreferences, HostInterceptor hostInterceptor) {
        return new HostUtils(appPreferences, hostInterceptor);
    }

    @Provides
    @Singleton
    TokenAuthenticator provideTokenAuthenticator(AuthService authService, AppPreferences appPreferences, TokenInterceptor tokenInterceptor) {
        return new TokenAuthenticator(authService, appPreferences, tokenInterceptor);
    }

    @Provides
    @Singleton
    Gson provideGson(BooleanTypeAdapter booleanTypeAdapter) {
        return new GsonBuilder()
                .registerTypeAdapter(boolean.class, booleanTypeAdapter)
                .create();
    }

    @Provides
    @Singleton
    BooleanTypeAdapter provideBooleanTypeAdapter() {
        return new BooleanTypeAdapter();
    }
}