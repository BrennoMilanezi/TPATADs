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
public class DSDijkstra extends DataSet{
    
    private int[] vet_custos = null;
    private String[] vet_antecessores = null;
    
    public DSDijkstra​(int[] custos,String[] antecessores){
        vet_custos = custos;
        vet_antecessores = antecessores;
    }
    
    @Override
    public int custo​(String origem, String destino){
        int indice = 0;
        for(int i = 0; i < vet_antecessores.length; i++){
            if(vet_antecessores[i].equals(destino)){
                indice = i;
            }
        }
        return vet_custos[indice];
    }
    
    @Override
    public LinkedList<String> caminho​(String origem, String destino){
        LinkedList<String> caminho_min = new LinkedList<>();
        for(int i = 0; i < vet_antecessores.length; i++){
            caminho_min.add(vet_antecessores[i]);
            if(vet_antecessores[i].equals(destino)){
                break;
            }
        }
        return caminho_min;
    }
}
