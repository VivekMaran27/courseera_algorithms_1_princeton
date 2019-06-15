/* *****************************************************************************
 *  Name: Vivek Maran
 *  Date: June-15, 2019
 *  Description: Implementation of percolation class.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] m_grid;
    private int m_numOpenSites = 0;
    private  int m_size = 0;
    private int m_dim = 0;
    private WeightedQuickUnionUF mUnionFind;
    private static final int  NUM_ADDITIONAL_NODES = 2;

    /**
     * Create n-by-n grid, with all sites blocked
     * @param n - Diemension of the grid.
     */
    public Percolation(int n) {

        /* Handle invalid arguments */
        if (n <= 0)
            throw new  java.lang.IllegalArgumentException("Invalid grid size");

        m_size = n*n;
        m_dim = n;
        m_grid= new int[n][n];
        mUnionFind = new WeightedQuickUnionUF(n*n+ NUM_ADDITIONAL_NODES);
    }

    /**
     * Open site (row, col) if it is not open already
     * @param row - Site row index
     * @param col - Site col index
     */
    public  void open(int row, int col) {

        //System.out.println("open++");

        --row; --col; //Zero map indices

        /* Handle invalid arguments */
        if (!isIndicesValid(row, col))
            throw new  java.lang.IllegalArgumentException("Invalid row/column");

        /* Open the site if not already open */
        if (!isOpen(row, col)) {
            //System.out.println("Opening site row: "+row+", col: "+col);
            m_grid[row][col] = 1;
            union(row,col,row,col-1);
            union(row,col,row,col+1);
            union(row,col,row-1,col);
            union(row,col,row+1,col);
            if(row == 0)
            {
                //System.out.println("Union to virtual top");
                mUnionFind.union(getUFIndex(row,col),m_size);
            }
            if(row == m_dim-1)
            {
                //System.out.println("Union to virtual bottom");
                mUnionFind.union(getUFIndex(row,col),m_size+1);
            }
            ++m_numOpenSites;
        }
        else
        {
            //System.out.println("The site row: "+row+", col: "+col +"is "
            //        + "already open");
        }
        //System.out.println("open--");
    }

    /**
     * is site (row, col) open?
     * @param row - Site row index
     * @param col - Site col index
     * @return - true if open, false otherwise
     */
    public boolean isOpen(int row, int col) {
        /* Handle invalid arguments */
        if (!isIndicesValid(row, col))
            throw new  java.lang.IllegalArgumentException();
        return (m_grid[row][col] == 1);
    }

    /**
     * Is the site full site
     * @param row - Site row index
     * @param col - Site col index
     * @return True if site is full, false otherwise
     */
    public boolean isFull(int row, int col) {
        --row; --col;
        return mUnionFind.connected(getUFIndex(row,col),m_size);
    }

    /**
     * Get number of open sites
     * @return - Number of open sites
     */
    public  int numberOfOpenSites() {
        return m_numOpenSites;
    }

    /**
     * Is system percolates
     * @return True if percolates, false otherwise
     */
    public boolean percolates() {
        return mUnionFind.connected(m_size,m_size+1);
    }

    /**
     * Check if the given site index (row, col) is valid
     * @param row - Site row index
     * @param col  - Site col index
     * @return true if site index is valid, false otherwise
     */
    private boolean isIndicesValid(int row, int col)
    {
        return (row>=0 && row<m_dim) && (col>=0 && col<m_dim);
    }

    /**
     * Given a row and col index, get the corresponding index for the union-find
     * datastructure
     * @param row - Site row index
     * @param col - Site col index
     * @return - Corresponding index in union-find datastructure
     */
    private int getUFIndex(int row, int col) {

        /* Handle invalid arguments */
        if (!isIndicesValid(row, col))
            throw new  java.lang.IllegalArgumentException();
        //System.out.println("("+row+","+col+")"+" mapped to "+(row*m_dim + col));
        return (row*m_dim + col);
    }

    /**
     * Union the given site indices
     * @param prow - Site row index for element-1
     * @param pcol - Site col index for element-1
     * @param qrow - Site row index for element-2
     * @param qcol - Site row index for element-2
     */
    private void union(int prow, int pcol, int qrow, int qcol)
    {
        if(isIndicesValid(prow, pcol) && isIndicesValid(qrow,qcol))
            if(isOpen(qrow,qcol))
            {
                //System.out.println("Union of (" + prow +"," +pcol +") and "
                //                           + "(" + qrow +"," +qcol +")");
                mUnionFind.union(getUFIndex(prow,pcol),getUFIndex(qrow,qcol));
            }
    }

    public static void main(String[] args)
    {
        int n = StdIn.readInt();
        try
        {
            Percolation percolation = new Percolation(n);
            //System.out.println("Percolation object of size " +n+" created");
            while (!StdIn.isEmpty()) {
                int p = StdIn.readInt();
                int q = StdIn.readInt();
                percolation.open(p,q);
                //System.out.println("("+p+","+q+")"+ " isFullsite: "+
                //                           percolation.isFull(p,q));
                //System.out.println("System percolates?: "+
                //                           percolation.percolates());
            }
        }
        catch(java.lang.IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
