/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.taddic;
import java.util.*;
/**
 *
 * @author User
 */
public class TADDicChain {
    private LinkedList[] vet = null;
    private double fator_de_carga = 0.75;
    private int tam = 0;
    private int quant_entradas = 0;
    private Hash_engine he = null;
    private boolean achou = false;
    
    public TADDicChain() {
        this.tam = (int)(50/fator_de_carga);
        this.setVet();
        this.he = new HashEngineDefault();
    }
    
    public TADDicChain(Hash_engine hash) {
        this.tam = (int)(50/fator_de_carga);
        this.setVet();
        if(hash == null){
            he = new HashEngineDefault();
        }else{
            this.he = hash;
        }
    }
    
    public TADDicChain(int n) {
        this.tam = (int)(n/fator_de_carga);
        this.setVet();
        this.he = new HashEngineDefault();
    }
    
    public TADDicChain(int n, Hash_engine hash) {
        this.tam = (int)(n/fator_de_carga);
        this.setVet();
        if(hash == null){
            he = new HashEngineDefault();
        }else{
            this.he = hash;
        }
    }
    
    private void setVet(){
        this.vet = new LinkedList[tam];
        for(int i=0; i< tam; i++){
            this.vet[i] = new LinkedList<TDicItem>();
        }
    }
    
    public LinkedList[] getVet(){
        return this.vet;
    }
    
    private int getIndice(long hash) {
        int indice = (int)(hash % this.getSizeVetBuckets());
        return indice;
    }
    
    private int buscaDicItem(LinkedList<TDicItem> list, Object chave) {
        /*return buscaRecursiva(list, chave, 0, list.size()-1);*/
        int pos = 0;
        while(pos < list.size()){
            if(((TDicItem)(list.get(pos))).getKey().equals(chave)){
                achou = true;
                return pos;
            }
            pos++;
        }
        achou = false;
        return -1; 
    }
    
    public int lenMaiorList(){
        int maior = 0;
        for (LinkedList vet1 : vet) {
            if (vet1 != null) {
                if (vet1.size() > maior) {
                    maior = vet1.size();
                }
            }
        }
        return maior;
    }

    public void insertItem(Object o, Object reg){
        findElement(o);
        long hash = this.he.hash_func(o);
        int indice = this.getIndice(hash);
        if(this.NO_SUCH_KEY()){
            vet[indice].add(new TDicItem(o, reg, hash));
            quant_entradas++;
            if(lenMaiorList() >= (int)this.getSizeVetBuckets()*0.3){
                redimensiona();
            }
        }else{
            int pos = buscaDicItem(vet[indice], o);
            if(pos != -1){
                ((TDicItem)(vet[indice].get(pos))).setDado(reg);
            }
        }
    }
    
    public Object findElement(Object o){
        long hash = this.he.hash_func(o);
        int indice = this.getIndice(hash);
        int pos = buscaDicItem(vet[indice], o);
        if(pos != -1){
            achou = true;
            return ((TDicItem)vet[indice].get(pos)).getDado();
        }else{
            return null;
        }
    }
    
    public Object removeElement(Object o){
        Object aux = findElement(o);
        
        if(this.NO_SUCH_KEY()){
            achou = false;
            return null;
        }else{
            long hash = this.he.hash_func(o);
            int indice = this.getIndice(hash);
            int pos = buscaDicItem(vet[indice], o);
            vet[indice].remove(pos);
            quant_entradas--;
            return aux;
        }
    }
    
    public LinkedList<Object> keys() {
        LinkedList<Object> chavesList = new LinkedList<>();
        
        for (LinkedList vet1 : vet) {
            if (vet1 != null) {
                for (int k = 0; k < vet1.size(); k++) {
                    chavesList.add(((TDicItem) vet1.get(k)).getKey());
                }
            }
        }
        
        return chavesList;
    }
    
    public LinkedList<Object> elements() {
        LinkedList<Object> valoresList = new LinkedList<>();
        
        for (LinkedList vet1 : vet) {
            if (vet1 != null) {
                for (int k = 0; k < vet1.size(); k++) {
                    valoresList.add(((TDicItem) vet1.get(k)).getDado());
                }
            }
        }
        
        return valoresList;
    }
    
    
    public LinkedList<Object> getItens() {
        LinkedList<Object> itensList = new LinkedList<>();
        
        for (LinkedList vet1 : vet) {
            if (vet1 != null) {
                for (int k = 0; k < vet1.size(); k++) {
                    itensList.add((TDicItem)vet1.get(k));
                }
            }
        }
        
        return itensList;
    }
    
    public void redimensiona(){
        int novotam = 2 * this.getSizeVetBuckets();
        LinkedList[] novovet = new LinkedList[novotam];
        for(int i=0; i< novotam; i++){
            novovet[i] = new LinkedList<TDicItem>();
        }
        for (LinkedList vet1 : vet) {
            if (vet1 != null) {
                for (int k = 0; k < vet1.size(); k++) {
                    TDicItem aux = (TDicItem) vet1.get(k);
                    int indice = (int)(aux.getHash() % novotam);
                    novovet[indice].add(aux);
                }
            }
        }
        vet = novovet;
    }
    
    public TADDicChain clone(){
        TADDicChain dicClone = new TADDicChain(he);
        
        for (LinkedList vet1 : vet) {
            for (int k = 0; k < vet1.size(); k++) {
                Object chave = ((TDicItem) (vet1.get(k))).getKey();
                Object valor = ((TDicItem) (vet1.get(k))).getDado();
                dicClone.insertItem(chave, valor);
            }
        }
        return dicClone;
    }
    
    public boolean equals(TADDicChain outroDic){
        if(he == outroDic.getHe()){
            if(this.size() == outroDic.size()){
                for(int i = 0; i < this.getSizeVetBuckets(); i++){
                    for(int k = 0; k < vet[i].size(); k++){
                        Object chave = ((TDicItem)(vet[i].get(k))).getKey();
                        Object valor = ((TDicItem)(vet[i].get(k))).getDado();
                        
                        Object outroValor = outroDic.findElement(chave);
                        if(outroDic.NO_SUCH_KEY() || (valor != outroValor)){
                            return false;
                        }
                    }
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
    
    public int[] getVetColisoes(){
        int[] colisoes = new int[this.getSizeVetBuckets()];
        for(int i=0; i < this.getSizeVetBuckets(); i++){
            colisoes[i] = this.vet[i].size();
        }
        return colisoes;
    }
    
    public int size(){
        return quant_entradas;
    }
    
    public Hash_engine getHe(){
        return he;
    }
    
    public int getSizeVetBuckets(){
        return vet.length;
    }
    
    public boolean isEmpty(){
        return quant_entradas == 0;
    }
    
    public boolean NO_SUCH_KEY(){
        return !achou;
    }
}
