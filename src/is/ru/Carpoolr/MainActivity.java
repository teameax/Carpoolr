package is.ru.Carpoolr;

import android.app.ActionBar;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import is.ru.Carpoolr.fragments.PassengerListFragment;
import is.ru.Carpoolr.fragments.RideListFragment;


public class MainActivity extends FragmentActivity {


    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Customizing the action bar.
        actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            LayoutInflater inflater = LayoutInflater.from(this);
            View customView         = inflater.inflate(R.layout.custom_actionbar, null);
            TextView header         = (TextView)customView.findViewById(R.id.header);
            header.setText(R.string.app_name);
            ImageButton backButton  = (ImageButton) customView.findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Back button functionality.
                    if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                }
            });

            actionBar.setCustomView(customView);
            actionBar.setDisplayShowCustomEnabled(true);
        }

        Fragment tab1fragment = new RideListFragment();
        Fragment tab2fragment = new PassengerListFragment();

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Drivers")
                        .setTabListener(new TabsListener(tab1fragment, this))
        );

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Passengers")
                        .setTabListener(new TabsListener(tab2fragment, this))
        );



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

}
