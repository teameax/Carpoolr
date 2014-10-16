package is.ru.Carpoolr.fragments;

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
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.service.PassengerListAdapter;

/**
 * Created by DrepAri on 11.10.14.
 */
public class PassengerListFragment extends android.support.v4.app.ListFragment {

    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";
    private Firebase firebase;
    private ValueEventListener connectedListener;
    private PassengerListAdapter passengerListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(getActivity());

        firebase = new Firebase(FIREBASE_URL).child("passengers");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle onInstanceState) {
        super.onActivityCreated(onInstanceState);

        passengerListAdapter = new PassengerListAdapter(firebase.limit(10), getActivity(), R.layout.list_record);
        setListAdapter(passengerListAdapter);

        passengerListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                getListView().setSelection(passengerListAdapter.getCount() - 1);
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        passengerListAdapter.cleanup();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Passenger ride = (Passenger) passengerListAdapter.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("rideInfo", ride);

        RideInfoFragment rideInfoFragment = new RideInfoFragment();
        rideInfoFragment.setArguments(bundle);


    }
}