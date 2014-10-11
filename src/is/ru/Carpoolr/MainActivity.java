package is.ru.Carpoolr;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

public class MainActivity extends ListActivity {

    private static final String FIREBASE_URL = "https://carpoolreax.firebaseio.com/";
    private Firebase ref;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


    }
}
