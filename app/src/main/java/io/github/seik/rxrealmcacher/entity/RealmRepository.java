package io.github.seik.rxrealmcacher.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ivan on 11/12/16.
 */

public class RealmRepository extends RealmObject {

    @PrimaryKey
    int id;

    String name;

    public RealmRepository() {
    }

    public RealmRepository(Repository repository) {
        this.id = repository.id;
        this.name = repository.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Repository toRepository() {
        Repository repository = new Repository();
        repository.id = id;
        repository.name = name;
        return repository;
    }

}
