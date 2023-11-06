package com.example.quizpractice.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyUtils {
    public static List<String> shuffleThreeStrings(String str1, String str2, String str3) {
        // Create a list containing the three input strings
        List<String> inputList = new ArrayList<>();
        inputList.add(str1);
        inputList.add(str2);
        inputList.add(str3);

        Collections.shuffle(inputList);

        return inputList;
    }

}
