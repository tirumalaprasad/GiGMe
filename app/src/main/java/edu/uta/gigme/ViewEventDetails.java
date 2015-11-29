//API key : AIzaSyC6y6eaIsow8BV0pkaw45l4PgGt-6_TseM
package edu.uta.gigme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class ViewEventDetails extends AppCompatActivity implements View.OnClickListener
{
    ServerRequest serverRequest;
    Event fetchedEvent;

    TextView tveventName, tveventAddress, tveventPhone,
            tveventCharge, tveventBeverage, tveventFood,
            tveventCity, tveventGenre, tveventPrivate,
            tveventAge, tveventFromDate, tveventFromTime,
            tveventToDate, tveventToTime;

    Button btnViewContact, btnViewOnMaps;

    public ViewEventDetails()
    {

    }

    public ViewEventDetails(Event event)
    {
        serverRequest = new ServerRequest(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

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

        btnViewContact = (Button) findViewById(R.id.btContact);
        btnViewOnMaps = (Button) findViewById(R.id.btMaps);

        btnViewOnMaps.setOnClickListener(this);
        btnViewContact.setOnClickListener(this);


        //fetch the name of the event that userclicked, and was passed through the intent.putExtra in AllEventsActivity



        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {
                fetchedEvent= null;
            }
            else
            {
                fetchedEvent = (Event)extras.get("fetchedEvent");
                Toast.makeText(ViewEventDetails.this, fetchedEvent.toString(), Toast.LENGTH_SHORT).show();
                tveventName.setText(fetchedEvent.eventName);
                tveventCity.setText(fetchedEvent.city);
                tveventCharge.setText(fetchedEvent.charge);
                tveventFood.setText(fetchedEvent.food);
                tveventGenre.setText(fetchedEvent.genre);
                tveventPrivate.setText(fetchedEvent.privateEvent);
                tveventAge.setText(fetchedEvent.age);
                tveventFromDate.setText(fetchedEvent.fromDate);
                tveventToDate.setText(fetchedEvent.toDate);
                tveventFromTime.setText(fetchedEvent.fromTime);
                tveventToTime.setText(fetchedEvent.toTime);
            }
        }
        else
        {
            fetchedEvent = (Event)savedInstanceState.getSerializable("fetchedEvent");
            //Toast.makeText(ViewEventDetails.this, fetchedEvent.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btContact:
                AlertDialog alertDialog = new AlertDialog.Builder(ViewEventDetails.this).create();
                alertDialog.setTitle("Organizer contact");
                alertDialog.setMessage(fetchedEvent.phoneNumber);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;

            case R.id.btMaps:
                Intent intent = new Intent(ViewEventDetails.this, MapsActivity.class);
                intent.putExtra("address", fetchedEvent.address);
                startActivity(intent);


            default:
                break;
        }
    }
}
