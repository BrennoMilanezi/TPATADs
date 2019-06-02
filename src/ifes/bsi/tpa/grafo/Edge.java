/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

/**
 *
 * @author User
 */
public class Edge {
    int id;
    String label;
    Object dado;
    
    Edge(int i, String l, Object o) {
    	this.id = i;
        this.label = l;
        this.dado = o;
    }

    public int getID(){
    	return id;
    }

    public String getLabel(){
    	return label;
    }

    public Object getDado(){
    	return dado;
    }

    public void setID(int i){
    	this.id = i;
    }

    public void setLabel(String l){
    	this.label = l;
    }

    public void setDado(Object o){
    	this.dado = o;
    }
    
}
