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
public class Timer {
    private float sumatoria;
    public Timer(float sumatoria){
        this.sumatoria = sumatoria;
    }
    
    // ms = relojContador[2]
    // s  = relojContador[1]
    // m  = relojContador[0]
    String sumatoriaNumerica;
    
    public static String Contador(float sumatoria){
        int m = 0;
        String segundos = "",minutos = "";
        
        segundos = ""+(int)sumatoria;
        
        m = (int)Float.parseFloat(segundos)/60;
        if (m < 10){
            minutos = "0"+m;
        }
        
        return minutos+":"+segundos;
    }   
    
}
