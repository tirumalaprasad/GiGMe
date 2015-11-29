package edu.uta.gigme;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
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

        //map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        //private final LatLng eventCoordinates = new LatLng("");

        //new ConvertAddressToCoordinates().execute();

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
                /*Barcode.GeoPoint point = new Barcode.GeoPoint(
                        (int) (a.getLatitude() * 1000),
                        (int) (a.getLongitude() * 1000));*/
                System.out.println("latlong : " + (double)a.getLatitude() + " " + (double)a.getLongitude());


                mMap = googleMap;

                // Add a marker to Event address and move the camera
                LatLng test = new LatLng((float)a.getLatitude(), (float)a.getLongitude());
                mMap.addMarker(new MarkerOptions().position(test).title("Test Marker"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(test));

            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public class ConvertAddressToCoordinates extends AsyncTask<String, Void, String>
    {
        public ConvertAddressToCoordinates()
        {

        }

        @Override
        protected String doInBackground(String... params)
        {
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams,CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);

            HttpPost post = new HttpPost(SERVER_ADDRESS);

            try
            {
                //client.execute(post);
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                System.out.println(result);

                JSONObject jsonObject = new JSONObject(result);

                System.out.println("blabla " + jsonObject.getString(""));
            }

            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
