/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import Interacciones.Colisiones;
import Interacciones.Teclado;
import ScoreAndTimer.SaveScore;
import ScoreAndTimer.Timer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import static java.lang.Math.pow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author MtsSk
 */
public class Ventana extends JFrame implements Runnable{
    
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    //Lienzo
    private Canvas canvas;
    //Control de datos en subhilo
    private Thread hilo;
    //Control de inicio de juego en FALSO
    private boolean ejecutar = false;
    //
    private BufferStrategy buffer;
    private Graphics g;
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
        
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true);
        
        //Se agrega canvas a la ventana
        add(canvas);
        
        //Lo hacemos visible
        setVisible(true);       
    }
    
    public static void main(String[] args) {
        Ventana ventana = new Ventana("Space War");
        ventana.iniciar();
        
    }
    
    int x = 0;
    int y = 0;
    int random;
    int contador = 0;
    boolean direccion = true;
    boolean movX = true;
    boolean movY = true;
    
    int xPaleta = 400;
    int yPaleta = 450;
    
    private void actualizar(){
        
        //TECLADO ACTUALIZACIÓN
        //System.out.println(teclado.movimiento());
        int dato = teclado.movimiento();
        
        if (dato == 1){
        xPaleta = xPaleta + 13;
        System.out.println(xPaleta);
        }
        else {
            if (dato == 2){
                xPaleta = xPaleta - 13;
                System.out.println(xPaleta);
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
    
    //Uso del timer
    int [] tt = {0,0,0};
    Timer tm = new Timer(tt);
    
    private void dibujar(){
        buffer = canvas.getBufferStrategy();
        if (buffer == null){
            canvas.createBufferStrategy(3);
            return;
        }
        
        g = buffer.getDrawGraphics();
        //Dibujo area inicio
         
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        g.drawRect(x, y, 100, 100);
        g.drawRect(x, y, 50, 50);
        g.drawRect(x+50, y+50, 50, 50);
        g.drawRect(x+25, y+25, 50, 50);
        g.drawString("x:"+x+"|y:"+y, x, y);
        g.drawString("PELOTA|x:"+x+"y:"+y,10,25);
        g.drawRect(120, 120, 50, 50);
                      
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
        
        //Colisiones
        Colisiones col = new Colisiones();
        int [] xObjP; 
        int [] yObjP;
        int [] xObj; 
        int [] yObj;
        
        boolean ciclo = true;
        while (ciclo){
            System.out.println(ejecutar);
            int dato = teclado.movimiento();
            if (dato == 3){
                ejecutar = true;
            }
        
        while (ejecutar) {
                        
            xObjP = col.determinarPuntosX(x, 100);
            yObjP = col.determinarPuntosY(y, 100);
            xObj = col.determinarPuntosX(120, 50);
            yObj = col.determinarPuntosY(120, 50);
            
            ejecutar = col.detecta(xObjP, yObjP, xObj, yObj);
                      
            
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