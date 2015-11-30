package edu.uta.gigme;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;

import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    //private GoogleMap map;
    public static final int CONNECTION_TIMEOUT = 1000*10;
    public static final String SERVER_ADDRESS = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyCds5YDa88pYvXVVhlFFW0mF6u3l8L_wH0";

    public String address;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
            {
                address = null;
            }

            else
            {
                address = (String)extras.get("address");
            }
        }

        else
        {
            address = (String)savedInstanceState.getSerializable("address");
            //Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        String textToSearch = address;
        List<Address> fromLocationName = null;
        try
        {
            fromLocationName = geocoder.getFromLocationName(textToSearch, 1);
            if (fromLocationName != null && fromLocationName.size() > 0)
            {
                Address a = fromLocationName.get(0);
                System.out.println("latlong : " + (double) a.getLatitude() + " " + (double) a.getLongitude());


                mMap = googleMap;

                // Add a marker to Event address and move the camera
                LatLng test = new LatLng((float)a.getLatitude(), (float)a.getLongitude());
                mMap.addMarker(new MarkerOptions().position(test).title("Test Marker"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(test));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(test)      // Sets the center of the map to event address
                        .zoom(19)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(90)                   // Sets the tilt of the camera to 90 degrees
                        .build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                mMap.animateCamera(cameraUpdate);

            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
