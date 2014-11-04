package is.ru.Carpoolr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    public String userEmailAddress;
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

    // TODO: This code is the same as in LoginActivity, that needs fixing.
    private void userSignUp(){
        final EditText userNameField = (EditText)findViewById(R.id.username);
        final String userName = userNameField.getText().toString();

        final EditText passwordField = (EditText)findViewById(R.id.password);
        final String password = passwordField.getText().toString();

        ref.createUser(userName, password, new Firebase.ResultHandler() {
            // Log the user in on success.
            @Override
            public void onSuccess() {
                ref.authWithPassword(userName, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("provider", authData.getProvider());
                        if(authData.getProviderData().containsKey("id")) {
                            map.put("provider_id", authData.getProviderData().get("id").toString());
                        }
                        if(authData.getProviderData().containsKey("displayName")) {
                            map.put("displayName", authData.getProviderData().get("displayName").toString());
                        }
                        if(authData.getProviderData().containsKey("email")) {
                            map.put("email", authData.getProviderData().get("email").toString());
                        }

                        ref.child("users").child(authData.getUid()).setValue(map);

                        userEmailAddress = authData.getProviderData().get("email").toString();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("userEmail", userEmailAddress);
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Log.d("Failure: ", firebaseError.getMessage());

                        switch(firebaseError.getCode()) {
                            case FirebaseError.INVALID_EMAIL:
                                userNameField.setError("Email is not valid!");
                                break;
                            case FirebaseError.INVALID_CREDENTIALS:
                                userNameField.setError("The credentials are invalid!");
                                break;
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                userNameField.setError("Invalid username");
                                break;
                            default:
                                userNameField.setError("Error has occurred, please try again!");
                        }
                    }
                });

            }
            @Override
            public void onError(FirebaseError firebaseError) {
                Log.d("Failure", firebaseError.getMessage());
            }
        });
    }
}
