package is.ru.Carpoolr;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import is.ru.Carpoolr.fragments.InfoFragment;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;

/**
 * Created by DrepAri on 16.10.14.
 */
public class InfoActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object info = getIntent().getExtras().get("OBJ");

        if (getResources().getBoolean(R.bool.has_two_panes)) {
            finish();
        }

        setupActionbar();
        InfoFragment newFragment = new InfoFragment();
        Bundle args = new Bundle();
        if (info instanceof Ride) {
            Ride ride = (Ride) info;
            args.putSerializable("OBJ", ride);
        } else {
            Passenger passenger = (Passenger) info;
            args.putSerializable("OBJ", passenger);
        }
        newFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, newFragment, MainActivity.INFO_TAG);
        ft.addToBackStack(null);

        ft.commit();
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    private void setupActionbar() {
        ColorDrawable green_base = (ColorDrawable)getResources().getDrawable(R.color.green_base);
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            //ACTION BAR
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            LayoutInflater inflater = LayoutInflater.from(this);
            View customView         = inflater.inflate(R.layout.custom_actionbar, null);
            TextView header         = (TextView)customView.findViewById(R.id.header);
            header.setText("Info");
            actionBar.setCustomView(customView);
            actionBar.setBackgroundDrawable(green_base);
        }
    }
}