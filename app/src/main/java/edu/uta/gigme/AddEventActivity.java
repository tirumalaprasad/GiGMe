package edu.uta.gigme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btnFromDate, btnFromTime, btnToDate, btnToTime;
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
        btnFromDate.setOnClickListener(this);
        btnFromTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_set_from_date:
                Calendar calendar = Calendar.getInstance();
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
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
                break;
            case R.id.btn_set_from_time:
                Calendar calendar2 = Calendar.getInstance();
                int mHour = calendar2.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar2.get(Calendar.MINUTE);
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
        }
    }
}
