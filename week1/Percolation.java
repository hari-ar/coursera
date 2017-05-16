package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by hari.l on 28/10/16.
 */

public class Percolation {

    private WeightedQuickUnionUF data;
    private boolean[][] isOpenFlag;
    private int maxIndex;
    private int multiplier;

    public Percolation(int n) {
        if (n > 0) {
            maxIndex = n + 1;
            multiplier = maxIndex + 1;
            data = new WeightedQuickUnionUF(multiplier * multiplier);
            isOpenFlag = new boolean[multiplier][multiplier];
            for (int i = 0; i <= maxIndex; i++) {
                open(0, i);
                open(maxIndex, i);
            }
        }
        else
            throw new IllegalArgumentException();
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            isOpenFlag[row][col] = true;
            if (row != 0 && isOpen(row - 1, col))
                data.union(getMultiplier(row, col), getMultiplier(row-1, col));
            if (row < maxIndex && isOpen(row + 1, col))
                data.union(getMultiplier(row, col), getMultiplier(row+1, col));
            if (col != 0 && isOpen(row, col - 1))
                data.union(getMultiplier(row, col), getMultiplier(row, col-1));
            if (col < maxIndex && isOpen(row, col + 1))
                data.union(getMultiplier(row, col), getMultiplier(row, col+1));
        }
    }

    private int getMultiplier(int row, int col) {
        return ((row*multiplier) +col);
    }

    public boolean isOpen(int row, int col) {

        return isOpenFlag[row][col];
    }

    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            return data.connected(0, getMultiplier(row, col));
        }
        return false;
    }

    public boolean percolates() {
        return data.connected(0, (maxIndex + 1) * (maxIndex + 1) - 1);
    }

}
