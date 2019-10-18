package io.alensnajder.gatekeeper.network;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HostInterceptor implements Interceptor {

    private String host;

    @Inject
    public HostInterceptor() {

    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (host != null) {
            HttpUrl url = HttpUrl.parse(host);
            HttpUrl newUrl = request.url().newBuilder()
                    .host(url.host())
                    .port(url.port())
                    .build();
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        }

        return chain.proceed(request);
    }
}
