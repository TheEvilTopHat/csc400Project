import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO EVERYTHING
class Inode {
    //location of VD for my computer change for user
    public RandomAccessFile acessSuperBlock;//File path needs to be added
       // ("F:\\Fall2019\\CSC\\CSC400\\400Project\\src\\virtdisk (1)","r");

    
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
    public int inode_table;
        
    
    
    Inode(RandomAccessFile raf,int inodeTable) {
        this.acessSuperBlock = raf;
        inode_table = inodeTable;
        try{
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
        return 404 ;
    }
    //
  
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
