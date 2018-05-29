/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScoreAndTimer;

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
    private String ms,s,m;
    
    public String Contador(){
        
        sumatoriaNumerica = Float.toString(sumatoria/1000000000);
        
        String [] parte = sumatoriaNumerica.split(".");
        
        System.out.println(parte.length);
        
        
        return (m+":"+s+":"+ms);
    }   
    
}
