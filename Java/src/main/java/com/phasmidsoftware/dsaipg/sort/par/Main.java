package com.phasmidsoftware.dsaipg.sort.par;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());


        List<Integer> arraySizes = Arrays.asList(1000, 10000, 100000, 1000000, 2000000, 3000000, 4000000, 5000000);
        List<Integer> cutoffs = Arrays.asList(1000, 5000, 7500, 10000, 12500, 15000, 20000, 25000, 50000, 100000, 200000, 300000, 400000, 500000);
        List<Integer> depthValues = Arrays.asList(8);

        int runs = 5;
        Random random = new Random();

        for (int size : arraySizes) {
            System.out.println("\nTesting arraySize = " + size);
            int[] array = new int[size];

            for (int cutoff : cutoffs) {
                for (int depthVal : depthValues) {

                    // Set ParSort parameters
                    ParSort.cutoff = cutoff;
                    ParSort.customDepth = depthVal; 
                    ParSort.useParallel = true;

                    // Parallel timing
                    long startParallel = System.currentTimeMillis();
                    for (int r = 0; r < runs; r++) {
                        for (int i = 0; i < size; i++) {
                            array[i] = random.nextInt(10_000_000);
                        }
                        ParSort.sort(array, 0, size);
                    }
                    long endParallel = System.currentTimeMillis();
                    long parallelTime = endParallel - startParallel;

                    // Sequential timing
                    ParSort.useParallel = false;
                    long startSeq = System.currentTimeMillis();
                    for (int r = 0; r < runs; r++) {
                        for (int i = 0; i < size; i++) {
                            array[i] = random.nextInt(10_000_000);
                        }
                        ParSort.sort(array, 0, size);
                    }
                    long endSeq = System.currentTimeMillis();
                    long seqTime = endSeq - startSeq;

                    System.out.println(
                        "ArraySize=" + size
                        +"\tCutoff=" + cutoff
                        + "\tDepth=\t" + depthVal
                        + "\tParallel=" + parallelTime + "ms"
                        + "\tSequential=" + seqTime + "ms"
                    );
                }
            }
        }
    }
}