package is.ru.Carpoolr.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by joddsson on 19.10.2014.
 */
public class RegistrationSuccessFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_success, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    public void updateFragment(Object info) {
        if (info instanceof Ride) {
            populateRideInfo((Ride) info);
        }
        else if (info instanceof Passenger) {
            populatePassengerInfo((Passenger) info);
        }
    }

    private void populateRideInfo(Ride rideInfo) {
        ((TextView) view.findViewById(R.id.driver_info)).setText(rideInfo.getUser().getUsername());

    }

    private void populatePassengerInfo(Passenger rideInfo) {
        ((TextView) view.findViewById(R.id.driver_info)).setText(rideInfo.getUser().getUsername());

    }
}
