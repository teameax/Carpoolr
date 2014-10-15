package is.ru.Carpoolr.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Ride;
import is.ru.Carpoolr.service.RideListAdapter;

/**
 * Created by DrepAri on 11.10.14.
 */
public class RideListFragment extends ListFragment {

    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";
    private Firebase firebase;
    private ValueEventListener connectedListener;
    private RideListAdapter rideListAdapter;

    private String type_filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(getActivity());

        firebase = new Firebase(FIREBASE_URL).child("rides");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        type_filter = getArguments().getString("filter");
        return inflater.inflate(R.layout.ridelist_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        final ListView listView = getListView();

        rideListAdapter = new RideListAdapter(firebase.limit(10), getActivity(), R.layout.ride_record, type_filter);
        listView.setAdapter(rideListAdapter);

        rideListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(rideListAdapter.getCount() - 1);
            }
        });

        connectedListener = firebase.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean)dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(getActivity(), "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                // No-op
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.getRoot().child(".info/connected").removeEventListener(connectedListener);
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
        fragmentTransaction.replace(R.id.fragment_container, rideInfoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}