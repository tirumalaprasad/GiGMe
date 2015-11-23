package edu.uta.gigme;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Siddharth Shah on 9/18/2015.
 */
public class UserLocalStore
{
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(User user)
    {
        /*System.out.println("Before Shared Preference is called : " + " " + user.name  + " " + user.email  + " " +  user.pass  + " " +  user.age);
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("uname", user.email);
        spEditor.putString("pass", user.pass);
        spEditor.putInt("age", user.age);
        spEditor.commit();
        System.out.println("After commit : " + " " + user.name + " " + user.email + " " + user.pass + " " + user.age);
        */
        Log.d("UserLocalStore", user.name + user.email + user.password + user.phone_number + user.dob + user.sex);
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putString("email", user.email);
        spEditor.putString("password", user.password);
        spEditor.putString("dob", user.dob);
        spEditor.putString("phone_number", user.phone_number);
        spEditor.putString("sex", user.sex);
        spEditor.commit();
    }

    public User getLoggedInUser()
    {
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");
        String pass = userLocalDatabase.getString("password", "");
        String dob = userLocalDatabase.getString("dob", "");
        String phone = userLocalDatabase.getString("phone_number", "");
        String sex = userLocalDatabase.getString("sex", "");

        User loggedInUser = new User(name, email, pass, dob, phone, sex);

        return loggedInUser;
    }

    public void setUserLoggedIn(boolean flag)
    {
        System.out.println("Entered setUserLoggedIn"/* + " " +userLocalDatabase.getString("name", "")+ " " +userLocalDatabase.getString("uname", "")+ " " +userLocalDatabase.getString("pass", "")+ " " +userLocalDatabase.getString("age", "")*/);
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", flag);
        spEditor.commit();
    }

    public void clearUserData()
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
    }

    public boolean getUserLoggedIn()
    {
        if(userLocalDatabase.getBoolean("loggedIn", false) == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}