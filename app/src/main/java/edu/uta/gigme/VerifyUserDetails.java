package edu.uta.gigme;

import android.util.Log;


/**
 * Created by Siddharth Shah on 11/21/2015.
 */
public class VerifyUserDetails
{
    String name, email, password;
    String age;

    String verificationResult = "";

    public VerifyUserDetails(String email, String password)
    {
        this.email=email;
        this.password=password;

        verifyIfEmptyOnLogin();
        verifyEmail();
        verifyPassword();
    }

    public VerifyUserDetails(String name, String email, String password, String age)
    {
        this.name=name;
        this.email=email;
        this.password=password;
        this.age = age;

        verifyIfEmptyOnRegister();
        verifyName();
        verifyEmail();
        verifyPassword();
        //verifyAge();
    }

    public void verifyIfEmptyOnLogin()
    {
        if(email.equals("") || password.equals("") /*|| age.toString()==null || name.toString()==null*/)
        {
            verificationResult += "Please enter all fields \n";
        }

        //return verificationResult;
    }

    public void verifyIfEmptyOnRegister()
    {
        if(email.equals("") || password.equals("") || name.equals(""))
        {
            verificationResult += "Please enter all fields \n";
        }
    }

    public void verifyName()
    {
        //Pattern pattern = Pattern.compile("^\\p{L}+$");
        //Matcher matcher = pattern.matcher(name);

        if (!name.matches("^[a-zA-Z]+$"))
        {
            verificationResult += "Name should contain only letters \n";
        }
    }

    public void verifyEmail()
    {

        if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"))
        {
            Log.d("BHENCHOD", email);
            verificationResult += "Please enter email in correct format i.e. a@b.c \n";
        }
    }

    public void verifyPassword()
    {
        if (password.length() < 5)
        {
            verificationResult += "Password should be at leaast 5 charachters long \n";
        }
    }

    public void verifyAge()
    {
        if (age.equals(""))
        {
            verificationResult += "Enter a valid Age \n";
        }

        else if(Integer.parseInt(age) <= 0)
        {
            verificationResult += "Enter a valid Age \n";
        }
    }

    boolean verify()
    {
        boolean flag = true;

        if (!verificationResult.equalsIgnoreCase(""))
        {
            flag = false;
        }

        return flag;
    }
}