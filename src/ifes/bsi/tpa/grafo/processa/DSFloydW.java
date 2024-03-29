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
        int linha = 0;
        int coluna = 0;
        for(int i = 0; i < lst_vet_label.length; i++){
            if(lst_vet_label[i].equals(destino)){
                linha = i;
            }
            if(lst_vet_label[i].equals(origem)){
                coluna = i;
            }
        }
        return mat_custos[linha][coluna];
    }
    
    @Override
    public LinkedList<String> caminho​(String origem, String destino){
        LinkedList<String> caminho_min = new LinkedList<>();
        for(int i = 0; i < lst_vet_label.length; i++){
            caminho_min.add(lst_vet_label[i]);
            if(lst_vet_label[i].equals(destino)){
                break;
            }
        }
        return caminho_min;
    }
}
