package is.ru.Carpoolr;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import is.ru.Carpoolr.fragments.MainFragment;
import is.ru.Carpoolr.fragments.RideListFragment;


public class MainActivity extends Activity {
    private FragmentManager fragmentManager = getFragmentManager();
    private FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Add the main fragment to the fragment container and start it.

        fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction.add(R.id.fragment_container, mainFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
