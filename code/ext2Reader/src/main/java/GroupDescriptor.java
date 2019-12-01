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
            raf.seek(2056);
            forBlockGroupDescrip();
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

public void forBlockGroupDescrip() throws IOException{
        //Block group descriptor tab;e
        block_bitmap=CommonFunctions.searchBlock(2048,0,4,acessSuperBlock);
        //System.out.println(bg_block_bitmap);
        
        inode_bitmap=CommonFunctions.searchBlock(2052,0,4,acessSuperBlock);
        //System.out.println(bg_inode_bitmap);
        
        inode_table=CommonFunctions.searchBlock(2056,0,4,acessSuperBlock);
        //System.out.println("*"+bg_inode_table);
        
        free_blocks_count=CommonFunctions.searchBlock(2060,0,4,acessSuperBlock);
        //System.out.println(bg_free_blocks_count);
        
        free_inodes_count=CommonFunctions.searchBlock(2064,0,4,acessSuperBlock);
        //System.out.println( bg_free_inodes_count);
        
        used_dirs_count=CommonFunctions.searchBlock(2064,0,4,acessSuperBlock);
        //System.out.println(bg_used_dirs_count);
        
    }

    
}






 
   
