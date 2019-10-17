package com.example.practice_9_task;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.resources.R;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practice_9_task.Repository.Repositroy;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    public static final int REQES_CODE_LOGINFRAGMENT = 1;
    public static final String TAG_SINNFRAGMENT = "TAG_SINNFRAGMENT";
    public static final int REQUEST_CODE_LOGINFRAGMENT = 1;
    public static final String ITEMFARAGMENSHOW = "itemfaragmenshow";
    Button mButton_login, mButton_sign;
    TextView mTextname, mTextpassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mButton_login = view.findViewById(R.id.btn_login);
        mButton_sign = view.findViewById(R.id.btn_sign);
        mTextname = view.findViewById(R.id.name_login1);
        mTextpassword = view.findViewById(R.id.password_login1);
        init();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != Activity.RESULT_OK || data == null) {
            Toast.makeText(getActivity(), data.getStringExtra(SignFragment.SET_ARMON_SIGNFRAGMENT_PASSWORD), Toast.LENGTH_SHORT).show();

            return;
        } else {
            mTextname.setText(data.getStringExtra(SignFragment.SET_ARMON_SIGNFRAGMENT_NAME));
            mTextpassword.setText(data.getStringExtra(SignFragment.SET_ARMON_SIGNFRAGMENT_PASSWORD));

        }

    }

    private void init() {
        mButton_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignFragment signFragment = SignFragment.newInstance();
                signFragment.setTargetFragment(LoginFragment.this, REQES_CODE_LOGINFRAGMENT);
                signFragment.show(getFragmentManager(), TAG_SINNFRAGMENT);
            }
        });
        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTextname.getText().toString().matches("") || mTextpassword.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "EMPTY", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Repositroy.newInstance(getContext()).find(mTextname.getText().toString(), mTextpassword.getText().toString())) {
                    Intent intent = ListTaskActivity.newIntet(getActivity(),
                            mTextname.getText().toString(), mTextpassword.getText().toString(),"");
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "password or username incorrect", Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });

    }

}
