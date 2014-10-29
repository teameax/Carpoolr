package is.ru.Carpoolr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joddsson on 29.10.2014.
 */
public class SignUpActivity extends Activity {
    private Firebase ref;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://carpoolreax.firebaseio.com/");

        Button signUpButton = (Button)findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });
    }

    private void userSignUp(){
        final Button goToLogin = (Button)findViewById(R.id.goToLogin);
        EditText userNameField = (EditText)findViewById(R.id.username);
        String userName = userNameField.getText().toString();

        EditText passwordField = (EditText)findViewById(R.id.password);
        String password = passwordField.getText().toString();

        ref.createUser(userName, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Log.d("Success", "User successfully signed up");
                goToLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateToLogin();
                    }
                });
                goToLogin.setText("Click here to log in");
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                Log.d("Failure", firebaseError.getMessage());
            }
        });
    }

    private void navigateToLogin(){
        NavUtils.navigateUpFromSameTask(this);
    }
}
