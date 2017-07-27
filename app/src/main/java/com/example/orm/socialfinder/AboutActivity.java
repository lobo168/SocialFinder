package com.example.orm.socialfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lobo on 2017-07-20.
 */
public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Binding views
        ButterKnife.bind(this);
        uncheckAll();

        // Bottom navigation On Item Click Actions
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent mBrowse = new Intent(getApplicationContext(), BrowseActivity.class);
                Intent mHome = new Intent(getApplicationContext(), HomeActivity.class);
                Intent mFavourite = new Intent(getApplicationContext(), FavouriteActivity.class);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(mHome);
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

    // Uncheck all bottom navigation elements
    private void uncheckAll() {
        int size = bottomNavigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            bottomNavigationView.getMenu().getItem(i).setChecked(false);
        }
    }

}