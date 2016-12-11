package io.github.seik.rxrealmcacher.network;

import java.util.List;

import io.github.seik.rxrealmcacher.entity.Repository;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Ivan on 11/12/16.
 */

public interface GithubApi {
    @GET("users/seik/repos")
    Observable<List<Repository>> getRepositories();

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build();
}
