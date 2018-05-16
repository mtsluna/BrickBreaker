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
    
    public boolean detecta(int [] xObjP,int [] yObjP,int [] xObj,int [] yObj){
        //Colision lado superior izquierdo
        if ((xObjP[0] > xObj[0] && xObjP[0] <= xObj[1]) && (yObjP[0] >= yObj[0] && yObjP[0] <= yObj[2])){
            return true;
        }
        //colision lado superior derecho
        if ((xObjP[1] >= xObj[0] && xObjP[1] <= xObj[1]) && (yObjP[1] >= yObj[0] && yObjP[1] <= yObj[2])){
            return true;
        }
        //Colision lado inferior izquierdo
        if ((xObjP[2] >= xObj[0] && xObjP[2] <= xObj[1]) && (yObjP[2] >= yObj[0] && yObjP[2] <= yObj[2])){
            return true;
        }
        //Colision lado inferior derecho
        if ((xObjP[3] >= xObj[0] && xObjP[3] <= xObj[1]) && (yObjP[3] >= yObj[0] && yObjP[3] <= yObj[2])){
            return true;
        }
        return true;
        
    }
    
    public int [] determinarPuntosX(int xObjParametro, int anchoPelota){
        int [] xObj = new int [4];
        //Lado superior izquierdo por parametro
        xObj[0] = xObjParametro;
        //Lado superior derecho
        xObj[1] = xObjParametro + anchoPelota;
        //Lado inferior izquierdo
        xObj[2] = xObjParametro;
        //Lado inferior derecho
        xObj[3] = xObjParametro + anchoPelota;
        return xObj;
    }
    public int [] determinarPuntosY(int yObjParametro, int altoPelota){
        int [] yObj = new int [4];
        //Lado superior izquierdo por parametro
        yObj[0] = yObjParametro;
        //Lado superior derecho
        yObj[1] = yObjParametro;
        //Lado inferior izquierdo
        yObj[2] = yObjParametro + altoPelota;
        //Lado inferior derecho
        yObj[3] = yObjParametro + altoPelota;
        return yObj;
    }
    
    //      L.S.I       L.S.D
    //      x=0 y=0     x=10 y=10
    //          ***********
    //          ***********
    //          ***********
    //          ***********
    //          ***********
    //      x=0 y=10    x=10 y=10
    //      L.I.I       L.I.D
    
}
