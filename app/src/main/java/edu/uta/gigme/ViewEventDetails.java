//API key : AIzaSyC6y6eaIsow8BV0pkaw45l4PgGt-6_TseM
package edu.uta.gigme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                tveventName.setText("Event Name: " + fetchedEvent.eventName);
                tveventCity.setText("City: "+fetchedEvent.city);
                tveventCharge.setText("Ticket Price: $"+fetchedEvent.charge);
                tveventBeverage.setText("Beverage Served: "+fetchedEvent.beverage);
                tveventFood.setText("Food Served: "+fetchedEvent.food);
                tveventGenre.setText("Genre: "+fetchedEvent.genre);
                //tveventPrivate.setText("Event Type: "+fetchedEvent.privateEvent);
                //tveventAge.setText("Event Age"+fetchedEvent.age);
                tveventFromDate.setText("Starts on: "+fetchedEvent.fromDate);
                tveventToDate.setText("Ends on: "+fetchedEvent.toDate);
                tveventFromTime.setText("Starts At: "+fetchedEvent.fromTime);
                tveventToTime.setText("Ends At: "+fetchedEvent.toTime);

                if(fetchedEvent.privateEvent.equals("private")){
                    tveventPrivate.setText("Event Type: Private Event");
                    tveventPrivate.setTextColor(Color.parseColor("#ff0000"));
                }
                else{
                    tveventPrivate.setText("Event Type: Public Event");
                }


                if(fetchedEvent.age.equals("1")){
                    tveventAge.setText("Age limit: "+"Restricted, 21+ only");
                    tveventAge.setTextColor(Color.parseColor("#ff0000"));
                }
                else{
                    tveventAge.setText("Age limit: "+"Open to all");
                }
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
