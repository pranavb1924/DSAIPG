package com.phasmidsoftware.dsaipg.sort.par;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public final class ParSort {

    public static int cutoff = 1000;
    public static boolean useParallel = true;
    public static int customDepth = -1;


    public static int defaultDepth = (int) (Math.log(ForkJoinPool.getCommonPoolParallelism()) / Math.log(2));

    public static void sort(int[] array, int from, int to) {
        if (!useParallel) {
            mergeSort(array, from, to);
        } else {
            sortParallel(array, from, to, 0);
        }
    }

    private static void sortParallel(int[] array, int from, int to, int depth) {

        int depthLimit = (customDepth >= 0) ? customDepth : defaultDepth;
        if (to - from < cutoff || depth >= depthLimit) {
            Arrays.sort(array, from, to);
            return;
        }

        int mid = from + (to - from) / 2;

        CompletableFuture<Void> left = CompletableFuture.runAsync(() -> sortParallel(array, from, mid, depth + 1));
        CompletableFuture<Void> right = CompletableFuture.runAsync(() -> sortParallel(array, mid, to, depth + 1));
        CompletableFuture.allOf(left, right).join();

        int[] merged = doMerge(
            Arrays.copyOfRange(array, from, mid),
            Arrays.copyOfRange(array, mid, to)
        );
        System.arraycopy(merged, 0, array, from, merged.length);
    }

    private static int[] doMerge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) result[k++] = left[i++];
            else result[k++] = right[j++];
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
        return result;
    }

    private static void mergeSort(int[] array, int from, int to) {
        if (to - from <= 1) return;
        int mid = (from + to) / 2;
        mergeSort(array, from, mid);
        mergeSort(array, mid, to);
        merge(array, from, mid, to);
    }

    private static void merge(int[] array, int from, int mid, int to) {
        int[] left = Arrays.copyOfRange(array, from, mid);
        int[] right = Arrays.copyOfRange(array, mid, to);
        int i = 0, j = 0, k = from;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) array[k++] = left[i++];
            else array[k++] = right[j++];
        }
        while (i < left.length) array[k++] = left[i++];
        while (j < right.length) array[k++] = right[j++];
    }
}

