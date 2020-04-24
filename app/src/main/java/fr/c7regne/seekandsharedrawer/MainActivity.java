package fr.c7regne.seekandsharedrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Set;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private BottomNavigationView bottomNav;
    Fragment selectedFragment;
    Fragment previousFragment;

    MenuItem selectedItemId;
    MenuItem previousSelectedItemId;

    Intent launchIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedFragment = new HomeFragment();
        previousFragment = new HomeFragment();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("SeekandShare");

        mDrawerLayout = findViewById(R.id.drawer);

        //bottom nav
        bottomNav = findViewById(R.id.bottom_bar_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navlistener);
        //nav view on left side(side menu)
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //open (or close) menu when action on the toggle button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }

     //open the menu on the right when toolbar click
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (selectedFragment instanceof HomeFragment) {
                super.onBackPressed();
                finish();
            } else {
                //a changer
                previousSelectedItemId.setChecked(true);
                selectedFragment = previousFragment;
                selectedItemId=previousSelectedItemId;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            }
        }
    }

    //switch between the different fragment when an item is click on bottom menu
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    //saving previous state for on back pressed
                    previousFragment = selectedFragment;
                    previousSelectedItemId=selectedItemId;
                    selectedItemId=menuItem;
                    //switching fragment
                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_post:
                            selectedFragment = new PubAnnounceFragment();
                            break;
                        case R.id.nav_message:
                            selectedFragment = new MessageFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    //switch between the different fragment when an item is click on side menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //saving previous state for onbaxk pressed
        previousFragment = selectedFragment;
        //switching fragment
        switch (menuItem.getItemId()) {
            case R.id.nav_favorite:
                launchIntent= new  Intent(this,FavoriteActivity.class);
                break;
            case R.id.nav_announce:
                launchIntent= new Intent(this, AnnounceActivity.class);
                break;
            case R.id.nav_settings:
                launchIntent= new Intent(this,SettingsActivity.class);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        selectedFragment = new HomeFragment();
        startActivity( launchIntent);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

        return true;
    }

    //appel des interfaces

}
