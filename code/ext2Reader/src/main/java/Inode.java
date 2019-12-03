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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO EVERYTHING
class Inode {
    //location of VD for my computer change for user
    public RandomAccessFile raf;//File path needs to be added
       // ("F:\\Fall2019\\CSC\\CSC400\\400Project\\src\\virtdisk (1)","r");

    public long byteOffset;
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
    public byte[] data;
    
    Inode(Superblock sb, GroupDescriptor gd, RandomAccessFile raf, int inode) {
        this.raf = raf;
        byteOffset = CommonFunctions.findByteInodeOffset(sb, gd, inode);
        try{
            forInode();
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
  
    public void forInode() throws IOException{//not done/corect...yet
        
        size=CommonFunctions.searchBlock(byteOffset+4,0,4,raf);
        //go to byte offset
        raf.seek(byteOffset+40);
        //read direct blocks
        int[] directBlocks = new int[12]; //first 12 direct
        
        //get block num from direct blocks
        for(int i = 0; i<directBlocks.length; i++){
            //get bytes from each block
            directBlocks[i] = CommonFunctions.searchBlock((40+(int)byteOffset+(i*4)),0,4,raf);
        }
        //todo get blocks num from non direct blocks
        
        //get the raw data from the bytes starting with direct blocks
        int bytesToRead = size;
        this.data = new byte[size];
        for(int i = 0; i<directBlocks.length; i++){
            byte[] block = new byte[1024];//block size
            //get bytes from each block
            raf.seek(directBlocks[i]*1024);
            raf.read(block);
            for(int j=0; j<block.length; j++){
                if(bytesToRead > 0){
                    data[size-bytesToRead] = block[j];
                    bytesToRead--;
                }
            }
            
        }
        System.out.println("TODO");
        
    }      
        
}
