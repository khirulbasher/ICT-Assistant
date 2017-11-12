package com.example.lemon.ict_assistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lemon.ict_assistant.library.converter.utility.Check;
import com.example.lemon.ict_assistant.library.converter.utility.Convert;
import com.example.lemon.ict_assistant.library.converter.utility.Format;
import com.example.lemon.ict_assistant.library.converter.utility.ObjectPackDecimal;
import com.example.lemon.ict_assistant.library.converter.utility.PatternException;

/**
 * Created by lemon on 12/14/2016.
 */

public class Mathematic extends Fragment {
    private EditText param1,param2;
    private Convert convert;
    private int FormatBase = Format.DEC;
    private int actionMathPerform=1;
    private RadioGroup systemGroup,actionGroup;
    private Button equalButton;
    private TextView paramOne,paramTwo,sign,answer;
    private double correspondingDecimalValue1=110,correspondingDecimalValue2=101,correspondingDecimalResult=9;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_math,container,false);
        try {
            param1= (EditText) view.findViewById(R.id.input_first);
            param2= (EditText) view.findViewById(R.id.input_second);
            systemGroup= (RadioGroup) view.findViewById(R.id.group_system);
            actionGroup= (RadioGroup) view.findViewById(R.id.group_action);
            equalButton = (Button) view.findViewById(R.id.btn_equal);
            equalButton.setEnabled(false);
            paramOne= (TextView) view.findViewById(R.id.param1_text);
            paramTwo= (TextView) view.findViewById(R.id.param2_text);
            sign= (TextView) view.findViewById(R.id.sign);
            answer= (TextView) view.findViewById(R.id.answerView);
            convert=new Convert();
            systemGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    double value1=correspondingDecimalValue1,value2=correspondingDecimalValue2,result=correspondingDecimalResult;

                    String stock1Pref="",stock2Pref="",stock3Pref="";
                    if(value1<0.0){
                        stock1Pref="-";
                        value1*=-1;
                    }
                    if(value2<0.0){
                        stock2Pref="-";
                        value2*=-1;
                    }
                    if(result<0.0){
                        result*=-1;
                        stock3Pref="-";
                    }

                    switch (checkedId){
                        case R.id.radio_bin:
                            FormatBase =Format.BIN;
                            break;
                        case R.id.radio_oct:
                            FormatBase =Format.OCT;
                            break;
                        case R.id.radio_dec:
                            FormatBase =Format.DEC;

                            break;
                        case R.id.radio_hex:
                            FormatBase =Format.HEX;

                            break;
                    }

                    if (FormatBase!=Format.DEC) {
                        paramOne.setText(""+stock1Pref+convert.dec_another(Double.parseDouble(""+value1),FormatBase,MainActivity.precision).combine_part);
                        paramTwo.setText(""+stock2Pref+convert.dec_another(Double.parseDouble(""+value2),FormatBase,MainActivity.precision).combine_part);
                        answer.setText(""+stock3Pref+convert.dec_another(Double.parseDouble(""+result),FormatBase,MainActivity.precision).combine_part);
                    }
                    else {
                        paramOne.setText(""+correspondingDecimalValue1);
                        paramTwo.setText(""+correspondingDecimalValue2);
                        answer.setText(""+correspondingDecimalResult);
                    }

                }
            });

            actionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.radio_add:
                            actionMathPerform=0;
                            break;
                        case R.id.radio_sub:
                            actionMathPerform=1;
                            break;
                        case R.id.radio_mul:
                            actionMathPerform=2;
                            break;
                        case R.id.radio_div:
                            actionMathPerform=3;
                            break;

                    }
                    mathPerform(correspondingDecimalValue1,correspondingDecimalValue2,actionMathPerform);
                }
            });

            equalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String p1=param1.getText().toString();
                    String p2=param2.getText().toString();


                    try {
                        Check.check(FormatBase,p1);
                        Check.check(FormatBase,p2);
                        param2.setText("");
                        param1.setText("");
                        equalButton.setEnabled(false);
                        double value1=110,value2=101;
                        if (FormatBase!=Format.DEC) {
                            String p1Pref="";
                            String p2Pref="";
                            if(p1.contains("-")){
                                p1Pref="-";
                                p1=p1.substring(p1.indexOf("-")+1);
                            }
                            if(p2.contains("-")){
                                p2Pref="-";
                                p2=p2.substring(p2.indexOf("-")+1);
                            }

                            ObjectPackDecimal pack1=convert.realAny_dec(p1,FormatBase);
                            ObjectPackDecimal pack2=convert.realAny_dec(p2,FormatBase);
                            value1= Double.parseDouble(""+p1Pref+pack1.combine);
                            value2=Double.parseDouble(""+p2Pref+pack2.combine);
                        }
                        else {

                            value1=Double.parseDouble(p1);
                            value2=Double.parseDouble(p2);

                        }
                        correspondingDecimalValue1=value1;
                        correspondingDecimalValue2=value2;

                        mathPerform(value1,value2,actionMathPerform);
                    } catch (PatternException e1) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setMessage(e1.getMessage());
                        builder.setTitle("Format Exception");
                        builder.setCancelable(true);
                        param1.setText("");
                        param2.setText("");
                        builder.setNegativeButton("OK",null);
                        builder.show();
                    }

                }
            });

            answer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String text=((TextView)v).getText().toString();
                    param1.setText(text);
                    param2.requestFocus();
                    return false;
                }
            });
        } catch (Exception e) {
            showError();
        }

        param1.addTextChangedListener(TEXTWATCHER);
        param2.addTextChangedListener(TEXTWATCHER);
        return view;
    }

    private void mathPerform(double value1,double value2,int actionMathPerform){
        try {
            double result=0;
            switch (actionMathPerform){
                case 0:
                    result=value1+value2;
                    sign.setText("+");
                    break;
                case 1:
                    result=value1-value2;
                    sign.setText("-");
                    break;
                case 2:
                    result=value1*value2;
                    sign.setText("X");
                    break;
                case 3:
                    result=value1/value2;
                    sign.setText("/");
                    break;
            }
            correspondingDecimalResult=result;
            String resultFormat="";
            String value1Format="";
            String value2Format="";
            if(result<0.0){
                resultFormat="-";
                result*=-1;
            }
            if(value1<0.0){
                value1Format="-";
                value1*=-1;
            }
            if(value2<0.0){
                value2Format="-";
                value2*=-1;
            }

            if (FormatBase!=Format.DEC) {
                paramOne.setText(""+value1Format+convert.dec_another(value1,FormatBase,MainActivity.precision).combine_part);
                paramTwo.setText(""+value2Format+convert.dec_another(value2,FormatBase,MainActivity.precision).combine_part);
                answer.setText(""+resultFormat+convert.dec_another(result,FormatBase,MainActivity.precision).combine_part);
            }
            else {
                paramOne.setText(""+correspondingDecimalValue1);
                paramTwo.setText(""+correspondingDecimalValue2);
                answer.setText(""+correspondingDecimalResult);
            }
        } catch (Exception e) {
            showError();
        }
    }

    private TextWatcher TEXTWATCHER=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String text1=param1.getText().toString();
            String text2=param2.getText().toString();

            if(text1.length()>0&&text2.length()>0)
                equalButton.setEnabled(true);
            else
                equalButton.setEnabled(false);
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
