package edu.uta.gigme;

import android.util.Log;


/**
 * Created by Siddharth Shah on 11/21/2015.
 */
public class VerifyUserDetails
{
    String name, email, password, repassword, phone_number;

    String verificationResult = "";

    public VerifyUserDetails(String email, String password)
    {
        this.email=email;
        this.password=password;

        verifyIfEmptyOnLogin();
        verifyEmail();
        verifyPassword();
    }

    public VerifyUserDetails(String name, String email, String password, String repassword, String phone_number)
    {
        this.name=name;
        this.email=email;
        this.password=password;
        this.repassword = repassword;
        this.phone_number = phone_number;

        verifyIfEmptyOnRegister();
        verifyName();
        verifyEmail();
        verifyPassword();
        verifyPasswords();
        verifyPhone();
        verify();
    }

    private void verifyPhone()
    {
        if (phone_number.length() != 10 && !phone_number.equals(""))
        {
            verificationResult += "Invalid phone number";
        }
    }

    public void verifyIfEmptyOnLogin()
    {
        if(email.equals("") || password.equals("") /*|| age.toString()==null || name.toString()==null*/)
        {
            verificationResult += "Please enter all fields \n";
        }
    }

    public void verifyIfEmptyOnRegister()
    {
        if(email.equals("") || password.equals("") || name.equals(""))
        {
            verificationResult += "Please enter all fields \n";
        }
    }

    public void verifyPasswords()
    {
        if (!password.equals(repassword))
        {
            verificationResult += "Entered passwords should match";
        }
    }

    public void verifyName()
    {
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