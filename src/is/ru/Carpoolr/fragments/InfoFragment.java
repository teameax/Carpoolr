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
import com.firebase.client.Firebase;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.RegistrationSuccessActivity;
import is.ru.Carpoolr.models.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrepAri on 13.10.14.
 */
public class InfoFragment extends Fragment implements View.OnClickListener{

    private View view;
    private boolean isDualPane = false;
    private Trip trip;
    private String id;
    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";
    private Firebase firebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ride_info, container, false);
        isDualPane = view != null && view.getVisibility() == View.VISIBLE;
        id = getArguments().getString("id");
        Object rideInfo = getArguments().getSerializable("OBJ");

        Button takeRide = (Button)view.findViewById(R.id.take_ride);
        takeRide.setOnClickListener(this);

        if (rideInfo instanceof Passenger) {
            populatePassengerInfo((Passenger) rideInfo);
            firebase = new Firebase(FIREBASE_URL).child("passengers/"+id);
        }
        else if (rideInfo instanceof Ride) {
            firebase = new Firebase(FIREBASE_URL).child("rides/"+id);
            populateRideInfo((Ride) rideInfo);
        }
        trip = (Trip) rideInfo;

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

    public void updateFragment(Object info, String ids) {
        if (info instanceof Ride) {
            populateRideInfo((Ride) info);
        }
        else if (info instanceof Passenger) {
            populatePassengerInfo((Passenger) info);
        }
        id = ids;
    }

    @Override
    public void onClick(View v) {
        User user = new User();
        if (firebase.getAuth().getProviderData().containsKey("email")) {
            user.setEmail(firebase.getAuth().getProviderData().get("email").toString());
        }
        if (firebase.getAuth().getProviderData().containsKey("username")) {
            user.setUsername(firebase.getAuth().getProviderData().get("username").toString());
        }
        else if(firebase.getAuth().getProviderData().containsKey("email")) {
            user.setUsername(firebase.getAuth().getProviderData().get("email").toString());
        }
        if (firebase.getAuth().getProviderData().containsKey("phone")) {
            user.setPhone(firebase.getAuth().getProviderData().get("phone").toString());
        }
        Registration registration = new Registration();
        registration.setSeats(1);
        registration.setUser(user);
        registration.setRegistration_date(Calendar.getInstance());
        registration.setTrip_id(id);
        if (trip.getType().equals("Driver")) {
            registration.setType("Passenger");
        }
        else {
            registration.setType("Driver");
        }

        if (trip.getSeats() > 0) {
            trip.setSeats(trip.getSeats() - 1);
            ((TextView) view.findViewById(R.id.seats_info)).setText(String.valueOf(trip.getSeats()));
        }
        else {
            return;
        }
        Map<String, Object> seats = new HashMap<String, Object>();
        seats.put("seats", trip.getSeats());


        firebase.updateChildren(seats);
        firebase = new Firebase(FIREBASE_URL).child("registrations");
        firebase.push().setValue(registration, trip.getUser().getEmail());


        Intent intent = new Intent(getActivity(), RegistrationSuccessActivity.class);
        intent.putExtra("OBJ", trip);
        startActivity(intent);
    }
}