/* *****************************************************************************
 *  Name: Vivek Maran
 *  Date: June-15, 2019
 *  Description: Implementation of percolation stats class.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private  Percolation mPercolation;
    private int mGridSize;
    private int[] mNumOpenSites;
    private double[] mPercolationTreshold;
    private int mTrials;
    private double mMean = Double.NaN;
    private double mSD = Double.NaN;
    private double mConfLo = Double.NaN;
    private double mConfHi  = Double.NaN;
    private static final double CONFIDENCE95_CONST = 1.96;
    /**
     * Constructor: Computes percolation stats for 'trials' experiments on nxn grid
     * @param n - Grid size
     * @param trials - Number of trials
     */
    public PercolationStats(int n, int trials)  {
        if (n <=0 || trials <=0)
            throw new IllegalArgumentException(
                    "Invalid grid size/ trial numbers");
        mGridSize = n;
        mTrials = trials;
        mNumOpenSites = new int[mTrials];
        mPercolationTreshold = new double[mTrials];

        for (int i =0; i < mTrials; ++i) {
            mNumOpenSites[i] = runMCSimul();
            mPercolationTreshold[i] = mNumOpenSites[i]/(double)(mGridSize*mGridSize);
        }
    }

    /**
     * Computes Mean of percolation treshold
     * @return Mean
     */
    public double mean()  {
        if(Double.isNaN(mMean))
            mMean = StdStats.mean(mPercolationTreshold);
        return  mMean;
    }

    /**
     * Computes standard deviation of percolation treshold
     * @return Standard deviation
     */
    public double stddev() {
        if (Double.isNaN(mSD))
            mSD = StdStats.stddev(mPercolationTreshold);
        return mSD;
    }

    /**
     * Computes low  endpoint of 95% confidence interval
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        if(Double.isNaN(mConfLo))
            mConfLo = mMean - (CONFIDENCE95_CONST * mSD /(Math.sqrt(mTrials)));
        return mConfLo;
    }

    public double confidenceHi() {
        if(Double.isNaN(mConfHi))
            mConfHi = mMean + (CONFIDENCE95_CONST * mSD /(Math.sqrt(mTrials)));
        return mConfHi;
    }

    /**
     * Run Monte-Carlo simulation for percolation
     * @return - Number of open sites when system percolates
     */
    private int runMCSimul() {
        mPercolation = new Percolation(mGridSize);
        /* Open sites unitl percolation */
        while(! mPercolation.percolates()) {
            int row = StdRandom.uniform(1, mGridSize+1);
            int col = StdRandom.uniform(1, mGridSize+1);
            mPercolation.open(row, col);
        }
        //System.out.println("Number of open sites: "+mPercolation.numberOfOpenSites());
        return mPercolation.numberOfOpenSites();
    }

    public static void main(String[] args) {
        int n;
        int trials;

        if(args.length < 2 )  {
            System.out.println("Expects two command-line arguments n and T");
        }
        else {
            String format1 ="%-23s = %s\n";
            String format2 ="%-23s = [%s %s]\n";
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
            PercolationStats ps = new PercolationStats(n, trials);
            System.out.format(format1, "Mean",
                              String.format("%.6f", ps.mean()));
            System.out.format(format1, "stddev",
                              String.format("%.18f", ps.stddev()));
            System.out.format(format2, "95% confidence interval",
                              String.format("%.18f", ps.confidenceLo()),
                              String.format("%.18f", ps.confidenceHi()));
        }
    }
}
