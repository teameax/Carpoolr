package is.ru.Carpoolr.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import is.ru.Carpoolr.MainActivity;
import is.ru.Carpoolr.R;

/**
 * Created by joddsson on 13.10.2014.
 */
public class MainFragment extends Fragment implements View.OnClickListener{
    private String list_type = null;
    protected RideListFragment rideListFragment;
    private LayoutInflater inflate;

    /**
     * Inflate the main_fragment when the main view is created.
     * @param inflater TODO: DOCUMENT THIS
     * @param container TODO: DOCUMENT THIS
     * @param savedInstanceState TODO: DOCUMENT THIS
     * @return The main view with the main_fragment running.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        return view;
    }

    /**
     * Start the rideListFragment when user clicks the <INSERT_BUTTON_NAME> button.
     * @param v The view the button belongs to.
     */
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("filter", list_type);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        rideListFragment = new RideListFragment();
        rideListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, rideListFragment);
        fragmentTransaction.addToBackStack(rideListFragment.getClass().getName());
        fragmentTransaction.commit();
    }


}
