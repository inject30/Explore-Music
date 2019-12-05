package com.play.tube.music.videos.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.play.tube.music.videos.AppRater;
import com.play.tube.music.videos.R;
import com.play.tube.music.videos.fragments.TopArtistsFragment;
import com.play.tube.music.videos.fragments.TopTagsFragment;
import com.play.tube.music.videos.fragments.TopTracksFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String mCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCountry = getCountry();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new TopTracksFragment())
                .commit();

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(MainActivity.this)
                .withHeaderBackground(R.color.colorPrimary)
                .addProfiles(new ProfileDrawerItem()
                        .withName(getString(R.string.app_name))
                        .withIcon(R.mipmap.ic_launcher))
                .withSelectionListEnabled(false)
                .build();

        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new SectionDrawerItem().withName(R.string.worldwide_charts).withDivider(false),
                        new PrimaryDrawerItem().withName(R.string.top_tracks).withIcon(GoogleMaterial.Icon.gmd_audiotrack),
                        new PrimaryDrawerItem().withName(R.string.top_tags).withIcon(GoogleMaterial.Icon.gmd_label),
                        new PrimaryDrawerItem().withName(R.string.top_artists).withIcon(GoogleMaterial.Icon.gmd_person),
                        new SectionDrawerItem().withName(R.string.local_charts),
                        new PrimaryDrawerItem().withName(R.string.top_tracks).withIcon(GoogleMaterial.Icon.gmd_audiotrack),
                        new PrimaryDrawerItem().withName(R.string.top_artists).withIcon(GoogleMaterial.Icon.gmd_person))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 2:
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new TopTracksFragment())
                                        .commit();
                                return false;
                            case 3:
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new TopTagsFragment())
                                        .commit();
                                return false;
                            case 4:
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new TopArtistsFragment())
                                        .commit();
                                return false;
                            case 6:
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, TopTracksFragment.newInstance(mCountry))
                                        .commit();
                                return false;
                            case 7:
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, TopArtistsFragment.newInstance(mCountry))
                                        .commit();
                                return false;
                            default:
                                return true;
                        }
                    }
                })
                .withSelectedItemByPosition(2)
                .build();

        AppRater.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String getCountry() {
        String country = getResources().getConfiguration().locale.getDisplayCountry(Locale.US);

        switch (country) {
            case "Russia":
                country = "Russian Federation";
                break;
            case "Syria":
                country = "Syrian Arab Republic";
                break;
            case "Tanzania":
                country = "Tanzania, United Republic of";
                break;
            case "Bosnia & Herzegovina":
                country = "Bosnia and Herzegovina";
                break;
            case "Antigua & Barbuda":
                country = "Antigua and Barbuda";
                break;
            case "Falkland Islands (Islas Malvinas)":
                country = "Falkland Islands (Malvinas)";
                break;
            case "St. Kitts & Nevis":
                country = "Saint Kitts and Nevis";
                break;
            case "St. Lucia":
                country = "Saint Lucia";
                break;
            case "Macau":
                country = "Macao";
                break;
            case "Pitcairn Islands":
                country = "Pitcairn";
                break;
            case "St. Helena":
                country = "Saint Helena";
                break;
            case "Turks & Caicos Islands":
                country = "Turks and Caicos Islands";
                break;
            case "Trinidad & Tobago":
                country = "Trinidad and Tobago";
                break;
            case "U.S. Outlying Islands":
                country = "United States Minor Outlying Islands";
                break;
            case "St. Vincent & Grenadines":
                country = "Saint Vincent and the Grenadines";
                break;
        }

        return country;
    }
}
