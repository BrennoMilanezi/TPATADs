/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.taddic;

/**
 *
 * @author User
 */
public class TDicItem {
    
    private Object key;
    private Object dado;
    private long cach_hash;
    
    public TDicItem(Object chave, Object valor, long hash){
        this.key = chave;
        this.dado = valor;
        this.cach_hash = hash;
    }

    public Object getKey(){
        return key;
    }
    
    public Object getDado(){
        return dado;
    }
    
    public long getHash(){
        return cach_hash;
    }
    
    public void setDado(Object o){
        dado = o;
    }
    
    public void setCash_Hash(long h){
        cach_hash = h;
    }
    
}
