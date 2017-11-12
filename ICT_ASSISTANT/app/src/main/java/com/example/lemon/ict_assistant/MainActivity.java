package com.example.lemon.ict_assistant;

import com.example.lemon.ict_assistant.library.converter.utility.ObjectPack;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.List;

public class MainActivity extends AppCompatActivity implements Callback {


    public static int precision=10;
    public static ObjectPack objectPack=null;
    private SharedPreferences preferences;
    private static final String PREFERENCES="PREFERENCES";
    public static boolean convertActivate=false;
    private LayoutInflater inflater;
    public static int decimal_precision=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences("pref",MODE_PRIVATE);
        precision=preferences.getInt(PREFERENCES,precision);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new Default()).commit();

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_about:
                showAbout();
                return true;
            case R.id.action_exit:
                finish();
                return true;
            case R.id.action_setPrecision:
                setPrecision();
                return true;
            case R.id.action_usage:
                showUsage();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUsage() {

    }

    private void setPrecision() {
        final Dialog dialog=new Dialog(this);
        dialog.setTitle(getString(R.string.set_your_fractional_precision));
        dialog.setContentView(R.layout.dialog_set_precision);
        final SeekBar seekBar= (SeekBar) dialog.findViewById(R.id.seekBar_dialog);
        Button okBtn= (Button) dialog.findViewById(R.id.ok_button);
        Button cancelBtn= (Button) dialog.findViewById(R.id.cancel_button);
        final TextView percentage= (TextView) dialog.findViewById(R.id.percentage);
        seekBar.setProgress(precision);
        percentage.setText(""+precision);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentage.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                precision=seekBar.getProgress();
                SharedPreferences.Editor editor=preferences.edit();
                editor.putInt(PREFERENCES,precision);
                editor.apply();
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showAbout() {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCancelable(true);
        dialog.setTitle("Developer's Dedication");
        dialog.show();
    }


    @Override
    public void onDetails(ObjectPack pack) {

        try {
            objectPack=pack;
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            DetailsFragment fragment=new DetailsFragment();
            Bundle bundle=new Bundle();
            bundle.putBoolean(DetailsFragment.ACTIVATE,true);
            fragment.setArguments(bundle);
            convertActivate=true;
            transaction.replace(R.id.fragment_container,fragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack("");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onMath() {
        try {
            Mathematic mathematic=new Mathematic();
            FragmentManager manager=getSupportFragmentManager();
            try {
                manager.popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.fragment_container,mathematic);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack("");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNumberSystem() {
        try {
            ConvertFragment convertFragment=new ConvertFragment();
            FragmentManager manager=getSupportFragmentManager();
            try {
                manager.popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.fragment_container,convertFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack("");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
