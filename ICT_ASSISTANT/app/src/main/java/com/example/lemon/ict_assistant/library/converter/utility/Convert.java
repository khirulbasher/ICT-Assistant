package com.example.lemon.ict_assistant.library.converter.utility;

import com.example.lemon.ict_assistant.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lemon on 12/13/2016.
 */
public class Convert {


    public ObjectPackInteger dec_another(int number,int toBase){
        StringBuilder builder=new StringBuilder();
        List<String>hands_onList=new ArrayList<>();
        List<String>remainderList=new ArrayList<>();
        ObjectPackInteger packInteger = new ObjectPackInteger(toBase);

        int hands_on,remainder;
        for(;;){
            if(number<=0)break;
            remainder=number/toBase;
            hands_on=number-(remainder*toBase);
            remainderList.add(""+remainder);

            if(hands_on>=10) {
                String ch=String.format("%c",('A' + (hands_on - 10)));
                builder.append(ch);
                hands_onList.add(ch);

            }
            else {
                builder.append(hands_on);
                hands_onList.add(""+hands_on);
            }
            number=remainder;
        }
        String data=builder.toString();
        StringBuilder anotherBuilder=new StringBuilder();
        for(int i=data.length()-1;i>=0;--i){
            anotherBuilder.append(data.charAt(i));
        }
        packInteger.integer_part=anotherBuilder.toString();
        packInteger.integerHands_on=hands_onList;
        packInteger.integerRemainder=remainderList;
        return packInteger;
    }
    public ObjectPackDouble dec_another(double number, int toBase, int precision){
        ObjectPackDouble pack=new ObjectPackDouble(toBase);
        List<String> hands_part=new ArrayList<>();
        List<String> remainder_part=new ArrayList<>();
        int int_part=(int)number;

        String numberString=""+number;
        double double_part=0.0;
        if (numberString.contains("."))
            double_part=Double.parseDouble(numberString.substring(numberString.indexOf(".")));
        
        ObjectPackInteger packInteger=dec_another(int_part,toBase);
        pack.integer_part=packInteger.integer_part;
        pack.integerHands_on=packInteger.integerHands_on;
        pack.integerRemainder=packInteger.integerRemainder;

        int hands_on;
        double remainder;

        for(int i=0;i<precision;++i){
            if(double_part==0.0)
                break;
            double_part*=toBase;
            hands_on=(int)double_part;
            remainder=double_part-hands_on;
            if(hands_on>=10)
                hands_part.add(String.format("%c",('A'+(hands_on-10))));
            else
                hands_part.add(""+hands_on);

            remainder_part.add(""+remainder);
            double_part=remainder;

        }
        pack.doubleHands_on =hands_part;
        pack.doubleRemainder =remainder_part;

        pack.pack();
        return pack;
    }

    public int intAny_intDec(String data,int sourceBase){
        int temp=0;
        int pow=0;
        for(int i=data.length()-1;i>=0;--i){
            char ch=data.charAt(i);
            int point;
            if(ch>='A'&&ch<='Z'){
                point=10+(ch-'A');
            }
            else
                point=ch-'0';

            temp+=point*Math.pow(sourceBase,pow++);
        }

        return temp;
    }

    public ObjectPackDecimal realAny_dec(String data, int sourceBase){
        ObjectPackDecimal pack=new ObjectPackDecimal();
        String int_part=data;
        String real_part="0";

        if(data.contains(".")){
            int_part=data.substring(0,data.indexOf("."));
            real_part=data.substring(data.indexOf(".")+1);
        }
        pack.integer_part=""+intAny_intDec(int_part,sourceBase);
        double temp=0.0;
        int pow=1;
        for(int i=0;i<real_part.length();++i){

            char ch=real_part.charAt(i);
            int point=ch-'0';
            if(ch>='A'&&ch<='Z'){
                point=10+ch-'A';
            }
            temp+=point*Math.pow(sourceBase,-pow++);
        }
        pack.real_part=""+temp;
        pack.pack();
        return pack;
    }

}
