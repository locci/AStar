package astar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author alexandre
 * Altere no arquivo Matrix a terceira coluna: 2 para origem e 3 para destino.
 */


public class AStar {

    /**
     * @param args the command line arguments
     */
    /*origem 2 5 destino 8 5 ok
      origem 0 2 destino 8 5 ok
      origem 5 8 destino 4 3 ok
      origem 5 8 destino 3 2 ok
      origem 5 8 destino 2 2 ok
      origem 5 8 destino 0 2 ok
      origem 9 0 destino 0 9 ok
      origem 9 0 destino 0 2 ok
    */
    private static final int MAXROW    = 10;
    private static final int MAXCOL    = 10;
//    private static final int STARTROW  =  9;
//    private static final int STARTCOL  =  0;
//    private static final int ENDROW    =  0;
//    private static final int ENDCOL    =  9;
    //Imposible
    private static final int STARTROW  =  0;
    private static final int STARTCOL  =  2;
    private static final int ENDROW    =  8;
    private static final int ENDCOL    =  5;
    
    public static void main(String[] args) {
        
        int contMatrix = 1;
        HashMap<String, String> parent = new HashMap<>();
        ArrayList<String> openList  = new ArrayList<>();
        ArrayList<String> closeList = new ArrayList<>();
        AStar as = new AStar();
        int matrix[][] = new int[MAXROW][MAXCOL];
        String path = "/home/alexandre/Dropbox/netbeansproject/AStar/src/astar/matrix";
//        String path = "/home/alexandre/Dropbox/netbeansproject/AStar/src/astar/matrixImpossible";
        String valueStart = "";
        String valueEnd = "";
        String adjTiles = "";
        String[] adjTilesArray;
        String[] posi;
        String[] key;
        boolean findDest = false;
        int num = 0;
        String finalPath = "";   
        
        try {
            
            Scanner sc = new Scanner(new FileReader(new File(path)));
            matrix = Matrix.buildMatrix(sc, MAXROW, MAXCOL); 
            System.out.println("Matrix number: " + contMatrix);
            Matrix.printMatrix(matrix); 
        
        } catch (FileNotFoundException ex) {
            
            Logger.getLogger(AStar.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
       openList.add(AStar.STARTROW + ";" + AStar.STARTCOL + ";" + "0");
       valueEnd = AStar.ENDROW + ";" + AStar.ENDCOL;
       
        while(!openList.isEmpty() && !findDest) {
            
            valueStart = as.minusValue(openList);
            posi = valueStart.split(";");
            
            if(Integer.parseInt(posi[0]) != STARTROW && Integer.parseInt(posi[1]) != STARTCOL){
                
                matrix[Integer.parseInt(posi[0])][Integer.parseInt(posi[1])] = 5;            
            
            }
            
            closeList.add(valueStart);
            openList.remove(valueStart);
            adjTiles   = as.adjPoint(matrix, valueStart);       
            adjTilesArray = adjTiles.split("/");            
            posi = valueStart.split(";");
            
            if(!adjTilesArray[0].equals("")) {
                
                for(int i=0; i < adjTilesArray.length; i++){       
                    
                   key = adjTilesArray[i].split(";");                    
                   parent.put(key[0] + " " + key[1], posi[0] + " " +posi[1]);                    
                               
                }
                
            }           
                                   
            for(int j = 0; j < adjTilesArray.length; j++){
                

                if(!openList.contains(adjTilesArray[j]) && !closeList.contains(adjTilesArray[j]) && !adjTilesArray[j].equals("")){
                    openList.add(adjTilesArray[j]);
                    posi = adjTilesArray[j].split(";");
                    if(matrix[Integer.parseInt(posi[0])][Integer.parseInt(posi[1])] != 3) {
                        matrix[Integer.parseInt(posi[0])][Integer.parseInt(posi[1])] = 5;
                    } else {
                       findDest = true;
                       key = posi;
                       posi = valueStart.split(";"); 
                       parent.put(key[0] + " " +key[1],  posi[0] + " " + posi[1]);
                    }                    
                }               
                 
            }
            
            System.out.println("");
            System.out.println("Matrix number: " + ++contMatrix);
            Matrix.printMatrix(matrix);
            
        }  
        
        key = new String[3];
        key[0] = Integer.toString(ENDROW);
        key[1] = Integer.toString(ENDCOL);
        key[2] = key[0] + " " + key[1];
        matrix[Integer.parseInt(key[0])][Integer.parseInt(key[1])] = 9;
        
        System.out.println("Best Path@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(key[2]);
        
        while (parent.containsKey(key[2])) {
            
            finalPath = parent.get(key[2]);
            System.out.println(finalPath);
            posi = finalPath.split(" ");
            matrix[Integer.parseInt(posi[0])][Integer.parseInt(posi[1])] = 9;
            key[2] = finalPath;
            
        }
        
        System.out.println("Final Matrix@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Matrix.printMatrix(matrix);
        
    }
    
    public String minusValue(ArrayList<String> list) {
        
        String[] strArray = new String[3];
        int minimum = 1000000000;
        int index = 0;
        
       for (int i = 0; i < list.size(); i++) {
                
           strArray = list.get(i).split(";");
           if(minimum > Integer.parseInt(strArray[2])) {
               minimum = Integer.parseInt(strArray[2]);
               index = i;
           } 
           
        }
        
        return list.get(index);
        
    }
    
    public String adjPoint(int[][]matrix, String point){
        
        AStar as      = new AStar();
        int step      = 0;
        int heuristic = 0;
        String[] str = point.split(";");
        int[]    value = new int[str.length];
        String adj = "";
        
        for(int i=0; i < str.length; i++) {
            value[i] = Integer.parseInt(str[i]);
        }
        
        //corners@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        if(value[0] == 0 && value[1] == 0){
            
            if(matrix[value[0]][value[1]+1] == 0 || matrix[value[0]][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0],(value[1] + 1));
                heuristic = as.countDist(value[0],(value[1] + 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + value[0] + ";" + (value[1] + 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]+1] == 0 || matrix[value[0]+1][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0] +1),(value[1] + 1));
                heuristic = as.countDist((value[0]+1),(value[1] + 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] + 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]] == 0 || matrix[value[0]+1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0] +1),(value[1]));
                heuristic = as.countDist((value[0]+1),(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1]) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
            
        }
        
        if(value[0] == MAXROW-1 && value[1] == 0){
            
            if(matrix[value[0] -1][value[1]] == 0 || matrix[value[0] -1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]));
                heuristic = as.countDist(value[0]-1,(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]-1][value[1]+1] == 0 || matrix[value[0]-1][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0] -1),(value[1] + 1));
                heuristic = as.countDist((value[0]-1),(value[1] + 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1] + 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]+1] == 0 || matrix[value[0]][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1]+1));
                heuristic = as.countDist((value[0]),(value[1]+1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]+1) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
            
        }
        
        if(value[0] == 0 && value[1] == MAXCOL - 1){
            
            if(matrix[value[0]][value[1]-1] == 0 || matrix[value[0]][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0],(value[1]-1));
                heuristic = as.countDist(value[0],(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]-1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]-1] == 0 || matrix[value[0]+1][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0] +1),(value[1] - 1));
                heuristic = as.countDist((value[0]+1),(value[1] - 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] - 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]] == 0 || matrix[value[0]+1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1]));
                heuristic = as.countDist((value[0]+1),(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1]) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
            
        }
        
        if(value[0] == MAXROW-1 && value[1] == MAXCOL - 1){
            
            if(matrix[value[0]-1][value[1]] == 0 || matrix[value[0]-1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]));
                heuristic = as.countDist(value[0]-1,(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]-1][value[1]-1] == 0 || matrix[value[0]-1][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0] -1),(value[1] - 1));
                heuristic = as.countDist((value[0]-1),(value[1] - 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1] - 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]-1] == 0 || matrix[value[0]][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1]-1));
                heuristic = as.countDist((value[0]),(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]-1) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
            
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        
        //board@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        if(value[0] == 0){
            
             if(matrix[value[0]][value[1] - 1] == 0 || matrix[value[0]][value[1] - 1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0],(value[1]-1));
                heuristic = as.countDist(value[0],(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]-1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]+1] == 0 || matrix[value[0]][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1] + 1));
                heuristic = as.countDist((value[0]),(value[1] + 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1] + 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1] - 1] == 0 || matrix[value[0]+1][value[1] - 1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]+1,(value[1]-1));
                heuristic = as.countDist(value[0]+1,(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1]-1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]+1] == 0 || matrix[value[0]+1][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1] + 1));
                heuristic = as.countDist((value[0]+1),(value[1] + 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] + 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]] == 0 || matrix[value[0]+1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1]));
                heuristic = as.countDist((value[0]+1),(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1]) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
            
        }
        
        if(value[0] == MAXROW - 1){
            
            if(matrix[value[0]-1][value[1]] == 0 || matrix[value[0]-1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]));
                heuristic = as.countDist(value[0]-1,(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]-1][value[1]+1] == 0 || matrix[value[0]-1][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]-1),(value[1] + 1));
                heuristic = as.countDist((value[0]-1),(value[1] + 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1] + 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]-1][value[1] - 1] == 0 || matrix[value[0]-1][value[1] - 1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]-1));
                heuristic = as.countDist(value[0]-1,(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]-1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]+1] == 0 || matrix[value[0]][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1] + 1));
                heuristic = as.countDist((value[0]),(value[1] + 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1] + 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]-1] == 0 || matrix[value[0]][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1]-1));
                heuristic = as.countDist((value[0]),(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]-1) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
            
        }
        
        if(value[1] == 0){
            
            if(matrix[value[0]-1][value[1]] == 0 || matrix[value[0]-1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]));
                heuristic = as.countDist(value[0]-1,(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]] == 0 || matrix[value[0]+1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1] ));
                heuristic = as.countDist((value[0]+1),(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] ) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]-1][value[1] + 1] == 0 || matrix[value[0]-1][value[1] + 1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]+1));
                heuristic = as.countDist(value[0]-1,(value[1]+1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]+1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]+1] == 0 || matrix[value[0]+1][value[1]+1] ==3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1] + 1));
                heuristic = as.countDist((value[0]+1),(value[1]+ 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] + 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]+1] == 0 || matrix[value[0]][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1]+1));
                heuristic = as.countDist((value[0]),(value[1]+1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]+1) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
            
        }
        
        if(value[1] == MAXCOL - 1){            
            
            if(matrix[value[0]-1][value[1]] == 0 || matrix[value[0]-1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]));
                heuristic = as.countDist(value[0]-1,(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]] == 0 || matrix[value[0]+1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1] ));
                heuristic = as.countDist((value[0]+1),(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] ) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]-1][value[1] - 1] == 0 || matrix[value[0]-1][value[1] - 1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]-1));
                heuristic = as.countDist(value[0]-1,(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]-1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]-1] == 0 || matrix[value[0]+1][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1] - 1));
                heuristic = as.countDist((value[0]+1),(value[1] - 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] - 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]-1] == 0 || matrix[value[0]][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1]-1));
                heuristic = as.countDist((value[0]),(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]-1) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
            
        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //geral situation@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            if(matrix[value[0]-1][value[1]] == 0 || matrix[value[0]-1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]));
                heuristic = as.countDist(value[0]-1,(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]] == 0 || matrix[value[0]+1][value[1]] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1] ));
                heuristic = as.countDist((value[0]+1),(value[1]), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] ) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]-1][value[1] - 1] == 0 || matrix[value[0]-1][value[1] - 1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,value[0]-1,(value[1]-1));
                heuristic = as.countDist(value[0]-1,(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]-1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]-1] == 0 || matrix[value[0]+1][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1] - 1));
                heuristic = as.countDist((value[0]+1),(value[1] - 1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1] - 1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]+1] == 0 || matrix[value[0]][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1]+1));
                heuristic = as.countDist((value[0]),(value[1]+1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]+1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]][value[1]-1] == 0 || matrix[value[0]][value[1]-1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]),(value[1]-1));
                heuristic = as.countDist((value[0]),(value[1]-1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]) + ";" + (value[1]-1) + ";" + (step + heuristic);
                adj = adj + "/";
                 
            }
            
            if(matrix[value[0]-1][value[1]+1] == 0 || matrix[value[0]-1][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]-1),(value[1]+1));
                heuristic = as.countDist((value[0]-1),(value[1]+1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]-1) + ";" + (value[1]+1) + ";" + (step + heuristic);
                adj = adj + "/";
                
            }
            
            if(matrix[value[0]+1][value[1]+1] == 0 || matrix[value[0]+1][value[1]+1] == 3){
                
                step      = as.countDist(AStar.STARTROW, AStar.STARTCOL,(value[0]+1),(value[1]+1));
                heuristic = as.countDist((value[0]+1),(value[1]+1), AStar.ENDROW, AStar.ENDCOL);
                adj = adj + (value[0]+1) + ";" + (value[1]+1) + ";" + (step + heuristic);
                
                
            }
            
            return adj;
        
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        
  
        
    }
    
    public int countDist(int startPointX, int startPointY, int endPointX, int endPointY){
        
        int countNum = 0;
        
        while(startPointX != endPointX || startPointY != endPointY) {
              
            if(startPointX > endPointX) {
                startPointX --;
            } else {
                
                if(startPointX < endPointX) {
                   startPointX ++;
                }
                
            }
            
                        
            if(startPointY > endPointY) {
                startPointY --;
            } else {
                
                if(startPointY < endPointY) {
                   startPointY ++;
                }
                
            }
            
            countNum++;
        
        }
        
        return countNum;
        
    }
    
    
    
}
