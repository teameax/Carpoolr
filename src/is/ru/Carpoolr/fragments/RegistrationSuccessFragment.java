package is.ru.Carpoolr.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;
import is.ru.Carpoolr.models.Trip;

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
        Trip trip = (Trip) getArguments().get("OBJ");
        populateFields(trip, view);
        Button button = (Button) view.findViewById(R.id.home_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    private void populateFields(Trip trip, View v) {
        ((TextView) v.findViewById(R.id.driver_info)).setText(trip.getUser().getUsername());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        ((TextView) v.findViewById(R.id.date_info)).setText(format.format(trip.getDate().getTime()));
        ((TextView) v.findViewById(R.id.destination_info)).setText(trip.getDestination());
    }



}
