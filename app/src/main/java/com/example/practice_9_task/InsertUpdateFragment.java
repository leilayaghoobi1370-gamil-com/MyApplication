package com.example.practice_9_task;


import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.practice_9_task.Repository.Repositroy;


/**
 * A simple {@link Fragment} subclass.
 */
public class InsertUpdateFragment extends DialogFragment {
    public static final String SRTNG_CHECH_BOX = "sting_check box";
    public static final String KEYNAME = "key";
    public static final int REQUEST_CODE_datefragment = 3;
    public static final String TAG_DATEFRAGMENT = "datefragment";
    public static final int REQUEST_CODE_TIMEFRAGMENT = 4;
    public static final String TAG_TIMEFRAMENT = "TIMEFRAMENT";
    public static final int REQUEST_CODE_INSERTFRAGMEETN = 1;
    public static final String DATA_SAVE_INSTANCE = "data_saveInstance";
    public static final String TIME_SAVE_INSTANCE = "time_saveInstance";
    public static final String FLAG_VISIBLE = "com.example.practice_9_task.Repository.Repositroy_FlagVisible";
    public static final String TABNAME_SPRINER = "com.example.practice_9_task.Repository.Repositroy_TABNAME_SPRINER";
    public static final String INSERTUBDDATE = "com.example.practice_9_task.Repository.Repositroy_insertubddate";
    public static final int REQUEST_CODE = 1;
    public static final String KEYPASSWORD="com.example.practice_9_task.Repository.Repositroy_insertubddate+PASSWORD";
    int FlagVisible = 0;
    View mView, view, viewdate;
    EditText mEditTextdescrition, mEditTextTitle;
    Button mBtnDate, mBtnTime, mBtnSave, mBtnCancle, mBtnEdit;
    CheckBox mCheckBox;
    boolean mCheckBoxbtnchekebox;
    String namecheckbox = "";
    AlertDialog time, date;
    Spinner mSpinner;
    int mSpinnername = 0;

    TimePicker timePicker;
    EditText mTitlelayout, mDescriptionlayout;
    String mTitle, mDescription, mTimeBtnTime, mDateBtnDate, saveTime, saveDate;
    private String strHrsToShow;
    private String strMin;
    private String am_pm;
    private String dateFormat;

    public static InsertUpdateFragment newInstance(String tabname, String NAME,String PASSWORD) {

        Bundle args = new Bundle();
        args.putString(SRTNG_CHECH_BOX, tabname);
        args.putString(KEYNAME, NAME);
        args.putString(KEYPASSWORD, PASSWORD);
        InsertUpdateFragment fragment = new InsertUpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public InsertUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        namecheckbox = getArguments().getString(SRTNG_CHECH_BOX);
        if (savedInstanceState != null) {
            saveDate = savedInstanceState.getString(DATA_SAVE_INSTANCE);
            saveTime = savedInstanceState.getString(TIME_SAVE_INSTANCE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_insert_update, container, false);

        init();
        lisener();

        return mView;
    }

    private void lisener() {
        mBtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeFragment timeFragment = new TimeFragment();
                timeFragment.setTargetFragment(InsertUpdateFragment.this, REQUEST_CODE_TIMEFRAGMENT);
                timeFragment.show(getFragmentManager(), TAG_TIMEFRAMENT);

            }
        });
        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateFragmetn dateFragmetn = DateFragmetn.newInstance();
                dateFragmetn.setTargetFragment(InsertUpdateFragment.this, REQUEST_CODE_datefragment);
                dateFragmetn.show(getFragmentManager(), TAG_DATEFRAGMENT);
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getresult())
                    return;
                else {
                    Repositroy.newInstance(getContext()).add(getArguments().getString(KEYNAME), getArguments().getString(KEYPASSWORD), mTitle
                            , mDescription, mCheckBoxbtnchekebox, mDateBtnDate, mTimeBtnTime, mSpinnername);

                    String name;
                    if (mSpinnername == 0)
                        name = "TODO";
                    else if (mSpinnername == 1)
                        name = "DOING";
                    else
                        name = "DONE";
                    Intent intent = new Intent();
                    intent.putExtra(FLAG_VISIBLE, FlagVisible);
                    intent.putExtra(TABNAME_SPRINER, name);
                    getTargetFragment().onActivityResult(REQUEST_CODE_INSERTFRAGMEETN, Activity.RESULT_OK, intent);
                    dismiss();

                }

            }
        });
        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSpinnername = mSpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void init() {
        mBtnCancle = mView.findViewById(R.id.btn_cancel);
        mBtnSave = mView.findViewById(R.id.btn_save);
        mBtnEdit = mView.findViewById(R.id.btn_edit);
        mBtnDate = mView.findViewById(R.id.btn_data);
        mBtnTime = mView.findViewById(R.id.btn_time);
        mCheckBox = mView.findViewById(R.id.check_insert);
        mTitlelayout = mView.findViewById(R.id.Insert_Title);
        mDescriptionlayout = mView.findViewById(R.id.Insert_Description);
        mBtnEdit.setVisibility(View.GONE);
        mSpinner = mView.findViewById(R.id.spinner);
        if (saveTime != null) {
            mBtnDate.setText(saveDate);
            mBtnTime.setText(saveTime);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

    }


    private boolean getresult() {
        if (mTitlelayout.getText().toString().matches("") || mDescriptionlayout.getText().toString().matches("")
                || mBtnTime.getText().toString().matches("") || mBtnDate.getText().toString().matches("")) {
            return false;
        } else {
            mTitle = mTitlelayout.getText().toString();
            mDescription = mDescriptionlayout.getText().toString();
            mDateBtnDate = mBtnDate.getText().toString();
            mTimeBtnTime = mBtnTime.getText().toString();
            mCheckBoxbtnchekebox = mCheckBox.isChecked();
            mBtnTime.setText(strHrsToShow + ":" + strMin + " " + am_pm);

            return true;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }
        if (requestCode == DateFragmetn.REQUEST_CODE_date) {
            dateFormat = data.getStringExtra(DateFragmetn.DATEFORMANT);
            mBtnDate.setText(dateFormat);
        }
        if (requestCode == TimeFragment.REQUEST_CODE_time) {
            strHrsToShow = data.getStringExtra(TimeFragment.SETHOURS);
            strMin = data.getStringExtra(TimeFragment.STR_MIN);
            am_pm = data.getStringExtra(TimeFragment.AM_PM);
            mBtnTime.setText(strHrsToShow + ":" + strMin + " " + am_pm);

        }
        Intent intent = new Intent();
        intent.putExtra(INSERTUBDDATE, true);
        getTargetFragment().onActivityResult(REQUEST_CODE, Activity.RESULT_OK, intent);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATA_SAVE_INSTANCE, mBtnDate.getText().toString());
        outState.putString(TIME_SAVE_INSTANCE, mBtnTime.getText().toString());


    }
}
