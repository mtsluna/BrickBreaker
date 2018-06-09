/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adicionales;
/**
 *
 * @author mgonzaloluna
 */
public class SaveScore {
    public SaveScore(){
    }
    
    public static int contadorScore(int score, int b){
        int aleatorioAdicional = (int)(Math.random()*8)+1;
        boolean bonus = false;
        if  (aleatorioAdicional == 7){
            bonus = true;
        }
        
        if (b == 0 || b == 3 || b == 6 || b == 9 || b == 12 || b == 15 || b == 18 || b == 21 || b == 24 || b == 27){
            if (bonus){
                return (score + (30 * aleatorioAdicional));
            }
            else {
                return (score + 30);
            }
        }
        else {
            if (b == 1 || b == 4 || b == 7 || b == 10 || b == 13 || b == 16 || b == 19 || b == 22 || b == 25 || b == 28){
                if (bonus){
                    return (score + (20 * aleatorioAdicional));
                }
                else {
                    return (score + 20);
                }
            }
            else {
                if (b == 2 || b == 5 || b == 8 || b == 11 || b == 14 || b == 17 || b == 20 || b == 23 || b == 26 || b == 29){
                    if (bonus){
                        return (score + (10 * aleatorioAdicional));
                    }
                    else {
                        return (score + 10);
                    }
                }
            }
        }
        return 0;
    }
    
    public static String completarScore(int score){
        String numero;
        String ceros = "";  
        numero = Integer.toString(score);
        
        for (int j = 0; j < 9-numero.length(); j++){
            ceros = "0".concat(ceros);
        }
        
        return (ceros.concat(numero));
    }    
}
