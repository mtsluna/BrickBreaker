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
public class Score {
    //Constructor
    public Score(){
    }
    //Método contador de score
    public static int contadorScore(int score, int b){
        //Genera un aleatrorio
        int aleatorioAdicional = (int)(Math.random()*8)+1;
        //Boleano que determinara una aplicación
        boolean bonus = false;
        //Si el aleatorio es 7
        if  (aleatorioAdicional == 7){
            //Se aplica BONUS
            bonus = true;
        }
        //Si los bloques son de la fila superior
        if (b == 0 || b == 3 || b == 6 || b == 9 || b == 12 || b == 15 || b == 18 || b == 21 || b == 24 || b == 27){
            //Si bonus esta activo
            if (bonus){
                //Retorna 30 puntos multiplicado por 7
                return (score + (30 * aleatorioAdicional));
            }
            else {
                //Retorna simplemente 30
                return (score + 30);
            }
        }
        //Sino
        else {
            //Si los bloques son de la fila central
            if (b == 1 || b == 4 || b == 7 || b == 10 || b == 13 || b == 16 || b == 19 || b == 22 || b == 25 || b == 28){
                //Si el bonus esta activado
                if (bonus){
                    //Retorna 20 puntos multiplicado por 7
                    return (score + (20 * aleatorioAdicional));
                }
                else {
                    //Retorna simplemente 20
                    return (score + 20);
                }
            }
            //Sino
            else {
                //Si los bloques son de la fila inferior
                if (b == 2 || b == 5 || b == 8 || b == 11 || b == 14 || b == 17 || b == 20 || b == 23 || b == 26 || b == 29){
                    //Si el bonus esta activado
                    if (bonus){
                        //Retorna 10 multiplicado por 7
                        return (score + (10 * aleatorioAdicional));
                    }
                    else {
                        //Retorna simplemente 10
                        return (score + 10);
                    }
                }
            }
        }
        //Retorna 0
        return 0;
    }
    
    //Método que completa el score con 0
    public static String completarScore(int score){
        //Variables
        String numero;
        String ceros = "";  
        //se asigna a la variable número score pasado a string
        numero = Integer.toString(score);
        
        //Por cada digito que tenga se le agregan X ceros
        for (int j = 0; j < 9-numero.length(); j++){
            ceros = "0".concat(ceros);
        }
        
        //Retorna el número transformado
        return (ceros.concat(numero));
    }    
}
