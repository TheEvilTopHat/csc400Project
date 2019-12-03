/**
 *
 * @author Joshua Ralls
 * @author Darius Thomas
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


class Superblock {
    //location of VD for my computer change for user
    public RandomAccessFile acessSuperBlock;//File path needs to be added
       // ("F:\\Fall2019\\CSC\\CSC400\\400Project\\src\\virtdisk (1)","r");
    
    public int inodes_count;
        
    public int blocks_count;
        
    public int log_block_size;
    
    public int block_size;
        
    public int log_frag_size;
        
    public int blocks_per_group;
        
    public int frags_per_group;
        
    public int inodes_per_group;
        
    public int first_ino;
        
    public int inode_size;
    
    public int  first_data_block;
    
    public int frag_size;
    
    public int block_bitmap;
        
    public  int inode_bitmap;
        
    public int inode_table;
        
    public int free_blocks_count;
        
    public int free_inodes_count;
        
    public int used_dirs_count;
    
    
    public int uid;
    public int size;
    public int atime;
    public int ctime;
    public int mtime;
    public int dtime;
    public int gid;
    public int links_count;
    public int blocks;
    public int flags;
        
    
    
    Superblock(RandomAccessFile raf) {
        this.acessSuperBlock = raf;
        try{
            forsuperblock();
            forBlockGroupDescrip();
            forInode();
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
    
    //
    public void forsuperblock() throws IOException{
  
        inodes_count=CommonFunctions.searchBlock(1024,0,4,acessSuperBlock);
        //System.out.println(inodes_count);
        
        blocks_count=CommonFunctions.searchBlock(1028,0,4,acessSuperBlock);
        //System.out.println(blocks_count);
        
        first_data_block=CommonFunctions.searchBlock(1044,0,4,acessSuperBlock);
        
        log_block_size=CommonFunctions.searchBlock(1048,0,4,acessSuperBlock);
        //System.out.println(log_block_size);
        //block size = 1024 << s_log_block_size;
        block_size = 1024 << log_block_size;
        
        log_frag_size=CommonFunctions.searchBlock(1052,0,4,acessSuperBlock);
        if( log_frag_size > 0 ){
            frag_size  = 1024 << log_frag_size;
        }
        else{
            frag_size = 1024 >> -log_frag_size;
        }
        //System.out.println(log_frag_size);
        
        blocks_per_group=CommonFunctions.searchBlock(1056,0,4,acessSuperBlock);
        //System.out.println(blocks_per_group);
        
        frags_per_group=CommonFunctions.searchBlock(1060,0,4,acessSuperBlock);
        //System.out.println(frags_per_group);
        
        inodes_per_group=CommonFunctions.searchBlock(1064,0,4,acessSuperBlock);
        //System.out.println(inodes_per_group);
         //((1024<<s_log_block_size)/s_inode_size)
        
        first_ino=CommonFunctions.searchBlock(1108,0,4,acessSuperBlock);
        //System.out.println(first_ino);
        
        inode_size=CommonFunctions.searchBlock(1112,0,2,acessSuperBlock); //default is 128 TODO
        //inode_size=128;
        //System.out.println(inode_size);
        
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
    public void forInode() throws IOException{//not done/corect...yet
        //Missing the corrct start location for the Inode table
        // inode
       uid=CommonFunctions.searchBlock(this.inode_table+2,0,2,acessSuperBlock);
        //System.out.println(i_uid);
        
        size=CommonFunctions.searchBlock(this.inode_table+4,0,4,acessSuperBlock);
        //System.out.println( i_size);
        
         atime=CommonFunctions.searchBlock(this.inode_table+8,0,4,acessSuperBlock);
        //System.out.println(i_atime);
        
        ctime=CommonFunctions.searchBlock(this.inode_table+12,0,4,acessSuperBlock);
        //System.out.println(i_ctime);
        
        mtime=CommonFunctions.searchBlock(this.inode_table+16,0,4,acessSuperBlock);
        //System.out.println( i_mtime);
        
        dtime=CommonFunctions.searchBlock(this.inode_table+20,0,4,acessSuperBlock);
        //System.out.println(i_dtime);
        
        gid=CommonFunctions.searchBlock(this.inode_table+24,0,2,acessSuperBlock);
        //System.out.println( i_gid);
        
        links_count=CommonFunctions.searchBlock(this.inode_table+26,0,2,acessSuperBlock);
        //
        blocks=CommonFunctions.searchBlock(this.inode_table+28,0,60,acessSuperBlock);
        //System.out.println(i_blocks);
        
        flags=CommonFunctions.searchBlock(this.inode_table+32,0,4,acessSuperBlock);
        //System.out.println(i_flags);
        
    }
                          
}
