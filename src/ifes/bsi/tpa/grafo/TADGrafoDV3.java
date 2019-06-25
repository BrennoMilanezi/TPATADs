/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import _my_tools.ArquivoTxt;
import ifes.bsi.tpa.taddic.TADDicChain;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class TADGrafoDV3 {
    private int mat[][];
    private int quantVertices = 0;
    private int quantEdges = 0;
    private String nome;
    private int primVertice = 0;
    private int ultiVertice = 0;
    private int IDedge = 1;
    private int IDvertex = 0;
    private LinkedList<Integer> lstEliminados;
    private TADDicChain dicLblVertex = new TADDicChain();
    private TADDicChain dicLblEdge = new TADDicChain();

    public TADGrafoDV3(String nome_mat) {
        this.mat = new int[16][16];
        this.lstEliminados = new LinkedList<>();
        this.nome = nome_mat;
    }
    
    public TADGrafoDV3(String nome_mat, int tam) {
        this.mat = new int[tam][tam];
        this.lstEliminados = new LinkedList<>();
        this.nome = nome_mat;
    }
    
    public void printgrafo(){
        for(int i = primVertice; i <= ultiVertice; i++){
            if(!lstEliminados.contains(i)){
                for(int k = primVertice; k <= ultiVertice; k++){
                    Vertex v = intToVertex(i);
                    Vertex v2 = intToVertex(k);
                    String aresta = "";
                    if(mat[i][k] != 0){
                        Edge e = intToEdge(mat[i][k]);
                        aresta = e.getLabel();
                    }
                    System.out.print(v.getLabel() + " - " + v2.getLabel() + " -> " + aresta);
                }
                System.out.println(" ");
            }
        }
    }

    public void printmat(){
        for(int i = primVertice; i <= ultiVertice; i++){
            if(!lstEliminados.contains(i)){
                for(int k = primVertice; k <= ultiVertice; k++){
                    System.out.print(String.format("%04d", mat[i][k]));
                    System.out.print(" ");
                }
                System.out.println(" ");
            }
        }
    }
    
    public int numVertices(){
       return quantVertices;
    }
    
    public int numEdges(){
       return quantEdges;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String n){
        this.nome = n;
    }
    
    public Vertex getVertex(String l){
        Vertex v = (Vertex) dicLblVertex.findElement(l);
        if(dicLblVertex.NO_SUCH_KEY()){
            return null;
        }else{
            return v;
        }
    }
    
    public Edge getEdge(String u){
        Edge e = (Edge) dicLblEdge.findElement(u);
        if(dicLblEdge.NO_SUCH_KEY()){
            return null;
        }else{
            return e;
        }
    }
    
    public Edge getEdge(String u, String v){

        Vertex ou = getVertex(u);
        if(ou == null){
            return null;
        }

        Vertex ov = getVertex(v);
        if(ov == null){
            return null;
        }

        int idE = mat[ou.getID()][ov.getID()];
        if(idE != 0){
            LinkedList<Object> lst = dicLblEdge.elements();
        
            for(int i = 0;i < lst.size();i++){
                Edge oe = (Edge)lst.get(i);
                if(idE == oe.getID()){
                    return oe;
                }
            }
        }
        return null;
    }
    
    public boolean areAdjacent​(String u, String v){
        Vertex ou = getVertex(u);
        if(ou == null){
            return false;
        }

        Vertex ov = getVertex(v);
        if(ov == null){
            return false;
        }
        
        if(mat[ou.getID()][ov.getID()] != 0){
            return true;
        }
        return false;
    }
    
    public Vertex intToVertex(int id){
        LinkedList<Object> list = dicLblVertex.elements();
        for(int i = 0; i < list.size(); i++){
           Vertex ov = (Vertex)list.get(i);
           if(ov.getID() == id){
               return ov;
           }
        }
        return null;
    }
    
    public Edge intToEdge(int id){
        LinkedList<Object> list = dicLblEdge.elements();
        for(int i = 0; i < list.size(); i++){
           Edge oe = (Edge)list.get(i);
           if(oe.getID() == id){
               return oe;
           }
        }
        return null;
    }

    public LinkedList<Edge> edges(){
        LinkedList<Object> lst = dicLblEdge.elements();
        LinkedList<Edge> lst_edge = new LinkedList<>();
        for(int i = 0; i < lst.size(); i++){
            Edge aux = (Edge)lst.get(i);
            lst_edge.add(aux);
        }
        return lst_edge;
    }

    public LinkedList<Vertex> vertices(){
        LinkedList<Object> lst = dicLblVertex.elements();
        LinkedList<Vertex> lst_vet = new LinkedList<>();
        for(int i = 0; i < lst.size(); i++){
            Vertex aux = (Vertex)lst.get(i);
            lst_vet.add(aux);
        }
        return lst_vet;
    }
    
    public Vertex[] endVertices(String l){
        Edge oe = (Edge) dicLblEdge.findElement(l);
        for(int i = primVertice; i <= ultiVertice; i++){
            for(int k = primVertice; k <= ultiVertice; k++){
                if(mat[i][k] == oe.getID()){
                    Vertex[] v = new Vertex[2];
                    Vertex oi = this.intToVertex(i);
                    Vertex ok = this.intToVertex(k);
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
        LinkedList<Edge> list = new LinkedList<>();
        for(int j = primVertice; j <= ultiVertice; j++){
            if(mat[j][ov.getID()] != 0){
                list.add(intToEdge(mat[j][ov.getID()]));
            }
        }
        return list;
    }
    
    public LinkedList<Edge> outIncidentEdges(String l){
        Vertex ov = getVertex(l);
        LinkedList<Edge> list = new LinkedList<>();
        for(int j = primVertice; j <= ultiVertice; j++){
            if(mat[ov.getID()][j] != 0){
                list.add(intToEdge(mat[ov.getID()][j]));
            }
        }
        return list;
    }
    
    public LinkedList<Vertex> inAdjacenteVertices(String l){
        Vertex ov = getVertex(l);
        LinkedList<Vertex> list = new LinkedList<>();
        for(int j = primVertice; j <= ultiVertice; j++){
            if(mat[j][ov.getID()] != 0){
                list.add(intToVertex(j));
            }
        }
        return list;
    }
    
    public LinkedList<Vertex> outAdjacenteVertices(String l){
        Vertex ov = getVertex(l);
        LinkedList<Vertex> list = new LinkedList<>();
        for(int j = primVertice; j <= ultiVertice; j++){
            if(mat[ov.getID()][j] != 0){
                list.add(intToVertex(j));
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
        LinkedList<Vertex> list = this.inAdjacenteVertices(l);
        list.addAll(this.outAdjacenteVertices(l));
        return list;
    }
    
    public Vertex opposite(String v, String e){

        Vertex ov = getVertex(v);
        if(ov == null){
            return null;
        }
        
        Edge oe = getEdge(e);
        if(oe == null){
            return null;
        }

        for(int i = primVertice; i <= ultiVertice; i++){

            if(!lstEliminados.contains(ov.getID()) && mat[ov.getID()][i] == oe.getID()){
                LinkedList<Object> lst = dicLblVertex.keys();
                for(int m = 0; m < lst.size(); m++){
                    Vertex ovv = (Vertex) dicLblVertex.findElement(lst.get(m));
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
        if(ov == null){
            return grau;
        }
        
        for(int i = primVertice; i <= ultiVertice; i++){
            if(mat[ov.getID()][i] != 0){
                grau++;
            }
        }
        return grau;
    }
    
    public int inDegree(String v){
        int grau = 0;

        Vertex ov = getVertex(v);
        if(ov == null){
            return grau;
        }

        for(int i = primVertice; i <= ultiVertice; i++){
            if(mat[i][ov.getID()] != 0){
                grau++;
            }
        }
        return grau;
    }

    public int degree(String v){
        return inDegree(v)+outDegree(v);   
    }
    
    public Edge geraEdge(String l, Object o){
        Edge e = new Edge(this.IDedge++, l, o); 
        dicLblEdge.insertItem(l, e);
        return e;
    }
    
    public Vertex geraVertex(String l, Object o){
        int idVertex;
        if(!(lstEliminados.isEmpty())) {
            idVertex = lstEliminados.get(0);
            lstEliminados.removeFirst();
        }else {
            idVertex = this.IDvertex++;
        }
        Vertex v = new Vertex(idVertex, l, o);
        dicLblVertex.insertItem(l, v);
        return v;
    }
    
    public Edge insertEdge(String u, String v, String label, Object o){
        
        Vertex ou =  getVertex(u);
        if(ou == null){
            return null;
        }
        
        Vertex ov =  getVertex(v);
        if(ov == null){
            return null;
        }
        Edge oe = getEdge(label);
        if(oe == null){
            oe = geraEdge(label, o);
            this.mat[ou.getID()][ov.getID()] = oe.getID();
            quantEdges++;
        }else{
            oe.setDado(o);
        }   
        return oe;
        
    }
    
    public Object removeEdge(String e){

        Edge oe = getEdge(e);
        if(oe != null){
        
            for(int i = primVertice; i <= ultiVertice; i++){
                for(int k = primVertice; k <= ultiVertice; k++){
                    if(mat[i][k] == oe.getID()){
                        mat[i][k] = 0;
                        quantEdges--;
                        dicLblEdge.removeElement(oe);
                        return oe.getDado();
                    }
                }
            }
        }
        return null;
    }
    
    public Vertex insertVertex(String label, Object o){
        Vertex v =  getVertex(label);
        if(v != null){
            return null;
        }else{
            v = geraVertex(label, o);
            int idV = v.getID();
            if(idV > ultiVertice){
                ultiVertice = idV;
            }

            if(idV < primVertice){
                primVertice = idV;
            }
            quantVertices++;
        }
        
        return v;
    }
    
    public Object removeVertex(String v){

        Vertex ov =  getVertex(v);
        if(ov != null){
            lstEliminados.add(ov.getID());
            
            if(ov.getID() == primVertice){
                for(int i = primVertice+1; i <= ultiVertice; i++){
                    if(!lstEliminados.contains(i)){
                        primVertice = i;
                        break;
                    }
                }
            }
            
            if(ov.getID() == ultiVertice){
                for(int i = ultiVertice-1; i >= primVertice; i--){
                    if(!lstEliminados.contains(i)){
                        ultiVertice = i;
                        break;
                    }
                }
            }
               
            for(int i = primVertice; i <= ultiVertice; i++){
                if(mat[ov.getID()][i] != 0){
                   mat[ov.getID()][i] = 0;
                   quantEdges--;
                }
                if(mat[i][ov.getID()] != 0 && (mat[ov.getID()][i] != mat[i][ov.getID()])){
                    mat[i][ov.getID()] = 0;
                    quantEdges--;
                }
            }
            
            quantVertices--;
            dicLblVertex.removeElement(ov);
            return ov.getDado();
        }else{
            return null;
        }
    }

    @Override
    public TADGrafoDV3 clone(){
        TADGrafoDV3 clone = new TADGrafoDV3("cloneg", this.numVertices());
        clone.lstEliminados = this.lstEliminados;
        LinkedList<Vertex> lst_vertex = this.vertices();
        LinkedList<Edge> lst_edges = this.edges();
        for(int i = 0; i < lst_vertex.size(); i++) {
            Vertex ov = lst_vertex.get(i);
            clone.insertVertex(ov.getLabel(), ov.getDado());
        }
        for(int j = 0; j < lst_edges.size(); j++) {
            Edge oe = lst_edges.get(j);
            clone.insertEdge((this.origin(oe.getLabel())).getLabel(), (this.destination(oe.getLabel())).getLabel(), oe.getLabel(), oe.getDado());
        }
        return clone;
    }

    public boolean equals(TADGrafoDV3 outrog){
        if(this.numVertices() == outrog.numVertices() && this.numEdges() == outrog.numEdges()){
            for(int i = primVertice; i <= ultiVertice; i++){
                for(int j = primVertice; j <= ultiVertice; j++){
                    Vertex ov = intToVertex(j);
                    Object vertex_outro = outrog.dicLblVertex.findElement(ov.getLabel());
                    if(ov.getDado().equals(vertex_outro)){
                        return false;
                    }
                    if(mat[i][j] != 0){
                        Edge oe = intToEdge(mat[i][j]);
                        Object edge_outro = outrog.dicLblEdge.findElement(oe.getLabel());
                        if(oe.getDado().equals(edge_outro)){
                            return false;
                        }
                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }

    public static TADGrafoDV3 carregaTGF(String nome_arq, int quant_vertices){
        ArquivoTxt arqIn = ArquivoTxt.open(nome_arq, "rt");
        String linha;
        String[] array_split;
        TADGrafoDV3 g = new TADGrafoDV3("grafo", quant_vertices);
        boolean troca = false;
        int cont = 0;
        while((linha = arqIn.readline()) != null){
            if(linha.contains("#")){
                troca = true;
                continue;
            }
            if(!troca){
                array_split = linha.split(" ");
                String valor_vertex = "";
                for(int i=0; i<array_split.length; i++){
                    valor_vertex += " "+array_split[i];
                }
                g.insertVertex(array_split[0], valor_vertex);
            }else{
                array_split = linha.split(" ");
                cont++;
                g.insertEdge(array_split[0], array_split[1], "Relacao"+cont, "");
            }
        }
        arqIn.close();
        return g;
    }

    /*public static String converteTXTtoTGF(String nome_arq){
        TADDicChain dicLblVertex = new TADDicChain();
        LinkedList<String> list_arestas = new LinkedList<>();

        ArquivoTxt arqIn = ArquivoTxt.open("./src/_my_tools/"+nome_arq+".txt", "rt");
        String linha;
        String[] array_split;
        
        int cont_vertice = 0;

        while((linha = arqIn.readline()) != null){
            array_split = linha.split("/");
            cont_vertice++;
            dicLblVertex.insertItem(cont_vertice, array_split[0]);
            TDicItem find_filme = (TDicItem)dicLblVertex.findElement(array_split[0]);
            int id_filme = 0;
            if(dicLblVertex.NO_SUCH_KEY()){
                cont_vertice++;
                dicLblVertex.insertItem(cont_vertice, array_split[0]);
                id_filme = cont_vertice;
            }else{
                id_filme = (int)(find_filme).getKey();
            }
            for(int i = 1; i < array_split.length; i++){
                String nome_autor = array_split[i];
                String[] array_split_nome = nome_autor.split(",");
                nome_autor = array_split_nome[0]+array_split_nome[1];
                TDicItem find_autor = (TDicItem)dicLblVertex.findElement(nome_autor);
                int id_autor = 0;
                if(dicLblVertex.NO_SUCH_KEY()){
                    cont_vertice++;
                    dicLblVertex.insertItem(cont_vertice, nome_autor);
                    id_autor = cont_vertice;
                }else{
                    id_autor = (int)(find_autor).getKey();
                }
                String nome_aresta = id_filme+" "+id_autor; 
                list_arestas.add(nome_aresta);
            } 
        }

        ArquivoTxt arqIn_2 = ArquivoTxt.open("./src/_my_tools/"+nome_arq+".tgf", "wt");
        LinkedList[] vet = dicLblVertex.getVet();
        for(int i = 0; i < dicLblVertex.getSizeVetBuckets(); i++){
            for(int k = 0; k < vet[i].size(); k++){
                Object chave = ((TDicItem)(vet[i].get(k))).getKey();
                Object valor = ((TDicItem)(vet[i].get(k))).getDado();
                arqIn_2.write(chave.toString()+" "+valor.toString());
                arqIn_2.write("\n");
            }
        }

        arqIn_2.write("#");
        arqIn_2.write("\n");

        for(int i = 0; i < list_arestas.size(); i++){
            arqIn_2.write(list_arestas.get(i));
            arqIn_2.write("\n");
        }

        arqIn_2.close();

        return nome_arq;
    }*/
}
