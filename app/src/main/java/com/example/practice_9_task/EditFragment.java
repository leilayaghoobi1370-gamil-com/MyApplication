package com.example.practice_9_task;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.practice_9_task.Model.Model;
import com.example.practice_9_task.Repository.Repositroy;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends DialogFragment {
    public static final String KEY_MODEL = "key_model";
    public static final String KEY_NAME = "tab name";
    public static final String EDIT_TRUE = "edit_true";
    public static final int REQUEST_CODE_EDIT_FRAGMENT = 1;
    public static final String DATA_SAVE_INSTANCE = "data_saveInstance";
    public static final String TIME_SAVE_INSTANCE = "time_saveInstance";
    public static final String FLAG_VISIBLE = " com.example.practice_9_task_FlagVisible";
    public static final String FLAG_VISIBLE1 = "Flag_Visible";
    public static final String TABNAME_SPRINER = "com.example.practice_9_task_TABNAME_SPRINER";
    public static final String PASWORD_EDIT_FRAGMENT = "com.example.practice_9_task_TABNAME_pasword";
    EditText mEditTextdescrition, mEditTextTitle;
    Button mBtnDate, mBtnTime, mBtnSave, mBtnCancle, mBtnEdit,mBtnDelete;
    CheckBox mCheckBox;
    Spinner mSpinner;
    int FlagVisible = 0;
    View mView;
    String mSpinnername;
    Model mModel;
    private String strHrsToShow;
    private String strMin;
    private String am_pm;
    private String dateFormat;
    String saveTime, saveDate;

    public static EditFragment newInstance(Model model, String name,String pasword) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_MODEL, model);
        args.putString(KEY_NAME, name);
        args.putString(PASWORD_EDIT_FRAGMENT,pasword);
        EditFragment fragment = new EditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = (Model) getArguments().getSerializable(KEY_MODEL);
        if (savedInstanceState != null) {
            saveDate = savedInstanceState.getString(DATA_SAVE_INSTANCE);
            saveTime = savedInstanceState.getString(TIME_SAVE_INSTANCE);
            FlagVisible = savedInstanceState.getInt(FLAG_VISIBLE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_edit, container, false);

        init();
        lisener();
        mBtnDate.setText(mModel.getDate());
        mBtnTime.setText(mModel.getTime());
        mCheckBox.setChecked(mModel.ismStatebool());
        mSpinner.setSelection(mModel.getMstate());
        mEditTextTitle.setText(mModel.getTitle());
        mEditTextdescrition.setText(mModel.getDescription());


        return mView;
    }

    private void lisener() {

        mBtnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                FlagVisible = 0;
            }
        });
        mBtnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeFragment timeFragment = new TimeFragment();
                timeFragment.setTargetFragment(EditFragment.this, InsertUpdateFragment.REQUEST_CODE_TIMEFRAGMENT);
                timeFragment.show(getFragmentManager(), InsertUpdateFragment.TAG_TIMEFRAMENT);

            }
        });
        mBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateFragmetn dateFragmetn = DateFragmetn.newInstance();
                dateFragmetn.setTargetFragment(EditFragment.this, InsertUpdateFragment.REQUEST_CODE_datefragment);
                dateFragmetn.show(getFragmentManager(), InsertUpdateFragment.TAG_DATEFRAGMENT);
            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSpinnername = mSpinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBtnDate.setEnabled(true);
                mBtnTime.setEnabled(true);
                mCheckBox.setEnabled(true);
                mSpinner.setEnabled(true);
                mEditTextTitle.setEnabled(true);
                mEditTextdescrition.setEnabled(true);
                FlagVisible = 0;


            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(mEditTextTitle.getText().toString().matches("") && mEditTextdescrition.getText().toString().matches("") &&
                        mBtnDate.getText().toString().matches("") && (mBtnTime.getText().toString().matches("")))) {
                    Model model = getModel();
                    Repositroy.newInstance(getContext()).replace(getArguments().getString(KEY_NAME), getArguments().getString(PASWORD_EDIT_FRAGMENT), mModel, model);
                    FlagVisible = 1;
                    Intent intent = new Intent();
                    intent.putExtra(EDIT_TRUE, true);
                    intent.putExtra(FLAG_VISIBLE, FlagVisible);
                    intent.putExtra(TABNAME_SPRINER, mSpinnername);
                    Fragment fragment = getTargetFragment();
                    fragment.onActivityResult(REQUEST_CODE_EDIT_FRAGMENT, Activity.RESULT_OK, intent);
                    dismiss();

                }
            }
        });
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model model = getModel();
                if (Repositroy.newInstance(getContext()).delete(((Model) getArguments().getSerializable(KEY_MODEL)), getArguments().getString(KEY_NAME))) {
                    Intent intent = new Intent();
                    intent.putExtra(EDIT_TRUE, true);
                    intent.putExtra(FLAG_VISIBLE, FlagVisible);
                    intent.putExtra(TABNAME_SPRINER, mSpinnername);
                    Fragment fragment = getTargetFragment();
                    fragment.onActivityResult(REQUEST_CODE_EDIT_FRAGMENT, Activity.RESULT_OK, intent);
                    dismiss();


                } else {
                    Snackbar snackbar = Snackbar
                            .make(mView, "UnSuccessFull Delete", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    dismiss();

                }
            }
        });
    }

    private Model getModel() {
        Model model = new Model();
        model.setTitle(mEditTextTitle.getText().toString());
        model.setDescription(mEditTextdescrition.getText().toString());
        model.setDate(mBtnDate.getText().toString());
        model.setTime(mBtnTime.getText().toString());
        model.setmStatebool(mCheckBox.isChecked());
        model.setMstate(mSpinner.getSelectedItemPosition());
        return model;
    }

    private void init() {
        mEditTextTitle = mView.findViewById(R.id.edit_Insert_Title);
        mEditTextdescrition = mView.findViewById(R.id.edit_Insert_Description);
        mBtnCancle = mView.findViewById(R.id.edit_btn_cancel);
        mBtnSave = mView.findViewById(R.id.edit_btn_save);
        mBtnEdit = mView.findViewById(R.id.edit_btn_edit);
        mBtnDate = mView.findViewById(R.id.edit_btn_data);
        mBtnTime = mView.findViewById(R.id.edit_btn_time);
        mCheckBox = mView.findViewById(R.id.edit_check_insert);
        mSpinner = mView.findViewById(R.id.edit_spinner);
        mBtnDelete=mView.findViewById(R.id.Delete);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mBtnDate.setText(mModel.getDate());
        mBtnTime.setText(mModel.getTime());
        mCheckBox.setChecked(mModel.ismStatebool());
        mSpinner.setSelection(mModel.getMstate());
        mEditTextTitle.setText(mModel.getTitle());
        mEditTextdescrition.setText(mModel.getDescription());
        mBtnDate.setEnabled(false);
        mBtnTime.setEnabled(false);
        mCheckBox.setEnabled(false);
        mSpinner.setEnabled(false);
        mEditTextTitle.setEnabled(false);
        mEditTextdescrition.setEnabled(false);
        if (saveTime != null) {
            mBtnDate.setText(saveDate);
            mBtnTime.setText(saveTime);
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

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATA_SAVE_INSTANCE, mBtnDate.getText().toString());
        outState.putString(TIME_SAVE_INSTANCE, mBtnTime.getText().toString());
        outState.putInt(FLAG_VISIBLE1, FlagVisible);
    }
}
