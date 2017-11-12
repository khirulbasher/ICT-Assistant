package com.example.lemon.ict_assistant;

import com.example.lemon.ict_assistant.library.converter.utility.ObjectPack;

import java.util.List;

/**
 * Created by lemon on 12/14/2016.
 */

public interface Callback {
    void onDetails(ObjectPack pack);
    void onMath();
    void onNumberSystem();

}
