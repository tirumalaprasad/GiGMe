package edu.uta.gigme;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewEventDetails extends AppCompatActivity implements View.OnClickListener
{
    ServerRequest serverRequest;
    Event event;

    TextView tveventName, tveventAddress, tveventPhone,
            tveventCharge, tveventBeverage, tveventFood,
            tveventCity, tveventGenre, tveventPrivate,
            tveventAge, tveventFromDate, tveventFromTime,
            tveventToDate, tveventToTime;
    Button btnViewContact, btnViewOnMaps;

    public ViewEventDetails()
    {
        serverRequest = new ServerRequest(this);
        event = new Event();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //this is my code
            tveventName = (TextView) findViewById(R.id.tvEventName);
            tveventAddress = (TextView) findViewById(R.id.tvEventAddress);
            tveventPhone = (TextView) findViewById(R.id.tvEventHostPhoneNumber);
                tveventCharge = (TextView) findViewById(R.id.tvEventCoverCharge);
                tveventBeverage = (TextView) findViewById(R.id.tv_beverage);
                tveventFood = (TextView) findViewById(R.id.tv_type_of_food);
                tveventCity = (TextView) findViewById(R.id.tv_city);
                tveventGenre = (TextView) findViewById(R.id.tv_genre);
                tveventPrivate = (TextView) findViewById(R.id.tv_private);
                tveventAge = (TextView) findViewById(R.id.tv_minAge);
                tveventFromDate = (TextView) findViewById(R.id.tv_from_date);
                tveventFromTime = (TextView) findViewById(R.id.tv_from_time);
                tveventToDate = (TextView) findViewById(R.id.tv_to_date);
                tveventToTime = (TextView) findViewById(R.id.tv_to_time);

        btnViewOnMaps.setOnClickListener(this);
        btnViewContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

    }
}
