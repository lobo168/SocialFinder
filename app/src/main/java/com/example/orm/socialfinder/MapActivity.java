package com.example.orm.socialfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.orm.socialfinder.realm.DataRoot;
import com.example.orm.socialfinder.realm.LoadJson;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Lobo on 2017-07-09.
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private final String JSON_VALID = "dataroot";
    private String mAge;
    private Realm realm;
    private LoadJson mLoad;
    private ArrayList<DataRoot> mInfo;
    private int mPosition;
    private GoogleMap mMap;
    private double mLatitude;
    private double mLongitude;

    @BindView(R.id.age)
    TextView ageView;

    @BindView(R.id.eye)
    TextView eyeView;

    @BindView(R.id.company)
    TextView companyView;

    @BindView(R.id.phone)
    TextView phoneView;

    @BindView(R.id.email)
    TextView emailView;

    @BindView(R.id.address)
    TextView addressView;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Getting position
        getExtras();
        // Binding views
        ButterKnife.bind(this);
        // Selecting browse icon on bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.action_location);

        // Getting realm instance
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realmDataLoad();

        // Setting all the data to TextViews etc
        setData();

        // Getting fragment manger and map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
                    case R.id.action_location:
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

    // Filling list with data and setting it to TableView
    private void setData() {
        mInfo = new ArrayList(realm.where(DataRoot.class).findAll());

        eyeView.setText(mInfo.get(mPosition).getEyeColor());
        mAge = Integer.toString(mInfo.get(mPosition).getAge());
        ageView.setText(mAge);
        companyView.setText(mInfo.get(mPosition).getCompany());
        addressView.setText(mInfo.get(mPosition).getAddress());
        phoneView.setText(mInfo.get(mPosition).getPhone());
        emailView.setText(mInfo.get(mPosition).getEmail());

        mLatitude = Double.parseDouble(mInfo.get(mPosition).getLatitude());
        mLongitude = Double.parseDouble(mInfo.get(mPosition).getLongitude());
    }

    // Handling map marker location and camera
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng socialPosition = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(new MarkerOptions().position(socialPosition).title("Marker in" + mPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(socialPosition));
    }

    // Check if realm got dataroot already, if not populate it from JSON
    private void realmDataLoad() {
        RealmResults<DataRoot> result = realm.where(DataRoot.class).findAll();

        if (result.isEmpty()) {
            mLoad = new LoadJson(JSON_VALID, this, realm);
            try {
                mLoad.loadDataRoot();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Getting person position from previous activity
    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey("position")) {
                mPosition = getIntent().getExtras().getInt("position");
            }
        }
    }
}