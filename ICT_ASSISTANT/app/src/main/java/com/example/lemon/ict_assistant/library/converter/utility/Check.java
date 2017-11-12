package com.example.lemon.ict_assistant.library.converter.utility;

/**
 * Created by lemon on 12/13/2016.
 */
public class Check {

    public static void Hex(String data) throws PatternException{
        int count=0;
        for(int i=0;i<data.length();++i){
            if(data.charAt(i)=='.') {
                count++;
                continue;
            }
            if(data.charAt(i)>='0'&&data.charAt(i)<='9'||data.charAt(i)>='A'&&data.charAt(i)<='F')
                continue;

            throw new PatternException(data.charAt(i)+"--> Is Not A Hexadecimal Digit");
        }
        if(count>1)throw new PatternException(data+" Is Not a Number");
    }

    public static void Dec(String data) throws PatternException{
        int count=0;
        for(int i=0;i<data.length();++i){
            if(data.charAt(i)=='.') {
                count++;
                continue;
            }
            if(data.charAt(i)>='0'&&data.charAt(i)<='9')
                continue;

            throw new PatternException(data.charAt(i)+"--> Is Not A Decimal Digit");
        }
        if(count>1)throw new PatternException(data+" Is Not a Number");
    }
    public static void Oct(String data) throws PatternException{
        int count=0;
        for(int i=0;i<data.length();++i){
            if(data.charAt(i)=='.') {
                count++;
                continue;
            }
            if(data.charAt(i)>='0'&&data.charAt(i)<='7')
                continue;

            throw new PatternException(data.charAt(i)+"--> Is Not A Octal Digit");
        }
        if(count>1)throw new PatternException(data+" Is Not a Number");
    }

    public static void Bin(String data) throws PatternException{
        int count=0;
        for(int i=0;i<data.length();++i){
            if(data.charAt(i)=='.') {
                count++;
                continue;
            }
            if(data.charAt(i)>='0'&&data.charAt(i)<='1')
                continue;

            throw new PatternException(data.charAt(i)+"--> Is Not A Binary Digit");
        }
        if(count>1)throw new PatternException(data+" Is Not a Number");
    }
    public static boolean isDigit(char ch){
        return (ch>='0'&&ch<='9'||ch>='A'&&ch<='F');
    }

    public static void check(int base,String data) throws PatternException{
        switch (base){
            case 2:
                Bin(data);
            case 8:
                Oct(data);
            case 10:
                Dec(data);
            case 16:
                Hex(data);
        }
    }

}
