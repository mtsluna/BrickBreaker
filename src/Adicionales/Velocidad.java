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
    
    public static int variacion(int velocidad){
        int aleatorioVelocidad = (int)(Math.random()*((5-1)+1))+1;
        System.out.println("Aleatorio: "+aleatorioVelocidad);
        if (aleatorioVelocidad == 2){
            if (velocidad < 2){
                velocidad++;
                System.out.println(velocidad);
                return velocidad;
            }
            else{
                if (velocidad == 2){
                    velocidad--;
                }
            }
        }
        else
            System.out.println(velocidad);
            return velocidad;
        }
                
    }
