import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO EVERYTHING
class Superblock {
    //location of VD for my computer change for user
    public RandomAccessFile acessSuperBlock;//File path needs to be added
       // ("F:\\Fall2019\\CSC\\CSC400\\400Project\\src\\virtdisk (1)","r");
    
    public int inodes_count;
        
    public int blocks_count;
        
    public int log_block_size;
        
    public int log_frag_size;
        
    public int blocks_per_group;
        
    public int frags_per_group;
        
    public int inodes_per_group;
        
    public int first_ino;
        
    public int inode_size;
    
    
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
        return -1;
    }
    //
    public void forsuperblock() throws IOException{
        
        inodes_count=searchBlock(1024,0,4);
        //System.out.println(inodes_count);
        
        blocks_count=searchBlock(1028,0,4);
        //System.out.println(blocks_count);
        
        log_block_size=searchBlock(1048,0,4);
        //System.out.println(log_block_size);
        
        log_frag_size=searchBlock(1052,0,4);
        //System.out.println(log_frag_size);
        
        blocks_per_group=searchBlock(1056,0,4);
        //System.out.println(blocks_per_group);
        
        frags_per_group=searchBlock(1060,0,4);
        //System.out.println(frags_per_group);
        
        inodes_per_group=searchBlock(1064,0,4);
        //System.out.println(inodes_per_group);
        
        first_ino=searchBlock(1108,0,4);
        //System.out.println(first_ino);
        
        inode_size=searchBlock(1112,0,4);
        //System.out.println(inode_size);
        
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
    public void forInode() throws IOException{//not done/corect...yet
        //Missing the corrct start location for the Inode table
        // inode
       uid=searchBlock(this.inode_table+2,0,2);
        //System.out.println(i_uid);
        
        size=searchBlock(this.inode_table+4,0,4);
        //System.out.println( i_size);
        
         atime=searchBlock(this.inode_table+8,0,4);
        //System.out.println(i_atime);
        
        ctime=searchBlock(this.inode_table+12,0,4);
        //System.out.println(i_ctime);
        
        mtime=searchBlock(this.inode_table+16,0,4);
        //System.out.println( i_mtime);
        
        dtime=searchBlock(this.inode_table+20,0,4);
        //System.out.println(i_dtime);
        
        gid=searchBlock(this.inode_table+24,0,2);
        //System.out.println( i_gid);
        
        links_count=searchBlock(this.inode_table+26,0,2);
        //
        blocks=searchBlock(this.inode_table+28,0,60);
        //System.out.println(i_blocks);
        
        flags=searchBlock(this.inode_table+32,0,4);
        //System.out.println(i_flags);
        
    }
                          
}
