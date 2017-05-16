package week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by hari.l on 28/10/16.
 */
public class PercolationStats {

    private double[] statsArray;
    private double mean = 0;
    private double standardDeviation = 0;
    private double confidenceLo = 0;
    private double confidenceHi = 0;

    public PercolationStats(int arraySize, int numberOfTrials) {
        if (arraySize <= 0 || numberOfTrials <= 0)
            throw new IllegalArgumentException();

        statsArray = new double[numberOfTrials];
        // System.out.println("Array Size = "+arraySize);
        // System.out.println("Trials = "+numberOfTrials);
        for (int i = 0; i < numberOfTrials; i++) {
            Percolation percolation = new Percolation(arraySize);
            double count = 0;
            while (!percolation.percolates()) {
                int low = StdRandom.uniform(1, arraySize+1);
                int high = StdRandom.uniform(1, arraySize+1);
                // System.out.println("Low and High "+low+" "+high);
                if (!percolation.isOpen(low, high)) {
                    count++;
                    percolation.open(low, high);
                }
            }
            statsArray[i] = count/(arraySize*arraySize);
        }
        mean = mean();
        standardDeviation = stddev();
        confidenceLo = confidenceLo();
        confidenceHi = confidenceHi();
        // System.out.println("mean                    = "+mean);
        // System.out.println("stddev                  = "+standardDeviation);
        // System.out.println("95% confidence interval = "+confidenceLo+", "+confidenceHi);

    }    // perform trials independent experiments on an n-by-n grid


    public double mean() {
        return StdStats.mean(statsArray);
    }
    public double stddev() {
        return StdStats.stddev(statsArray);
    }
    public double confidenceLo() {
        confidenceLo = mean - (1.96 * Math.sqrt(standardDeviation))/statsArray.length;
        return confidenceLo;
    }
    public double confidenceHi() {
        confidenceHi = mean + (1.96 * Math.sqrt(standardDeviation))/statsArray.length;
        return confidenceHi;
    }

    public static void main(String[] args) {
        int arraySize = Integer.parseInt(args[0]);
        int numberOfTrials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(arraySize, numberOfTrials);
        double mean = ps.mean;
        // System.out.println();
    }    // test client (described below)
}