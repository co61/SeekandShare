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
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.viewpager.widget.ViewPager;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    Fragment selectedFragment;
    Fragment previousFragment;

    MenuItem selectedItemId;
    MenuItem previousSelectedItemId;

    Intent launchIntent;

    private ViewPager viewPager;
    HomeFragment homeFragment;
    SearchFragment searchFragment;
    PubAnnounceFragment postFragment;
    MessageFragment messageFragment;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedFragment = new HomeFragment();
        previousFragment = new HomeFragment();

        //initialize viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //initialize bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);

        //initialize Top toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("SeekandShare");

        //initialize left Drawer Layout
        mDrawerLayout = findViewById(R.id.drawer);

        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //open (or close) menu when action on the toggle button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // launch HomeFragment at the beginning in case it doesn't work usually
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ( previousSelectedItemId != null) {
                    previousSelectedItemId.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                previousSelectedItemId = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(viewPager);
    }

    //create the differents Fragments to switch between
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment=new HomeFragment();
        searchFragment=new SearchFragment();
        postFragment=new PubAnnounceFragment();
        messageFragment=new MessageFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(searchFragment);
        adapter.addFragment(postFragment);
        adapter.addFragment(messageFragment);
        viewPager.setAdapter(adapter);
    }






    //open the menu on the left when toolbar click
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (selectedFragment instanceof HomeFragment) {
                super.onBackPressed();
                finish();
            } else {
                startActivity( new Intent(this,MainActivity.class));
                overridePendingTransition(R.anim.slide_no_translation,R.anim.slide_no_translation);
                finish();
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
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.nav_search:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.nav_post:
                            viewPager.setCurrentItem(2);
                            break;
                        case R.id.nav_message:
                            viewPager.setCurrentItem(3);
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    //switch between the different fragment when an item is click on side menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //saving previous state for onback pressed
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


}
