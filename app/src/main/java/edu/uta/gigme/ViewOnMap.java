// how to use key : https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyC6y6eaIsow8BV0pkaw45l4PgGt-6_TseM
//idk what to do with this EB:10:72:4F:B6:BD:A8:63:9A:03:F0:1B:55:3E:7A:39:1A:0B:8F:9D;edu.uta.gigme

package edu.uta.gigme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

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

public class ViewOnMap extends AppCompatActivity
{
    private GoogleMap map;

    public static final int CONNECTION_TIMEOUT = 1000*10;
    public static final String SERVER_ADDRESS = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyCds5YDa88pYvXVVhlFFW0mF6u3l8L_wH0";

    public ViewOnMap()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_on_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        //private final LatLng eventCoordinates = new LatLng("");

        new ConvertAddressToCoordinates().execute();
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
            HttpConnectionParams.setConnectionTimeout(httpRequestParams,CONNECTION_TIMEOUT);
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

                System.out.println("blabla " + jsonObject.keys());
            }

            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
