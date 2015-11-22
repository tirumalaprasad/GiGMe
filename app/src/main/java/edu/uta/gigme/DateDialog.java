package edu.uta.gigme;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Tirumala on 11/18/2015.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText textDate;
    public DateDialog(View view){
        textDate = (EditText)view;
    }

    public Dialog onCreDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this, year,month,date);
    }

    public void onDateSet(DatePicker view,int year,int month,int day){
        String date=day+"-"+(month+1)+"-"+year;
        textDate.setText(date);
    }

}
