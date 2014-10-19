package is.ru.Carpoolr;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import is.ru.Carpoolr.fragments.RegistrationSuccessFragment;



/**
 * Created by joddsson on 19.10.2014.
 */
public class RegistrationSuccessActivity extends android.support.v4.app.FragmentActivity{
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getResources().getBoolean(R.bool.has_two_panes)){
            finish();
        }

        RegistrationSuccessFragment registrationSuccessFragment = new RegistrationSuccessFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, registrationSuccessFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
