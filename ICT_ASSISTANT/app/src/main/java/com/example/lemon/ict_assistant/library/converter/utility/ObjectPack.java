package com.example.lemon.ict_assistant.library.converter.utility;

import java.util.List;

/**
 * Created by lemon on 12/14/2016.
 */

public class ObjectPack {
    public final List<String> integerHandsOn,integerRemainder,realHandsOn,realRemainder;
    public String startNumber,middleNumber,lastNumber;
    public final int fromBase,toBase;

    public ObjectPack(List<String> integerHandsOn, List<String> integerRemainder, List<String> realHandsOn, List<String> realRemainder, String startNumber, String middleNumber, String lastNumber, int fromBase, int toBase) {
        this.integerHandsOn = integerHandsOn;
        this.integerRemainder = integerRemainder;
        this.realHandsOn = realHandsOn;
        this.realRemainder = realRemainder;
        this.startNumber = startNumber;
        this.middleNumber = middleNumber;
        this.lastNumber = lastNumber;
        this.fromBase = fromBase;
        this.toBase = toBase;
    }
}
