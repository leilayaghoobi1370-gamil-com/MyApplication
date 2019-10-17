package com.example.practice_9_task;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class VeiwFragment extends Fragment {

    public static VeiwFragment newInstance() {
        
        Bundle args = new Bundle();
        
        VeiwFragment fragment = new VeiwFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public VeiwFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_veiw, container, false);
    }


}
