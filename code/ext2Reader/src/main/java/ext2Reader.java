/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joshua
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;


public class ext2Reader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //try to open file throw exception if no file found break if found file
        while(true){
            try {
                Scanner scan = new Scanner(System.in);
                String filename = scan.nextLine();
                RandomAccessFile raf = new RandomAccessFile(filename,"r");
                System.out.println("OPENING FILE");
                break;
            }
            catch(FileNotFoundException e){
                System.out.println("NO FILE FOUND");
            }
        }
    }
    
}
