package com.example.lemon.ict_assistant.library.converter.utility;

/**
 * Created by lemon on 12/13/2016.
 */
public class ObjectPackDecimal {
    public String integer_part,real_part,combine;

    public void pack(){
        if(real_part.length()>1)
            combine=""+integer_part+real_part.substring(real_part.indexOf("."));
        else
            combine=integer_part;
        if(combine.length()<1)
            combine="0.0";
    }
}
