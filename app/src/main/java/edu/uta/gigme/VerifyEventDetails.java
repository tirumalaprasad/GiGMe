package edu.uta.gigme;

/**
 * Created by Pavan on 25/11/2015.
 */
public class VerifyEventDetails {
    String eventName, eventAddress, eventPhone, eventCharge;
    String verificationResult ="";

    public VerifyEventDetails(String eventName, String eventAddress, String eventPhone, String eventCharge) {
        this.eventName = eventName;
        this.eventAddress = eventAddress;
        this.eventPhone = eventPhone;
        this.eventCharge = eventCharge;
        verify();
        verifyEmpty();
    }

    public void verifyEmpty(){
        if(eventName.equalsIgnoreCase("")){
            verificationResult = "Event name can't be empty";
        }
        else if (eventAddress.equalsIgnoreCase("")){
            verificationResult = "Event address can't be empty";
        }
        else if (eventPhone.equalsIgnoreCase("") || eventPhone.length() != 10){
            verificationResult = "Event phone number is invalid";
        }
        else if (eventCharge.equalsIgnoreCase("")){
            verificationResult = "Event charge can't be empty. Enter 0 for free events";
        }
    }
    public boolean verify()
    {
        boolean flag = true;

        if (!verificationResult.equalsIgnoreCase(""))
        {
            flag = false;
        }

        return flag;
    }
}
