/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.aplicacao;

import _my_tools.ArquivoTxt;
import ifes.bsi.tpa.taddic.TADDicChain;
import java.util.LinkedList;

/**
 *
 * @author User
 */
public class TADMatriz {
    private int lins;
    private int cols;
    private TADDicChain dados;
    private LinkedList<ElemtMatriz> chaves;
    
    public TADMatriz(int i, int j){
        this.lins = i;
        this.cols = j;
        this.dados = new TADDicChain();
        this.chaves = new LinkedList<ElemtMatriz>();
    }
    
    public int quantLinhas(){
        return lins;
    }
    
    public int quantColunas(){
        return cols;
    }
    
    public Float getElem(int i, int j){
        if(i > this.quantLinhas() || j > this.quantColunas()){
            return null;
        }else{
            ElemtMatriz chave = this.findElemtMatriz(i, j);
            if(chave != null){
                Float valor = (Float)dados.findElement(chave);
                if(dados.NO_SUCH_KEY()){
                    return null;
                }else{
                    return valor; 
                }
            }else{
                return null;
            }
        }
    }
    
    public ElemtMatriz findElemtMatriz(int i, int j){
        if(chaves != null){
            for(int k = 0; k < chaves.size(); k++){
                if(chaves.get(k).getCol() == j && chaves.get(k).getLin() == i){
                    return chaves.get(k);
                }
            }
            return null;
        }else{
            return null;
        }
    }
    
    public Float setElem(int i, int j, Float valor){
        if(i > this.quantLinhas() || j > this.quantColunas()){
            return null;
        }else{
            if(valor == 0){
                valor = null;
            }
            ElemtMatriz chave_existe = this.findElemtMatriz(i, j);
            if(chave_existe != null){
                dados.insertItem(chave_existe, valor);
                return valor;
            }else{
                ElemtMatriz chave = new ElemtMatriz(i, j);
                dados.insertItem(chave, valor);
                chaves.add(chave);
                return valor;
            }
        }

    }
    
    public Float getElem_calculo(int i, int j){
        if(this.getElem(i, j) != null){
            return this.getElem(i, j);
        }else{
            return 0f;
        }
    }
    
    public TADMatriz soma(TADMatriz m){
        if(this.quantLinhas() == m.quantLinhas() && this.quantColunas() == m.quantColunas()){
            TADMatriz matriz_soma = new TADMatriz(this.quantLinhas(), this.quantColunas());
            for (int i = 0; i < this.quantLinhas(); i++) {
                for (int j = 0; j < this.quantColunas(); j++) {
                    Float a = this.getElem_calculo(i, j) + m.getElem_calculo(i, j);
                    matriz_soma.setElem(i, j, a);
		}
            }
            return matriz_soma;
        }else{
            return null;
        }
    } 
    
    public TADMatriz subtracao(TADMatriz m){
        if(this.quantLinhas() == m.quantLinhas() && this.quantColunas() == m.quantColunas()){
            TADMatriz matriz_sub = new TADMatriz(this.quantLinhas(), this.quantColunas());
            for (int i = 0; i < this.quantLinhas(); i++) {
                for (int j = 0; j < this.quantColunas(); j++) {
                    Float a = this.getElem_calculo(i, j) - m.getElem_calculo(i, j);
                    matriz_sub.setElem(i, j, a);
		}
            }
            return matriz_sub;
        }else{
            return null;
        }
    }
    
    public void vezesK(float k){

        for (int i = 0; i < this.quantLinhas(); i++) {
            for (int j = 0; j < this.quantColunas(); j++) {
                Float a = (this.getElem_calculo(i, j))*k;
                this.setElem(i, j, (this.getElem_calculo(i, j))*k);
            }
        }
    }
    
    public TADMatriz multi(TADMatriz m){
        if(this.quantColunas() == m.quantLinhas()){
            TADMatriz matriz_multi = new TADMatriz(this.quantLinhas(), m.quantColunas());
            for (int i = 0; i < this.quantLinhas(); i++) {
                for (int j = 0; j < m.quantColunas(); j++) {
                    float aux = 0;
                    for(int k = 0; k < this.quantColunas(); k++){
                        aux = aux + (this.getElem_calculo(i, k) * m.getElem_calculo(k, j));
                    }
                    matriz_multi.setElem(i, j, aux);
		}
            }
            return matriz_multi;
        }else{
            return null;
        }
    }
    
    public TADMatriz transposta(){
        TADMatriz matriz_transposta = new TADMatriz(this.quantColunas(), this.quantLinhas());
        for (int i = 0; i < this.quantLinhas(); i++) {
            for (int j = 0; j < this.quantColunas(); j++) {
                matriz_transposta.setElem(j, i, this.getElem(i, j));
            }
        }
        return matriz_transposta;
    }
    
    public static TADMatriz carrega(String nome_arq){
        ArquivoTxt arqIn = ArquivoTxt.open("./src/ifes/bsi/tpa/aplicacao/"+nome_arq+".txt", "rt");
        String linha;
        String[] array_split = null;
        int quant_linhas = 0;
        while((linha = arqIn.readline()) != null){
            quant_linhas++;
            array_split = linha.split("         ");
        }
        arqIn.close();
        int quant_colunas = array_split.length-1;
        TADMatriz m = new TADMatriz(quant_linhas, quant_colunas);
        int i = 0;
        ArquivoTxt arqIn_2 = ArquivoTxt.open("./src/ifes/bsi/tpa/aplicacao/"+nome_arq+".txt", "rt");
        while((linha = arqIn_2.readline()) != null){
            array_split = linha.split("         ");
            int aux = 0;
            for(int j=1; j<quant_colunas+1; j++){
                Float valor = Float.parseFloat(array_split[j]);
                m.setElem(i, aux, valor);
                aux++;
            }
            i++;
        }
        arqIn_2.close();
        return m;
    }
    
    public String salva(String nome_arq){
        ArquivoTxt arqIn = ArquivoTxt.open("./src/ifes/bsi/tpa/aplicacao/"+nome_arq+".txt", "wt");
        for(int i = 0; i < this.quantLinhas(); i++){
            for(int j=0; j < this.quantColunas(); j++){
                arqIn.write("         "+this.getElem_calculo(i, j));
            }
            arqIn.write("\n");
        }
        arqIn.close();
        return nome_arq;
    }
    
    
    public void printmat(){
        System.out.println("Matriz:");
        System.out.println("Qnt Linhas: "+this.quantLinhas());
        System.out.println("Qnt Colunas: "+this.quantColunas());
        System.out.println(" ");
        for(int i = 0; i < this.quantLinhas(); i++){
            for(int j=0; j < this.quantColunas(); j++){
                System.out.print(this.getElem(i, j)+"         ");
            }
            System.out.println(" ");
        }
    }
    
    public void printDigPri(){
        System.out.print("Diagonal Primaria: [");
        for(int i = 0; i < this.quantLinhas(); i++){
            for(int j=0; j < this.quantColunas(); j++){
                if(i == j){
                    System.out.print(this.getElem(i, j)+"  ");
                }
            }
        }
        System.out.println("]");
    }
    
    public void printDigSec(){
        System.out.print("Diagonal Secundaria: [");
        for(int i = 0; i < this.quantLinhas(); i++){
            for(int j=0; j < this.quantColunas(); j++){
                if(i == this.quantColunas() - 1 - j){
                    System.out.print(this.getElem(i, j)+"  ");
                }
            }
        }
        System.out.println("]");
    }
}
