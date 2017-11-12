package com.example.lemon.ict_assistant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lemon.ict_assistant.library.converter.utility.Check;
import com.example.lemon.ict_assistant.library.converter.utility.Format;
import com.example.lemon.ict_assistant.library.converter.utility.ObjectPack;

/**
 * Created by lemon on 12/14/2016.
 */

public class DetailsFragment extends Fragment {
    public static final String ACTIVATE="ACTIVATE";

    private TextView start_text,integerPartStart,integerPartRemain,fractionalPartStart,fractionalPartRemain;
    private LinearLayout integerParent,fractionalParent;
    private LayoutInflater inflater;
    private ObjectPack pack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_details,container,false);
        start_text= (TextView) view.findViewById(R.id.details_start_textView);
        integerParent= (LinearLayout) view.findViewById(R.id.details_integer_parent);
        fractionalParent= (LinearLayout) view.findViewById(R.id.details_fractional_parent);
        this.inflater=inflater;

        integerPartStart= (TextView) view.findViewById(R.id.integer_part_start_divider);
        integerPartRemain= (TextView) view.findViewById(R.id.integer_part_start_remain);
        fractionalPartStart= (TextView) view.findViewById(R.id.fractional_part_start_divider);
        fractionalPartRemain= (TextView) view.findViewById(R.id.fractional_part_start_remain);

        pack=MainActivity.objectPack;
        if(pack.fromBase!= Format.DEC)
            showStartText();
        showIntegerDetails();
        showFractionalPartDetails();

        return view;
    }

    private void showFractionalPartDetails() {
        try {
            if(pack.startNumber.contains(".")){
                String number=pack.middleNumber.substring(pack.middleNumber.indexOf("."));
                fractionalPartStart.setText("");
                fractionalPartRemain.setText(""+number+"\nX "+pack.toBase);

                int n=pack.realHandsOn.size()-1;
                StringBuilder builder=new StringBuilder();
                for(int i=0;i<=n;++i){
                    LinearLayout custom= (LinearLayout) inflater.inflate(R.layout.integer_child,null);
                    TextView divider,remains;
                    divider= (TextView) custom.findViewById(R.id.integer_child_divider);
                    remains= (TextView) custom.findViewById(R.id.integer_child_remain);
                    divider.setText(pack.realHandsOn.get(i));
                    builder.append(pack.realHandsOn.get(i));
                    remains.setText(""+pack.realRemainder.get(i)+"\nX "+pack.toBase);
                    fractionalParent.addView(custom);
                }

                TextView view= (TextView) inflater.inflate(R.layout.temp_result,null);
                view.setText(Html.fromHtml("<br>SO,FRACTIONAL PART:<br>("+number+")<sub>"+Format.DEC+"</sub>=(0."+builder.toString()+")<sub>"+pack.toBase+"</sub><br>"));
                fractionalParent.addView(view);

                TextView view1= (TextView) inflater.inflate(R.layout.finally_result,null);
                String mainPart=pack.lastNumber.substring(0,pack.lastNumber.indexOf("."))+"."+builder.toString();
                if(mainPart.length()==pack.lastNumber.length())
                    view1.setText(Html.fromHtml("<br>And,Finally:<br>("+pack.startNumber+")<sub>"+pack.fromBase+"</sub>=("+pack.lastNumber+")<sub>"+pack.toBase+"</sub><br>"));
                else
                    view1.setText(Html.fromHtml("<br>And,Finally:<br>("+pack.startNumber+")<sub>"+pack.fromBase+"</sub>=("+mainPart+")<sub>"+pack.toBase+"</sub><br>"+"<br>Which Can Say:<br>("+pack.startNumber+")<sub>"+pack.fromBase+"</sub>=("+pack.lastNumber+")<sub>"+pack.toBase+"</sub><br>"));
                fractionalParent.addView(view1);
            }
        } catch (Exception e) {
            showError();
        }
    }

    private void showIntegerDetails() {
        try {
            integerPartStart.setText(""+pack.toBase);
            String middle=pack.middleNumber;
            String last=pack.lastNumber;
            if(middle.contains("."))
                middle=pack.middleNumber.substring(0,pack.middleNumber.indexOf("."));
            if(last.contains("."))
                last=pack.lastNumber.substring(0,pack.lastNumber.indexOf("."));
            integerPartRemain.setText(""+middle);
            int n=pack.integerHandsOn.size()-1;
            for(int i=0;i<=n;++i){
                LinearLayout custom= (LinearLayout) inflater.inflate(R.layout.integer_child,null);
                TextView divider,remains;
                divider= (TextView) custom.findViewById(R.id.integer_child_divider);
                remains= (TextView) custom.findViewById(R.id.integer_child_remain);
                divider.setText("");
                if(i!=n)
                    divider.setText(""+pack.toBase);
                remains.setText(""+pack.integerRemainder.get(i)+" - "+pack.integerHandsOn.get(i));
                integerParent.addView(custom);
            }
            TextView view= (TextView) inflater.inflate(R.layout.temp_result,null);
            view.setText(Html.fromHtml("<br>SO,INTEGER PART:<br>("+middle+")<sub>"+Format.DEC+"</sub>=("+last+")<sub>"+pack.toBase+"</sub><br>"));
            integerParent.addView(view);
        } catch (Exception e) {
            showError();
        }

    }

    private void showStartText() {
        try {
            StringBuilder text=new StringBuilder();
            String number=pack.startNumber;
            if(!number.contains("."))
                number=pack.startNumber+".";
            int pow=number.indexOf(".");
            pow--;

            for (int i=0;i<pack.startNumber.length();++i){
                char temp=pack.startNumber.charAt(i);
                if(!Check.isDigit(temp))
                    continue;
                text.append(""+temp+"x"+pack.fromBase+"<sup>"+pow+"</sup>+");
                pow--;
            }

            String str=text.toString();
            str=str.substring(0,str.lastIndexOf("+"));
            str=str+"<br>="+pack.middleNumber;
            start_text.setText(Html.fromHtml("<br>"+str+"<br>"+"<br>SO:<br>("+pack.startNumber+")<sub>"+pack.fromBase+"</sub>=("+pack.middleNumber+")<sub>"+Format.DEC+"</sub><br>"));
        } catch (Exception e) {
            showError();
        }

    }
    private void showError(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("OK",null);
        builder.setTitle("Application Exception");
        builder.setMessage("Error Occurred!");
        builder.create().show();
    }

}
