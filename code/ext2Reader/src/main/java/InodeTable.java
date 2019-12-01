import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 *
 * @author joshua
 */
class InodeTable {
    
    class inode{
        public int inode = -1;
        public int rec_len = -1;
        public int name_len = -1;
        public int file_type = -1;
        public String name = "";
        
        long reverseBytes(RandomAccessFile raf, int len){
            byte[] b  = new byte[len];
            try{
                raf.read(b);
            }
            catch(IOException e){
                System.out.println(e);   
            }
            ByteBuffer wrapped = ByteBuffer.wrap(b);
            wrapped.order(ByteOrder.LITTLE_ENDIAN);
            try{
                wrapped.getLong();
            }catch(java.nio.BufferUnderflowException e1){
                try{
                    return wrapped.getInt();
                }
                catch(java.nio.BufferUnderflowException e2){
                    try{
                        return wrapped.getShort();
                    }
                    catch(java.nio.BufferUnderflowException e3){
                        System.out.println(e3);
                    }
                }  
            }
            return -1;
        }
        
        inode(RandomAccessFile raf){
            try{
                this.inode = (int)reverseBytes(raf,4);//raf.readInt();
                this.rec_len = (int)reverseBytes(raf,2);
                this.name_len = raf.read();
                this.file_type = raf.read();
                for(int i=0;i<this.name_len;i++){
                    int c = raf.read();
                    //System.out.println(String.valueOf(c));
                    this.name += String.valueOf((char)c);
                }
                raf.skipBytes(this.rec_len-this.name_len-8);
                //System.out.println(name);
            }
            catch(IOException e){
                System.out.println(e);
            }
        }
        
    }
    
    long reverseBytes(RandomAccessFile raf, int len){
            byte[] b  = new byte[len];
            try{
                raf.read(b);
            }
            catch(IOException e){
                System.out.println(e);   
            }
            ByteBuffer wrapped = ByteBuffer.wrap(b);
            wrapped.order(ByteOrder.LITTLE_ENDIAN);
            try{
                wrapped.getLong();
            }catch(java.nio.BufferUnderflowException e1){
                try{
                    return wrapped.getInt();
                }
                catch(java.nio.BufferUnderflowException e2){
                    try{
                        return wrapped.getShort();
                    }
                    catch(java.nio.BufferUnderflowException e3){
                        System.out.println(e3);
                    }
                }  
            }
            return -1;
        }
    
    int getNumBytes(RandomAccessFile raf, int length) throws IOException{
        int x = 0;              
            int byteRead=0;
            //for loop to assign read integer into an array
            //reads the needed bytes based on the given length
            //for (int i=0; i<length;i++){
              // //int y=raf.read();
              // byte b = raf.readByte();
              // byteRead+=b; //+raf.read();
              // x++;
             byte[] b = new byte[length];
             raf.read(b);
            ByteBuffer wrapped = ByteBuffer.wrap(b);
            wrapped.order(ByteOrder.LITTLE_ENDIAN);
            byteRead = wrapped.getInt();
            return byteRead;
    }
    //http://homepage.smc.edu/morgan_david/cs40/analyze-ext2.htm
    //http://cs.smith.edu/~nhowe/262/oldlabs/ext2.html

    public List<inode> folders;
    public List<inode> files;
    
    InodeTable(RandomAccessFile raf,Superblock sup,GroupDescriptor gd,int inodeTable) {
        this.folders = new ArrayList<inode>();
        this.files = new ArrayList<inode>();
        long startAddress = CommonFunctions.findByteInodeOffset(sup, gd, inodeTable);
        try{

            raf.seek(startAddress);
            // go to where the locatoin of the data is 
            raf.skipBytes(4);
            int size = (int)reverseBytes(raf,4);
            raf.skipBytes(32);
            int[] loc = new int[15];
            for(int i=0;i<loc.length;i++){
                loc[i] = getNumBytes(raf,4);
            }
            raf.seek((loc[0]*sup.block_size));
            while(size > 0){
                inode in = new inode(raf); 
                size -= in.rec_len;
                if(in.file_type == 2){
                    folders.add(in);
                }
                else{
                    files.add(in);
                }
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
    
}
