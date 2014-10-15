package is.ru.Carpoolr.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Ride;

/**
 * Created by DrepAri on 13.10.14.
 */
public class RideInfoFragment extends Fragment {

    private Ride rideInfo;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ride_info, container, false);
        this.rideInfo = (Ride) getArguments().getSerializable("rideInfo");
        populateFields();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void populateFields() {
        ((TextView) view.findViewById(R.id.from_info)).setText(rideInfo.getStart());
        ((TextView) view.findViewById(R.id.to_info)).setText(rideInfo.getDestination());
        ((TextView) view.findViewById(R.id.email_info)).setText(rideInfo.getUser().getEmail());
        ((TextView) view.findViewById(R.id.username_info)).setText(rideInfo.getUser().getUsername());
        ((TextView) view.findViewById(R.id.phone_info)).setText(rideInfo.getUser().getPhone());
        ((TextView) view.findViewById(R.id.demands)).setText(rideInfo.getDemands());
        ((TextView) view.findViewById(R.id.notes)).setText(rideInfo.getNote());
    }


}