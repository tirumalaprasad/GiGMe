package edu.uta.gigme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyGigsActivity extends AppCompatActivity implements View.OnClickListener{
    Button mCbDelete;
    ListView mLvMyEvents;
    Event fetchedEvent;

    ServerRequest serverRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gigs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLvMyEvents = (ListView) findViewById(R.id.listEventsMyGigs);
        serverRequest = new ServerRequest(this);

        mLvMyEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fetchedEvent = (Event) mLvMyEvents.getItemAtPosition(position);//((TextView)view.findViewById(R.id.tv_list)).getText().toString();
                serverRequest.deleteEvent(fetchedEvent.eventName, MyGigsActivity.this);
                finish();
                startActivity(getIntent());
                //Toast.makeText(MyGigsActivity.this, fetchedEvent.eventName, Toast.LENGTH_SHORT).show();

            }
        });

        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.accessWebServiceForDelete(this, this);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(MyGigsActivity.this, AllEventsActivity.class));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            /*
            case R.id.btn_delete:

                break;

            default:
                break;
                */
        }
    }
}