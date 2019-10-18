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
import android.widget.TextView;

import com.example.practice_9_task.Repository.Repositroy;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignFragment extends DialogFragment {
    public static final String SET_ARMON_SIGNFRAGMENT_NAME = "SET_ARMON_SIGNFRAGMENT_name";
    public static final String SET_ARMON_SIGNFRAGMENT_PASSWORD = "SET_ARMON_SIGNFRAGMENT_password";
    Button msign;
    View view;
    TextView mTextname, mTextpassword;

    public static SignFragment newInstance() {

        Bundle args = new Bundle();

        SignFragment fragment = new SignFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        view = layoutInflater.inflate(R.layout.fragment_sign, null, false);
        msign = view.findViewById(R.id.btn_sign_sign);
        mTextname = view.findViewById(R.id.name_login);
        mTextpassword = view.findViewById(R.id.password_login);
        setlisener();
        return new AlertDialog.Builder(getActivity()).setTitle("Sign").setView(view)
                .setPositiveButton("Cencle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .setIcon(R.drawable.ic_account_circle_black_24dp)
                .create();
    }

    private void setlisener() {
        msign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTextname.getText().toString().matches("") || mTextpassword.getText().toString().matches("")) {
                    return;

                } else {
                    Repositroy.newInstance(getContext()).addAcount(mTextname.getText().toString(), mTextpassword.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra(SET_ARMON_SIGNFRAGMENT_NAME, mTextname.getText().toString());
                    intent.putExtra(SET_ARMON_SIGNFRAGMENT_PASSWORD, mTextpassword.getText().toString());
                    getTargetFragment().onActivityResult(Activity.RESULT_OK, getTargetRequestCode(), intent);

                }
                dismiss();

            }
        });
    }
}
