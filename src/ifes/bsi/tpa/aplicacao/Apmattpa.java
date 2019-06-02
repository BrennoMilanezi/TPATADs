/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifes.bsi.tpa.aplicacao;

import _my_tools.ArquivoTxt;
import java.io.IOException;

/**
 *
 * @author User
 */
public class Apmattpa {
    public static void main(String[] args) throws IOException {
        ArquivoTxt arqIn = ArquivoTxt.open("./src/ifes/bsi/tpa/aplicacao/bdaritmat.csv", "rt");
        String linha = arqIn.readline();
        TADMatriz m = TADMatriz.carrega(linha);
        linha = arqIn.readline();
        while(linha != null) {
            if(linha.equals("t")){
                m = m.transposta();
            }else{
                String[] lst = linha.split(",");
                switch (lst[0]) {
                    case "+":
                        TADMatriz m_2 = TADMatriz.carrega(lst[1]);
                        m = m.soma(m_2);
                        break;
                    case "-":
                        TADMatriz m_3 = TADMatriz.carrega(lst[1]);
                        m = m.subtracao(m_3);
                        break;
                    case "*":
                        boolean is_numeric = true;
                        try {
                            Float num = Float.parseFloat(lst[1]);
                        } catch (NumberFormatException e) {
                            is_numeric = false;
                        }   
                        if(is_numeric){
                            Float num = Float.parseFloat(lst[1]);
                            m.vezesK(num);
                        }else{
                            TADMatriz m_4 = TADMatriz.carrega(lst[1]);
                            m = m.multi(m_4);
                        }   break;
                    default:
                        break;
                }
            }
            linha = arqIn.readline();
        }
        arqIn.close();
        m.printmat();
        System.out.println("");
        m.printDigPri();
        m.printDigSec();
        m.salva("resposta");
    }
}
