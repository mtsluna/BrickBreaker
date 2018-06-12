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
    
    public static boolean detecta(int [] xObjP,int [] yObjP,int [] xObj,int [] yObj){
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
        return false;        
    }
    public static int lado(int [] xObjP,int [] yObjP,int [] xObj,int [] yObj){
        if ((xObjP[0] >= xObj[0] && xObjP[0] <= xObj[1]) && (yObjP[0] >= yObj[0] && yObjP[0] <= yObj[2])){
            return 1;
        }
        //colision lado superior derecho
        if ((xObjP[1] >= xObj[0] && xObjP[1] <= xObj[1]) && (yObjP[1] >= yObj[0] && yObjP[1] <= yObj[2])){
            return 2;
        }
        //Colision lado inferior izquierdo
        if ((xObjP[2] >= xObj[0] && xObjP[2] <= xObj[1]) && (yObjP[2] >= yObj[0] && yObjP[2] <= yObj[2])){
            return 3;
        }
        //Colision lado inferior derecho
        if ((xObjP[3] >= xObj[0] && xObjP[3] <= xObj[1]) && (yObjP[3] >= yObj[0] && yObjP[3] <= yObj[2])){
            return 4;
        }
        return 5; 
    }
    
    public static int [] determinarPuntosX(int xObjParametro, int anchoPelota){
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
    public static int [] determinarPuntosY(int yObjParametro, int altoPelota){
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
    
    //Detecta la colision de los bloques y devuelve el número y el tipo de impacto
    public static int [] bloques(int [] puntosPelotaX,int [] puntosPelotasY, int [] xBloques,int [] yBloques){
        int [] puntosBloqueX;
        int c = 0;
        int l = 5;
        int [] devolucion = new int [2];
        int [] puntosBloqueY;
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 3; j++){
                puntosBloqueX = determinarPuntosX(xBloques[i], 70);               
                puntosBloqueY = determinarPuntosY(yBloques[j], 20);
                if (detecta(puntosPelotaX, puntosPelotasY, puntosBloqueX, puntosBloqueY)){
                    l = lado(puntosPelotaX, puntosPelotasY, puntosBloqueX, puntosBloqueY);
                    devolucion[0] = c;
                    devolucion[1] = l;
                    return devolucion;
                }
                else{
                    devolucion[0] = -1;
                    devolucion[1] = -1;
                    c++;
                }                
            }   
        }
        return devolucion;
    }
    
    //Devuelve el tipo de impacto lateral que se produce
    public static boolean [] lateral(int [] puntosPelotaX,int [] puntosPelotasY, int [] xBloques,int [] yBloques){
        boolean [] lateral = {false,false};
        int [] puntosBloqueY;
        int [] puntosBloqueX;
        
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 3; j++){
                puntosBloqueX = determinarPuntosX(xBloques[i], 70);               
                puntosBloqueY = determinarPuntosY(yBloques[j], 20);
                
                if ((puntosBloqueX[1]-3 <= puntosPelotaX[0] && puntosBloqueX[1]+3 >= puntosPelotaX[0]) && puntosBloqueY[0] <= puntosPelotasY[0] && puntosBloqueY[3] <= puntosPelotasY[0]){
                        lateral[0] = false;
                        lateral[1] = true;
                        return lateral;
                }
                else {
                    if ((puntosBloqueX[0]-3 <= puntosPelotaX[1] && puntosBloqueX[0]+3 >= puntosPelotaX[1]) && puntosBloqueY[0] <= puntosPelotasY[0] && puntosBloqueY[3] <= puntosPelotasY[0]){
                        lateral[0] = true;
                        lateral[1] = false;
                        return lateral;
                    }
                }
                
            }   
        }
        lateral[0] = false;
        lateral[1] = false;
        return lateral;                
    }
}
