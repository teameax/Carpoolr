package is.ru.Carpoolr.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    public static DatePickerFragment newInstance(){

        return new DatePickerFragment();

    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Intent intent = new Intent();
            intent.putExtra("year", selectedYear);
            intent.putExtra("month", selectedMonth);
            intent.putExtra("day", selectedDay);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);


        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        switch (getTargetRequestCode()) {
            case 1:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                Calendar calendar = Calendar.getInstance();
                return new DatePickerDialog(getActivity(), pickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }
}
