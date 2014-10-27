package com.edison.algorithms;

public class MergeSort {

    public void sort(int[] array) {

        int midPoint = array.length / 2;

    }

    private void split(int[] array, int startA, int endA, int startB, int endB) {

    }

    private int[] merge(int[] original, int startA, int endA, int startB,
        int endB) {

        int[] result = null;
        int size = endB - startA;

        // base case do merge
        if (size <= 4) {
            result = new int[size];

            for (int k = 0; k < result.length; k++) {

                if (original[startA] < original[startB]) {
                    result[k] = original[startA++];
                } else {
                    result[k] = original[startB++];
                }
            }
        } // split more
        else {
            int midPoint = size / 2;
            result = merge(original, startA, endA, startB, endB);
        }
        return result;
    }
}
