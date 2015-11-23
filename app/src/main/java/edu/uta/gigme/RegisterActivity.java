package edu.uta.gigme;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText mEtName, mEtEmail, mEtPassword, mEtRePassword, mEtPhone, mEtDOB;
    RadioGroup mRgSex;
    //RadioButton mRbSex;
    Button mBtnRegister;
    String sSex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeUI();
        initializeClickListeners();
    }

    private void initializeClickListeners()
    {
        mBtnRegister.setOnClickListener(this);
        mEtDOB.setOnClickListener(this);
    }

    private void initializeUI()
    {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtRePassword = (EditText) findViewById(R.id.et_re_password);
        mEtDOB = (EditText) findViewById(R.id.et_dob);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mEtPhone = (EditText) findViewById(R.id.et_phone_number);
        mRgSex = (RadioGroup) findViewById(R.id.rg_sex);
        sSex = ((RadioButton) this.findViewById(mRgSex.getCheckedRadioButtonId())).getText().toString();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.et_dob:
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                mEtDOB.setText(monthOfYear+1 + "/" + dayOfMonth + "/" + year);
                                mEtPhone.requestFocus();
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
                break;

            case R.id.btn_register:
                String sName = mEtName.getText().toString();
                String sEmail = mEtEmail.getText().toString();
                String sPassword = mEtPassword.getText().toString();
                String sRePassword = mEtRePassword.getText().toString();
                //Log.d("RegisterActivity", sSex);

                String sDOB = mEtDOB.getText().toString();
                String sPhoneNumber = mEtPhone.getText().toString();
                VerifyUserDetails verifyUserDetails = new VerifyUserDetails(sName, sEmail, sPassword, sRePassword, sPhoneNumber);

                if(verifyUserDetails.verify())
                    {
                        User registeredUser = new User(sName, sEmail, sPassword, sDOB, sPhoneNumber, sSex);
                        registerUser(registeredUser);
                        break;
                    }

                else
                    {
                        Toast.makeText(this, verifyUserDetails.verificationResult, Toast.LENGTH_LONG).show();
                        break;
                    }
        }
    }

    private void registerUser(User registeredUser)
    {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeUserDataInBackground(registeredUser, new GetUserCallback()
        {
            @Override
            public void done(User returnedUser)
            {
                Toast.makeText(RegisterActivity.this, "You've been registered successully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}
