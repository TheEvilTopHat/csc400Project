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
    Superblock sb;
    GroupDescriptor gd;
    
    Inode(Superblock sb, GroupDescriptor gd, RandomAccessFile raf, int inode) {
        this.sb = sb;
        this.gd = gd;
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
        long[] directBlocks = new long[12]; //first 12 direct
        //get block num from direct blocks
        for(int i = 0; i<directBlocks.length; i++){
            //get bytes from each block
            directBlocks[i] = CommonFunctions.searchBlock((40+byteOffset+(i*4)),0,4,raf);
        }
        //todo get blocks num from non direct blocks
        //get 13 block
        int indirectBlock = CommonFunctions.searchBlock((40+48+byteOffset),0,4,raf);
        int doubleIndirect = CommonFunctions.searchBlock((40+52+byteOffset),0,4,raf);
        int tripIndirect = CommonFunctions.searchBlock((40+56+byteOffset),0,4,raf);
        
        long[] ibl = new long[sb.block_size/4]; //indirect block list
        //get the blocks from the indrectBlocks
        for(int i = 0; i< sb.block_size/4; i++ ){
            ibl[i] = CommonFunctions.searchBlock((indirectBlock*1024+(i*4)),0,4,raf);
        }
        long[] dbl = new long[sb.block_size/4]; //indirect block list
        //get the blocks from the indrectBlocks
        for(int i = 0; i< sb.block_size/4; i++ ){
            dbl[i] = CommonFunctions.searchBlock((doubleIndirect*1024+(i*4)),0,4,raf);
        }
        long[] tbl = new long[sb.block_size/4]; //indirect block list
        for(int i = 0; i< sb.block_size/4; i++ ){
            tbl[i] = CommonFunctions.searchBlock((tripIndirect*1024+(i*4)),0,4,raf);
        }
        //get the raw data from the bytes starting with direct blocks
        int bytesToRead = size;
        this.data = new byte[size];
        for(int i = 0; i<directBlocks.length; i++){
            byte[] block = new byte[1024];//block size
            //get bytes from each block
            raf.seek(directBlocks[i]*1024);
            //for(int j = 0; j<ibl.length; j++){
            //    block[j] = (byte)raf.readUnsignedByte();
            //}
            raf.read(block);
            for(int j=0; j<block.length; j++){
                if(bytesToRead > 0){
                    data[size-bytesToRead] = block[j];
                    bytesToRead--;
                }
            }           
        } 
            //indirect blocks
            for(int i = 0; i<ibl.length; i++){
                byte[] block = new byte[1024];//block size
                //get bytes from each block
                raf.seek(ibl[i]*1024);
                //for(int j = 0; j<ibl.length; j++){
                //    block[j] = (byte)raf.readUnsignedByte();
                //}
                raf.read(block);
                for(int j=0; j<block.length; j++){
                    if(bytesToRead > 0){
                        data[size-bytesToRead] = block[j];
                        bytesToRead--;
                    }
                }            
            }
            //double indirect block
            for(int i = 0; i<ibl.length; i++){
                byte[] block = new byte[1024];//block size
                //get bytes from each block
                for(int k=0; k<dbl.length; k++){
                    //long[] b = new long[sb.block_size/4]; //indirect block list
                    //get the blocks from the indrectBlocks
                    for(int h = 0; h< sb.block_size/4; h++ ){
                        long b = CommonFunctions.searchBlock((dbl[i]*1024+(h*4)),0,4,raf);
                        //b[i] = CommonFunctions.searchBlock((dbl[i]*1024+(h*4)),0,4,raf);
                        raf.seek(b*1024);
                        raf.read(block);
                        for(int j=0; j<block.length; j++){
                            if(bytesToRead > 0){
                                data[size-bytesToRead] = block[j];
                                bytesToRead--;
                            }
                            else{
                                return;
                            }
                        }
                    }
                        
                }
            }
      //  System.out.println("todo");
    }      
        
}
