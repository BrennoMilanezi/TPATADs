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
public class RegDados {
    
    private String eng;
    private String pt;
    
    public RegDados(String eng, String pt){
        this.eng = eng;
        this.pt = pt;
    }
    
    public String getEng(){
        return this.eng;
    }
    
    public String getPt(){
        return this.pt;
    }
    
    public void setEng(String palavra){
        eng = palavra;
    }
    
    public void setPt(String palavra){
        pt = palavra;
    }
}
