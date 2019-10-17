package com.example.practice_9_task;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.practice_9_task.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends DialogFragment {
    public static final String SETHOURS = " com.example.practice_9_task_sethours";
    public static final String STR_MIN = "com.example.practice_9_task_strMin";
    public static final int REQUEST_CODE_time = 6;
    public static final String AM_PM = "com.example.practice_9_task_am_pm";
    TimePicker mTimePicker;
    View view, mView;

    public static TimeFragment newInstance() {

        Bundle args = new Bundle();

        TimeFragment fragment = new TimeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public TimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time, container, false);
        mTimePicker = view.findViewById(R.id.time_layout1);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        return time();
    }

    private AlertDialog time() {
        mView= LayoutInflater.from(getContext()).inflate(R.layout.fragment_time, null, false);

        return new AlertDialog.Builder(getActivity())
                .setTitle("TIME")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mTimePicker = mView.findViewById(R.id.time_layout1);
                        final Calendar c = Calendar.getInstance();
                        int hour = mTimePicker.getCurrentHour();
                        int min = mTimePicker.getCurrentMinute();
                        settime(mTimePicker, hour, min);

                    }
                })
                .setView(mView)
                .create();
    }

    private void settime(TimePicker view, int hourOfDay, int minute) {
        String am_pm = "";
        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);
        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
        String strMin = (datetime.get(Calendar.MINUTE) < 9) ? "0" + datetime.get(Calendar.MINUTE) : Integer.toString(datetime.get(Calendar.MINUTE));
        Intent intent = new Intent();
        intent.putExtra(SETHOURS, strHrsToShow);
        intent.putExtra(STR_MIN, strMin);
        intent.putExtra(AM_PM,am_pm);
        getTargetFragment().onActivityResult(REQUEST_CODE_time, Activity.RESULT_OK, intent);

    }


}
