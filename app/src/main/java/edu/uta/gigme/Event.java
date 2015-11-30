package edu.uta.gigme;

import java.io.Serializable;

/**
 * Created by Siddharth Shah on 11/1/2015.
 */
public class Event implements Serializable
{

    int eventId;
    String eventName;
    String emailId;
    String genre;
    String address;
    String phoneNumber;
    String charge;
    String beverage, food, city, privateEvent, age, fromDate, fromTime, toDate, toTime;


    public Event(int eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public Event(String eventEmail, String eventName, String eventAddress, String eventPhone, String eventCharge, String eventBeverage, String eventFood, String eventCity, String eventGenre, String eventPrivate, String eventAge, String eventFromDate, String eventFromTime, String eventToDate, String eventToTime) {
        this.emailId = eventEmail;
        this.eventName = eventName;
        this.address = eventAddress;
        this.phoneNumber = eventPhone;
        this.charge = eventCharge;
        this.beverage = eventBeverage;
        this.food = eventFood;
        this.city = eventCity;
        this.genre = eventGenre;
        this.privateEvent = eventPrivate;
        this.age = eventAge;
        this.fromDate = eventFromDate;
        this.fromTime = eventFromTime;
        this.toDate = eventToDate;
        this.toTime = eventToTime;
    }

    public Event(int eventId, String eventName, String genre, String address, String phoneNumber, String charge, String beverage, String food, String city, String privateEvent, String age, String fromDate, String toDate, String fromTime, String toTime)
    {
        this.eventId = eventId;
        this.eventName = eventName;
        //this.emailId = emailId;
        this.genre = genre;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.charge = charge;
        this.beverage = beverage;
        this. food = food;
        this.city = city;
        this.privateEvent = privateEvent;
        this.age = age;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public Event()
    {

    }

    @Override
    public String toString()
    {
        return this.eventName;
    }

    public static Event findEventbyName(String s)
    {
        Event e = null;



        return e;
    }
}
