/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo.processa;

import ifes.bsi.tpa.grafo.Edge;
import ifes.bsi.tpa.grafo.TADGrafoDV3;
import ifes.bsi.tpa.grafo.Vertex;
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
        /*retorna uma lista contendo os vertices
        encontrados durante uma busca em PROFUNDIDADE a partir do vertice label do grafo g*/
        Vertex ov = grafo.getVertex(label);
        if(ov != null){
            LinkedList<Vertex> lst_v = new LinkedList<>();
            LinkedList<Vertex> pilha = new LinkedList<>();
            pilha.add(ov);
            while(pilha.size() > 0){
                Vertex v_pilha = pilha.pollLast();
                LinkedList<Vertex> list = grafo.outAdjacenteVertices(v_pilha.getLabel());
                if(!lst_v.contains(v_pilha)){
                    lst_v.add(v_pilha);
                }
                if(pilha.size() > 0){
                    for(int i = 0; i < list.size(); i++){
                        if(!lst_v.contains(list.get(i))){
                            pilha.add(list.get(i));
                        }
                    }
                }
            }
            return lst_v;
        }else{
            return null;
        }
    }

    public LinkedList<Vertex> bsf(String label){
        /*retorna uma lista contendo os vertices 
            encontrados durante uma busca em LARGURA a partir do vertice label do grafo g*/
        Vertex ov = grafo.getVertex(label);
        if(ov != null){
            LinkedList<Vertex> lst_v = new LinkedList<>();
            LinkedList<Vertex> fila = new LinkedList<>();
            fila.add(ov);
            while(fila.size() > 0){
                Vertex v_fila = fila.pop();
                LinkedList<Vertex> list = grafo.outAdjacenteVertices(v_fila.getLabel());
                if(!lst_v.contains(v_fila)){
                    lst_v.add(v_fila);
                }
                if(fila.size() > 0){
                    for(int i = 0; i < list.size(); i++){
                        if(!lst_v.contains(list.get(i))){
                            fila.add(list.get(i));
                        }
                    }
                }
            }
            return lst_v;
        }else{
            return null;
        }
    }
    
    public DSDijkstra cmBFord(String label_origem){
        Vertex v_origem = grafo.getVertex(label_origem);
        if(v_origem == null){
            return null;
        }
        
        LinkedList<Vertex> list_vertices = grafo.vertices();
        int[] custos = new int[list_vertices.size()];
        String[] antecessores = new String[list_vertices.size()];
        
        for(int i = 0; i < list_vertices.size(); i++){
            if(list_vertices.get(i).getLabel().equals(label_origem)){
                custos[i] = 0;
            }else{
                custos[i] = Integer.MAX_VALUE;
            }
        }
        
        for(int i = 0; i < list_vertices.size(); i++){
            int[] custos_temp = custos.clone();
            String[] antecessores_temp = antecessores.clone();
            for(int j = 0; j < custos_temp.length; j++){
                if(custos_temp[j] != Integer.MAX_VALUE){
                    Vertex v1 = list_vertices.get(j);
                    LinkedList<Vertex> lst_vertices_adj = grafo.adjacentVertices(v1.getLabel());
                    for(int k = 0; k < lst_vertices_adj.size(); k++){
                        Vertex v2 = lst_vertices_adj.get(k);
                        Edge e = this.grafo.getEdge(v1.getLabel(), v2.getLabel());
                        if(e != null){
                            int soma_custos = custos_temp[indiceListVertex(v1.getLabel())] + Integer.parseInt(e.getDado().toString());
                            if(custos_temp[indiceListVertex(v2.getLabel())] > soma_custos){
                                custos_temp[indiceListVertex(v2.getLabel())] = soma_custos;
                                antecessores_temp[indiceListVertex(v2.getLabel())] = v2.getLabel();
                            }
                        }
                    }
                }
            }
            if(custos.equals(custos_temp)){
                custos = custos_temp;
                antecessores = antecessores_temp;
                break;
            }else{
                custos = custos_temp;
                antecessores = antecessores_temp;
            }
        }   
        
        return new DSDijkstra(custos, antecessores);
    }
    
    public DSDijkstra cmDijkstra(String label_origem){
        Vertex v_origem = grafo.getVertex(label_origem);
        if(v_origem == null){
            return null;
        }
        
        LinkedList<Vertex> list_vertices = grafo.vertices();
        int[] custos = new int[list_vertices.size()];
        String[] antecessores = new String[list_vertices.size()];
        LinkedList<Vertex> naoVisitados = new LinkedList<>();
        int indice = -1;
        
        for(int i = 0; i < list_vertices.size(); i++){
            // Vertice origem tem distancia zero, e todos os outros Integer.MAX_VALUE("infinita")
            if(list_vertices.get(i).getLabel().equals(label_origem)){
                custos[i] = 0;
                indice = i;
            }else{
                custos[i] = Integer.MAX_VALUE;
            }
            // Insere o vertice na lista de vertices nao visitados
            naoVisitados.add(list_vertices.get(i));
        }
        
        antecessores[indice] = label_origem;
        
        while (!naoVisitados.isEmpty()) {
            int[] custos_temp = custos.clone();
            String[] antecessores_temp = antecessores.clone();
            LinkedList<Vertex> lst_vertices_adj = grafo.adjacentVertices(list_vertices.get(indice).getLabel()); 
            for(int k = 0; k < lst_vertices_adj.size(); k++){
                Vertex v = lst_vertices_adj.get(k);
                Edge edge = grafo.getEdge(list_vertices.get(indice).getLabel(), v.getLabel());
                if(edge != null && naoVisitados.contains(indiceListVertex(v.getLabel()))){
                    int custo_aux_vert = custos[indiceListVertex(v.getLabel())];
                    if(custo_aux_vert > custos[indice] + Integer.parseInt(edge.getDado().toString())){
                        custos_temp[indiceListVertex(v.getLabel())] = custos[indice] + Integer.parseInt(edge.getDado().toString());
                        antecessores_temp[indiceListVertex(v.getLabel())] = v.getLabel();
                    }
                }
            }
            custos = custos_temp;
            antecessores = antecessores_temp;
            if(!naoVisitados.isEmpty()){
                int aux_custo = Integer.MAX_VALUE;
		indice = -1;
		for(int i = 0; i < custos.length; i++){
                    if(aux_custo > custos[i]) {
                        aux_custo = custos[i];
                        indice = i;
                    }
		}
            }
        }
        
        return new DSDijkstra(custos, antecessores);
    }
    
    private int indiceListVertex(String v) {
        LinkedList<Vertex> list_vertices = grafo.vertices();
        for(int i = 0; i < list_vertices.size(); i++){
            if(grafo.vertices().get(i).getLabel().equals(v)){
                return i;
            }
        }
        return -1;
    }
    
    public DSFloydW cmFWarshall(){
        LinkedList<Vertex> list_vertices = grafo.vertices();
        int[][] matdist = new int[list_vertices.size()][list_vertices.size()];
        String[] vet_label =new String[list_vertices.size()];
        for(int i = 0; i < list_vertices.size(); i++){
            Vertex v1 = list_vertices.get(i);
            for(int j = 0; j < list_vertices.size(); j++){
                Vertex v2 = list_vertices.get(j);
                if(v1.getID() == v2.getID()){
                    matdist[i][j] =  0;
                }else{
                    Edge edge = grafo.getEdge(v1.getLabel(), v2.getLabel());
                    if(edge != null){
                        matdist[i][j] =  Integer.parseInt(edge.getDado().toString());
                    }else{
                        matdist[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }
 
        for(int k = 0; k < list_vertices.size(); k++){
            for(int i = 0; i < list_vertices.size(); i++){
                for (int j = 0; j < list_vertices.size(); j++){
                    if (matdist[i][k] + matdist[k][j] < matdist[i][j]) {
                        matdist[i][j] = matdist[i][k] + matdist[k][j];
                    }
                }
            }
        }
        
        return new DSFloydW(matdist, vet_label);
    }
    
    

}