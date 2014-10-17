package is.ru.Carpoolr;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import is.ru.Carpoolr.fragments.CreateRideFragment;

/**
 * Created by joddsson on 16.10.2014.
 */
public class CreateRideActivity extends FragmentActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getBoolean(R.bool.has_two_panes)) {
            finish();
        }

        setupActionbar();
        CreateRideFragment newFragment = new CreateRideFragment();

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, newFragment);
        ft.addToBackStack(null);

        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    private void setupActionbar() {
        ColorDrawable green_base = (ColorDrawable)getResources().getDrawable(R.color.green_base);
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setLogo(R.drawable.ic_action_back);

            LayoutInflater inflater = LayoutInflater.from(this);
            View customView         = inflater.inflate(R.layout.custom_actionbar, null);
            TextView header         = (TextView)customView.findViewById(R.id.header);
            header.setText("Create new");
            actionBar.setCustomView(customView);
            actionBar.setBackgroundDrawable(green_base);
        }
    }
}
