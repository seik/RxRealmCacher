package io.github.seik.rxrealmcacher.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import io.github.seik.rxrealmcacher.App;
import io.github.seik.rxrealmcacher.R;
import io.github.seik.rxrealmcacher.entity.Repository;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Subscription repositoriesSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repositoriesSubscription = App.model.getRepositories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<Repository>>() {
                    @Override
                    public void call(List<Repository> repositories) {
                        if (repositories != null) {
                            repositories.toString();
                        }
                    }
                });

    }
}
