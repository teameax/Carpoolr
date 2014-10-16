package is.ru.Carpoolr.fragments;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Ride;
import is.ru.Carpoolr.service.RideListAdapter;

/**
 * Created by DrepAri on 11.10.14.
 */
public class RideListFragment extends android.support.v4.app.ListFragment {

    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";
    private Firebase firebase;
    private ValueEventListener connectedListener;
    private RideListAdapter rideListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(getActivity());

        firebase = new Firebase(FIREBASE_URL).child("rides");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle onInstanceState) {
        super.onActivityCreated(onInstanceState);

        rideListAdapter = new RideListAdapter(firebase.limit(10), getActivity(), R.layout.list_record);
        setListAdapter(rideListAdapter);

        rideListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                getListView().setSelection(rideListAdapter.getCount() - 1);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        rideListAdapter.cleanup();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Ride ride = (Ride) rideListAdapter.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("rideInfo", ride);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        RideInfoFragment rideInfoFragment = new RideInfoFragment();
        rideInfoFragment.setArguments(bundle);
        //fragmentTransaction.replace(R.id.fragment_container, rideInfoFragment);
        //fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commit();


    }
}