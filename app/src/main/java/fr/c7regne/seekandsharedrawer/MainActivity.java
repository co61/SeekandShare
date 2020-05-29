/*** MainActivity deals with how the application should act and react when used in the main screens ***/

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.viewpager.widget.ViewPager;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private Fragment selectedFragment;
    private Fragment previousFragment;

    private MenuItem selectedItemId;
    private MenuItem previousSelectedItemId;

    private Intent launchIntent;

    private ViewPager viewPager; //ViewPager is a pattern used to swipe horizontally between fragments
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private PubAnnounceFragment postFragment;
    private MessageFragment messageFragment;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedFragment = new HomeFragment();
        previousFragment = new HomeFragment();

        //initialize the swipe
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //initialize the bottom navigation bar
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_bar_navigation);
        //watch if an item is selected on this bar and set the current view to it
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //initialize the Top toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("SeekandShare");

        //initialize the left Drawer Layout
        mDrawerLayout = findViewById(R.id.drawer);

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
            public void onPageSelected(int position) {
                if ( previousSelectedItemId != null) {
                    previousSelectedItemId.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                previousSelectedItemId = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
        });

        setupViewPager(viewPager);
    }

    //create the different Fragments to switch between
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



    //open or close the menu on the left when the toolbar is clicked
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
                    //switching fragment depending on the item selected on the bottom bar
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

    //switch between the different fragments when an item is clicked on the left side menu
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
