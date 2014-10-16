package is.ru.Carpoolr.service;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import com.firebase.client.Query;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by DrepAri on 11.10.14.
 */
public class PassengerListAdapter extends FirebaseListAdapter<Passenger> {

    public PassengerListAdapter(Query ref, Activity activity, int layout) {
        super(ref, Passenger.class, layout, activity);

    }

    @Override
    protected void populateView(View view, Passenger model) {
        TextView trip = (TextView) view.findViewById(R.id.trip);
        String tripString = model.getStart() + " - " + model.getDestination();
        trip.setText(tripString);

        ((TextView) view.findViewById(R.id.user)).setText(model.getUser().getUsername());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        TextView date = (TextView) view.findViewById(R.id.date);
        Calendar dateModel = model.getDate();
        date.setText(dateFormat.format(dateModel.getTime()));

    }
}
