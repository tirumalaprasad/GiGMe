package edu.uta.gigme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private AutoCompleteTextView mEmailTextView;
    private Button mBtnRegister, mBtnLogin;
    private EditText mPasswordTextView;
    private View mProgressView;
    private View mLoginFormView;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailTextView = (AutoCompleteTextView) findViewById(R.id.et_emailID);
        mPasswordTextView = (EditText) findViewById(R.id.password);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String mEmail = mEmailTextView.getText().toString();
                String mPassword = mPasswordTextView.getText().toString();

                VerifyUserDetails verifyUserDetails = new VerifyUserDetails(mEmail, mPassword);

                if (verifyUserDetails.verify())
                {
                    User user = new User(mEmail, mPassword);
                    authenticate(user);
                    break;
                }

                else
                {
                    Toast.makeText(this, verifyUserDetails.verificationResult, Toast.LENGTH_LONG).show();
                    break;
                }

            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    public void authenticate(User user)
    {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchUserDataInBackground(user, new GetUserCallback(){
            @Override
            public void done(User returnedUser)
            {
                if (returnedUser == null)
                {
                    System.out.println("If");
                    showErrorMessage();
                }

                else
                {
                    System.out.println("Else");
                    //LoginActivity l = new LoginActivity();
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage()
    {
        Toast.makeText(this, "Incorrect uname and/or pass", Toast.LENGTH_LONG).show();
    }


    public void logUserIn(User returnedUser)
    {
        System.out.println("Entered logUserIn: " + returnedUser.email + " " + returnedUser.name + " " + returnedUser.password + " " + returnedUser.dob
        + " " +returnedUser.phone_number + " " + returnedUser.sex);
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        System.out.println("Just before start_main");
        startActivity(new Intent(this, AllEventsActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}