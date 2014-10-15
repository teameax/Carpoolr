package is.ru.Carpoolr;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import is.ru.Carpoolr.fragments.MainFragment;
import is.ru.Carpoolr.fragments.RideListFragment;


public class MainActivity extends Activity {
    private FragmentManager fragmentManager = getFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Customizing the action bar.
        actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater inflater = LayoutInflater.from(this);
            View customView         = inflater.inflate(R.layout.custom_actionbar, null);
            TextView header         = (TextView)customView.findViewById(R.id.header);
            header.setText(R.string.app_name);
            ImageButton backButton  = (ImageButton) customView.findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Back button functionality.
                    if(fragmentManager.getBackStackEntryCount() > 0){
                        getFragmentManager().popBackStackImmediate();
                    }
                }
            });

            actionBar.setCustomView(customView);
            actionBar.setDisplayShowCustomEnabled(true);
        }

        // Add the main fragment to the fragment container and start it.
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
