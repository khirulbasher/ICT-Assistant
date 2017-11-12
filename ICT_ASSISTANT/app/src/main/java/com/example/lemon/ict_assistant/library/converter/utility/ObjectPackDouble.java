package com.example.lemon.ict_assistant.library.converter.utility;

import java.util.List;

/**
 * Created by lemon on 12/13/2016.
 */
public class ObjectPackDouble {
    public String integer_part,real_part,combine_part;
    public List<String> doubleHands_on, doubleRemainder;
    public List<String> integerHands_on, integerRemainder;
    public final int FORMAT_BASE;

    public ObjectPackDouble(final int FORMAT_BASE) {
        this.FORMAT_BASE=FORMAT_BASE;
    }

    public void pack(){
        StringBuilder builder=new StringBuilder();
            for(String s: doubleHands_on)
                builder.append(s);
        real_part=builder.toString();
        if(real_part.length()>0)
            combine_part=""+integer_part+"."+real_part;
        else
            combine_part=""+integer_part;

        if(combine_part.length()<1)
            combine_part="0.0";
    }
}
