/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo.processa;

import ifes.bsi.tpa.grafo.Vertex;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class DSFloydW extends DataSet{
    
    private int[][] mat_custos = null;
    private String[] lst_vet_label = null;
    
    public DSFloydW​(int[][] matdist, String[] vet_label){
       mat_custos = matdist;
       lst_vet_label = vet_label;
    }
    
    @Override
    public int custo​(String origem, String destino){
        return 1;
    }
    
    @Override
    public LinkedList<String> caminho​(String origem, String destino){
        return new LinkedList<>();
    }
}
