package ru.itmo.sortvis.ui;

import ru.itmo.sortvis.ArrayModelListener;

import java.util.Arrays;

public class ConsolePrinter implements ArrayModelListener {
    @Override
    public void arrayChanged(int[] newArray) {
        System.out.println(Arrays.toString(newArray));
    }

}
