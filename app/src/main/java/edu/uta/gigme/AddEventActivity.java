package edu.uta.gigme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnFromDate, btnFromTime, btnToDate, btnToTime, btnCreateEvent;
    EditText etEventName, etEventAddress, etEventPhone, etEventCharge;
    Spinner spBeverage, spFood, spCity, spGenre;
    CheckBox cbPrivate, cbAge;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnFromDate = (Button) findViewById(R.id.btn_set_from_date);
        btnFromTime = (Button) findViewById(R.id.btn_set_from_time);
        btnToDate = (Button) findViewById(R.id.btn_set_to_date);
        btnToTime = (Button) findViewById(R.id.btn_set_to_time);
        btnCreateEvent = (Button) findViewById(R.id.btn_create_event);
        etEventName = (EditText) findViewById(R.id.etEventName);
        etEventAddress = (EditText) findViewById(R.id.etEventAddress);
        etEventPhone = (EditText) findViewById(R.id.etEventHostPhoneNumber);
        etEventCharge = (EditText) findViewById(R.id.etEventCoverCharge);
        spBeverage = (Spinner) findViewById(R.id.spnr_beverage);
        spFood = (Spinner) findViewById(R.id.spnr_type_of_food);
        spCity = (Spinner) findViewById(R.id.spnr_city);
        spGenre = (Spinner) findViewById(R.id.spnr_genre);
        cbPrivate = (CheckBox) findViewById(R.id.cb_private);
        cbAge = (CheckBox) findViewById(R.id.cb_minAge);
        btnFromDate.setOnClickListener(this);
        btnFromTime.setOnClickListener(this);
        btnToDate.setOnClickListener(this);
        btnToTime.setOnClickListener(this);
        btnToDate.setEnabled(false);
        btnCreateEvent.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
    }
    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();

        switch (v.getId()){
            case R.id.btn_set_from_date:

                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                btnFromDate.setText(monthOfYear + 1 + "/" + dayOfMonth + "/" + year);
                                btnFromDate.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                btnFromDate.setTextColor(getResources().getColor(R.color.colorClouds));
                                btnToDate.setEnabled(true);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_set_from_time:
                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                btnFromTime.setText(hourOfDay + ":" +minute);
                                btnFromTime.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                btnFromTime.setTextColor(getResources().getColor(R.color.colorClouds));

                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
                break;
            case R.id.btn_set_to_date:
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(
                        AddEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                btnToDate.setText(monthOfYear + 1 + "/" + dayOfMonth + "/" + year);
                                btnToDate.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                btnToDate.setTextColor(getResources().getColor(R.color.colorClouds));
                            }
                        }, mYear, mMonth, mDay);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                try {
                    Date date = simpleDateFormat.parse(btnFromDate.getText().toString());
                    datePickerDialog.getDatePicker().setMinDate(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                datePickerDialog.show();
                break;
            case R.id.btn_set_to_time:
                mHour = calendar.get(Calendar.HOUR_OF_DAY);
                mMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(
                        AddEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                btnToTime.setText(hourOfDay + ":" +minute);
                                btnToTime.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                btnToTime.setTextColor(getResources().getColor(R.color.colorClouds));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
                break;
            case R.id.btn_create_event:
                String eventName = etEventName.getText().toString();
                String eventAddress = etEventAddress.getText().toString();
                String eventPhone = etEventPhone.getText().toString();
                System.out.println("eventPhone========"+eventPhone);
                String eventCharge = etEventCharge.getText().toString();
                String eventBeverage = spBeverage.getSelectedItem().toString();
                String eventFood = spFood.getSelectedItem().toString();
                String eventCity = spCity.getSelectedItem().toString();
                String eventGenre = spGenre.getSelectedItem().toString();
                String eventPrivate, eventAge;
                if (cbPrivate.isChecked()){
                    eventPrivate = "private";
                }else{
                    eventPrivate = "public";
                }
                if(cbAge.isChecked()){
                    eventAge = "1"; // open only for above 21
                }else{
                    eventAge = "0"; // open for all
                }

                String eventFromDate = btnFromDate.getText().toString();
                String eventFromTime = btnFromTime.getText().toString();
                String eventToDate = btnToDate.getText().toString();
                String eventToTime = btnToTime.getText().toString();
                User user = userLocalStore.getLoggedInUser();

                VerifyEventDetails verifyEventDetails = new VerifyEventDetails(eventName,
                        eventAddress, eventPhone, eventCharge);
                if (verifyEventDetails.verify()){
                    Event createdEvent = new Event(user.email, eventName, eventAddress, eventPhone,
                            eventCharge, eventBeverage, eventFood, eventCity, eventGenre,
                            eventPrivate, eventAge, eventFromDate, eventFromTime, eventToDate,
                            eventToTime);

                    createEvent(createdEvent);
                }else{
                    Toast.makeText(AddEventActivity.this, verifyEventDetails.verificationResult, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void createEvent(Event createdEvent) {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.createEventInBackground(createdEvent, new GetEventCallBack() {
            @Override
            public void done(Event returnedEvents) {
                Toast.makeText(AddEventActivity.this, "Event has been successfully created", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddEventActivity.this, AllEventsActivity.class));
            }
        }, AddEventActivity.this);
    }
}
