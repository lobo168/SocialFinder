package com.example.orm.socialfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.orm.socialfinder.adapter.BrowseAdapter;
import com.example.orm.socialfinder.realm.LoadJson;
import com.example.orm.socialfinder.realm.Name;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Lobo on 2017-07-10.
 */
public class BrowseActivity extends AppCompatActivity {

    private Realm realm;
    private LoadJson mDataLoad;
    private final String JSON = "name";

    @BindView(R.id.list)
    ListView listView;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @OnItemClick(R.id.list)
    public void onItemClick(int position) {
        Intent activityIntent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(activityIntent);

        Intent positionIntent = new Intent(getApplicationContext(), MapActivity.class);
        positionIntent.putExtra("position", position - 1);
        startActivity(positionIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        // Binding views
        ButterKnife.bind(this);
        // Selecting browse icon on bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.action_browse);

        // Getting realm instance and populating it
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realmDataLoad();

        // Header inflating
        View header = (View) getLayoutInflater().inflate(R.layout.header_browse, null);
        listView.addHeaderView(header);

        // Filling adapter with data
        RealmResults<Name> results = realm.where(Name.class).findAllAsync();
        final BrowseAdapter myAdapter = new BrowseAdapter(results, this);
        listView.setAdapter(myAdapter);

        // Bottom navigation On Item Click Actions
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Intent mHome = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(mHome);
                        return true;
                    case R.id.action_browse:
                        Intent mBrowse = new Intent(getApplicationContext(), BrowseActivity.class);
                        startActivity(mBrowse);
                        return true;
                    case R.id.action_favorite:
                        Intent mFavourite = new Intent(getApplicationContext(), FavouriteActivity.class);
                        startActivity(mFavourite);
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    // Check if realm got names already, if not populate it from JSON
    private void realmDataLoad() {
        RealmResults<Name> result = realm.where(Name.class).findAll();

        if (result.isEmpty()) {
            mDataLoad = new LoadJson(JSON, this, realm);
            try {
                mDataLoad.loadJsonFromStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}