/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interacciones;

/**
 *
 * @author MtsSk
 */
public class Colisiones {
    
    int xEnMov;
    int yEnMov;
    int xObj;
    int yObj;
    int alto;
    int ancho;
    
    public Colisiones(int xEnMov,int yEnMov,int xObj,int ancho,int yObj, int alto){
        this.xEnMov = xEnMov;
        this.yEnMov = yEnMov;
        this.xObj = xObj;
        this.yObj = yObj;
        this.alto = alto;
        this.ancho = ancho;
    }
    int i = 0;
    public boolean colision(){
        if ((xEnMov >= xObj && xEnMov <= xObj) && (yEnMov >= yObj && yEnMov <= yObj)){
            return false;
        }
        else{
            return true;
        }
    }
}
