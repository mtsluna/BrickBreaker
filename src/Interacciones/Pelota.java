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
public class Pelota {
    private int x;
    private int y;
    
    public Pelota(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void rebotes(int [] limitesX, int [] limitesY, boolean movX, boolean movY){
        if (x < 693 && movX == true){
            x++;
            if (x == 693){
                movX = false;
            }
        }
        else{
            if (x > 0 && movX == false){
                x--;
                if (x == 1){
                    movX = true;
                }
            }
        }
        if (y < 370 && movY == true){
            y++;
            if (y == 370){
                movY = false;
            }
        }
        else{
            if (y > 0 && movY == false){
                y--;
                if (y == 1){
                    movY = true;
                }
            }
        }
    }
}
