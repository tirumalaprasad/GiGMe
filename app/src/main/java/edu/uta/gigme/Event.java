package edu.uta.gigme;

/**
 * Created by Siddharth Shah on 11/1/2015.
 */
public class Event
{
    //String eventId;
    String eventName;
    String emailId;
    String genre;
    String address;
    String phoneNumber;

    public Event(String eventName, String emailId, String genre, String address, String phoneNumber)
    {
        this.eventName = eventName;
        this.emailId = emailId;
        this.genre = genre;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
