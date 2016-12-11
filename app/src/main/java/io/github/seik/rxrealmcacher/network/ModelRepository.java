package io.github.seik.rxrealmcacher.network;

import java.util.ArrayList;
import java.util.List;

import io.github.seik.rxrealmcacher.entity.RealmRepository;
import io.github.seik.rxrealmcacher.entity.Repository;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by Ivan on 11/12/16.
 */

public class ModelRepository {

    private GithubApi githubApi;
    private Realm realm;

    public ModelRepository(GithubApi githubApi, Realm realm) {
        this.githubApi = githubApi;
        this.realm = realm;
    }

    public Observable<List<Repository>> getRepositories() {
        Observable<List<Repository>> remote = getRemoteRepositories();
        Observable<List<Repository>> local = getLocalRepositories();

        return Observable.concat(local, remote);
    }

    private Observable<List<Repository>> getLocalRepositories() {
        return Observable.defer(new Func0<Observable<List<Repository>>>() {
            @Override
            public Observable<List<Repository>> call() {
                try (Realm realm1 = Realm.getDefaultInstance()) {
                    List<Repository> repositories = new ArrayList<>();
                    for (RealmRepository realmRepository : realm1.where(RealmRepository.class).findAll()) {
                        repositories.add(realmRepository.toRepository());
                    }
                    return Observable.just(repositories);
                }
            }
        });
    }


    private Observable<List<Repository>> getRemoteRepositories() {
        return githubApi.getRepositories()
                .doOnNext(new Action1<List<Repository>>() {
                    @Override
                    public void call(final List<Repository> repositories) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.where(Repository.class).findAll().deleteAllFromRealm();
                                for (Repository repository : repositories) {
                                    realm.copyToRealm(new RealmRepository(repository));
                                }
                            }
                        });
                        realm.close();
                    }
                })
                .onErrorReturn(new Func1<Throwable, List<Repository>>() {
                    @Override
                    public List<Repository> call(Throwable throwable) {
                        return null;
                    }
                });
    }


}
