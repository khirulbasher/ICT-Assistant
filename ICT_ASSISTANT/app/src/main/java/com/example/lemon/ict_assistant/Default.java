package com.example.lemon.ict_assistant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by lemon on 12/14/2016.
 */

public class Default extends Fragment {

    private LinearLayout number_system,mathematics;
    private Callback callback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_default,container,false);
        number_system= (LinearLayout) view.findViewById(R.id.number_system);
        mathematics= (LinearLayout) view.findViewById(R.id.math);
        number_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onNumberSystem();
            }
        });

        mathematics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMath();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback =(Callback)context;
    }
}
