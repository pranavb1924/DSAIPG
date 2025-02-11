
package com.phasmidsoftware.dsaipg.assignments;
import com.phasmidsoftware.dsaipg.sort.elementary.InsertionSortComparator;
import com.phasmidsoftware.dsaipg.util.*;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BenchmarkMain {
    public static void main(String[] args) {
        int initialN = 1000;
        int numDoubles = 5;
        int runs = 10;
        int[] ns = new int[numDoubles];
        for (int i = 0, n = initialN; i < numDoubles; i++, n *= 2) {
            ns[i] = n;
        }
        
        System.out.println("1. Ordered Array Benchmark");
        System.out.println("----------------------------------------");
        System.out.printf("%10s%20s%n", "n", "Time (ms)");
        System.out.println("----------------------------------------");
        for (int n : ns) {
            double time = benchmarkInsertionSortOrdered(n, runs);
            System.out.printf("%10d%20.3f%n", n, time);
        }
        System.out.println("----------------------------------------\n");
        
        System.out.println("2. Random Array Benchmark");
        System.out.println("----------------------------------------");
        System.out.printf("%10s%20s%n", "n", "Time (ms)");
        System.out.println("----------------------------------------");
        for (int n : ns) {
            double time = benchmarkInsertionSortRandom(n, runs);
            System.out.printf("%10d%20.3f%n", n, time);
        }
        System.out.println("----------------------------------------\n");
        
        System.out.println("3. Partially Ordered Array Benchmark");
        System.out.println("----------------------------------------");
        System.out.printf("%10s%20s%n", "n", "Time (ms)");
        System.out.println("----------------------------------------");
        for (int n : ns) {
            double time = benchmarkInsertionSortPartial(n, runs);
            System.out.printf("%10d%20.3f%n", n, time);
        }
        System.out.println("----------------------------------------\n");
        
        System.out.println("4. Reverse Ordered Array Benchmark");
        System.out.println("----------------------------------------");
        System.out.printf("%10s%20s%n", "n", "Time (ms)");
        System.out.println("----------------------------------------");
        for (int n : ns) {
            double time = benchmarkInsertionSortReverse(n, runs);
            System.out.printf("%10d%20.3f%n", n, time);
        }
        System.out.println("----------------------------------------");
    }
    
    private static Integer[] orderedArray(int n) {
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++)
            arr[i] = i;
        return arr;
    }
    
    private static Integer[] randomArray(int n) {
        Integer[] arr = new Integer[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++)
            arr[i] = rand.nextInt(n);
        return arr;
    }
    
    private static Integer[] partiallyOrderedArray(int n) {
        Integer[] arr = new Integer[n];
        int half = n / 2;
        for (int i = 0; i < half; i++)
            arr[i] = i;
        Random rand = new Random();
        for (int i = half; i < n; i++)
            arr[i] = rand.nextInt(n);
        return arr;
    }
    
    private static Integer[] reverseOrderedArray(int n) {
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++)
            arr[i] = n - i;
        return arr;
    }
    
    private static double benchmarkInsertionSort(int n, Supplier<Integer[]> supplier, int runs) {
        Consumer<Integer[]> sortConsumer = array -> InsertionSortComparator.sort(array);
        Benchmark_Timer<Integer[]> timer = new Benchmark_Timer<>("InsertionSort", array -> array, sortConsumer);
        return timer.runFromSupplier(supplier, runs);
    }
    
    private static double benchmarkInsertionSortOrdered(int n, int runs) {
        return benchmarkInsertionSort(n, () -> orderedArray(n), runs);
    }
    
    private static double benchmarkInsertionSortRandom(int n, int runs) {
        return benchmarkInsertionSort(n, () -> randomArray(n), runs);
    }
    
    private static double benchmarkInsertionSortPartial(int n, int runs) {
        return benchmarkInsertionSort(n, () -> partiallyOrderedArray(n), runs);
    }
    
    private static double benchmarkInsertionSortReverse(int n, int runs) {
        return benchmarkInsertionSort(n, () -> reverseOrderedArray(n), runs);
    }
}

