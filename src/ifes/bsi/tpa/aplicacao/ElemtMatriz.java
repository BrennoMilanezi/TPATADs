/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.aplicacao;
/**
 *
 * @author User
 */
public class ElemtMatriz {
    
    private int lin;
    private int col;
    
    public ElemtMatriz(int l, int c){
        this.lin = l;
        this.col = c;
    }
    
    public int getLin() {
        return lin;
    }

    public int getCol() {
        return col;
    }
    
    public void setLin(int l) {
        this.lin = l;
    }

    public void setCol(int c) {
        this.col = c;
    }
}
