/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joshua
 */


//TODO EVERYTHING
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupDescriptor {
    //location of VD for my computer change for user
    public RandomAccessFile  acessSuperBlock;//File path needs to be added
       // ("F:\\Fall2019\\CSC\\CSC400\\400Project\\src\\virtdisk (1)","r");

    public int block_bitmap;
        
    public  int inode_bitmap;
        
    public int inode_table;
        
    public int free_blocks_count;
        
    public int free_inodes_count;
        
    public int used_dirs_count;
    
 
    GroupDescriptor(RandomAccessFile raf) {
        this.acessSuperBlock = raf;
        try{
            forBlockGroupDescrip();
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
     public int searchBlock(int seekoffset,int byteOffset, int length) throws IOException{
        acessSuperBlock.seek(seekoffset);
        int x = 0;       
        byte[] bytes = new byte[5];//
        try {            
            int byteRead=0;
            //for loop to assign read integer into an array
            //reads the needed bytes based on the given length
            for (int i=0; i<length;i++){
               int y=acessSuperBlock.read();
               byteRead+=+acessSuperBlock.read();
               x++;
               acessSuperBlock.seek(seekoffset+x);

        } 
            return byteRead;
        } catch (IOException ex) {
            Logger.getLogger(Superblock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 404 ;
    }


public void forBlockGroupDescrip() throws IOException{
        //Block group descriptor tab;e
        block_bitmap=searchBlock(2048,0,4);
        //System.out.println(bg_block_bitmap);
        
        inode_bitmap=searchBlock(2052,0,4);
        //System.out.println(bg_inode_bitmap);
        
        inode_table=searchBlock(2056,0,4);
        //System.out.println("*"+bg_inode_table);
        
        free_blocks_count=searchBlock(2060,0,4);
        //System.out.println(bg_free_blocks_count);
        
        free_inodes_count=searchBlock(2064,0,4);
        //System.out.println( bg_free_inodes_count);
        
        used_dirs_count=searchBlock(2064,0,4);
        //System.out.println(bg_used_dirs_count);
        
    }

    
}






 
   
