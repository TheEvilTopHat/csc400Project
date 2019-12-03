/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.OutputStream; 
/**
 *
 * @author Joshua Ralls
 * @author Darius Thomas
 * 
 */
public class CommonFunctions {
    
    public static long findByteInodeOffset(Superblock sup, GroupDescriptor gd,int inodeTable) {
        int block_group = (inodeTable - 1) / sup.inodes_per_group;
        int inode_index = (inodeTable - 1) % sup.inodes_per_group;
        long startAddress;
        startAddress = (gd.inode_table*sup.block_size);
        startAddress += (inode_index*sup.inode_size);//(block_group*sup.block_size*sup.blocks_per_group);//+(gd.inode_bitmap*sup.block_size);//(block_group*sup.block_size*sup.blocks_per_group);
        if(block_group >= 1){
            //startAddress = 0;
            startAddress = 1024;
            if(block_group%2 == 1){
                startAddress += ((gd.inode_table-1)*sup.block_size);
            }
            else{
                startAddress += sup.block_size*2;
            }
            //startAddress = (gd.inode_table*sup.block_size);
            //block_group = 1;
            startAddress += (sup.block_size*8*sup.block_size*block_group);
            startAddress += (inode_index*sup.inode_size);
            //startAddress -= 1024+1024;
        }
        return startAddress;
    }
      
      
    public static int searchBlock(long seekoffset,int byteOffset, int length, RandomAccessFile raf) throws IOException{
         byte[] data = new byte[length];
        raf.seek(seekoffset);
        raf.read(data);
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int v;
        try{
            v = Math.abs(buffer.getInt());
        }
        catch(BufferUnderflowException e){
            v = Math.abs(buffer.getShort());
        }
        return v;
        /*
        raf.seek(seekoffset);
        try {            
            int byteRead=0;
            //for loop to assign read integer into an array
            //reads the needed bytes based on the given length
            for (int i=0; i<length;i++){
               byteRead+=+raf.read();
        } 
            return byteRead;
        } catch (IOException ex) {
            Logger.getLogger(Superblock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
        */
    }
    
    // Method which write the bytes into a file 
    static void writeBytes(String path, String name, byte[] bytes) 
    //https://www.geeksforgeeks.org/convert-byte-array-to-file-using-java/
    { 
        try { 
            String fullPath = path+name;
            // Initialize a pointer 
            // in file using OutputStream 
            OutputStream os = new FileOutputStream(fullPath); 
  
            // Starts writing the bytes in it 
            os.write(bytes); 
            System.out.println("file copied"); 
  
            // Close the file 
            os.close(); 
        } 
        catch (Exception e) { 
            System.out.println("Exception: " + e); 
        } 
    } 
    
    
}
