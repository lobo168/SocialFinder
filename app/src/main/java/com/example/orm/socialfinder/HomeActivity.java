package com.example.orm.socialfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lobo on 2017-07-06.
 */
public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.browseButton)
    Button buttonBrowse;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.aboutButton)
    public void onAboutClick() {
        Intent mAbout = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(mAbout);
    }

    @OnClick(R.id.browseButton)
    public void onBrowseClick() {
        Intent mBrowse = new Intent(getApplicationContext(), BrowseActivity.class);
        startActivity(mBrowse);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Binding views
        ButterKnife.bind(this);
        // Selecting home icon on bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        // Setting menu acton bar
        setSupportActionBar(toolbar);

        // Bottom navigation On Item Click Actions
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent mBrowse = new Intent(getApplicationContext(), BrowseActivity.class);
                Intent mFavourite = new Intent(getApplicationContext(), FavouriteActivity.class);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        //Inside Home right now
                        return true;
                    case R.id.action_browse:
                        startActivity(mBrowse);
                        return true;
                    case R.id.action_favorite:
                        startActivity(mFavourite);
                        return true;
                }
                return true;
            }
        });
    }

    // Top menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent mAbout = new Intent(getApplicationContext(), AboutActivity.class);
        Intent mBrowse = new Intent(getApplicationContext(), BrowseActivity.class);
        Intent mFavourite = new Intent(getApplicationContext(), FavouriteActivity.class);
        if (id == R.id.info) {
            startActivity(mAbout);
            return true;
        } else if (id == R.id.home_top) {
            //Inside Home right now
            return true;
        } else if (id == R.id.browse_top) {
            startActivity(mBrowse);
            return true;
        } else if (id == R.id.favourites_top) {
            startActivity(mFavourite);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }
}
