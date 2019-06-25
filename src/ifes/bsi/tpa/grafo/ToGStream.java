/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class ToGStream {
    
    private Graph g;
 
    public ToGStream(TADGrafoDV3 graf,boolean vertice_vis, boolean edge_vis, boolean dir){
        /*Converte um grafo do tipo TADGrafo para um grafo do tipo GraphStream*/
        g = new SingleGraph(graf.getNome());
        if(vertice_vis) {
            LinkedList<Vertex> lst_vet = graf.vertices();
            for(int i = 0; i < lst_vet.size(); i++){
                Node no = g.addNode(lst_vet.get(i).getLabel());
                no.addAttribute("ui.label", lst_vet.get(i).getLabel());
            }
            if(edge_vis) {
                LinkedList<Edge> lst_edge = graf.edges();
                LinkedList<String[]> lst_edges = new LinkedList<>();
                for (int i = 0; i < lst_edge.size(); i++) {
                    Edge oe = lst_edge.get(i);
                    Vertex aux_origin = graf.origin(oe.getLabel());
                    Vertex aux_dest = graf.destination(oe.getLabel());
                    if(dir){
                        org.graphstream.graph.Edge edge = g.addEdge(oe.getLabel(), aux_origin.getLabel(),aux_dest.getLabel(),true);
                        edge.addAttribute("ui.label", oe.getLabel());
                        String[] string_edges = {aux_origin.getLabel(),aux_dest.getLabel()};
                        lst_edges.add(string_edges);
                    }else{
                        org.graphstream.graph.Edge edge = g.addEdge(oe.getLabel(), aux_origin.getLabel(),aux_dest.getLabel());
                        edge.addAttribute("ui.label", oe.getLabel());
                        String[] string_edges = {aux_origin.getLabel(),aux_dest.getLabel()};
                        lst_edges.add(string_edges);	
                    }
                }
            }
        }	
    }
    
    public void exibe(String css){
        /*Exibe o grafo com o visual GraphStream definido pela string css*/
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        g.addAttribute("ui.stylesheet", css);
        g.display();
    }
    
    public void exibe(){
        /*Exibe o grafo com o visual GraphStream sem css*/
        g.display();
    }
}