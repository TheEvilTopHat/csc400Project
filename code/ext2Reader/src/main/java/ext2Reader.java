/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joshua Ralls
 * @author Darius Thomas
 * 
 */
//imports for file handling 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//imports for text input
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//byte buffer
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

//imports for text input
import java.util.Scanner;

public class ext2Reader {
    
    //class variables
    static RandomAccessFile raf;
    static Superblock sb;
    static GroupDescriptor gd;
    static String currentDirPath = "command"; //not implmeted
    static InodeTable currentIT;
    static  InputStream source;
    static  OutputStream dest;
    
    //functions to interact with the ext2 file system
    static void help(){
        //shows the avalibale commands to the user
        System.out.println("COMMANDS");
        System.out.println("cd -> change directory");
        System.out.println("ls -> list directory contents");
        System.out.println("cp -> copy file");
        System.out.println("q -> quit");
        System.out.println("h -> view commands/help");
    }
    static void changeFolder(InodeTable it){
        //allows the user to change what directory he is in
        Scanner scan = new Scanner(System.in);
        for(int i=0;i<it.folders.size();i++){
            //output which folders can be chagned to
            System.out.println(Integer.toString(i) + ": " + it.folders.get(i).name);
        }
        while(true){
            try{
                //get user input for which folder they want
                System.out.print("folder: ");
                int dir = Integer.parseInt(scan.nextLine());
                //get inode for chosen dir
                int inode = it.folders.get(dir).inode;
                currentIT = new InodeTable(raf, sb, gd, inode);
                break;
            }
            //user input error hanlding
            catch(NumberFormatException e){
                System.out.println("INPUT VALID NUMBER");
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("ENTER NUMBER IN ACCEPTED VALUES");
            }
        }
    }
   static void copyFile(String desiredFilePath, String pathforCopiedContents) throws IOException{//copies a file and places it in a another folder
        //allows the user to change what directory he is in
        Scanner scan = new Scanner(System.in);
        int inode;
        String name;
        for(int i=0;i<currentIT.files.size();i++){
            //output which folders can be chagned to
            System.out.println(Integer.toString(i) + ": " + currentIT.files.get(i).name);
        }
        while(true){
            try{
                //get user input for which folder they want
                System.out.print("file: ");
                int dir = Integer.parseInt(scan.nextLine());
                //get inode for chosen dir
                inode = currentIT.files.get(dir).inode;
                name = currentIT.files.get(dir).name;
                break;
            }
            //user input error hanlding
            catch(NumberFormatException e){
                System.out.println("INPUT VALID NUMBER");
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("ENTER NUMBER IN ACCEPTED VALUES");
            }
        }
        //bytes to save
        //ByteBuffer buffer = ByteBuffer.wrap(data);
        //buffer.order(ByteOrder.LITTLE_ENDIAN);
        //go to inode and parse which blocks
       Inode in = new Inode(sb,gd,raf,inode);
       //save
       CommonFunctions.writeBytes(pathforCopiedContents,name,in.data);
    }

    static void listContents(InodeTable it){
        //output all folders
        System.out.println("FOLDERS");
        for(int i=0;i<it.folders.size();i++){
            System.out.println("    * " + it.folders.get(i).name);
        }
        //output all files
        System.out.println("FILES");
        for(int i=0;i<it.files.size();i++){
            System.out.println("    * " + it.files.get(i).name);
        }
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //scanner creattion
        Scanner scan = new Scanner(System.in);
        //file system
        //try to open file throw exception if no file found break if found file
        while(true){
            try {
                //on my pc it's at /home/joshua/Documents/csc400Project/virtdisk
                System.out.print("FILE PATH:");
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
        sb = new Superblock(raf); //superblock class
        //create group discriptor class
        gd = new GroupDescriptor(raf);
        //go to root
        currentIT = new InodeTable(raf, sb, gd, 2);
        //changeFolder(currentIT);

        
        
        String command = "";
        //command loop
        while(command.equalsIgnoreCase("q") == false){
            //get input
            System.out.print(currentDirPath + ": ");
            command = scan.nextLine(); 
            //parse and do command
            switch(command){
                case "h":
                case "H":
                case "help":
                    help();
                    break;
                case "cd":
                    changeFolder(currentIT);
                    break;
                case "ls":
                    listContents(currentIT);
                    break;
                case "cp":
                    System.out.print("Enter a Path to where you would like the File to be placed on the Host Drive:");
                    String pathforCopiedContents= scan.nextLine(); //Path to where it will be placed in host drive
                    System.out.println("What File in the Current Directory will be copied?");
                    try{
                        copyFile( currentDirPath, pathforCopiedContents);
                    }
                    catch(IOException e){
                        System.out.println(e);
                    }
                    //System.out.println("copied contents to host device");
                    break;
                case "q":
                case "Q":
                    System.out.println("goodbye");
                    break;
                default:
                    System.out.println("Not a reconized command, type h for help");
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
