# Assignment description
[Link](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)

# Steps to verify
1. Import the intellij intellij_project/Percolation.zip into your IntelliJ IDE. I used the one in [Link](https://lift.cs.princeton.edu/java/windows/)
2. Click Build->Build Project
3. In the project window, right click 'PercolationStats' and click 'Run 'PercolationStats.main()'' 
4. The program expects two integer arguments, the grid size and trials. It can be configured in 'Run->Edit Configurations->Program arguments'  

# Sample output

        ##For inputs 200 100
        Mean                    = 0.592552
        stddev                  = 0.008997035212426164
        95% confidence interval = [0.590788831098364700 0.594315668901635800]
        
        ##For inputs 2 10000
        Mean                    = 0.665750
        stddev                  = 0.118177130120042970
        95% confidence interval = [0.663433728249647100 0.668066271750352800]
        
        ##For inputs 2 100000
        Mean                    = 0.666588
        stddev                  = 0.117879679334208940
        95% confidence interval = [0.665856874177969100 0.667318125822030900]

Thanks to professor Bob Sedgewick  for creating and guiding through this assignment.
