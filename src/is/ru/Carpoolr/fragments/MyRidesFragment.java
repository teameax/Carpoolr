package is.ru.Carpoolr.fragments;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.firebase.client.Firebase;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.service.RegistrationListAdapter;

/**
 * Created by DrepAri on 2.11.14.
 */
public class MyRidesFragment extends ListFragment {
    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";
    private Firebase firebase;
    private RegistrationListAdapter registrationListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(getActivity());

        firebase = new Firebase(FIREBASE_URL).child("registrations");
    }

    @Override
    public void onActivityCreated(Bundle onInstanceState) {
        super.onActivityCreated(onInstanceState);
        String email = firebase.getAuth().getProviderData().get("email").toString();
        final ListView view = getListView();
        registrationListAdapter = new RegistrationListAdapter(firebase.startAt(email).endAt(email), getActivity(), R.layout.myride_record);
        setListAdapter(registrationListAdapter);

        registrationListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                view.setSelection(registrationListAdapter.getCount() - 1);
            }
        });

    }

}