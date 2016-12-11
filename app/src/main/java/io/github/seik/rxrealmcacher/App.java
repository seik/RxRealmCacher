package io.github.seik.rxrealmcacher;

import android.app.Application;

import io.github.seik.rxrealmcacher.network.GithubApi;
import io.github.seik.rxrealmcacher.network.ModelRepository;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ivan on 11/12/16.
 */

public class App extends Application {

    public static ModelRepository model;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        GithubApi githubApi = GithubApi.retrofit.create(GithubApi.class);

        model = new ModelRepository(githubApi, Realm.getDefaultInstance());
    }
}
