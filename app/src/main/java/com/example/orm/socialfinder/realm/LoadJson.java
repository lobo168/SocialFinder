package com.example.orm.socialfinder.realm;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;

/**
 * Created by Lobo on 2017-07-10.
 */
public class LoadJson {

    private Realm realm;
    private Context context;
    private String jsonFile;

    public LoadJson(String json, Context myContext, Realm realmLoad) {
        this.context = myContext;
        this.realm = realmLoad;
        this.jsonFile = json;
    }

    public void loadJsonFromStream() throws IOException {
        InputStream stream = context.getAssets().open(jsonFile + ".json");

        // Open a transaction to store items into the realm
        realm.beginTransaction();
        try {
            realm.createAllFromJson(Name.class, stream);
            realm.commitTransaction();
        } catch (IOException e) {
            // Cancel the transaction if anything goes wrong.
            realm.cancelTransaction();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    public void loadDataRoot() throws IOException {
        InputStream stream = context.getAssets().open(jsonFile + ".json");

        // Open a transaction to store items into the realm
        realm.beginTransaction();
        try {
            realm.createAllFromJson(DataRoot.class, stream);
            realm.commitTransaction();
        } catch (IOException e) {
            // Cancel the transaction if anything goes wrong.
            realm.cancelTransaction();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}
