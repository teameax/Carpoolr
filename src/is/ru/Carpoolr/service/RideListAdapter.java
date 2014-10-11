package is.ru.Carpoolr.service;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import com.firebase.client.Query;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Ride;

/**
 * Created by DrepAri on 11.10.14.
 */
public class RideListAdapter extends FirebaseListAdapter<Ride> {

    private String filter;

    public RideListAdapter(Query ref, Activity activity, int layout, String filter) {
        super(ref, Ride.class, layout, activity);
        this.filter = filter;

    }

    @Override
    protected void populateView(View view, Ride model) {
        if (filter == null || filter.equals(model.getType()) ) {
            ((TextView) view.findViewById(R.id.from)).setText(model.getStart());
            ((TextView) view.findViewById(R.id.to)).setText(model.getDestination());
            ((TextView) view.findViewById(R.id.date)).setText(model.getDate().toString());
            ((TextView) view.findViewById(R.id.user)).setText(model.getUser().getUsername());
        }
    }
}
