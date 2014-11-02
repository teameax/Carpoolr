package is.ru.Carpoolr.service;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import com.firebase.client.*;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Registration;
import is.ru.Carpoolr.models.Ride;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DrepAri on 2.11.14.
 */
public class RegistrationListAdapter extends FirebaseListAdapter<Registration> {

    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";

    public RegistrationListAdapter(Query ref, Activity activity, int layout){
        super(ref, Registration.class , layout, activity);
    }

    @Override
    protected void populateView(View v, Registration model) {
        if (model.getType().equals("Driver")) {
            recordForPassenger(v, model);
        }
        else {
            recordForDriver(v, model);
        }

    }

    private void recordForDriver(final View v, Registration model) {
        Firebase firebase = new Firebase(FIREBASE_URL).child("rides/"+model.getTrip_id());
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Ride ride = dataSnapshot.getValue(Ride.class);
                ((TextView) v.findViewById(R.id.seats_left)).setText("Seats left:" + String.valueOf(ride.getSeats()));
                ((TextView) v.findViewById(R.id.trip_details)).setText(ride.getStart() + " - " + ride.getDestination());
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println(error.getMessage());
            }
        });

        ((TextView) v.findViewById(R.id.myride_user)).setText(model.getUser().getEmail() + " has registered to ride with you");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        TextView date = (TextView) v.findViewById(R.id.registration_date);
        Calendar dateModel = model.getRegistration_date();
        date.setText(dateFormat.format(dateModel.getTime()));
    }

    private void recordForPassenger(final View v, Registration model) {
        Firebase firebase = new Firebase(FIREBASE_URL).child("passengers/"+model.getTrip_id());
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Passenger ride = dataSnapshot.getValue(Passenger.class);
                ((TextView) v.findViewById(R.id.seats_left)).setText("Seats left:" + String.valueOf(ride.getSeats()));
                ((TextView) v.findViewById(R.id.trip_details)).setText(ride.getStart() + " - " + ride.getDestination());
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println(error.getMessage());
            }
        });

        ((TextView) v.findViewById(R.id.myride_user)).setText(model.getUser().getEmail() + " has registered to be your driver");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        TextView date = (TextView) v.findViewById(R.id.registration_date);
        Calendar dateModel = model.getRegistration_date();
        date.setText(dateFormat.format(dateModel.getTime()));

    }
}
