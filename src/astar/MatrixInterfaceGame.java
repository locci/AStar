 /*gera matrz para interface.*/
package astar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexandre
 */


public class MatrixInterfaceGame {
    
    private static final int NUMROW = 600;
    private static final int NUMCOL  = 800;
    private static final int DESROW  = 300;
    private static final int DESCOL   = 400;
    
    
    public static void main(String[] args) {
        
        //tratar depois o caso em que ambas as coordenadas são iguais
        int noPlayCol = (int) (Math.random() * NUMCOL);
        int noPlayRow = (int) (Math.random() * NUMROW);
//        int PlayCol = (int) (Math.random() * NUMCOL);
//        int PlayRow = (int) (Math.random() * NUMROW);
        File out = new File("matrix");
        int ganb  = 0;
        
        try {
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(out));
            
            bw.write(noPlayRow+";"+noPlayCol+";"+DESROW+";"+DESCOL+"\n");
            
            for (int i = 0; i < NUMROW; i++) {
                
                for (int j = 0; j < NUMCOL; j++) {
                      
                        ganb = (int) (Math.random() *100);                   
                                     
                        if (ganb <= 30) {                    
                               
                                if (i != noPlayRow || j!= noPlayCol){
                                    
                                    if (i != DESROW || j!= DESCOL){
                                    
                                        bw.write(i+";"+j+";"+1+"\n");
                                        
                                    }   else {
                                    
                                        bw.write(i+";"+j+";"+3+"\n");
                                        
                                   }
                                    
                                } else {
                                    
                                     bw.write(i+";"+j+";"+2+"\n");
                                    
                                }
                                
                        
                         } else {
                        
                                 if (i != noPlayRow || j!= noPlayCol){
                                    
                                    if (i != DESROW || j!= DESCOL){
                                    
                                        bw.write(i+";"+j+";"+0+"\n");
                                        
                                    }   else {
                                    
                                        bw.write(i+";"+j+";"+3+"\n");
                                        
                                   }
                                    
                                } else {
                                    
                                     bw.write(i+";"+j+";"+2+"\n");
                                    
                                }
                        
                        
                         }                
                  
                    
                }                
                
            }
            
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(MatrixInterfaceGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
}
