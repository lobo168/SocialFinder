package com.example.orm.socialfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.orm.socialfinder.adapter.FavouriteAdapter;
import com.example.orm.socialfinder.realm.Name;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Lobo on 2017-07-18.
 */
public class FavouriteActivity extends AppCompatActivity {

    private Realm realm;

    @BindView(R.id.favourite_list)
    ListView listViewFav;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        // Binding views
        ButterKnife.bind(this);
        //Selecting favourite icon on bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.action_favorite);

        // Getting realm instance
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        // Header inflating
        View header = (View) getLayoutInflater().inflate(R.layout.header_favourite, null);
        listViewFav.addHeaderView(header);

        // Filling adapter with data
        RealmResults<Name> result = realm.where(Name.class)
                .equalTo("isFavourite", true)
                .findAllAsync();
        final FavouriteAdapter favouriteAdapter = new FavouriteAdapter(result, this);
        listViewFav.setAdapter(favouriteAdapter);

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
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}