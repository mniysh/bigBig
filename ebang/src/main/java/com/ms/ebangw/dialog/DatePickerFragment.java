package com.ms.ebangw.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.ms.ebangw.utils.L;

import java.util.Calendar;

public  class DatePickerFragment extends DialogFragment
                            implements DatePickerDialog.OnDateSetListener {

    private Calendar cal = Calendar.getInstance();



    private DatePickerDialog.OnDateSetListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        L.d("year:" + year + " month:" + month + " day:" + day);

        if (null != listener) {
            listener.onDateSet(view, year, month, day);
        }
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }
}