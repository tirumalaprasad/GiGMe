package edu.uta.gigme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Siddharth Shah on 9/18/2015.
 */

/*
$mysql_host = "mysql2.000webhost.com";
$mysql_database = "a9686140_testdb";
$mysql_user = "a9686140_testdb";
$mysql_password = "testing123";
 */

public class ServerRequest
{
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000*10;
    public static final String SERVER_ADDRESS = "http://omega.uta.edu/~sbs5577/";

    public ServerRequest(Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Connecting");
        progressDialog.setMessage("Please wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallback userCallBack)
    {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }


    public void fetchUserDataInBackground(User user, GetUserCallback userCallBack)
    {
        progressDialog.show();
        new FetchUserDataAsyncTask(user, userCallBack).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>
    {
        User user;
        GetUserCallback userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallBack)
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
        GetUserCallback userCallBack;

        public FetchUserDataAsyncTask(User user, GetUserCallback userCallBack)
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

    //public String url = "http://omega.uta.edu/~sbs5577/getList.php";

    /*
    public class fetchEventListAsyncTask extends AsyncTask<String, Void, String>{
        public Context context;
        Activity activity;
        public String jsonResult;

        public ListView listView;


        public fetchEventListAsyncTask(Context context, Activity activity)
        {
            this.context = context;
            this.activity = activity;
            listView = (ListView)this.activity.findViewById(R.id.listEvents);
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
            List<Map<String, String>> eventList = new ArrayList<Map<String, String>>();
            try
            {
                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonArray = jsonResponse.getJSONArray("events");
                System.out.println("jsonArray.length()"+jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonChildNode = jsonArray.getJSONObject(i);
                    String event_name = jsonChildNode.optString("EVENT_NAME");
                    String event_id = jsonChildNode.optString("EVENT_ID");
                    String event_output = event_name + "-" +event_id;
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
            SimpleAdapter simpleAdapter = new SimpleAdapter(
                    context.getApplicationContext(),
                    eventList,
                    R.layout.custom_textview,
                    new String[]{"all_events"},
                    new int[] {R.id.tv_list}
            );

            listView.setAdapter(simpleAdapter);
        }

        private Map<String, String> createEventHashmap(String key_name, String event_output)
        {
            HashMap<String, String> eventNameId = new HashMap<String, String>();
            eventNameId.put(key_name, event_output);
            System.out.println("key_name:" + key_name);
            return eventNameId;
        }
    }


    public void accessWebService(Context context, Activity activity)
    {
        fetchEventListAsyncTask jsonReadTask = new fetchEventListAsyncTask(context, activity);
        jsonReadTask.execute(new String[]{url});

    }

    */
}