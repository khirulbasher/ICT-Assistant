package com.example.lemon.ict_assistant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lemon.ict_assistant.library.converter.utility.Check;
import com.example.lemon.ict_assistant.library.converter.utility.Convert;
import com.example.lemon.ict_assistant.library.converter.utility.Format;
import com.example.lemon.ict_assistant.library.converter.utility.ObjectPack;
import com.example.lemon.ict_assistant.library.converter.utility.ObjectPackDecimal;
import com.example.lemon.ict_assistant.library.converter.utility.ObjectPackDouble;
import com.example.lemon.ict_assistant.library.converter.utility.PatternException;

/**
 * Created by lemon on 12/14/2016.
 */

public class ConvertFragment extends Fragment {
    private RadioGroup fromGroup,toGroup;
    private int formatFromBase= Format.DEC;
    private int formatToBase=Format.DEC;
    private TextView from_text,from_flag,to_text,to_flag;
    private EditText input;
    private Button convert_button,details_button;
    private Convert convert;
    private Callback callback;
    private RadioButton fromBin,fromOct,fromDec,fromHex,toBin,toDec,toOct,toHex;
    private String correspondingDecimalValue="255";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_convert,container,false);
        fromGroup= (RadioGroup) view.findViewById(R.id.radio_group_from);
        toGroup= (RadioGroup) view.findViewById(R.id.radio_group_to);

        fromBin= (RadioButton) view.findViewById(R.id.from_bin);
        fromOct=(RadioButton) view.findViewById(R.id.from_oct);
        fromDec=(RadioButton) view.findViewById(R.id.from_dec);
        fromHex=(RadioButton) view.findViewById(R.id.from_hex);

        toBin= (RadioButton) view.findViewById(R.id.to_bin);
        toOct=(RadioButton) view.findViewById(R.id.to_oct);
        toDec=(RadioButton) view.findViewById(R.id.to_dec);
        toHex=(RadioButton) view.findViewById(R.id.to_hex);


        fromGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    switch (checkedId){
                        case R.id.from_bin:
                            formatFromBase=Format.BIN;
                            from_flag.setText("BIN:");
                            break;
                        case R.id.from_oct:
                            formatFromBase=Format.OCT;
                            from_flag.setText("OCT:");
                            break;
                        case R.id.from_dec:
                            formatFromBase=Format.DEC;
                            from_flag.setText("DEC:");
                            break;
                        case R.id.from_hex:
                            formatFromBase=Format.HEX;
                            from_flag.setText("HEX:");
                            break;
                    }

                    if(formatFromBase!=Format.DEC){
                        ObjectPackDouble packDouble=convert.dec_another(Double.parseDouble(correspondingDecimalValue),formatFromBase,MainActivity.precision);
                        from_text.setText(""+packDouble.combine_part);
                    }
                    else
                        from_text.setText(correspondingDecimalValue);

                } catch (NumberFormatException e) {
                    showError();
                }
            }
        });

        toGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    switch (checkedId){
                        case R.id.to_bin:
                            formatToBase=Format.BIN;
                            to_flag.setText("BIN:");
                            break;
                        case R.id.to_oct:
                            formatToBase=Format.OCT;
                            to_flag.setText("OCT:");
                            break;
                        case R.id.to_dec:
                            formatToBase=Format.DEC;
                            to_flag.setText("DEC:");
                            break;
                        case R.id.to_hex:
                            formatToBase=Format.HEX;
                            to_flag.setText("HEX:");
                            break;
                    }
                    if(formatToBase!=Format.DEC) {
                        ObjectPackDouble packDouble = convert.dec_another(Double.parseDouble(correspondingDecimalValue), formatToBase, MainActivity.precision);
                        to_text.setText("" + packDouble.combine_part);
                    }
                    else
                        to_text.setText(""+correspondingDecimalValue);
                } catch (NumberFormatException e) {
                    showError();
                }
            }
        });
        convert=new Convert();
        from_text= (TextView) view.findViewById(R.id.from_tex);
        from_flag= (TextView) view.findViewById(R.id.from_flag);
        to_flag= (TextView) view.findViewById(R.id.to_flag);
        to_text= (TextView) view.findViewById(R.id.to_tex);
        input= (EditText) view.findViewById(R.id.input);
        input.addTextChangedListener(TEXTWATCHER);
        convert_button= (Button) view.findViewById(R.id.convert_button);
        convert_button.setEnabled(false);
        convert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String number=input.getText().toString();
                    convert_button.setEnabled(false);
                    try {
                        Check.check(formatFromBase,number);
                        from_text.setText(number);
                        input.setText("");
                        if(formatFromBase!=Format.DEC){
                            ObjectPackDecimal pack=convert.realAny_dec(number,formatFromBase);
                            number=pack.combine;

                        }
                        correspondingDecimalValue=number;
                        ObjectPackDouble packDouble=convert.dec_another(Double.parseDouble(number),formatToBase,MainActivity.precision);
                        to_text.setText(""+packDouble.combine_part);
                    } catch (PatternException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setMessage(e.getMessage());
                        builder.setTitle("Format Exception");
                        builder.setCancelable(true);
                        input.setText("");
                        builder.setNegativeButton("OK",null);
                        builder.show();
                    }

                } catch (NumberFormatException e) {
                    showError();
                }
            }
        });
        details_button= (Button) view.findViewById(R.id.button_details);
        details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ObjectPackDouble packDouble=convert.dec_another(Double.parseDouble(correspondingDecimalValue),formatToBase,MainActivity.precision);
                    ObjectPack objectPack=new ObjectPack(packDouble.integerHands_on,packDouble.integerRemainder,packDouble.doubleHands_on,packDouble.doubleRemainder,from_text.getText().toString(),correspondingDecimalValue,to_text.getText().toString(),formatFromBase,formatToBase);
                    callback.onDetails(objectPack);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        from_text.setOnLongClickListener(LONG_CLICK_TEXT);
        to_text.setOnLongClickListener(LONG_CLICK_TEXT);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback=(Callback)context;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if(MainActivity.convertActivate&&MainActivity.objectPack!=null){
                from_text.setText(MainActivity.objectPack.startNumber);
                to_text.setText(MainActivity.objectPack.lastNumber);

                switch (MainActivity.objectPack.fromBase){
                    case Format.BIN:
                        fromBin.setChecked(true);
                        break;
                    case Format.DEC:
                        fromDec.setChecked(true);
                        break;
                    case Format.OCT:
                        fromOct.setChecked(true);
                        break;
                    case Format.HEX:
                        fromHex.setChecked(true);
                        break;
                }

                switch (MainActivity.objectPack.toBase){
                    case Format.BIN:
                        toBin.setChecked(true);
                        break;
                    case Format.DEC:
                        toDec.setChecked(true);
                        break;
                    case Format.OCT:
                        toOct.setChecked(true);
                        break;
                    case Format.HEX:
                        toHex.setChecked(true);
                        break;
                }
            }
            MainActivity.convertActivate=false;
            MainActivity.objectPack=null;
        } catch (Exception e) {
            showError();
        }
    }

    private OnLongClickListener LONG_CLICK_TEXT=new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            String text=((TextView)v).getText().toString();
            input.setText(text);
            return false;
        }
    };

    private TextWatcher TEXTWATCHER =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(input.getText().toString().length()>0)
                convert_button.setEnabled(true);
            else
                convert_button.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void showError(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("OK",null);
        builder.setTitle("Application Exception");
        builder.setMessage("Error Occurred!");
        builder.create().show();
    }
}
