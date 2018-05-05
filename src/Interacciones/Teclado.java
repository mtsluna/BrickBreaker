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
    
    public static boolean derecha,izquierda;
    public Teclado(){
        derecha = false;
        izquierda = false;
    }
    
    public int movimiento(){
        if (derecha){
            System.out.println("Presiono derecha");
            derecha = false;
            return 1;
        }
        if (izquierda){
            System.out.println("Presiono izquierda");
            izquierda = false;
            return 2;
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}