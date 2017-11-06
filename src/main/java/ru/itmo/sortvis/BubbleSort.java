package ru.itmo.sortvis;

public class BubbleSort implements SortAlgorithm {

    @Override
    public void sort(ArrayModel arr) {
        int n = arr.length();

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                try {

                    Thread.sleep(1);

                    if (arr.gt(j - 1, j)) {
                        arr.swap(j - 1, j);

                    }

                } catch (InterruptedException ex) {

                }

            }

        }

    }

}
