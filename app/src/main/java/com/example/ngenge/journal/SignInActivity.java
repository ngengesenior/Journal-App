package com.example.ngenge.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private final int RC_SIGN = 123;
    @BindView(R.id.buttonSignIn)
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        auth = FirebaseAuth.getInstance();
        login();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this,"Signed in successfully",Toast.LENGTH_SHORT)
                        .show();

                startActivity(new Intent(this,MainActivity.class));
                finish();
            }

            else {
                Toast.makeText(this,"Could not sign in",Toast.LENGTH_LONG)
                        .show();
                if(response == null)
                {
                    Toast.makeText(this,"Sign in cancelled",Toast.LENGTH_SHORT)
                            .show();
                }

                else if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK)
                {
                    Toast.makeText(this,"No internet connection",Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
            }
        }
    }


    void login()
    {
        if(auth.getCurrentUser() == null)
        {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.PhoneBuilder().build())
                            ).build(),RC_SIGN
            );
        }

        else {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
    @OnClick(R.id.buttonSignIn)
    void signIn()
    {

        login();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(auth.getCurrentUser() != null)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
}
