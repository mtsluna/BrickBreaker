/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adicionales;

/**
 *
 * @author MtsSk
 */
public class Velocidad {
    
    //Método variación de velocidad
    public static int variacion(int velocidad){
        //Genera random
        int aleatorioVelocidad = (int)(Math.random()*((5-1)+1))+1;
        //Si el aleatorio es igual a 2
        if (aleatorioVelocidad == 2){
            //Si velocidad es menor que 2
            if (velocidad < 2){
                //Que retorne velocidad ++
                velocidad++;
                return velocidad;
            }
            //Sino
            else{
                //Si velocidad es igual a 2
                if (velocidad == 2){
                    //La devuelve a 1 
                    velocidad--;
                }
            }
        }
        //Sino
        else{
            //Retorna velocidad
            return velocidad;
        }
        //Retorna velocidad
        return velocidad;
    }
                
}
