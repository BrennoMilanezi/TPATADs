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
        
        /*LEITURA DO ARQUIVO*/

        TADDicChain dicVertex = null;
        LinkedList<String> list_arestas = LinkedList<>();

        ArquivoTxt arqIn = ArquivoTxt.open("./src/_my_tools/"+nome_arq+".txt", "rt");
        String linha;
        String[] array_split;
        
        int cont_vertice = 0;

        while((linha = arqIn.readline()) != null){
            array_split = linha.split("/");
            cont_vertice++;
            dicVertex.insertItem(cont_vertice, array_split[0]);
            Object id_filme = dicVertex.findElement(array_split[0]);
            for(int i = 1; i < array_split.length; i++){
                String nome_autor = array_split[i];
                String[] array_split_nome = nome_autor.split(",");
                nome_autor = array_split_nome[0]+array_split_nome[1];
                int id_autor = (int)(dicVertex.findElement(nome_autor)).getKey();;
                if(dicVertex.NO_SUCH_KEY()){
                    cont_vertice++;
                    dicVertex.insertItem(cont_vertice, nome_autor);
                    int id_autor = cont_vertice;
                }
                String nome_aresta = id_filme+" "+id_autor+ ""; 
                list_arestas.add(nome_aresta);
            } 
        }

        /*FIM DA LEITURA DO ARQUIVO*/

        /*ESCRITA DO ARQUIVO TGF*/
        /*Escrever os vertices*/
        ArquivoTxt arqIn = ArquivoTxt.open("./src/_my_tools/"+nome_arq+".tgf", "wt");
        for(int i = 0; i < dicVertex.getSizeVetBuckets(); i++){
            for(int k = 0; k < dicVertex.vet[i].size(); k++){
                Object chave = ((TDicItem)(dicVertex.vet[i].get(k))).getKey();
                Object valor = ((TDicItem)(dicVertex.vet[i].get(k))).getDado();
                arqIn.write(chave.toString()+" "+valor.toString());
            }
        }
        /*FIM da Escrita dos vertices*/
        arqIn.write("#");
        /*Escrever as relacoes*/
        for(int i = 0; i < list_arestas.length; i++){
            arqIn.write(list_arestas.get(i));
        }
        /*FIM da Escrita das relacoes*/
        arqIn.close();
        /*FIM DA ESCRITA DO ARQUIVO TGF*/
    }

    public static TADGrafo carrega(String nome_arq){
        ArquivoTxt arqIn = ArquivoTxt.open("./src/_my_tools/"+nome_arq+".tgf", "rt");
        String linha;
        String[] array_split;
        TADGrafo g = new TADGrafo("filmes", 300);
        boolean troca = false;
        while((linha = arqIn.readline()) != null){
            if(linha.contains("#")){
                troca = true;
            }
            if(!troca){
                array_split = linha.split(" ");
                g.insertVertex(array_split[0], array_split[1]);
            }else{
                array_split = linha.split(" ");
                g.insertEdge(array_split[0], array_split[1], "Relacao", "...");
            }
        }
        arqIn.close();
        return g;
    }
}
