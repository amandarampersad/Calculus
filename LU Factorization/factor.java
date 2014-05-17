/* Amanda Rampersad
 * COT 4500
 * Program 2
*/

import java.util.*;
import java.io.*;

public class factor {
    
    public static int numElements = 0;
    public static int n = 0;

    public static void main(String[] args) {
        
        // Get filename from command line
        String fname = args[0];
        File file = new File(fname);
        double[] array = new double[100];
        
        // Read file contents
        // Store matrix values in array
        try {
            Scanner input = new Scanner(file);
            
            while(input.hasNext()) {
                array[numElements] = input.nextDouble();
                numElements++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        // Get matrix dimension
        n = (int) Math.sqrt(numElements);
        
        // Transfer matrix values to an actual matrix
        double[][] matrix = createMatrix(array);
        
        // Print input matrix contents
        System.out.println("Input Matrix:");
        System.out.println();
        printMatrix(matrix);
        
        // Initialize L matrix to have all 1s in the diagonal
        double[][] L = initializeL();
        
        // Perform LU factorization
        // If 0 is returned, matrix is not factorizable
        if(factor(matrix, L) == 0) {
            System.out.println("Factorization impossible.");
        }
        
    }
    
    public static double[][] createMatrix(double[] array) {
        
        double[][] matrix = new double[n][n];
        int index = 0;
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                matrix[i][j] = array[index];
                index++;
            }
        }
        
        return matrix;
        
    }
    
    public static void printMatrix(double[][] matrix) {
        
//        for(int i = 0; i < n; i++) {
//            for(int j = 0; j < n; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
        
        for(double[] row : matrix) {
            for(double i : row) {
                System.out.print(i);
                System.out.print("\t");
            }
            System.out.println();
        }
        
    }
    
    public static int factor(double[][] matrix, double[][] L) {
        
        double[][] U = new double[n][n];
        
        // STEP 1
        // Set U[0][0] such that L[0][0] * U[0][0] = matrix[0][0]
        U[0][0] = matrix[0][0]/L[0][0];
        
        if(L[0][0] * U[0][0] == 0) {
            return 0;
        }
        
        // STEP 2
        // Set the first row of U - U[0][j] = matrix[0][j]/L[0][0]
        // Set the first column of L - L[j][0] = matrix[j][0]/U[0][0]
        for(int j = 1; j < n; j++) {
            U[0][j] = matrix[0][j]/L[0][0];
            L[j][0] = matrix[j][0]/U[0][0];
        }
        
        // STEP 3
        for(int i = 1; i < n - 1; i++) {
            double sum = 0;
            
            // STEP 4
            for(int k = 0; k <= i - 1; k++) {
                sum = sum + (L[i][k] * U[k][i]);
            }
            
            double difference = matrix[i][i] - sum;
            
            if(difference == 0) {
                return 0;
            }
            
            U[i][i] = difference/L[i][i];
            
            // STEP 5
            for(int j = i + 1; j < n; j++) {
                double sum1 = 0;
                double sum2 = 0;
                
                for(int k = 0; k <= i - 1; k++) {
                    sum1 = sum1 + L[i][k] * U[k][j];
                    sum2 = sum2 + L[j][k] * U[k][i];
                }
                
                U[i][j] = (matrix[i][j] - sum1)/L[i][i];
                L[j][i] = (matrix[j][i] - sum2)/U[i][i];
                
            }
            
        }
        
        // STEP 6
        double sum = 0;
        
        for(int k = 0; k < n - 1; k++) {
            sum = sum + L[n-1][k] * U[k][n-1];
        }
        
        U[n-1][n-1] = (matrix[n-1][n-1] - sum)/L[n-1][n-1];
        
        // STEP 7
        System.out.println();
        System.out.println("'L' Matrix:");
        System.out.println();
        printMatrix(L);
        System.out.println();
        System.out.println("'U' Matrix:");
        System.out.println();
        printMatrix(U);
        
        return 1;
        
    }
    
    public static double[][] initializeL() {
        
        double[][] matrix = new double[n][n];
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(i == j) {
                    matrix[i][j] = 1.0;
                }
                else {
                    matrix[i][j] = 0.0;
                }
            }
        }
        
        return matrix;
        
    }

}