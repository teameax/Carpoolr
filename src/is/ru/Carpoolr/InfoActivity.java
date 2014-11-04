package is.ru.Carpoolr;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import is.ru.Carpoolr.fragments.InfoFragment;
import is.ru.Carpoolr.fragments.OnSingleRideSelectListener;
import is.ru.Carpoolr.fragments.RegistrationSuccessFragment;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;

/**
 * Created by DrepAri on 16.10.14.
 */
public class InfoActivity extends FragmentActivity implements OnSingleRideSelectListener{
    private boolean isDualPane = false;
    private View view;
    protected static final String INFO_TAG = "info";
    protected NotificationManager notificationManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_info);
        Object info                 = getIntent().getExtras().get("OBJ");
        String id                   = (String) getIntent().getExtras().get("id");
        InfoFragment newFragment    = new InfoFragment();
        Bundle args                 = new Bundle();
        view = findViewById(R.id.registration_success_fragment);
        isDualPane = view != null && view.getVisibility() == View.VISIBLE;

        if (getResources().getBoolean(R.bool.has_two_panes)) {
            finish();
        }

        setupActionbar();

        if (info instanceof Ride) {
            Ride ride = (Ride) info;
            args.putSerializable("OBJ", ride);
        } else {
            Passenger passenger = (Passenger) info;
            args.putSerializable("OBJ", passenger);
        }
        args.putString("id", id);
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

    public void setupActionbar() {
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

    @Override
    public void onSingleRideSelectListener(Object info) {
        if(isDualPane){
            RegistrationSuccessFragment newFragment = new RegistrationSuccessFragment();
            Bundle args = new Bundle();
            if(info instanceof Ride){
                Ride ride = (Ride) info;
                args.putSerializable("OBJ", ride);
            } else{
                Passenger passenger = (Passenger) info;
                args.putSerializable("OBJ", passenger);
            }
            newFragment.setArguments(args);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.registration_success_fragment, newFragment, INFO_TAG);

        }
        else{
            Intent intent = new Intent(this, RegistrationSuccessActivity.class);
            if(info instanceof Ride){
                intent.putExtra("OBJ", (Ride) info);
            }
            else if(info instanceof Passenger){
                intent.putExtra("OBJ", (Passenger) info);
            }
            startActivity(intent);

        }
    }
}