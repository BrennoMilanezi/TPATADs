/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import _my_tools.ArquivoTxt;

/**
 *
 * @author 20151BSI0290
 */
public class AplicacaoTGF {

    public static void main(String[] args){
       TADGrafo g;
       g = carrega("movies");
       g.printGrafo();
    }

    public static TADGrafo carrega(String nome_arq){
        ArquivoTxt arqIn = ArquivoTxt.open("./src/_my_tools/"+nome_arq+".txt", "rt");
        String linha;
        String[] array_split;
        TADGrafo g = new TADGrafo("filmes", 300);
        while((linha = arqIn.readline()) != null){
            array_split = linha.split("/");
            String nome_filme = array_split[0];
            Vertex vf = g.insertVertex(nome_filme, nome_filme);
            if(vf != null){
            
            }else{
            }
            for(int i = 1; i < array_split.length; i++){
                String nome_autor = array_split[i];
                String[] array_split_nome = nome_autor.split(",");
                nome_autor = array_split_nome[0]+array_split_nome[1];
                /*VERIFICAR SE EST NO DICIONARIO SE NAO ESTIVER INSERIR SE ESTIVER PEGAR O VERTICE EXISTENTE*/
                Vertex va = g.insertVertex(nome_autor, nome_autor);
                if(va.equals(null)){
                    g.getVertex(nome_autor);
                }   
                g.insertEdge(vf.getLabel(), va.getLabel(), "Autor", va.getLabel());
            } 
        }
        arqIn.close();
        return g;
    }
    
    public String salva(String nome_arq){
        ArquivoTxt arqIn = ArquivoTxt.open("./src/ifes/bsi/tpa/aplicacao/"+nome_arq+".txt", "wt");

        arqIn.close();
        return nome_arq;
    }

}
