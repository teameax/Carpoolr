package is.ru.Carpoolr;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import is.ru.Carpoolr.fragments.*;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;

public class MainActivity extends FragmentActivity implements OnRideSelectListener {

    protected static final String INFO_TAG = "info";
    protected static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";

    private boolean isDualPane = false;
    private String[] mMenuListItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private FrameLayout frameLayout;
    private Firebase ref;
    private String userEmail;
    private TextView userSignupEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Firebase.setAndroidContext(this);
        ref = new Firebase(FIREBASE_URL);

        if (ref.getAuth() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("userEmail");
        }

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header, mDrawerList, false);

        setupActionbar();
        setupNavigation(header);

        View view = findViewById(R.id.info_fragment);

        isDualPane = view != null && view.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    // Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        // Access menu.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.add_new_ride).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onRideSelected(Object info, String id) {

        if (isDualPane) {
            InfoFragment fragment = (InfoFragment) getSupportFragmentManager().findFragmentByTag(INFO_TAG);

            if (fragment != null) {
                fragment.updateFragment(info, id);
            } else {
                InfoFragment newFragment = new InfoFragment();
                Bundle args = new Bundle();
                args.putString("id", id);
                if (info instanceof Ride) {
                    Ride ride = (Ride) info;
                    args.putSerializable("OBJ", ride);
                } else {
                    Passenger passenger = (Passenger) info;
                    args.putSerializable("OBJ", passenger);
                }
                newFragment.setArguments(args);

                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.info_fragment, newFragment, INFO_TAG);
                ft.addToBackStack(null);

                ft.commit();
            }
        }
        else {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtra("id", id);
            if (info instanceof Ride) {
                intent.putExtra("OBJ", (Ride) info);
            }
            else if (info instanceof Passenger) {
                intent.putExtra("OBJ", (Passenger) info);
            }
            startActivity(intent);
        }
    }

    private void setupActionbar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {

            //ACTION BAR
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            ColorDrawable green_base = (ColorDrawable)getResources().getDrawable(R.color.green_base);

            LayoutInflater inflater = LayoutInflater.from(this);
            View customView         = inflater.inflate(R.layout.custom_actionbar, null);
            TextView header         = (TextView)customView.findViewById(R.id.header);
            header.setText(R.string.app_name);
            actionBar.setCustomView(customView);
            actionBar.setBackgroundDrawable(green_base);

            //ACTION BAR TABS
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            Fragment tab1fragment = new RideListFragment();
            Fragment tab2fragment = new PassengerListFragment();

            actionBar.addTab(
                    actionBar.newTab()
                            .setText(R.string.drivers)
                            .setTabListener(new TabsListener(tab1fragment, this))
            );

            actionBar.addTab(
                    actionBar.newTab()
                            .setText(R.string.passengers)
                            .setTabListener(new TabsListener(tab2fragment, this))
            );

        }
    }

    public void setupNavigation(ViewGroup header) {
        frameLayout     = (FrameLayout)findViewById(R.id.fragment_placeholder);
        mMenuListItems  = getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerLayout   = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList     = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mMenuListItems));
        mDrawerList.requestLayout();
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Get the header view and add it to the nav menu.
        mDrawerList.addHeaderView(header);

        userSignupEmail= (TextView)findViewById(R.id.userEmailHeader);
        userSignupEmail.setText(userEmail);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  // host Activity
                mDrawerLayout,         // DrawerLayout object
                R.drawable.ic_drawer,  // nav drawer icon to replace 'Up' caret
                R.string.drawer_open,  // "open drawer" description
                R.string.drawer_close   // "close drawer" description
        ) {

            // Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            // Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position){
        if(position == 3){
            ref.unauth();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        mDrawerList.setItemChecked(position, true);
        //setTitle(mMenuListItems[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle click on hamburger icon.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle click on add ride icon.
        switch (item.getItemId()){
            case R.id.add_new_ride:
                startCreateRideFragment();
                return false;
            case R.id.my_rides:
                startMyRidesFragment();
                return false;
            default:
                // Handle your other action bar items
                return super.onOptionsItemSelected(item);
        }
    }

    private void startMyRidesFragment() {
        if(isDualPane){
            Fragment fragment = new MyRidesFragment();
            android.support.v4.app.FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
            fft.replace(R.id.info_fragment, fragment);
            fft.commit();
        }
        else{
            Intent intent = new Intent(this, MyRidesActivity.class);
            startActivity(intent);
        }
    }

    private void startCreateRideFragment(){
        if(isDualPane){
            Fragment fragment = new CreateRideFragment();
            android.support.v4.app.FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
            fft.replace(R.id.info_fragment, fragment);
            fft.commit();
        }
        else{
            Intent intent = new Intent(this, CreateRideActivity.class);
            startActivity(intent);
        }
    }

    // Called whenever we call invalidateOptionsMenu()
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    protected class TabsListener implements ActionBar.TabListener {

        private Fragment fragment;
        private FragmentActivity activity;

        public TabsListener(Fragment fragment, FragmentActivity ctx) {
            this.fragment = fragment;
            this.activity = ctx;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            android.support.v4.app.FragmentTransaction fft = activity.getSupportFragmentManager().beginTransaction();
            fft.replace(R.id.fragment_placeholder, fragment);
            fft.commit();
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

}
