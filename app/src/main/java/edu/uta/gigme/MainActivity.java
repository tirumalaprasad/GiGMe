package edu.uta.gigme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
$mysql_host = "mysql2.000webhost.com";
$mysql_database = "a9686140_testdb";
$mysql_user = "a9686140_testdb";
$mysql_password = "testing123";
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    //private String jsonEventResult;
    //private String jsonEventURL = "http://omega.uta.edu/~sbs5577/getList.php";
    //private ListView listView;
    public ProgressDialog progressDialog;

    Button btLogout;
    TextView tvName, tvEmail, tvDOB, tvPhone, tvSex;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = (TextView) findViewById(R.id.tvName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvDOB = (TextView) findViewById(R.id.tvDOB);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvSex = (TextView) findViewById(R.id.tvSex);

        btLogout = (Button) findViewById(R.id.btLogout);
        btLogout.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if(authenticate()== true)
        {
            displayUserDetails();
        }

        else
        {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private boolean authenticate()
    {
        return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails()
    {
        User u = userLocalStore.getLoggedInUser();
        tvName.setText(u.name);
        tvEmail.setText(u.email);
        tvDOB.setText(u.dob);
        tvPhone.setText(u.phone_number);
        tvSex.setText(u.sex);
    }
}