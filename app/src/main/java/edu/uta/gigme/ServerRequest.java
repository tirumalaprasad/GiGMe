package edu.uta.gigme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Siddharth Shah on 9/18/2015.
 */


public class ServerRequest
{
    ProgressDialog progressDialog;
    String url;
    UserLocalStore userLocalStore;
    public static final int CONNECTION_TIMEOUT = 1000*10;
    public static final String SERVER_ADDRESS = "http://omega.uta.edu/~sbs5577/";

    public ServerRequest(Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Connecting");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallBack userCallBack)
    {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }

    public void deleteEvent(String eventName, Context context)
    {
        progressDialog.show();
        new DeleteEvent(eventName, context).execute();
    }

    public void createEventInBackground(Event event, GetEventCallBack eventCallBack, Context context) {
        progressDialog.show();
        new CreateEventDataAsyncTask(event, eventCallBack, context).execute();
    }

    public class CreateEventDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Event event;
        GetEventCallBack eventCallBack;
        Context context;

        public CreateEventDataAsyncTask(Event event, GetEventCallBack eventCallBack, Context context) {
            this.context = context;
            this.event = event;
            this.eventCallBack = eventCallBack;
            userLocalStore = new UserLocalStore(context);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            eventCallBack.done(null);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("eventEmail", userLocalStore.getLoggedInUser().email));
            dataToSend.add(new BasicNameValuePair("eventName", event.eventName));
            dataToSend.add(new BasicNameValuePair("eventAddress", event.address));
            dataToSend.add(new BasicNameValuePair("eventPhone", event.phoneNumber));
            dataToSend.add(new BasicNameValuePair("eventCharge", event.charge));
            dataToSend.add(new BasicNameValuePair("eventBeverage", event.beverage));
            dataToSend.add(new BasicNameValuePair("eventFood", event.food));
            dataToSend.add(new BasicNameValuePair("eventCity", event.city));
            dataToSend.add(new BasicNameValuePair("eventGenre", event.genre));
            dataToSend.add(new BasicNameValuePair("eventPrivate", event.privateEvent));
            dataToSend.add(new BasicNameValuePair("eventAge", event.age));
            dataToSend.add(new BasicNameValuePair("eventFromDate", event.fromDate));
            dataToSend.add(new BasicNameValuePair("eventFromTime", event.fromTime));
            dataToSend.add(new BasicNameValuePair("eventToDate", event.toDate));
            dataToSend.add(new BasicNameValuePair("eventToTime", event.toTime));

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMEOUT);

            HttpClient httpClient = new DefaultHttpClient(httpParams);
            HttpPost httpPost = new HttpPost(SERVER_ADDRESS + "create_event.php");

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));
                httpClient.execute(httpPost);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void deleteEvent(Context context, String EventName) {
        new DeleteEventAsyncTask(context, EventName).execute();
    }
    public class DeleteEventAsyncTask extends AsyncTask<Void, Void, Void>{
        Context context;
        String EventName;

        public DeleteEventAsyncTask(Context context, String eventName) {
            this.context = context;
            EventName = eventName;
        }

        @Override
        protected Void doInBackground(Void... params) {
            System.out.println("=======Deleting=============="+EventName);
            return null;
        }
    }



    public void fetchUserDataInBackground(User user, GetUserCallBack userCallBack)
    {
        progressDialog.show();
        new FetchUserDataAsyncTask(user, userCallBack).execute();
    }


    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>
    {
        User user;
        GetUserCallBack userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallBack userCallBack)
        {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            progressDialog.dismiss();
            userCallBack.done(null);
            super.onPostExecute(aVoid);
        }


        @Override
        protected Void doInBackground(Void... params)
        {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            dataToSend.add(new BasicNameValuePair("dob", user.dob));
            dataToSend.add(new BasicNameValuePair("phone_number", user.phone_number));
            dataToSend.add(new BasicNameValuePair("sex", user.sex));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);

            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            System.out.println("Before try " + dataToSend);
            try
            {
                System.out.println("Inside try " + dataToSend);
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User>
    {
        User user;
        GetUserCallBack userCallBack;

        public FetchUserDataAsyncTask(User user, GetUserCallBack userCallBack)
        {
            this.user = user;
            this.userCallBack = userCallBack;
        }

        @Override
        protected void onPostExecute(User returnedUser)
        {
            progressDialog.dismiss();
            //System.out.println(returnedUser.email+returnedUser.password+returnedUser.name);
            userCallBack.done(returnedUser);  //got a user
            super.onPostExecute(returnedUser);
        }

        @Override
        protected User doInBackground(Void... params)
        {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            //dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("password", user.password));
            //dataToSend.add(new BasicNameValuePair("pass", user.pass));
            System.out.println(dataToSend);
            //dataToSend.add(new BasicNameValuePair("age", user.age + ""));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);

            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);

                Log.i("tagconverstr", "[" + result + "]");
                JSONObject jsonObject = new JSONObject(result.substring(result.indexOf('{'), result.lastIndexOf('}')+1));

                if (jsonObject.length() == 0)
                {
                    //doNothing;
                }

                else
                {
                    String name = jsonObject.getString("name");
                    //String email = jsonObject.getString("email");
                    //String password = jsonObject.getString("password");
                    String dob = jsonObject.getString("dob");
                    String phone_number = jsonObject.getString("phone_number");
                    String sex = jsonObject.getString("sex");

                    returnedUser = new User(name, user.email, user.password, dob, phone_number, sex);

                    System.out.println("ServerRequest ka doInBackground : "+returnedUser.email+returnedUser.password);
                }
            }

            catch(Exception e)
            {
                e.printStackTrace();
            }

            return returnedUser;
        }
    }
    public class fetchEventListForDeleteAsyncTask extends AsyncTask<String, Void, String> {
        public Context context;
        Activity activity;
        public String jsonResult;
        UserLocalStore userLocalStore;
        public ListView listView;
        public User user;
        ProgressDialog pd2;


        public fetchEventListForDeleteAsyncTask(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
            url = SERVER_ADDRESS + "getListForDelete.php";
            listView = (ListView) this.activity.findViewById(R.id.listEventsMyGigs);
            userLocalStore = new UserLocalStore(context);
            pd2 = new ProgressDialog(context);
            pd2.setMessage("Loading events");
            pd2.setCancelable(false);
            pd2.show();
        }

        //Events events;
        @Override
        protected String doInBackground(String... strings) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("eventEmail", userLocalStore.getLoggedInUser().email));
            System.out.println(dataToSend);

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(strings[0]);
            try {
                System.out.println("if was called");
                System.out.println("Entered try " + httpPost);

                httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                if (inputStream!=null){
                    jsonResult = inputStreamToString(inputStream).toString();
                    System.out.println("jsonResult of normal fetch========"+jsonResult);
                }
                //System.out.println("jsonResult after inputStream : "+jsonResult);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private StringBuilder inputStreamToString(InputStream content) {
            String rLine = "";
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
            try {
                System.out.println("in try of inputStreamToString");
                while ((rLine = bufferedReader.readLine()) != null) {
                    //System.out.println("in while loop : ");
                    stringBuilder.append(rLine);
                    System.out.println("rLine" + rLine);
                }
            } catch (ConnectException e) {
                System.out.println("ConnectException");
            } catch (IOException e) {
                System.out.println("IOException");
                e.printStackTrace();
            }
            System.out.println("stringBuilder : " + stringBuilder);
            return stringBuilder;
        }

        @Override
        protected void onPostExecute(String s) {
            ListDrawer();
        }

        private void ListDrawer() {
            Event[] events = null;
            List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();
            try {
                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonArray = jsonResponse.getJSONArray("events");
                System.out.println("jsonArray.length()" + jsonArray.length());
                events = new Event[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonChildNode = jsonArray.getJSONObject(i);
                    String event_id = jsonChildNode.optString("EVENT_ID");
                    String event_name = jsonChildNode.optString("EVENT_NAME");
                    String event_output = event_name;// "-" +event_id;
                    events[i] = new Event(Integer.parseInt(event_id), event_name);
                    eventList.add(createEventHashmap("all_events", event_output));
                    System.out.println("eventlist : " + eventList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("JSONException");
            }

            System.out.println("getContext:" + context.getApplicationContext());


            /*final SimpleAdapter simpleAdapter = new SimpleAdapter
                    (
                            context.getApplicationContext(),
                            eventList,
                            R.layout.custom_delete_my_gigs,
                            new String[]{"all_events"},
                            new int[]{R.id.tv_delete_list}
                    );
*/
            ArrayAdapter<Event> eventArrayAdapter = new ArrayAdapter<Event>(
                    context.getApplicationContext(),
                    R.layout.custom_delete_my_gigs,
                    R.id.tv_delete_list,
                    events);
            listView.setAdapter(eventArrayAdapter);
            pd2.dismiss();


            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                    System.out.println("Position=============" + map.values());
                    fetchEventDetailsInBackground(String.valueOf(map.values()));
                }
            });*/
        }


        private Map<String, String> createEventHashmap(String key_name, String event_output) {
            HashMap<String, String> eventNameId = new HashMap<String, String>();
            eventNameId.put(key_name, event_output);
            System.out.println("key_name:" + key_name);
            return eventNameId;
        }
    }





    public class fetchEventListAsyncTask extends AsyncTask<String, Void, String>
    {

        public Context context;
        Activity activity;
        public String jsonResult;

        public ListView listView;

        ProgressDialog pd2;


        public fetchEventListAsyncTask(Context context, Activity activity)
        {
            this.context = context;
            this.activity = activity;
            url = SERVER_ADDRESS + "getList.php";
            listView = (ListView)this.activity.findViewById(R.id.listEvents);
            pd2 = new ProgressDialog(context);
            pd2.setMessage("Loading events");
            pd2.setCancelable(false);
            pd2.show();
        }

        //Events events;
        @Override
        protected String doInBackground(String... strings)
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(strings[0]);

            try
            {
                System.out.println("Entered try "+httpPost);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                //System.out.println("jsonResult before inputStream : "+jsonResult);
                //System.out.println("httpResponse : "+httpResponse.getEntity().getContent());
                jsonResult = inputStreamToString(httpResponse.getEntity().getContent()).toString();
                //System.out.println("jsonResult after inputStream : "+jsonResult);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream content)
        {
            String rLine = "";
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
            try
            {
                System.out.println("in try of inputStreamToString");
                while((rLine = bufferedReader.readLine()) != null)
                {
                    //System.out.println("in while loop : ");
                    stringBuilder.append(rLine);
                    System.out.println("rLine" + rLine);
                }
            }

            catch (ConnectException e)
            {
                System.out.println("ConnectException");
            }

            catch (IOException e)
            {
                System.out.println("IOException");
                e.printStackTrace();
            }
            System.out.println("stringBuilder : "+stringBuilder);
            return stringBuilder;
        }

        @Override
        protected void onPostExecute(String s)
        {
            ListDrawer();
        }

        private void ListDrawer()
        {
            Event[] events = null;
            List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();
            try
            {
                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonArray = jsonResponse.getJSONArray("events");
                System.out.println("jsonArray.length()"+jsonArray.length());

                events = new Event[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonChildNode = jsonArray.getJSONObject(i);
                    String event_name = jsonChildNode.optString("EVENT_NAME");
                    String event_id = jsonChildNode.optString("EVENT_ID");
                    String emailId = jsonChildNode.optString("EMAIL_ID");
                    String genre = jsonChildNode.optString("GENRE");
                    String address = jsonChildNode.optString("ADDRESS");
                    String phoneNumber = jsonChildNode.optString("PHONE_NUMBER");
                    String charge = jsonChildNode.optString("EVENT_FEES");
                    String beverage = jsonChildNode.optString("DRINKS");
                    String food = jsonChildNode.optString("FOOD");
                    String city = jsonChildNode.optString("CITY");
                    String privateEvent = jsonChildNode.optString("EVENT_TYPE");
                    String age = jsonChildNode.optString("AGE_RESTRICTION");
                    String fromDate = jsonChildNode.optString("START_DATE");
                    String toDate = jsonChildNode.optString("END_DATE");
                    String fromTime = jsonChildNode.optString("START_TIME");
                    String toTime = jsonChildNode.optString("END_TIME");
                    //String eventId, String eventName, String genre, String address, String phoneNumber, String charge, String beverage, String food, String city, String privateEvent, String age, String fromDate, String toDate, String fromTime, String toTime)
                    events[i]= new Event(Integer.parseInt(event_id), event_name, genre,
                            address, phoneNumber, charge, beverage, food, city,
                            privateEvent, age, fromDate, toDate, fromTime, toTime);

                    String event_output = event_name;// + "-" +event_id;

                    eventList.add(createEventHashmap("all_events" ,event_output));
                    System.out.println("eventlist : "+eventList);
                }
            }

            catch (JSONException e)
            {
                e.printStackTrace();
                System.out.println("JSONException");
            }

            System.out.println("getContext:" + context.getApplicationContext());


            /*SimpleAdapter simpleAdapter = new SimpleAdapter
                    (
                    context.getApplicationContext(),
                    eventList,
                    R.layout.custom_textview,
                    new String[]{"all_events"},
                    new int[] {R.id.tv_list}
                    );
*/

            ArrayAdapter<Event> event = new ArrayAdapter<Event>(context.getApplicationContext(), R.layout.custom_textview, events);
            listView.setAdapter(event);
            pd2.dismiss();
        }

        private Map<String, String> createEventHashmap(String key_name, String event_output)
        {
            HashMap<String, String> eventNameId = new HashMap<String, String>();
            eventNameId.put(key_name, event_output);
            System.out.println("key_name:" + key_name);
            return eventNameId;
        }
    }


    //This is to delete an event
    public class DeleteEvent extends AsyncTask<Void, Void, Void>
    {
        String eventName;
        public Context context;
        //Activity activity;
        //ProgressDialog pd2;
        public String jsonResult;

        public DeleteEvent()
        {

        }

        public DeleteEvent(String eventName, Context context)
        {
            this.eventName = eventName;
            this.context = context;
            url = SERVER_ADDRESS + "DeleteEvent.php";
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("eventName", eventName));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient httpClient = new DefaultHttpClient(httpRequestParams);
            HttpPost httpPost = new HttpPost(SERVER_ADDRESS + "DeleteEvent.php");

            try
            {
                System.out.println("Inside try " + dataToSend);

                httpPost.setEntity(new UrlEncodedFormEntity(dataToSend));
                httpClient.execute(httpPost);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                //System.out.println("jsonResult before inputStream : "+jsonResult);
                //System.out.println("httpResponse : "+httpResponse.getEntity().getContent());
                jsonResult = httpResponse.getEntity().getContent().toString();
                System.out.println("jsonResult : "+jsonResult);
                progressDialog.dismiss();
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void accessWebServiceForDelete(Context context, Activity activity) {
        fetchEventListForDeleteAsyncTask jsonReadTask = new fetchEventListForDeleteAsyncTask(context, activity);
        jsonReadTask.execute(new String[]{url});
    }


    public void accessWebService(Context context, Activity activity)
    {
        fetchEventListAsyncTask jsonReadTask = new fetchEventListAsyncTask(context, activity);
        jsonReadTask.execute(new String[]{url});
    }
}


























/*
    public class FetchEventDetails extends AsyncTask<Integer, Void, String>
    {
        public String jsonResult;

        @Override
        protected String doInBackground(Integer... params)
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("");

            try
            {
                System.out.println("Entered try "+httpPost);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                //System.out.println("jsonResult before inputStream : "+jsonResult);
                //System.out.println("httpResponse : "+httpResponse.getEntity().getContent());
                jsonResult = inputStreamToString(httpResponse.getEntity().getContent()).toString();
                //System.out.println("jsonResult after inputStream : "+jsonResult);
            }

            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream content)
        {
            String rLine = "";
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
            try
            {
                System.out.println("in try of inputStreamToString");
                while((rLine = bufferedReader.readLine()) != null)
                {
                    //System.out.println("in while loop : ");
                    stringBuilder.append(rLine);
                    System.out.println("rLine" + rLine);
                }
            }

            catch (ConnectException e)
            {
                System.out.println("ConnectException");
            }

            catch (IOException e)
            {
                System.out.println("IOException");
                e.printStackTrace();
            }
            System.out.println("stringBuilder : "+stringBuilder);
            return stringBuilder;
        }



    }

    */