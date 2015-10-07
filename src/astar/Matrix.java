package astar;

import java.util.Scanner;

/**
 *
 * @author alexandre
 */


public class Matrix {
    
    public static void printMatrix(int [][] matrix) {
        
        for(int i=0; i < matrix.length; i++){
            
                for(int j =0; j< matrix.length; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println(" ");
                
        }        
        
    }
    
    public static int[][] buildMatrix(Scanner sc, int rowNum, int colNum) {
        
            String line = "";
            int col = 0;
            int row = 0;
            int val = 0;
            String[] lineArray = new String[3];
            int matrix[][] = new int[rowNum][colNum];
            
            while(sc.hasNextLine()) {
                
                line = sc.nextLine();
                lineArray = line.split("	");
                row = Integer.valueOf(lineArray[0]);
                col = Integer.valueOf(lineArray[1]);
                val = Integer.valueOf(lineArray[2]);
                matrix[row][col] = val;
                
            }
              
        return matrix;
        
    }
    
    
    
}
