package is.ru.Carpoolr.fragments;

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
import is.ru.Carpoolr.MainActivity;
import is.ru.Carpoolr.R;

/**
 * Created by joddsson on 13.10.2014.
 */
public class MainFragment extends Fragment implements View.OnClickListener{
    private String list_type = null;
    private RideListFragment rideListFragment;
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        Button button = (Button)view.findViewById(R.id.gunnar);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //RideListFragment mainFragment = (RideListFragment)fm.findFragmentById(R.id.ridelist_fragment);

        Log.d("penis", "yolo");
        Bundle bundle = new Bundle();
        bundle.putString("filter", list_type);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        rideListFragment = new RideListFragment();
        rideListFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, rideListFragment);
        fragmentTransaction.commit();
    }
}
