/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import java.util.LinkedList;

/**
 *
 * @author User
 */
public class ProcessaGrafo {

    private TADGrafoDV3 grafo;

    public ProcessaGrafo(TADGrafoDV3 g){
            this.grafo = g;
    }

    public LinkedList<Vertex> dsf(String label){
        Vertex ov = grafo.getVertex(label);
        LinkedList<Vertex> v = new LinkedList<>();
        LinkedList<Vertex> pilha = new LinkedList<>();
        pilha.add(ov);
        v.add(ov);
        while(pilha != null){
            LinkedList<Vertex> list = grafo.outAdjacenteVertices((pilha.pollLast()).getLabel());
            for(int i = 0; i < list.size(); i++){
                if(!v.contains(list.get(i))){
                    v.add(list.get(i));
                    pilha.add(list.get(i));
                }
            }
        }
        /*retorna uma lista contendo os vertices
        encontrados durante uma busca em PROFUNDIDADE a partir do vertice label do grafo g*/
        return v;
    }

    public LinkedList<Vertex> bsf(String label){
        Vertex ov = grafo.getVertex(label);
        LinkedList<Vertex> v = new LinkedList<>();
        LinkedList<Vertex> fila = new LinkedList<>();
        fila.add(ov);
        v.add(ov);
        while(fila != null){
            LinkedList<Vertex> list = grafo.outAdjacenteVertices((fila.pop()).getLabel());
            for(int i = 0; i < list.size(); i++){
                if(!v.contains(list.get(i))){
                    v.add(list.get(i));
                    fila.add(list.get(i));
                }
            }
        }
        /*retorna uma lista contendo os vertices 
            encontrados durante uma busca em LARGURA a partir do vertice label do grafo g*/
        return v;
    }

}