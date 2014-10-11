package is.ru.Carpoolr;

import android.app.Activity;
import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import is.ru.Carpoolr.service.RideListAdapter;

public class MainActivity extends ListActivity {

    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";
    private Firebase firebase;
    private ValueEventListener connectedListener;
    private RideListAdapter rideListAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Firebase.setAndroidContext(this);

        firebase = new Firebase(FIREBASE_URL).child("rides");


    }

    @Override
    public void onStart() {
        super.onStart();

        final ListView listView = getListView();

        rideListAdapter = new RideListAdapter(firebase.limit(10), this, R.layout.ride_record);
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
                    Toast.makeText(MainActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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
}
