package is.ru.Carpoolr;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import is.ru.Carpoolr.fragments.RideListFragment;


public class MainActivity extends Activity {

    private String list_type = null;
    private RideListFragment rideListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle bundle = new Bundle();
        bundle.putString("filter", list_type);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        rideListFragment = new RideListFragment();
        rideListFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, rideListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
