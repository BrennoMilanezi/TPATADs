/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.grafo;

import _my_tools.ArquivoTxt;
import ifes.bsi.tpa.taddic.TADDicChain;
import ifes.bsi.tpa.taddic.TDicItem;
import java.util.LinkedList;

/**
 *
 * @author 20151BSI0290
 */
public class AplicacaoTGF {

    public static void main(String[] args){
        String nome_arq = converte_arquivo("movies");
        System.out.println(nome_arq);
        TADGrafo g = carrega(nome_arq);
        g.printGrafo();
    }

    public static String converte_arquivo(String nome_arq){
        TADDicChain dicVertex = new TADDicChain();
        LinkedList<String> list_arestas = new LinkedList<>();

        ArquivoTxt arqIn = ArquivoTxt.open("./src/_my_tools/"+nome_arq+".txt", "rt");
        String linha;
        String[] array_split;
        
        int cont_vertice = 0;

        while((linha = arqIn.readline()) != null){
            array_split = linha.split("/");
            cont_vertice++;
            dicVertex.insertItem(cont_vertice, array_split[0]);
            TDicItem find_filme = (TDicItem)dicVertex.findElement(array_split[0]);
            int id_filme = 0;
            if(dicVertex.NO_SUCH_KEY()){
                cont_vertice++;
                dicVertex.insertItem(cont_vertice, array_split[0]);
                id_filme = cont_vertice;
            }else{
                id_filme = (int)(find_filme).getKey();
            }
            for(int i = 1; i < array_split.length; i++){
                String nome_autor = array_split[i];
                String[] array_split_nome = nome_autor.split(",");
                nome_autor = array_split_nome[0]+array_split_nome[1];
                TDicItem find_autor = (TDicItem)dicVertex.findElement(nome_autor);
                int id_autor = 0;
                if(dicVertex.NO_SUCH_KEY()){
                    cont_vertice++;
                    dicVertex.insertItem(cont_vertice, nome_autor);
                    id_autor = cont_vertice;
                }else{
                    id_autor = (int)(find_autor).getKey();
                }
                String nome_aresta = id_filme+" "+id_autor; 
                list_arestas.add(nome_aresta);
            } 
        }

        /*FIM DA LEITURA DO ARQUIVO*/

        /*ESCRITA DO ARQUIVO TGF*/
        /*Escrever os vertices*/
        ArquivoTxt arqIn_2 = ArquivoTxt.open("./src/_my_tools/"+nome_arq+".tgf", "wt");
        LinkedList[] vet = dicVertex.getVet();
        for(int i = 0; i < dicVertex.getSizeVetBuckets(); i++){
            for(int k = 0; k < vet[i].size(); k++){
                Object chave = ((TDicItem)(vet[i].get(k))).getKey();
                Object valor = ((TDicItem)(vet[i].get(k))).getDado();
                arqIn_2.write(chave.toString()+" "+valor.toString());
                arqIn_2.write("\n");
            }
        }
        /*FIM da Escrita dos vertices*/
        arqIn_2.write("#");
        arqIn_2.write("\n");
        /*Escrever as relacoes*/
        for(int i = 0; i < list_arestas.size(); i++){
            arqIn_2.write(list_arestas.get(i));
            arqIn_2.write("\n");
        }
        /*FIM da Escrita das relacoes*/
        arqIn_2.close();
        /*FIM DA ESCRITA DO ARQUIVO TGF*/
        return nome_arq;
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
                continue;
            }
            if(!troca){
                array_split = linha.split(" ");
                String valor_vertex = array_split[1];
                for(int i=2; i<array_split.length; i++){
                    valor_vertex += " "+array_split[i];
                }
                g.insertVertex(array_split[0], valor_vertex);
            }else{
                array_split = linha.split(" ");
                g.insertEdge(array_split[0], array_split[1], "Relacao", ".");
            }
        }
        arqIn.close();
        return g;
    }
}
