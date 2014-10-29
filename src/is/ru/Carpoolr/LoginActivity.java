package is.ru.Carpoolr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
public class LoginActivity extends Activity {
    private Firebase ref;
    public String userEmailAddress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://carpoolreax.firebaseio.com/");

        Button loginButton = (Button)findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogIn();
            }
        });

        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    Log.d("Success", "User signed in");
                } else {
                    Log.d("Failure", "User signed out");
                }
            }
        });

        Button signup = (Button)findViewById(R.id.goToSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userLogIn(){
        EditText userNameField = (EditText)findViewById(R.id.usernameLogin);
        String userName = userNameField.getText().toString();

        EditText passwordField = (EditText)findViewById(R.id.passwordLogin);
        String password = passwordField.getText().toString();

        ref.authWithPassword(userName, password, new Firebase.AuthResultHandler(){
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
                ref.child("users").child(authData.getUid()).setValue(map);

                userEmailAddress = authData.getProviderData().get("email").toString();
                Log.d("aa", userEmailAddress);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userEmail", userEmailAddress);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.d("Failure: ", firebaseError.getMessage());
            }
        });
    }
}
