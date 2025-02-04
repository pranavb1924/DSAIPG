package com.phasmidsoftware.dsaipg.adt.threesum;

import java.util.Arrays;
import java.util.Random;
import com.phasmidsoftware.dsaipg.util.*;;

public class ThreeSumQuadraticTime {
    public static void main(String[] args) {
        int run = 1;
        for (int size : new int[]{5, 10, 20, 30, 40, 50, 100 , 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000}) {
            int[] randomArray = generateRandomArray(size);
            Arrays.sort(randomArray);
            System.out.println("Run " + run);
            System.out.println("--------------------------------------------");
            System.out.println("Input array size: " + randomArray.length);
            
            Triple[] quadraticResults;
            double quadraticTime;
            try (Stopwatch stopwatch = new Stopwatch()) {
                ThreeSumQuadratic threeSumQuadratic = new ThreeSumQuadratic(randomArray);
                quadraticResults = threeSumQuadratic.getTriples();
                quadraticTime = stopwatch.lap();
            }
            
            Triple[] quadrithmicResults;
            double quadrithmicTime;
            try (Stopwatch stopwatch = new Stopwatch()) {
                ThreeSumQuadrithmic threeSumQuadrithmic = new ThreeSumQuadrithmic(randomArray);
                quadrithmicResults = threeSumQuadrithmic.getTriples();
                quadrithmicTime = stopwatch.lap();
            }
            
            System.out.println("Time for quadratic approach: " + quadraticTime + " ms");
            System.out.println("Time for quadrithmic approach: " + quadrithmicTime + " ms");
            System.out.println("Result size for quadratic approach: " + quadraticResults.length);
            System.out.println("Result size for quadrithmic approach: " + quadrithmicResults.length);
            
            System.out.println();
            run++;
        }
    }

    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] randomArray = new int[size];
        for (int i = 0; i < size; i++) {
            randomArray[i] = random.nextInt(2000) - 1000;
        }
        return randomArray;
    }
}