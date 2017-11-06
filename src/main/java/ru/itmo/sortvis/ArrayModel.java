package ru.itmo.sortvis;

import java.util.ArrayList;
import java.util.List;

public class ArrayModel {
    private final List<ArrayModelListener> listeners;
    private final int[] array;

    private int modificationsCounter = 0;

    public ArrayModel(int[] array) {
        this.array = array;

        this.listeners = new ArrayList<>();
    }

    public void swap(int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;

        notifyChanged();

        modificationsCounter += 2;
    }

    public int length() {
        return array.length;

    }

    public boolean gt(int i, int j) {
        return array[i] > array[j];

    }

    public boolean lt(int i, int j) {
        return array[i] < array[j];

    }

    public void println() {
//         System.out.println(Arrays.toString(array));

        System.out.println("Statistics:");
        System.out.printf("Modifications: %d%n", modificationsCounter);
    }

    public void addArrayListener(ArrayModelListener l) {
        listeners.add(l);

    }

    public void removeArrayListener(ArrayModelListener l) {
        listeners.remove(l);

    }

    private void notifyChanged() {
        for (ArrayModelListener listener : listeners) {
            listener.arrayChanged(array);

        }
    }

}
