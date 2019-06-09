/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import ifes.bsi.tpa.taddic.TADDicChain;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class TADGrafo {
    private int mat[][] = null;
    private int quantVertices = 0;
    private int quantEdges = 0;
    private String nome;
    private int primeiroVertice = 0;
    private int ultimoVertice = 0;
    private int IDedge = 0;
    private int IDvertex = 0;
    private LinkedList<Integer> list_eliminados;
    private TADDicChain dicVertex = new TADDicChain();
    private TADDicChain dicEdge = new TADDicChain();

    public TADGrafo(String nome_mat) {
        this.mat = new int[16][16];
        this.list_eliminados = new LinkedList<>();
        this.nome = nome_mat;
    }
    
    public TADGrafo(String nome_mat, int tam) {
        this.mat = new int[tam][tam];
        this.list_eliminados = new LinkedList<>();
        this.nome = nome_mat;
    }
    
    public void printGrafo(){
        for(int i = primeiroVertice; i < ultimoVertice; i++){
            if(!list_eliminados.contains(i)){
                for(int k=primeiroVertice; k < ultimoVertice; k++){
                    System.out.print(String.format("%04d", mat[i][k]));
                    System.out.print(" ");
                }
                System.out.println(" ");
            }
        }
    }
    
    public int numVertice(){
       return quantVertices;
    }
    
    public int numEdges(){
       return quantEdges;
    }
    
    public Vertex getVertex(String l){
        Vertex v = (Vertex) (dicVertex.findElement(l));
        if(dicVertex.NO_SUCH_KEY()){
            return null;
        }else{
            return v;
        }
    }
    
    public Edge getEdge(String u, String v){

        Vertex ou = getVertex(u);
        if(dicVertex.NO_SUCH_KEY()){
            return null;
        }

        Vertex ov = getVertex(v);
        if(dicVertex.NO_SUCH_KEY()){
            return null;
        }

        int idE = mat[ou.getID()][ov.getID()];
        Edge e = inToEdge(idE);
        return e;
    }
    
    public boolean areAdjacentes(String u, String v){
        Vertex ou = getVertex(u);
        if(dicVertex.NO_SUCH_KEY()){
            return false;
        }

        Vertex ov = getVertex(v);
        if(dicVertex.NO_SUCH_KEY()){
            return false;
        }
        
        if(mat[ou.getID()][ov.getID()] != 0){
            return true;
        }else{
            return false;
        } 
    }
    
    public Vertex inToVertex(int id){
        LinkedList<Object> list = dicVertex.elements();
        for(int i = 0; i< list.size(); i++){
           Vertex ov = (Vertex)list.get(i);
           if(ov.getID() == id){
               return ov;
           }
        }
        return null;
    }
    
    public Edge inToEdge(int id){
        LinkedList<Object> list = dicEdge.elements();
        for(int i = 0; i< list.size(); i++){
           Edge oe = (Edge)list.get(i);
           if(oe.getID() == id){
               return oe;
           }
        }
        return null;
    }
    
    public Vertex[] endVertices(String l){
        Edge oe = (Edge) (dicEdge.findElement(l));
        for(int i = primeiroVertice; i < ultimoVertice; i++){
            for(int k=primeiroVertice; k < ultimoVertice; k++){
                if(mat[i][k] == oe.getID()){
                    Vertex[] v = new Vertex[2];
                    Vertex oi = this.inToVertex(i);
                    Vertex ok = this.inToVertex(k);
                    v[0] = oi;
                    v[1] = ok;
                    return v;
                }
            }
        }
        return null;
    }
    
    public Vertex destination(String l){
        Vertex[] v = endVertices(l);
        return v[1];
    }
    
    public Vertex origin(String l){
        Vertex[] v = endVertices(l);
        return v[0];
    }
    
    public LinkedList<Edge> inIncidentEdges(String l){
        Vertex ov = getVertex(l);
        LinkedList<Edge> list = null;
        for(int j=primeiroVertice; j < ultimoVertice; j++){
            if(mat[ov.getID()][j] != 0){
                list.add(inToEdge(mat[ov.getID()][j]));
            }
        }
        return list;
    }
    
    public LinkedList<Edge> outIncidentEdges(String l){
        Vertex ov = getVertex(l);
        LinkedList<Edge> list = null;
        for(int j=primeiroVertice; j < ultimoVertice; j++){
            if(mat[j][ov.getID()] != 0){
                list.add(inToEdge(mat[j][ov.getID()]));
            }
        }
        return list;
    }
    
    public LinkedList<Vertex> inAdjacenteVertex(String l){
        Vertex ov = getVertex(l);
        LinkedList<Vertex> list = null;
        for(int j=primeiroVertice; j < ultimoVertice; j++){
            if(mat[ov.getID()][j] != 0){
                list.add(inToVertex(j));
            }
        }
        return list;
    }
    
    public LinkedList<Vertex> outAdjacenteVertex(String l){
        Vertex ov = getVertex(l);
        LinkedList<Vertex> list = null;
        for(int j=primeiroVertice; j < ultimoVertice; j++){
            if(mat[j][ov.getID()] != 0){
                list.add(inToVertex(j));
            }
        }
        return list;
    }
    
    public LinkedList<Edge> incidentEdges(String l){
        LinkedList<Edge> list = this.inIncidentEdges(l);
        list.addAll(this.outIncidentEdges(l));
        return list;
    }
    
    public LinkedList<Vertex> adjacentVertices(String l){
        LinkedList<Vertex> list = this.inAdjacenteVertex(l);
        list.addAll(this.outAdjacenteVertex(l));
        return list;
    }
    
    public Vertex opposite(String v, String e){

        Vertex ov = getVertex(v);
        if(dicVertex.NO_SUCH_KEY()){
            return null;
        }
        
        Edge oe = (Edge) (dicEdge.findElement(e));
        if(dicEdge.NO_SUCH_KEY()){
            return null;
        }

        for(int i = primeiroVertice; i < ultimoVertice; i++){

            if(!list_eliminados.contains(ov.getID()) && mat[ov.getID()][i] == oe.getID()){
                LinkedList<Object> lst = dicVertex.keys();
                for(int m = 0; m < lst.size(); m++){
                    Vertex ovv = (Vertex) dicVertex.findElement(lst.get(m));
                    if(ovv.getID() == i){
                        return ovv;
                    }
                }
            }
        }
        return null;
        
    }
    
    public int outDegree(String v){
        int grau = 0;

        Vertex ov = getVertex(v);
        if(dicVertex.NO_SUCH_KEY()){
            return grau;
        }
        
        for(int i = primeiroVertice; i < ultimoVertice; i++){
            if(mat[ov.getID()][i] != 0){
                grau++;
            }
        }
        return grau;
    }
    
    public int inDegree(String v){
        int grau = 0;

        Vertex ov = getVertex(v);
        if(dicVertex.NO_SUCH_KEY()){
            return grau;
        }

        for(int i = primeiroVertice; i < ultimoVertice; i++){
            if(mat[i][ov.getID()] != 0){
                grau++;
            }
        }
        return grau;
    }
    
    public Edge geraEdge(String l, Object o){
        int idEdge = IDedge++;
        Edge e = new Edge(idEdge, l, o); 
        dicEdge.insertItem(l, e);
        return e;
    }
    
    public Vertex geraVertex(String l, Object o){
        int idVertex = IDvertex++;
        Vertex v = new Vertex(idVertex, l, o);
        dicVertex.insertItem(l, v);
        return v;
    }
    
    public Edge insertEdge(String u, String v, String label, Object o){
        
        Vertex ou =  getVertex(u);
        if(dicVertex.NO_SUCH_KEY()){
            return null;
        }
        
        Vertex ov =  getVertex(v);
        if(dicVertex.NO_SUCH_KEY()){
            return null;
        }
        
        Edge e = geraEdge(label, o);
        mat[ou.getID()][ov.getID()] = e.getID();
        quantEdges++;
        return e;
        
    }
    
    public void removeEdge(String e){

        Edge oe = (Edge) (dicEdge.findElement(e));
        if(!dicEdge.NO_SUCH_KEY()){
        
            for(int i = primeiroVertice; i < ultimoVertice; i++){
                for(int k=primeiroVertice; k < ultimoVertice; k++){
                    if(mat[i][k] == oe.getID()){
                        mat[i][k] = 0;
                        quantEdges--;
                        dicEdge.removeElement(oe);
                    }
                }
            }
        }
    }
    
    public Vertex insertVertex(String label, Object o){
        Vertex v =  getVertex(label);
        if(v != null){
            return null;
        }else{
            v = geraVertex(label, o);
            int idV = v.getID();
            if(idV > ultimoVertice){
                ultimoVertice = idV;
            }

            if(idV < primeiroVertice){
                primeiroVertice = idV;
            }
            quantVertices++;
        }
        
        return v;
    }
    
    public Vertex removeVertex(String v){

        Vertex ov =  getVertex(v);
        if(!dicVertex.NO_SUCH_KEY()){
            list_eliminados.add(ov.getID());
            
            if(ov.getID() == primeiroVertice){
                for(int i=primeiroVertice+1; i <= ultimoVertice; i++){
                    if(!list_eliminados.contains(i)){
                        primeiroVertice = i;
                        break;
                    }
                }
            }
            
            if(ov.getID() == ultimoVertice){
                for(int i=ultimoVertice-1; i >= primeiroVertice; i--){
                    if(!list_eliminados.contains(i)){
                        ultimoVertice = i;
                        break;
                    }
                }
            }
            
            for(int i=primeiroVertice; i < ultimoVertice; i++){
                if(mat[ov.getID()][i] != 0){
                    mat[ov.getID()][i] = 0;
                    quantEdges--;
                }
                if(mat[i][ov.getID()] != 0 && mat[ov.getID()][i] != mat[i][ov.getID()]){
                    mat[i][ov.getID()] = 0;
                    quantEdges--;
                }
            }
            
            quantVertices--;
            dicVertex.removeElement(ov);
            return ov;
        }else{
            return null;
        }
    }
}
