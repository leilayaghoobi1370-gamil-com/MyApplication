package com.example.practice_9_task;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragmetn extends DialogFragment {

    public static final String DATEFORMANT = "com.example.practice_9_task_dateformant";
    public static final int REQUEST_CODE_date = 7;
    DatePicker mDatePicker;
    View view,mView;

    public static DateFragmetn newInstance() {

        Bundle args = new Bundle();

        DateFragmetn fragment = new DateFragmetn();
        fragment.setArguments(args);
        return fragment;
    }


    public DateFragmetn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_date_fragmetn, container, false);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        return date();
    }

    private AlertDialog date() {

        mView= LayoutInflater.from(getContext()).inflate(R.layout.fragment_date_fragmetn, null, false);
        return new AlertDialog.Builder(getActivity()).setTitle("DATE")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatePicker = mView.findViewById(R.id.layout_date);
                        int year = mDatePicker.getYear();
                        int monthOfYear = mDatePicker.getMonth();
                        int dayOfMonth = mDatePicker.getDayOfMonth();
                        GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        Date date = calendar.getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat(" MMM dd yyyy");
                        Intent intent = new Intent();
                        intent.putExtra(DATEFORMANT,  dateFormat.format(date));
                        getTargetFragment().onActivityResult(REQUEST_CODE_date, Activity.RESULT_OK, intent);

                    }
                }).setView(mView).create();

    }

}
