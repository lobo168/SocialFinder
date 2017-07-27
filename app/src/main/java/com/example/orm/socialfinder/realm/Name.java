package com.example.orm.socialfinder.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Lobo on 2017-07-05.
 */
@Setter
@Getter
public class Name extends RealmObject {
    @PrimaryKey
    private String first;
    private String last;
    private boolean isFavourite;
}
