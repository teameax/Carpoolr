package is.ru.Carpoolr;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import is.ru.Carpoolr.fragments.RegistrationSuccessFragment;
import is.ru.Carpoolr.models.Passenger;
import is.ru.Carpoolr.models.Ride;


/**
 * Created by joddsson on 19.10.2014.
 */
public class RegistrationSuccessActivity extends FragmentActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        RegistrationSuccessFragment newFragment = new RegistrationSuccessFragment();
        

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, newFragment, InfoActivity.INFO_TAG);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
