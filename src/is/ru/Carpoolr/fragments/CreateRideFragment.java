package is.ru.Carpoolr.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.firebase.client.Firebase;
import is.ru.Carpoolr.R;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;
import is.ru.Carpoolr.models.Trip;
import is.ru.Carpoolr.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by joddsson on 16.10.2014.
 */
public class CreateRideFragment extends Fragment implements AdapterView.OnItemSelectedListener, NumberPicker.OnValueChangeListener {

    private String type;
    private String start;
    private String destination;
    private String note;
    private String demands;
    private Calendar date;
    private int seats = 1;

    int mStackLevel = 0;
    public static final int DIALOG_FRAGMENT = 1;
    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";
    private Firebase firebase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_ride_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            // set up spinner
            Spinner spinner = (Spinner) view.findViewById(R.id.type_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.type_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

            //set up submit button
            Button button = (Button) view.findViewById(R.id.submit_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submit(v);
                }
            });

            //set up number picker
            NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.seats);
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(6);
            numberPicker.setWrapSelectorWheel(false);
            numberPicker.setOnValueChangedListener(this);

            //set up date picker
            Button dateButton = (Button) view.findViewById(R.id.date);
            date = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            dateButton.setText(simpleDateFormat.format(date.getTime()));
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(DIALOG_FRAGMENT);
                }
            });

        }
        Firebase.setAndroidContext(getActivity());
        firebase = new Firebase(FIREBASE_URL);

    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type", type);
        outState.putString("start", start);
        outState.putString("destination", destination);
        outState.putString("note", note);
        outState.putString("demands", demands);
        outState.putInt("seats", seats);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            type = savedInstanceState.getString("type");
            start = savedInstanceState.getString("start");
            destination = savedInstanceState.getString("destination");
            note = savedInstanceState.getString("note");
            demands = savedInstanceState.getString("demands");
            seats = savedInstanceState.getInt("seats");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void submit(View v) {
        View view = getView();
        if (view != null) {
            start = ((EditText) view.findViewById(R.id.start)).getText().toString();
            destination = ((EditText) view.findViewById(R.id.destination)).getText().toString();
            note = ((EditText) view.findViewById(R.id.notes)).getText().toString();
            demands = ((EditText) view.findViewById(R.id.demands)).getText().toString();
        }
        Trip trip;

        if (type.equals("Driver")) {
            trip = new Passenger();
            firebase = new Firebase(FIREBASE_URL).child("passengers");
            trip.setType("Passenger");
        }
        else {
            trip = new Ride();
            firebase = new Firebase(FIREBASE_URL).child("rides");
            trip.setDemands(demands);
            trip.setType("Driver");
        }
        trip.setDate(date);
        trip.setDestination(destination);
        trip.setNote(note);
        trip.setStart(start);
        trip.setSeats(seats);

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

        trip.setUser(user);

        firebase.push().setValue(trip, user.getEmail());
        getActivity().finish();
    }

    void showDialog(int type) {

        mStackLevel++;

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        switch (type) {

            case DIALOG_FRAGMENT:

                DialogFragment dialogFrag = DatePickerFragment.newInstance();
                dialogFrag.setTargetFragment(this, DIALOG_FRAGMENT);
                dialogFrag.show(getActivity().getSupportFragmentManager().beginTransaction(), "dialog");
                break;
        }
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        seats = newVal;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case DIALOG_FRAGMENT:

                if (resultCode == Activity.RESULT_OK) {
                    date.set(Calendar.YEAR, data.getExtras().getInt("year"));
                    date.set(Calendar.MONTH, data.getExtras().getInt("month"));
                    date.set(Calendar.DAY_OF_MONTH, data.getExtras().getInt("day"));
                    setTime();
                }

                break;
        }
    }

    private void setTime() {
        View view = getView();

        if(view != null) {
            Button dateButton = (Button) view.findViewById(R.id.date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
            dateButton.setText(simpleDateFormat.format(date.getTime()));
        }
    }

}

