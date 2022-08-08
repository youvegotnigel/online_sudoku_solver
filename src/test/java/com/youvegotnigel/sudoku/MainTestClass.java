package com.youvegotnigel.sudoku;

public class MainTestClass {

    public static void main(String[] args) {

        for(int i=0; i<3; i++){
            MultiThreadingClass obj1 = new MultiThreadingClass(i);
            Thread myThread = new Thread(obj1);
            myThread.start();
        }


    }

}
