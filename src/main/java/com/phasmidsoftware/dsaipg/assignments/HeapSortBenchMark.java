package com.phasmidsoftware.dsaipg.assignments;

import com.phasmidsoftware.dsaipg.adt.pq.PriorityQueue;
import com.phasmidsoftware.dsaipg.adt.pq.PQException;
import com.phasmidsoftware.dsaipg.util.Benchmark_Timer;
import java.util.*;
import java.util.function.*;

public class HeapSortBenchMark {

    private static final int number_of_inserstions = 16000; 
    private static final int number_of_removals = 4000;    
    private static final int number_of_runs = 10;          

    private static class BenchmarkResult {
        private final String name;
        private final double averageTime;

        public BenchmarkResult(String name, double averageTime) {
            this.name = name;
            this.averageTime = averageTime;
        }

        public String getName() {
            return name;
        }

        public double getTime() {
            return averageTime;
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting HeapSortBenchMark...");
        int capacity = 4095;
        System.out.println("\nBenchmark Data:");
        System.out.printf("%10s %20s %20s %20s %20s%n", "Capacity", "Basic Binary", "Binary Floyd", "4-ary", "4-ary Floyd");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
     
            BenchmarkResult basic = runBenchmark("Basic Binary Heap", false, 2, capacity);
            BenchmarkResult binaryFloyd = runBenchmark("Binary Heap with Floyd", true, 2, capacity);
            BenchmarkResult fourAry = runBenchmark("4-ary Heap", false, 4, capacity);
            BenchmarkResult fourAryFloyd = runBenchmark("4-ary Heap with Floyd", true, 4, capacity);
            System.out.printf("%-10s %-20s %-20s %-20s %-20s%n", "Capacity", "Basic Time", "Binary Floyd", "Four-Ary", "Four-Ary Floyd");
            System.out.printf("%-15s: %d%n", "Capacity", capacity);
            System.out.printf("%-15s: %20.3f%n", "Basic Time", basic.getTime());
            System.out.printf("%-15s: %20.3f%n", "Binary Floyd", binaryFloyd.getTime());
            System.out.printf("%-15s: %20.3f%n", "Four-Ary", fourAry.getTime());
            System.out.printf("%-15s: %20.3f%n", "Four-Ary Floyd", fourAryFloyd.getTime());
        
    }

    private static BenchmarkResult runBenchmark(String description, boolean useFloyd, int arity, int capacity) {
        System.out.println("Benchmarking: " + description + " with capacity " + capacity);
        Supplier<PriorityQueue<Integer>> supplier = () -> new PriorityQueue<Integer>(
                capacity,
                1,
                true,
                Comparator.naturalOrder(),
                useFloyd,
                arity
        );
        Benchmark_Timer<PriorityQueue<Integer>> timer = new Benchmark_Timer<PriorityQueue<Integer>>(
                description,
                (pq) -> pq,
                (pq) -> runOperations(pq, capacity),
                null
        );
        double averageTime = timer.runFromSupplier(supplier, number_of_runs);
        System.out.println(description + " (capacity " + capacity + ") average time over " + number_of_runs + " runs: " + averageTime + " ms");
        return new BenchmarkResult(description, averageTime);
    }

    private static void runOperations(PriorityQueue<Integer> pq, int capacity) {
        Random random = new Random();
        Integer spilledHighest = null;
        for (int i = 0; i < number_of_inserstions; i++) {
            int element = random.nextInt();
            if (pq.size() == capacity) {
                if (spilledHighest == null || element > spilledHighest) {
                    spilledHighest = element;
                }
            }
            pq.give(element);
        }
        int removals = 0;
        while (removals < number_of_removals && !pq.isEmpty()) {
            try {
                pq.take();
                removals++;
            } catch (PQException e) {
                break;
            }
        }
        if (spilledHighest != null) {
            System.out.println("Highest spilled element (capacity " + capacity + "): " + spilledHighest);
        }
    }
}
