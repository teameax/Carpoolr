package is.ru.Carpoolr;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
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

    private void setupActionbar() {
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
            header.setText(R.string.app_name);
            actionBar.setCustomView(customView);
            actionBar.setBackgroundDrawable(new ColorDrawable(R.color.green_base));
        }
    }
}
