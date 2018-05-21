/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import Interacciones.Colisiones;
import Interacciones.Teclado;
import ScoreAndTimer.Timer;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author MtsSk
 */
public class Ventana extends JFrame implements Runnable{
    
    //Escala de pantalla
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    //Lienzo
    private Canvas canvas;
    private BufferStrategy buffer;
    private BufferStrategy buffer1;
    private Graphics g;
    //Control de datos en subhilo
    private Thread hilo;
    //Control de inicio de juego en FALSO
    private boolean ejecutar = false;    
    //Objeto teclado
    private Teclado teclado;
    
    //Constructor de clase
    public Ventana(String nombre){
        //Creador de ventana
        setTitle(nombre);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        
        //Area de pintado
        canvas = new Canvas();
        
        //Objeto teclado
        teclado = new Teclado();
        canvas.addKeyListener(teclado);
        
        //Visibilidad y tamaño de la ventana
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true);
        
        //Se agrega canvas a la ventana
        add(canvas);
        
        //Lo hacemos visible
        setVisible(true);       
    }
    
    //Main
    public static void main(String[] args) {
        //Creacion de objeto ventana
        Ventana ventana = new Ventana("Space War");
        //Inicia el ciclo del juego
        ventana.iniciar();
        
    }
    
    int xPelota = 387;
    int yPelota = 410;
    int random;
    int contador = 0;
    boolean direccion = true;
    boolean movX = true;
    boolean movY = true;
    
    int xPaleta = 346;
    int yPaleta = 430;
    
    int [] xBloques0 = {20,90,165,235,325,398,488,558,633,703};
    int [] yBloques0 = {30,55,80};
    
    public final int limitesX [] = {15,778};
    public final int limitesY [] = {15,456};
    
    private int tamañoPelota = 10;
    private int [] puntosPelotaX;
    private int [] puntosPelotaY;
    private int [] puntosPaletaX;
    private int [] puntosPaletaY;
    private int [] bloquesColision;
    private boolean [] posiciones = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
    
    int [] tt = {0,0,0};
    Timer tm = new Timer(tt);
    
    private void actualizar(){
        //Pelota
        puntosPelotaX = Colisiones.determinarPuntosX(xPelota, tamañoPelota);
        puntosPelotaY = Colisiones.determinarPuntosY(yPelota, tamañoPelota);
        
        //System.out.println(puntosPelotaX[0]+"|"+puntosPelotaX[1]+"|"+puntosPelotaX[2]+"|"+puntosPelotaX[3]);
        //System.out.println(puntosPelotaY[0]+"|"+puntosPelotaY[1]+"|"+puntosPelotaY[2]+"|"+puntosPelotaY[3]);
        
        //TECLADO ACTUALIZACIÓN
        //System.out.println(teclado.movimiento());
        int dato = teclado.movimiento();
        
        if (dato == 1){
            if (xPaleta >= limitesX[1] - 100 || xPaleta >= limitesX[1] - 110){
                if (xPaleta != limitesX[1] - 100){
                    xPaleta = xPaleta + ((limitesX[1]-100) - xPaleta);
                }
            }
            else{
                xPaleta = xPaleta + 12;
                //System.out.println(xPaleta);
            }            
        }
        else {
            if (dato == 2){
                if (xPaleta <= limitesX[0]+15){
                    if (xPaleta != limitesX[0]){
                        xPaleta = xPaleta - (xPaleta - limitesX[0]);
                    }
                }
                else{
                    xPaleta = xPaleta - 12;
                    //System.out.println(xPaleta);
                }
            }
            else{
                if (dato == 3){
                    ejecutar = false;
                }
                else{
                    
                }
            }
        }
        dato = -1;
        
        //Colisiones entre la pelota y paleta
        puntosPaletaX = Colisiones.determinarPuntosX(xPaleta, 100);
        puntosPaletaY = Colisiones.determinarPuntosY(yPaleta, 10);
//        System.out.println(puntosPaletaX[0]+"|"+puntosPaletaX[1]+"|"+puntosPaletaX[2]+"|"+puntosPaletaX[3]);
//        System.out.println(puntosPaletaY[0]+"|"+puntosPaletaY[1]+"|"+puntosPaletaY[2]+"|"+puntosPaletaY[3]);        
//        System.out.println(Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY));

        if (xPelota <= limitesX[1]-tamañoPelota && movX == true){
            xPelota++;
            if (xPelota == limitesX[1]-tamañoPelota){
                movX = false;
            }
        }
        else{
            if (xPelota >= limitesX[0] && movX == false){
                xPelota--;
                if (xPelota == limitesX[0]){
                    movX = true;
                }
            }
        }
        if (yPelota-3 <= yPaleta-tamañoPelota && movY == true){
            yPelota++;
            if (yPelota-3 >= (yPaleta-tamañoPelota) && Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY)){
                movY = false;
            }
            else{
                if (yPelota-3 >= (yPaleta-tamañoPelota) && !Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY)){
                    movY = true;
                }
            }
        }
        else{
            if (yPelota >= limitesY[0] && movY == false){
                yPelota--;
                if (yPelota == limitesY[0]){
                    movY = true;
                }
            }
        }
        
        //Bloques 
        bloquesColision = Colisiones.bloques(puntosPelotaX, puntosPelotaY, xBloques0, yBloques0);
        if (bloquesColision[0] < 0){
            
        }
        else{
            posiciones[bloquesColision[0]] = false;
            System.out.println(posiciones[bloquesColision[0]]);
        }
    }
    
    //Uso del timer
    
    
    private void dibujar(){
        buffer = canvas.getBufferStrategy();
        if (buffer == null){
            canvas.createBufferStrategy(3);
            return;
        }
        
        g = buffer.getDrawGraphics();
        //Dibujo area inicio
         
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        g.drawOval(xPelota, yPelota, tamañoPelota, tamañoPelota);
        g.drawString("x:"+xPelota+"|y:"+yPelota, xPelota, yPelota);
        g.drawString("PELOTA|x:"+xPelota+"y:"+yPelota,10,25);
        g.drawRect(120, 120, 50, 50);
        
        //Ring de JUEGO
        g.drawLine(limitesX[0], limitesY[0], limitesX[0], limitesY[1]);
        g.drawLine(limitesX[0], limitesY[0], limitesX[1], limitesY[0]);
        g.drawLine(limitesX[1], limitesY[0], limitesX[1], limitesY[1]);
        g.drawLine(limitesX[0], limitesY[1], limitesX[1], limitesY[1]);        
        
        for (int i = 0; i < 10; i++){
            g.drawRect(xBloques0[i], yBloques0[0], 70, 20);
            g.drawRect(xBloques0[i], yBloques0[1], 70, 20);
            g.drawRect(xBloques0[i], yBloques0[2], 70, 20);
        }
        
        //Paleta
        g.drawRect(xPaleta,yPaleta,100,10);
        
        g.drawString("FPS: "+promedioFPS, 10, 15);
        g.drawString("TIME: "+tm.Contador(), 10, 35);

        //Dibujo area fin
        g.dispose();
        buffer.show();
        
    }
    
    private final int FPS = 60;
    private final double tiempoObjetivo = 1000000000/FPS;
    private double tiempoTranscurrido = 0;
    private int promedioFPS = FPS;
    
    //Ejecución
    @Override
    public void run() {
        
        long ahora;
        long antes = System.nanoTime();
        int frames = 0;
        long tiempo = 0;        
        boolean primera = true;
        
        //Ciclo infinito que no puede ser detenido
        boolean ciclo = true;
        while (ciclo){
            int dato = teclado.movimiento();
            if (dato == 3){
                ejecutar = true;
                antes = System.nanoTime();
            }
            //Ciclo de ejecución del juego
            while (ejecutar) {
                ahora = System.nanoTime();
                tiempoTranscurrido += (ahora - antes)/tiempoObjetivo;
                tiempo += (ahora - antes);
                antes = ahora;
                
                if (tiempoTranscurrido >= 1){
                    actualizar();
                    dibujar();
                    tiempoTranscurrido--;
                    frames++;
                }
                if (tiempo >= 1000000000){
                    promedioFPS = frames;
                    frames = 0;
                    tiempo = 0;
                }
                if ((tt[0] == 0 && tt[1] == 0 && tt[2] == 3) && primera == true){
                    System.out.println("Hola");
                    ejecutar = false;
                    primera = false;
                }
            }
        }
        
        detener();
    }
    
    //Iniciar hilo
    private void iniciar(){
        hilo = new Thread(this);
        hilo.start();
        ejecutar = true;
    }
    
    //Detener hilo
    private void detener(){
        try {
            hilo.join();
            ejecutar = false;
        } catch (InterruptedException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}