package io.github.seik.rxrealmcacher.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ivan on 11/12/16.
 */

public class Repository extends RealmObject {

    @PrimaryKey
    int id;

    String name;

    public Repository() {
    }

    public Repository(int id, String name) {
        this.id = id;
        this.name = name;
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
}
