/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joshua
 */

//imports for file handling 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//imports for text input
import java.util.Scanner;

public class ext2Reader {
    
    static void changeFolder(String folder){
        
    }
    static void copyFile(String from, String to){
        
    }
    static String listContents(){
        return "";
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //scanner creattion
        Scanner scan = new Scanner(System.in);
        //file system
        RandomAccessFile raf;
        //try to open file throw exception if no file found break if found file
        while(true){
            try {
                //on my pc it's at /home/joshua/Documents/csc400Project/virtdisk
                String filename = scan.nextLine();
                raf = new RandomAccessFile(filename,"r");
                System.out.println("OPENING FILE");
                break;
            }
            catch(FileNotFoundException e){
                System.out.println("NO FILE FOUND");
            }
        }
        
        //create superblock class and pass the file
        Superblock sb = new Superblock(raf); //superblock class
        //go to root
        changeFolder("/");
        
        String command = "";
        //command loop
        while(command.equalsIgnoreCase("q") == false){
            //get input
            command = scan.nextLine(); 
            //parse and do command
            switch(command){
                case "h":
                case "H":
                    System.out.println("help list");
                    break;
                case "cd":
                    System.out.println("change dir");
                    break;
                case "ls":
                    System.out.println("list directory contents");
                    break;
                case "cp":
                    System.out.println("copy contents to host device");
                    break;
                case "q":
                case "Q":
                    System.out.println("goodbye");
                    break;
                default:
                    System.out.println("Not a reconized command");
                    break;
            }
        }
        
        //clean up area
        try {
            raf.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
        
    }
    
}
