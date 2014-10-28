package is.ru.Carpoolr.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.RegistrationSuccessActivity;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DrepAri on 13.10.14.
 */
public class InfoFragment extends Fragment implements View.OnClickListener{

    private View view;
    private boolean isDualPane = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ride_info, container, false);
        isDualPane = view != null && view.getVisibility() == View.VISIBLE;
        Object rideInfo = getArguments().getSerializable("OBJ");

        Button takeRide = (Button)view.findViewById(R.id.take_ride);
        takeRide.setOnClickListener(this);

        if (rideInfo instanceof Passenger) {
            populatePassengerInfo((Passenger) rideInfo);
        }
        else if (rideInfo instanceof Ride) {
            populateRideInfo((Ride) rideInfo);
        }

        return view;
    }

    private void populateRideInfo(Ride rideInfo) {

        TextView tripInfo = (TextView) view.findViewById(R.id.trip_info);
        tripInfo.setText(rideInfo.getStart() + " - " + rideInfo.getDestination());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        TextView date = (TextView) view.findViewById(R.id.date_info);
        Calendar dateModel = rideInfo.getDate();
        date.setText(dateFormat.format(dateModel.getTime()));

        ((TextView) view.findViewById(R.id.user_header)).setText("DRIVER INFO");
        ((TextView) view.findViewById(R.id.email_info)).setText(rideInfo.getUser().getEmail());
        ((TextView) view.findViewById(R.id.username_info)).setText(rideInfo.getUser().getUsername());
        ((TextView) view.findViewById(R.id.phone_info)).setText(rideInfo.getUser().getPhone());
        ((TextView) view.findViewById(R.id.demands_info)).setText(rideInfo.getDemands());
        ((TextView) view.findViewById(R.id.seats_info)).setText(String.valueOf(rideInfo.getSeats()));
        ((TextView) view.findViewById(R.id.notes_info)).setText(rideInfo.getNote());
    }

    private void populatePassengerInfo(Passenger rideInfo) {
        TextView tripInfo = (TextView) view.findViewById(R.id.trip_info);
        tripInfo.setText(rideInfo.getStart() + " - " + rideInfo.getDestination());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        TextView date = (TextView) view.findViewById(R.id.date_info);
        Calendar dateModel = rideInfo.getDate();
        date.setText(dateFormat.format(dateModel.getTime()));

        TextView demands = (TextView) view.findViewById(R.id.demands_header);
        demands.setVisibility(View.INVISIBLE);

        ((TextView) view.findViewById(R.id.user_header)).setText("PASSENGER INFO");
        ((TextView) view.findViewById(R.id.email_info)).setText(rideInfo.getUser().getEmail());
        ((TextView) view.findViewById(R.id.username_info)).setText(rideInfo.getUser().getUsername());
        ((TextView) view.findViewById(R.id.phone_info)).setText(rideInfo.getUser().getPhone());
        ((TextView) view.findViewById(R.id.seats_info)).setText(String.valueOf(rideInfo.getSeats()));
        ((TextView) view.findViewById(R.id.notes_info)).setText(rideInfo.getNote());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void updateFragment(Object info) {
        if (info instanceof Ride) {
            populateRideInfo((Ride) info);
        }
        else if (info instanceof Passenger) {
            populatePassengerInfo((Passenger) info);
        }
    }

    @Override
    public void onClick(View v) {
        boolean isDual = false;

        Intent intent = new Intent(getActivity(), RegistrationSuccessActivity.class);
        startActivity(intent);
    }
}