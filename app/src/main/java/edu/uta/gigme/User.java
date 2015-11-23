package edu.uta.gigme;

/**
 * Created by Siddharth Shah on 9/18/2015.
 */
public class User
{
    String name, email, password, dob, phone_number, sex;

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, String dob, String phone_number, String sex)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.phone_number = phone_number;
        this.sex = sex;
    }

    /*String name, uname, pass;
    int age;

    public User(String name, String uname, String pass, int age)
    {
        this.name=name;
        this.uname=uname;
        this.pass=pass;
        this.age=age;
    }

    public User(String uname, String pass)
    {
        this.uname=uname;
        this.pass=pass;
        name="";
        age = -1;
    }
    */
}
