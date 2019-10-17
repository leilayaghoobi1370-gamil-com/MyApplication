package com.example.practice_9_task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.resources.R;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    public  static Intent newintent(Context context)
    {
        Intent intent=new Intent(context, LoginActivity.class);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FragmentManager fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.contianer_login_sign,LoginFragment.newInstance()).commit();
    }

}
