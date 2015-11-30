package edu.uta.gigme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class AllEventsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    TextView mName, mEmail;
    UserLocalStore userLocalStore;
    public ProgressDialog progressDialog;
    ListView listView;
    String SelectedCity = null;
    String SelectedGenre = null;
    ArrayList<Integer> selectedGenreList;
    public ServerRequest serverRequest;


    public Event fetchedEvent;

        @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.nav_header_all_events);

        setContentView(R.layout.activity_all_events);
        mName = (TextView) findViewById(R.id.tv_name);
        mEmail = (TextView) findViewById(R.id.tv_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userLocalStore = new UserLocalStore(this);
        User user = userLocalStore.getLoggedInUser();
        // mName.setText(user.name);
        // mEmail.setText(user.email);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AllEventsActivity.this, AddEventActivity.class));
                /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //this is to list the events
        serverRequest = new ServerRequest(this);

        listView = (ListView) findViewById(R.id.listEvents);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> view, View v, int position, long id)
            {
                //String selected = ((TextView) v.findViewById(R.id.)).getText().toString()
                fetchedEvent = (Event) listView.getItemAtPosition(position);//((TextView)view.findViewById(R.id.tv_list)).getText().toString();

                //ViewEventDetails viewEventDetails = new ViewEventDetails(e);
                //Toast.makeText(AllEventsActivity.this, fetchedEventName , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AllEventsActivity.this, ViewEventDetails.class);
                intent.putExtra("fetchedEvent", (Serializable)fetchedEvent);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }

        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        if (id == R.id.nav_city) {

            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("City");
            String[] types = {"Arlington", "Dallas","Fort Worth","Houston"};
            b.setItems(types, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    switch (which) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "City Arlington Selected",
                                    Toast.LENGTH_SHORT).show();
                            SelectedCity = "Arlington";
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "City Dallas Selected",
                                    Toast.LENGTH_SHORT).show();
                            SelectedCity = "Dallas";
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "City Fort Worth Selected",
                                    Toast.LENGTH_SHORT).show();
                            SelectedCity = "Fort Worth";
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(), "City Houston Selected",
                                    Toast.LENGTH_SHORT).show();
                            SelectedCity = "Houston";
                            break;
                    }
                }

            });

            b.show();


        }
        else if (id == R.id.nav_genre)
        {

            selectedGenreList = new ArrayList<>();
            final boolean[] isSelectedGenre = {false,false,false,false,false};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Genre")
                    .setMultiChoiceItems(R.array.genre_arrays, isSelectedGenre, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    if (isChecked)
                                    {
                                        selectedGenreList.add(which);
                                        SelectedGenre = " ";

                                    } else if (selectedGenreList.contains(which)) {
                                        selectedGenreList.remove(Integer.valueOf(which));
                                    }
                                }
                            }
                    );

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),
                            "Genre Selected",
                            Toast.LENGTH_LONG)
                            .show();
                    selectedGenreList.add(which);

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        } else if (id == R.id.nav_filter) {
            if(SelectedCity==null){
                if(SelectedGenre==null){
                    Toast.makeText(getApplicationContext(), "Select your City and Genre preference",
                            Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Select your City preference",
                            Toast.LENGTH_SHORT).show();
                }

            }
            if(SelectedGenre==null){
                if(SelectedCity==null){
                    Toast.makeText(getApplicationContext(), "Select your City and Genre preference",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Select your Genre preference",
                            Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(getApplicationContext(), "Filtering Events",
                        Toast.LENGTH_SHORT).show();
                buildPreferenceList();

                //Toast.makeText(getApplicationContext(), getSelectedCity()+" "+getSelectedGenre(),Toast.LENGTH_SHORT).show();
                serverRequest.accessWebService(getSelectedCity(), getSelectedGenre().substring(0, getSelectedGenre().length()-1), this, this);
            }

        }  else if (id == R.id.nav_mygigs) {

            startActivity(new Intent(this, MyGigsActivity.class));


        } else if (id == R.id.nav_logout) {
            UserLocalStore userLocalStore = new UserLocalStore(this);
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            startActivity(new Intent(this, LoginActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onStart()
    {
        super.onStart();

        if(authenticate())
        {
            // do nothing
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

    public String getSelectedCity()
    {
        return SelectedCity;
    }

    public void setSelectedGenre(String list){
        SelectedGenre = list;
    }

    public String getSelectedGenre(){
        return SelectedGenre;
    }

    String[] genreStringArray = new String[]{"Metal","Rock","Pop","EDM","Psychedelic"};
    void buildPreferenceList(){
        StringBuilder sb = new StringBuilder(5);
        for (Integer i: selectedGenreList)
        {

        String x = i.toString();
        switch (x)
        {
            case "0":
                sb.append(genreStringArray[0]+",");
                break;
            case "1":
                sb.append(genreStringArray[1]+",");
                break;
            case "2":
                sb.append(genreStringArray[2]+",");
                break;
            case "3":
                sb.append(genreStringArray[3]+",");
                break;
            case "4":
                sb.append(genreStringArray[4]+",");
                break;
            default:
                i = 0;
        }
     }

        //Toast.makeText(getApplicationContext(), sb.toString(),Toast.LENGTH_SHORT).show();
        setSelectedGenre(sb.toString());
    }

}
