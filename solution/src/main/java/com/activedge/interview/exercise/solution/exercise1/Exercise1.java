package com.activedge.interview.exercise.solution.exercise1;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Exercise1 {

    public int smallestNonOccurringInteger(int [] arrayOfnumbers){
        Set<Integer> positiveNumbersSet = new HashSet<>();
        int smallestNonOccurringInteger = 1;
        for (int num : arrayOfnumbers) {
            if (num > 0) {
                positiveNumbersSet.add(num);
            }
        }
        for (int i = 1; i <= arrayOfnumbers.length + 1; i++) {
            if (!positiveNumbersSet.contains(i)) {
                smallestNonOccurringInteger = i;
                break;
            }
        }
        return smallestNonOccurringInteger;
    }
}
