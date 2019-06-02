package ifes.bsi.tpa.taddic;
/*
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class HashEngineDefault extends Hash_engine {
    @Override
    public long hash_func(Object o) {
        long hash = 0;
        /*try {
            byte[] array_by = array_bytes(o);
            String s = getString(array_by);
            for (int i = 0; i < s.length(); i++) {
                hash = hash + (int)s.charAt(i);
            }
        } catch (IOException ex) {
            Logger.getLogger(HashEngineDefault.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HashEngineDefault.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        String s = o.toString();
        for (int i = 0; i < s.length(); i++) {
            hash = hash + (int)s.charAt(i);
        }
        return hash;
    }
    
    
    /*public byte[] array_bytes(Object o) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
          out = new ObjectOutputStream(bos);   
          out.writeObject(o);
          out.flush();
          byte[] yourBytes = bos.toByteArray();
          return yourBytes;
        } finally {
          try {
            bos.close();
          } catch (IOException ex) {
            // ignore close exception
          }
        }
    }
    
    public String getString(byte[] array_bytes) throws ClassNotFoundException, IOException{
        ByteArrayInputStream bis = new ByteArrayInputStream(array_bytes);
        ObjectInput in = null;
        try {
          in = new ObjectInputStream(bis);
          String s = (String) in.readObject(); 
          return s;
        } finally {
          try {
            if (in != null) {
              in.close();
            }
          } catch (IOException ex) {
          }
        }
    }*/
}
