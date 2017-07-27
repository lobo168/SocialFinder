package com.example.orm.socialfinder.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Lobo on 2017-07-10.
 */

@Setter
@Getter
public class DataRoot extends RealmObject {
    private String _id;
    @PrimaryKey
    private int index;
    private String guid;
    private boolean isActive;
    private String balance;
    private String picture;
    private int age;
    private String eyeColor;
    private String company;
    private String email;
    private String phone;
    private String address;
    private String about;
    private String registered;
    private String latitude;
    private String longitude;
    private String greeting;
    private String favoriteFruit;
}

