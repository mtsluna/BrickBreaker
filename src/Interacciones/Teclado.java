/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interacciones;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author mgonzaloluna
 */
public class Teclado implements KeyListener{
    
    public static boolean derecha,izquierda,pausa,a,enter,arriba,abajo,desarrollador;
    public Teclado(){
        derecha = false;
        izquierda = false;
        pausa = false;
        a = false;
        enter = false;
        arriba = false;
        abajo = false;
    }
    
    public int movimiento(){
        if (derecha){
            //System.out.println("Presiono derecha");
            derecha = false;
            return 1;
        }
        if (izquierda){
            //System.out.println("Presiono izquierda");
            izquierda = false;
            return 2;
        }
        if (pausa){
            //System.out.println("Presiono espacio");
            pausa = false;
            return 3;
        }
        if (a){
            a = false;
            return 4;
        }
        if (enter){
            enter = false;
            return 5;
        }
        if (arriba){
            arriba = false;
            return 6;
        }
        if (abajo){
            abajo = false;
            return 7;
        }
        return 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == 37){
            izquierda = true;
        }
        if (tecla == 39){
            derecha = true;
        }
        if (tecla == KeyEvent.VK_SPACE){
            pausa = true;
        }
        if (tecla == 65 || tecla == 97){
            a = true;
        }
        if (tecla == KeyEvent.VK_ENTER){
            enter = true;
        }
        if (tecla == KeyEvent.VK_UP){
            arriba = true;
        }
        if (tecla == KeyEvent.VK_DOWN){
            abajo = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}
