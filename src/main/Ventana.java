/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import Interacciones.Colisiones;
import Interacciones.Imagenes;
import Interacciones.Teclado;
import ScoreAndTimer.Timer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author MtsSk
 */
public class Ventana extends JFrame /*implements Runnable*/{
    
    //Escala de pantalla
    public static final int WIDTH = 800;
    public static final int HEIGHT = 530;
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
    public static void main(String[] args) throws InterruptedException{
        //Creacion de objeto ventana
        Ventana ventana = new Ventana("Space War");
        //Inicia el ciclo del juego
//        ventana.iniciar();
        ventana.ciclo();
        
        
    }
    
    int xPelota = 387;
    int yPelota = 410; //410
    int random;
    int contador = 0;
    boolean direccion = true;
    boolean movX = true;
    boolean movY = true;
    
    int xPaleta = 346;
    int yPaleta = 430;
    
    int nivel = 2;
    int nivelesHechos = 1;
    
    int [] xBloques0 = {20,90,165,235,325,398,488,558,633,703};
    int [] yBloques0 = {30,55,80};
    
    int [] xBloques1 = {20,90,165,235,325,398,488,558,633,703};
    int [] yBloques2 = {30,55,80};
    
    public final int limitesX [] = {15,778};
    public final int limitesY [] = {15,456};
    
    private int tamañoPelota = 10;
    private int [] puntosPelotaX;
    private int [] puntosPelotaY;
    private int [] puntosPaletaX;
    private int [] puntosPaletaY;
    private int [] bloquesColision;
    private boolean [] posiciones = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
    private boolean [] posiciones1 = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
    private int [] posicionesNum = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int [] posicionesNum1 = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int [] posicionesContadorToques = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int [] posicionesRebotes = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private boolean piso = true;
    private boolean paletaMov = true;
    private int lado;
    private int vidas = 4;
    private boolean [] vecesVidas = {true,true,true,true};
    private boolean [] lateral = {false,false};
    //MENU
    private int seleccionPelota = 0;
    private int seleccionPaleta = 1;
    private boolean automatico = false;
    
    int [] tt = {0,0,0};
    Timer tm = new Timer(tt);
    
    public void actualizarPaletaPausa(int dato){
        //System.out.println(vidas);
        if (piso){
            if (vidas == 4){
                xPelota = 45 + xPaleta;
                yPelota = 410;
                if (dato == 3){
                    vidas--;
                }
            }
        }
        else {
            if (vecesVidas[3] && vidas == 4) {
                //System.out.println("SINO");
                xPelota = 45 + xPaleta;
                yPelota = 410;
                if (dato == 3){
                    vecesVidas[3] = false;
                }
            }
            else {
                if (vecesVidas[2] && vidas == 3){
                    //System.out.println("SINO");
                    xPelota = 45 + xPaleta;
                    yPelota = 410;
                    if (dato == 3){
                        vecesVidas[2] =  false;
                    }
                }
                else {
                    if (vecesVidas[1] && vidas == 2){
                        //System.out.println("SINO");
                        xPelota = 45 + xPaleta;
                        yPelota = 410;
                        if (dato == 3){
                            vecesVidas[1] = false;
                        }
                    }
                    else {
                        if (vecesVidas[0] && vidas == 1){
                            //System.out.println("SINO");
                            xPelota = 45 + xPaleta;
                            yPelota = 410;
                            if (dato == 3){
                                vecesVidas[0] = false;
                            }
                        }
                    }
                    if (vidas == 0){
                        xPelota = 45 + xPaleta;
                        yPelota = 410;
                    }
                }
            }
        }
        if (dato == 1 && paletaMov){
            if (xPaleta >= limitesX[1] - 100 || xPaleta >= limitesX[1] - 110){
                System.out.println("1");
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
            if (dato == 2 && paletaMov){
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
    }
    private int dato;
    //Método utilizado para mover la paleta automaticamente
    public void moverPaletaComputadora(){
        dato = teclado.movimiento();
        xPaleta = xPelota - 45;
        if (xPaleta >= limitesX[1] - 100){
            xPaleta = limitesX[1] - 100;
        }
        else{
            if (xPaleta <= limitesX[0]+15){
                if ((xPaleta-15) < 45){
                    xPaleta = xPaleta - (xPaleta-15);
                }   
            }
        }
        if (dato == 3){
            ejecutar = false;
            paletaMov = false;
        }
        else {
            if (dato == 4){
                moverPaleta = true;
                automatico = false;
            }
        }
        System.out.println("Chau");
        dato = -1;
    }
    
    //Método utilizado para mover la paleta
    public void moverPaleta(){
        dato = teclado.movimiento();
        if (dato == 1){
            if (xPaleta >= limitesX[1] - 100 || xPaleta >= limitesX[1] - 110){
                if (xPaleta != limitesX[1] - 100){
                    xPaleta = xPaleta + ((limitesX[1]-100) - xPaleta);
                }
            }
            else{
                xPaleta = xPaleta + 12;
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
                }
            }
            else{
                if (dato == 3){
                    ejecutar = false;
                    paletaMov = false;
                }
                else {
                    if (dato == 4){
                        automatico = true;
                        moverPaleta = false;
                    }
                }
            }
            System.out.println("Hola");
        }
        dato = -1;
    }
    private static boolean moverPaleta = true;
    //Método actualizado para actualizar el dibujo durante -> ejecutar = true <-
    public void actualizar(){
        
        for (int i = 0; i < 30; i++){
            //System.out.print(posiciones1[i]+"|");
        }
        //System.out.println("\n");
//        for (int i = 0; i < 30; i++){
//            System.out.print(posicionesRebotes[i]+"|");
//        }
        if (moverPaleta){
            moverPaleta();
        }
        if (automatico){
            moverPaletaComputadora();
        }
        
        
        
        //Pelota
        puntosPelotaX = Colisiones.determinarPuntosX(xPelota, tamañoPelota);
        puntosPelotaY = Colisiones.determinarPuntosY(yPelota, tamañoPelota);
        //Paleta
        puntosPaletaX = Colisiones.determinarPuntosX(xPaleta, 100);
        puntosPaletaY = Colisiones.determinarPuntosY(yPaleta, 10);
        //Bloques 
        bloquesColision = Colisiones.bloques(puntosPelotaX, puntosPelotaY, xBloques0, yBloques0);
        if (bloquesColision[0] < 0){
            
        }
        else{
            if (nivel == 1){
                posiciones[bloquesColision[0]] = false;
                //System.out.println(posiciones[bloquesColision[0]]);
                for (int i = 0; i < 30; i++){
                    System.out.print(posiciones[i]+"|");
                }
            }
            else {
                if (nivel == 2){
                    if (posicionesContadorToques[bloquesColision[0]] > 2){
                        posiciones1[bloquesColision[0]] = false;
                    }
                    else {
                        posicionesContadorToques[bloquesColision[0]]++;
                    }   
                    System.out.println("Toques: "+posicionesContadorToques[bloquesColision[0]]);
                }
            }
        }        
        lado = bloquesColision[1];
        
        //movX = true -> xPelota++
        //movX = false -> xPelota--
        
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
        if ((yPelota-3 <= yPaleta-tamañoPelota && movY == true)){
            yPelota++;
            if (yPelota-3 >= (yPaleta-tamañoPelota) && Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY)){
                movY = false;
            }
            else{
                if (yPelota-3 >= (yPaleta-tamañoPelota) && !Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY)){
                    movY = true;
                    piso = false;
                    ejecutar = false;
                    paletaMov = true;
                    vidas--;
                }
            }
            if (nivel == 1){
                if (bloquesColision[0] > 0 && posicionesNum[bloquesColision[0]] == 0){
                    //System.out.println(bloquesColision[0]);
                    if ((lado == 3 || lado == 4)){
                        movY = false;
                    }
                    posicionesNum[bloquesColision[0]] = bloquesColision[0];
                }
            }
            else {
                if (nivel == 2){
                    if (bloquesColision[0] > 0 && posicionesNum1[bloquesColision[0]] == 0){
                        if (posicionesRebotes[bloquesColision[0]] <= 2){
                            if ((lado == 3 || lado == 4)){
                                movY = false;
                                posicionesRebotes[bloquesColision[0]]++;
                                if (posicionesRebotes[bloquesColision[0]] == 2){
                                    posicionesNum1[bloquesColision[0]] = bloquesColision[0];
                                    movY = false;
                                }
                            }
                        }
                        else {
                        }
                    }
                }
            }
        }
        else{
            if (yPelota >= limitesY[0] && movY == false){
                yPelota--;
                if (yPelota == limitesY[0]){
                    movY = true;
                }
                if (nivel == 1){
                    if (bloquesColision[0] > 0 && posicionesNum[bloquesColision[0]] == 0){
                        //System.out.println(bloquesColision[0]);
                        if ((lado == 1 || lado == 2)){
                            movY = true;
                        }   
                        posicionesNum[bloquesColision[0]] = bloquesColision[0];
                    }
                }
                else { 
                    if (nivel == 2){
                        if (bloquesColision[0] > 0 && posicionesNum1[bloquesColision[0]] == 0){
                            if (posicionesRebotes[bloquesColision[0]] <= 2){
                                if ((lado == 1 || lado == 2)){
                                    movY = true;
                                    posicionesRebotes[bloquesColision[0]]++;
                                    if (posicionesRebotes[bloquesColision[0]] == 2){
                                        posicionesNum1[bloquesColision[0]] = bloquesColision[0];
                                        movY = true;
                                    }
                                }
                            }
                            else{
                                
                            }
                        }
                    }
                }
            }
        }
        lateral = Colisiones.lateral(puntosPelotaX, puntosPelotaY, xBloques0, yBloques0);
        if (nivel == 1){
            if (lateral[1] && bloquesColision[0] > 0 && posicionesNum[bloquesColision[0]] == 0){
                movX = true;
                movY = false;
            }
            else {
                if (lateral[0] && bloquesColision[0] > 0 && posicionesNum[bloquesColision[0]] == 0){
                   movX = true;
                }
                else {
                
                }
            }
        }
        else {
            if (nivel == 2){
                if (lateral[1] && bloquesColision[0] > 0 && posicionesNum1[bloquesColision[0]] == 0){
                    if (posicionesRebotes[bloquesColision[0]] <= 2){
                        movX = true;
                        movY = false;
                        posicionesRebotes[bloquesColision[0]]++;
                        if (posicionesRebotes[bloquesColision[0]] == 2){
                            posicionesNum1[bloquesColision[0]] = bloquesColision[0];
                            movX = true;
                            movY = false;
                        }
                    }
                    
                }
                else {
                    if (lateral[0] && bloquesColision[0] > 0 && posicionesNum1[bloquesColision[0]] == 0){
                        if (posicionesRebotes[bloquesColision[0]] <= 2){
                            movX = true;
                            if (posicionesRebotes[bloquesColision[0]] == 2){
                                posicionesNum1[bloquesColision[0]] = bloquesColision[0];
                                movX = true;
                            }
                        }
                    }
                    else {

                    }
                }
            }
        }
        
        lado = bloquesColision[1];
        
        if (nivel == 2){
            for (int i = 0; i < 30; i++){
                if (posicionesNum1[i] == 0 && !posiciones1[i]){
                    posicionesNum1[i] = i;
                    posiciones1[i] = false;
                }
            }
        }
        
        
    }
    
    //Uso del timer
    
    Imagenes imagenes = new Imagenes();
    List<BufferedImage> dibujos = imagenes.cargarImagenes();
    
    public void dibujar(){
        buffer = canvas.getBufferStrategy();
        if (buffer == null){
            canvas.createBufferStrategy(3);
            return;
        }
        
        g = buffer.getDrawGraphics();
        //Dibujo area inicio
        
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        //Relleno area de juego
        g.setColor(Color.lightGray);
        g.fillRect(15, 15, 763, 441);
        //Contorneado
        g.setColor(Color.black);
        g.drawRect(15, 15, 763, 441);
        //Caja de datos relleno
        g.setColor(Color.lightGray);
        g.fillRect(15, 465, 763, 25);
        //Caja de datos contorno
        g.setColor(Color.black);
        g.drawRect(15, 465, 763, 25);
        
        
        
        switch (seleccionPelota){
            case 0: g.drawImage(dibujos.get(0), xPelota, yPelota, tamañoPelota, tamañoPelota, this);
            break;
            case 1: g.drawImage(dibujos.get(1), xPelota, yPelota, tamañoPelota, tamañoPelota, this);
            break;
            case 2: g.drawImage(dibujos.get(2), xPelota, yPelota, tamañoPelota, tamañoPelota, this);
            break;
            case 3: g.drawImage(dibujos.get(3), xPelota, yPelota, tamañoPelota, tamañoPelota, this);
            break;
            case 4: g.drawImage(dibujos.get(4), xPelota, yPelota, tamañoPelota, tamañoPelota, this);
        }
        
        //g.drawOval(xPelota, yPelota, tamañoPelota, tamañoPelota);
        g.drawString("x:"+xPelota+"|y:"+yPelota, xPelota, yPelota);
        g.drawString("PELOTA|x:"+xPelota+"y:"+yPelota,10,25);
        //g.drawImage(dibujos.get(4), 50, 50, 10, 10, this);
        
        //Ring de JUEGO
        g.drawLine(limitesX[0], limitesY[0], limitesX[0], limitesY[1]);
        g.drawLine(limitesX[0], limitesY[0], limitesX[1], limitesY[0]);
        g.drawLine(limitesX[1], limitesY[0], limitesX[1], limitesY[1]);
        g.drawLine(limitesX[0], limitesY[1], limitesX[1], limitesY[1]);        
        
        int contadorInterno = 0;
        int contadorInterno1 = 0;
        if (nivel == 1){
            for (int i = 0; i < 10; i++){
                for (int j = 0; j < 3; j++){
                    if (posiciones[contadorInterno] == false) {

                    }
                    else{
                        if (j == 0){
                            g.drawImage(dibujos.get(5), xBloques0[i], yBloques0[j], 70, 20, this);
                        }
                        else {
                            if (j == 1){
                                g.drawImage(dibujos.get(6), xBloques0[i], yBloques0[j], 70, 20, this);
                            }
                            else {
                                if (j == 2){
                                    g.drawImage(dibujos.get(7), xBloques0[i], yBloques0[j], 70, 20, this);
                                }
                            }
                        }
                    }
                    contadorInterno++;
                }
            }
        }
        else {
            if (nivel == 2){
                for (int i = 0; i < 10; i++){
                    for (int j = 0; j < 3; j++){
                        if (posiciones1[contadorInterno1] == false) {

                        }
                        else{
                            if (j == 0){
                                g.drawImage(dibujos.get(5), xBloques0[i], yBloques0[j], 70, 20, this);
                            }
                            else {
                                if (j == 1){
                                    g.drawImage(dibujos.get(6), xBloques0[i], yBloques0[j], 70, 20, this);
                                }
                                else {
                                    if (j == 2){
                                        g.drawImage(dibujos.get(7), xBloques0[i], yBloques0[j], 70, 20, this);
                                    }
                                }
                            }
                        }
                        contadorInterno1++;
                    }
            }
            }
        }
        //System.out.println("C: "+contadorInterno);
        
        //Paleta
        if (seleccionPaleta == 0){
            g.drawImage(dibujos.get(8),xPaleta,yPaleta,100,10,this);
        }
        else {
            if (seleccionPaleta == 1){
                g.drawImage(dibujos.get(9),xPaleta,yPaleta,100,10,this);
            }
        }
        
        g.drawString("FPS: "+promedioFPS, 10, 15);
        if (ejecutar){
            g.drawString("TIEMPO: "+tm.Contador(), 10, 35);
        }
        else{
            g.drawString("TIEMPO: PAUSADO", 10, 35);
        }
        
        //Dibujo area fin
        g.dispose();
        buffer.show();
        
    }
    
    private final int FPS = 60;
    private final double tiempoObjetivo = 1000000000/FPS;
    private double tiempoTranscurrido = 0;
    private int promedioFPS = FPS;
    
    //Ejecución
    public void ciclo() throws InterruptedException {
        
        long ahora;
        long antes = System.nanoTime();
        int frames = 0;
        long tiempo = 0;        
        boolean primera = true;
        float sumatoria = 0;
        
        //Ciclo infinito que no puede ser detenido
        boolean ciclo = true;
        while (ciclo){
            int dato = teclado.movimiento();
            actualizarPaletaPausa(dato);
            dibujar();
            Thread.sleep(5);
            //System.out.println(vidas);
            if (dato == 3 && vidas > 0){
                ejecutar = true;
                antes = System.nanoTime();
                
            }
            //Ciclo de ejecución del juego
            while (ejecutar) {
//                System.out.println("Hola");
                ahora = System.nanoTime();
//                tiempoTranscurrido += (ahora - antes)/tiempoObjetivo;
//                tiempo += (ahora - antes);
                sumatoria = sumatoria + (ahora - antes);
                //System.out.println("Sumatoria: "+(sumatoria/1000000000));
                antes = ahora;
                
                actualizar();
                dibujar();
                Thread.sleep(5);

//                if (tiempoTranscurrido >= 1){
//                    actualizar();
//                    dibujar();
//                    tiempoTranscurrido--;
//                    frames++;
//                }
//                if (tiempo >= 1000000000){
//                    promedioFPS = frames;
//                    frames = 0;
//                    tiempo = 0;
//                }
            }
        }
        
//        detener();
    }
    
//    //Iniciar hilo
//    private void iniciar(){
//        hilo = new Thread(this);
//        hilo.start();
//        ejecutar = false;
//    }
//    
//    //Detener hilo
//    private void detener(){
//        try {
//            hilo.join();
//            ejecutar = false;
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}