package ru.itmo.sortvis;

public class SelectionSort implements SortAlgorithm {
    @Override
    public void sort(ArrayModel arr) {
        try {
            for (int i = 0; i < arr.length() - 1; i++) {
                int index = i;
                for (int j = i + 1; j < arr.length(); j++) {
                    Thread.sleep(1);
                    if (arr.lt(j, index)) {
                        index = j;
                    }
                }

                arr.swap(index, i);

        }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
