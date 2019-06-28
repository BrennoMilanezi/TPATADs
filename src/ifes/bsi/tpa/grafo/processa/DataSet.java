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
public abstract class DataSet {
    
    public abstract int custo​(String origem, String destino);
    public abstract LinkedList<String> caminho​(String origem, String destino);
}
