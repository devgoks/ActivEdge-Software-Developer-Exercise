package com.activedge.interview.exercise.solution.exercise1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Exercise1Test {

    @Autowired
    private Exercise1 exercise1;

    @Test
    public void testPositiveNumbersArrayInput(){
        int [] array1 = new int [] {1, 3, 6, 4, 1, 2};
        int [] array2 = new int [] {6,7,8,3,2,1};
        int response1 = exercise1.smallestNonOccurringInteger(array1);
        int response2 = exercise1.smallestNonOccurringInteger(array2);
        Assertions.assertEquals(response1,5);
        Assertions.assertEquals(response2,4);
    }

    @Test
    public void testPositiveAndNegativeNumbersArrayInput(){
        int [] array1 = new int [] {5, -1, -3};
        int [] array2 = new int [] {-1, -3, 2, 4, 1, 2,5,3,-6};
        int response1 = exercise1.smallestNonOccurringInteger(array1);
        int response2 = exercise1.smallestNonOccurringInteger(array2);
        Assertions.assertEquals(response1,1);
        Assertions.assertEquals(response2,6);
    }

    @Test
    public void testEmptyArrayInput(){
        int [] emptyArray = new int []{};
        int response = exercise1.smallestNonOccurringInteger(emptyArray);
        Assertions.assertEquals(response,1);
    }
}
