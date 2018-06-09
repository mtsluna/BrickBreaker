/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

//Clases importadas
import Interacciones.Colisiones;
import Interacciones.Imagenes;
import Interacciones.Teclado;
import Adicionales.SaveScore;
import Adicionales.Velocidad;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author MtsSk
 */
public class Ventana extends JFrame{
    
    //Escala de pantalla
    public static final int WIDTH = 800;
    public static final int HEIGHT = 530;
    
    //Lienzo
    private Canvas canvas;
    private BufferStrategy buffer;
    private Graphics g;
    
    //Control de inicio de juego en FALSO
    private boolean ejecutar = false;    
    
    //Objeto teclado
    private Teclado teclado; 
    
    //PELOTA variables de control
    int xPelota = 387;
    int yPelota = 410;
    private int tamañoPelota = 10;
    boolean movX = true;
    boolean movY = true;
    boolean direccion = true;
    private boolean piso = true;
    private int velocidad = 1;
    
    //PALETA variables de control
    int xPaleta = 346;
    int yPaleta = 430;
    private boolean paletaMov = true;
    
    //Posicion BLOQUES
    int [] xBloques0 = {20,90,165,235,325,398,488,558,633,703};
    int [] yBloques0 = {30,55,80};    
    int [] xBloques1 = {20,90,165,235,325,398,488,558,633,703};
    int [] yBloques2 = {30,55,80};
    
    //Area de juego
    public final int limitesX [] = {15,778};
    public final int limitesY [] = {15,456};
    
    //Aleatorios y control de ciclos
    int random;   
    int contador = 0;
      
    //Niveles   
    int contadorNivel = 1;
    int nivel = 1;
    int nivelesHechos = 1;
    
    //Vidas
    private int vidas = 4;
    private boolean [] vecesVidas = {true,true,true,true};
    
    //Colisiones
    private int [] puntosPelotaX;
    private int [] puntosPelotaY;
    private int [] puntosPaletaX;
    private int [] puntosPaletaY;
    private int [] bloquesColision;
    private int lado;
    private int i = 0;
    private int colisionAnterior;
    private int contadorRestante = 30;
    private boolean [] colisionLateral;
    
    //Rotura de bloques
    private boolean [] posiciones = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
    private boolean [] posiciones1 = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true};
    private int [] posicionesContadorToques = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    
    //Modos
    private boolean automatico = false;
    
    //Score
    private int score = 0;
    private int scoreAnterior = 0;
    private boolean colorScore = false;
    private String scoreString = "000000000";
    private boolean menuScore = false;
    private int mensajeDerrota = 0;  
    
    //Menu
    private int seleccionPelota = 0;
    private int seleccionPaleta = 1;
    private int seleccionMenu = 0;
    private boolean skins = true;
    private boolean menuPrincipal = true;
    private boolean dibujarMenu = true;
    private int contadorColor = 0;
    
    //Tiempo
    private float sumatoria = 0;
    private int [] tt = {0,0,0};
    
    //Teclado
    private int dato;
    
    //Imagenes
    public Imagenes imagenes = new Imagenes();
    public List<BufferedImage> dibujos = imagenes.cargarImagenes();
    
    //Velocidad de juego
    private int threadVelocidad = 9;
    
    //Sistema Operativo
    private int fuente = 36;
    
    /*CONSTRUCTORES*/
    
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
    
    /*FUNCIONES*/
    
    public static void main(String[] args) throws InterruptedException{
        //Creacion de objeto ventana
        Ventana ventana = new Ventana("Brick Breaker");
        //Inicia el ciclo del juego
        ventana.ciclo(); 
    }
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
                automatico = false;
            }
        }
        dato = -1;
    }
    public void moverPaleta(){
        dato = teclado.movimiento();
        if (!automatico){
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
                        }
                    }
                }
            }
            dato = -1;
        }
        else {
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
                    automatico = false;
                }
            }
            dato = -1;
        }
    }
    public void reiniciar(){
        i = 0;
        contadorRestante = 30;
        velocidad = 1;
        
        contador = 0;
        direccion = true; movX = true; movY = true;
        xPelota = 387; yPelota = 410; xPaleta = 346; yPaleta = 430;

        nivel = (int)(Math.random()*2)+1;
        nivelesHechos++;        
        
        for (int j = 0; j < posiciones.length; j++){
            posiciones[j] = true;
            posiciones1[j] = true;
            posicionesContadorToques[j] = 0;
        }     
        
        for (int j = 0; j < vecesVidas.length; j++){
            vecesVidas[j] = true;
        }
        
        piso = false;
        paletaMov = true;   
        ejecutar = false;
    }
    public void actualizar(){                 
        if (i == 0){
            //System.out.println(colisionAnterior);
            colisionAnterior = -1;
            i++;
        }
        else {
            colisionAnterior = bloquesColision[0];
        }
         
        moverPaleta();      
        
        //Pelota
        puntosPelotaX = Colisiones.determinarPuntosX(xPelota, tamañoPelota);
        puntosPelotaY = Colisiones.determinarPuntosY(yPelota, tamañoPelota);
        //Paleta
        puntosPaletaX = Colisiones.determinarPuntosX(xPaleta, 100);
        puntosPaletaY = Colisiones.determinarPuntosY(yPaleta, 10);
        //Bloques 
        bloquesColision = Colisiones.bloques(puntosPelotaX, puntosPelotaY, xBloques0, yBloques0);
        lado = bloquesColision[1];
        colisionLateral = Colisiones.lateral(puntosPelotaX, puntosPelotaY, xBloques0, yBloques0);
        
        if (movX){
            if (xPelota >= limitesX[1]-tamañoPelota){
                movX = false;
            }
            else {
                xPelota += velocidad;
            }
        }
        else {
            if (xPelota <= limitesX[0]){
                movX = true;
            }
            else {
                xPelota += velocidad*(-1);
            }
        }
        if (movY){
            if (yPelota >= limitesY[1]-tamañoPelota){
                movY = false;
            }
            else {
                yPelota += velocidad;
            }
        }
        else{
            if (yPelota <= limitesY[0]){
                movY = true;
            }
            else {
                yPelota += velocidad*(-1);
            }
        }
        
        if (yPelota+8 >= yPaleta && Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY)){
            movY = false;
        }
        else {
            if (yPelota+8 >= yPaleta && !Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY)){
                movY = true;
                piso = false;
                ejecutar = false;
                paletaMov = true;
                vidas--;
            }
        }
        
        if (nivel == 1){
            if (bloquesColision[0] > -1 && posiciones[bloquesColision[0]]){
                
                posiciones[bloquesColision[0]] = false;    
                scoreAnterior = score;
                score = SaveScore.contadorScore(score, bloquesColision[0]);
                
                if ((score-scoreAnterior)>35){
                    colorScore = true;
                }
                
                scoreString = SaveScore.completarScore(score);
                contadorRestante--;
                velocidad = Velocidad.variacion(velocidad);
                
                if (lado == 1 || lado == 2){
                    movY = true;
                    //System.out.println(colisionLateral[0]);
                    if (colisionLateral[0]){
                        movX = false;
                    }
                    if (colisionLateral[1]){
                        movX = true;
                    }
                }
                else {
                    if (lado == 3 || lado == 4){
                        movY = false;
                        if (colisionLateral[0]){
                            movX = false;
                        }
                        if (colisionLateral[1]){
                            movX = true;
                        }
                    }   
                }
            }
        }
        else {
            if (nivel == 2){
                if (bloquesColision[0] > -1 && posiciones1[bloquesColision[0]]){
                    if (lado == 1 || lado == 2){
                        movY = true;
                        //System.out.println(colisionLateral[0]);
                        if (colisionLateral[0]){
                            movX = false;
                        }
                        if (colisionLateral[1]){
                            movX = true;
                        }
                    }
                    else {
                        if (lado == 3 || lado == 4){
                            movY = false;
                            if (colisionLateral[0]){
                                movX = false;
                            }
                            if (colisionLateral[1]){
                                movX = true;
                            }
                        }   
                    }
                    if (colisionAnterior != bloquesColision[0]){
                        posicionesContadorToques[bloquesColision[0]]++;
                        //System.out.println(posicionesContadorToques[bloquesColision[0]]);
                        if (posicionesContadorToques[bloquesColision[0]] >= 2){
                            
                            posiciones1[bloquesColision[0]] = false;
                            scoreAnterior = score;
                            score = SaveScore.contadorScore(score, bloquesColision[0]);
                            if ((score-scoreAnterior)>35){
                                colorScore = true;
                                System.out.println(colorScore);
                            }
                            
                            scoreString = SaveScore.completarScore(score);
                            contadorRestante--;
                            velocidad = Velocidad.variacion(velocidad);
                            
                        }
                    }
                }
            }
        }
    }
    public void dibujar(){
        System.out.println(fuente);
        buffer = canvas.getBufferStrategy();
        if (buffer == null){
            canvas.createBufferStrategy(3);
            return;
        }
        
        g = buffer.getDrawGraphics();
        //Dibujo area inicio
        
        g.clearRect(0, 0, WIDTH, HEIGHT);
                        
        //Relleno area de juego
//        g.setColor(Color.black);
//        g.fillRect(15, 15, 763, 441);
        //Contorneado
        g.setColor(Color.black);
        g.drawRect(15, 15, 763, 441);
        //Caja de datos relleno
        g.setColor(Color.lightGray);
        g.fillRect(15, 465, 763, 25);
        g.drawImage(dibujos.get(10), limitesX[0], limitesY[0], 764, 441, this);
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
        
        g.drawString("N: "+contadorNivel+"| D: "+nivel, 20, 482);
        
        int Aux = 0;
        
        if (score < 999999999){
            if (colorScore){
                g.setColor(Color.yellow);
                g.drawString("PUNTAJE: "+scoreString, 346, 482);
                contadorColor++;
                if (contadorColor == 120){
                    colorScore = false;
                    contadorColor = 0;
                }
            }
            else{
                g.setColor(Color.blue);
                g.drawString("PUNTAJE: "+scoreString, 346, 482);
            }            
        }        
        else{
            g.drawString("PUNTAJE: "+"INCREIBLE", 346, 482);
        }
        
        
        g.setColor(Color.black);
        
        g.drawString("VIDAS: ", 650, 482);
        if (vidas == 4 || vidas == 3){
            g.drawImage(dibujos.get(seleccionPelota), 690, 470, 15, 15, this);
            g.drawString("3", 755, 482);
        }
        if (vidas >= 2) {
            g.drawImage(dibujos.get(seleccionPelota), 710, 470, 15, 15, this);
            if (vidas == 2) {
                g.drawString("2", 755, 482);
            }
        }
        if (vidas >= 1) {
            g.drawImage(dibujos.get(seleccionPelota), 730, 470, 15, 15, this);
            if (vidas == 1) {
                g.drawString("1", 755, 482);
            }
        }
        if (vidas < 1) {
            g.drawString("PERDISTE", 696, 482);
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
                            g.drawImage(dibujos.get(6), xBloques0[i], yBloques0[j], 70, 20, this);
                        }
                        else {
                            if (j == 1){
                                g.drawImage(dibujos.get(5), xBloques0[i], yBloques0[j], 70, 20, this);
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
                        g.drawString("Bloque: "+contadorInterno1, xBloques0[i], yBloques0[j]);
                        if (posiciones1[contadorInterno1] == false) {

                        }
                        else{
                            if (posicionesContadorToques[contadorInterno1] == 0){
                                if (j == 0){
                                    g.drawImage(dibujos.get(6), xBloques0[i], yBloques0[j], 70, 20, this);
                                }
                                else {
                                    if (j == 1){
                                        g.drawImage(dibujos.get(5), xBloques0[i], yBloques0[j], 70, 20, this);
                                    }
                                    else {
                                        if (j == 2){
                                            g.drawImage(dibujos.get(7), xBloques0[i], yBloques0[j], 70, 20, this);
                                        }
                                    }
                                }
                            }
                            else{
                                if (j == 0){
                                    g.drawImage(dibujos.get(13), xBloques0[i], yBloques0[j], 70, 20, this);
                                }
                                else {
                                    if (j == 1){
                                        g.drawImage(dibujos.get(12), xBloques0[i], yBloques0[j], 70, 20, this);
                                    }
                                    else {
                                        if (j == 2){
                                            g.drawImage(dibujos.get(14), xBloques0[i], yBloques0[j], 70, 20, this);
                                        }
                                    }
                                }
                            }
                        }
                        contadorInterno1++;
                    }
            }
            }
        }
        
        //Paleta
        if (seleccionPaleta == 0){
            g.drawImage(dibujos.get(8),xPaleta,yPaleta,100,10,this);
        }
        else {
            if (seleccionPaleta == 1){
                g.drawImage(dibujos.get(9),xPaleta,yPaleta,100,10,this);
            }
        }
        
        if (menuScore){
            g.setColor(Color.black);
            g.fillRect(limitesX[0], limitesY[0], 764, 475);
            
            g.setFont(new Font("Impact", Font.BOLD, fuente));
            
            g.setColor(Color.yellow);
            g.drawString("PERDISTE", ((limitesX[1]-limitesX[0])/2)-68, 88);
            g.setColor(Color.cyan);
            g.drawString("PERDISTE", ((limitesX[1]-limitesX[0])/2)-72, 92);
            g.setColor(Color.magenta);
            g.drawString("PERDISTE", ((limitesX[1]-limitesX[0])/2)-76, 96);
            
            g.setColor(Color.white);            
            g.drawString("PERDISTE", ((limitesX[1]-limitesX[0])/2)-80, 100);
            
            g.setColor(Color.white);
            
                        
            g.setColor(Color.black);
        }
        
        if (ejecutar){
            String minutos = "", segundos = "";
            if (tt[1] < 10){
                segundos = segundos.concat("0"+tt[1]);
            }
            else{
                segundos = segundos.concat(""+tt[1]);
            }
            if (tt[0] < 10){
                minutos = minutos.concat("0"+tt[0]);
            }
            else{
                minutos = minutos.concat(""+tt[0]);
            }
            g.drawString("| TIEMPO: "+minutos+":"+segundos, 100, 482);
        }
        else{
            g.drawString("| TIEMPO: PAUSADO", 100, 482);
        }
        
        if (dibujarMenu){
            g.setColor(Color.black);
            g.fillRect(limitesX[0], limitesY[0], 764, 475);
            g.drawImage(dibujos.get(10), limitesX[0], limitesY[0], 764, 475, this);
            g.setColor(Color.white);
            g.drawString("[<-] Mover a la izquierda || [ENTER] Seleccionar || Mover a la derecha [->]", 200, 485);
            
            //Titulo
            g.setColor(Color.yellow);
            g.setFont(new Font("Impact", Font.BOLD, fuente));
            g.drawString("BRICK BREAKER", ((limitesX[1]-limitesX[0])/2)-138, 96);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-138, 31, 320, 10);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-138, 116, 320, 10);
            g.setColor(Color.magenta);
            g.drawString("BRICK BREAKER", ((limitesX[1]-limitesX[0])/2)-134, 100);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-134, 35, 320, 10);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-134, 120, 320, 10);
            g.setColor(Color.cyan);
            g.drawString("BRICK BREAKER", ((limitesX[1]-limitesX[0])/2)-138, 104);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-138, 39, 320, 10);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-138, 124, 320, 10);
            g.setColor(Color.white);
            g.drawString("BRICK BREAKER", ((limitesX[1]-limitesX[0])/2)-138, 100);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-138, 35, 320, 10);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-138, 120, 320, 10);
            
            int [] x = {100, 321, 538};
            int y = 390;
            
            g.setColor(Color.magenta);
            g.fillRect(x[seleccionMenu]-12, y-12, 154, 79);
            g.setColor(Color.yellow);
            g.fillRect(x[seleccionMenu]-8, y-8, 154, 79);
            g.setColor(Color.cyan);
            g.fillRect(x[seleccionMenu]-4, y-4, 154, 79);
            
            g.setColor(Color.white);
            g.fillRect(100, y, 150, 75);
            g.fillRect(538, y, 150, 75);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-60, y, 150, 75);
                        
            g.setColor(Color.black);
            g.drawString("JUGAR", x[0]+10, y+55);
            g.drawString("SKINS", x[1]+10, y+55);
            g.drawString("INFO", x[2]+28, y+55);            
            
            if (!skins){
                g.setColor(Color.cyan); g.fillRect(338, 188, 100, 100);
                g.setColor(Color.yellow); g.fillRect(342, 192, 100, 100);
                g.setColor(Color.magenta); g.fillRect(346, 196, 100, 100);
                g.drawImage(dibujos.get(seleccionPelota), 350, 200, 100, 100, this);
                g.setColor(Color.white);
                
                g.drawImage(dibujos.get(15), 390, 160, 20, 20, this);
                g.drawImage(dibujos.get(16), 390, 310, 20, 20, this);
            }
                        
        }
        
        
        
        //Dibujo area fin
        g.dispose();
        buffer.show();
        
    }
    private int SO(){
        try{
            String so = System.getProperty("os.name");
            System.out.println(so);

            if (so.toLowerCase().trim().startsWith("windows") || so.toLowerCase().trim().startsWith("win")){
                return 1;
            }
            else{
                if (so.toLowerCase().trim().startsWith("linux") || so.toLowerCase().trim().startsWith("lin")){
                    return 2;
                }
                else{
                    return 2;
                }
            }
        }
        catch(Exception e){
            return 2;
        }
    }
    public void ciclo() throws InterruptedException {
               
        long ahora;
        long antes = System.nanoTime();
        int verificarSO = 0;
        
        //Ciclo infinito que no puede ser detenido
        boolean ciclo = true;
        
        if (verificarSO == 0){
            if (SO() == 1){
                fuente = 36;
            }
            else{
                if (SO() == 2){
                    fuente = 46;
                }
                else{
                    fuente = 46;
                }
            }
            verificarSO++;
        }
        
        while (menuPrincipal){            
            Thread.sleep(threadVelocidad);
            int dato = teclado.movimiento();
            dibujar();
            if (dato == 1){
                if (seleccionMenu < 2){
                    seleccionMenu++;
                }
                else{
                    seleccionMenu = 0;
                }
            }
            else{
                if (dato == 2){
                    if (seleccionMenu > 0){
                        seleccionMenu--;
                    }
                    else{
                        seleccionMenu = 2;
                    }
                }
            }
            System.out.println(seleccionPelota);
            if (dato == 6){
                if (seleccionPelota == 4){
                    seleccionPelota = 0;
                }
                else{
                    if (seleccionPelota < 4){
                        seleccionPelota++;
                    }
                }
            }
            else {
                if (dato == 7){
                    if (seleccionPelota > 0){
                        seleccionPelota--;
                    }
                    else{
                        if (seleccionPelota == 0){
                            seleccionPelota = 4;
                        }
                    }
                }
            }
            if (seleccionMenu == 1){
                skins = false;
            }
            else {
                if (seleccionMenu != 1){
                    skins = true;
                }                
            }
            
            if (seleccionMenu == 2 && dato == 5){
                try{
                String [] seleccion2 = {"Mirar la información","Ajustar el rendimiento"};        
                String info = (String) JOptionPane.showInputDialog(null, "Que deseas realizar...?",
                "INFO", JOptionPane.QUESTION_MESSAGE, null, seleccion2, seleccion2[0]);
                switch(info){
                    case "Mirar la información":
                        JOptionPane.showMessageDialog(null, "Información: \n"
                                                          + "Bienvenido a Brick Breaker!!!\n\n"
                                                          + "No hay reglas, solo trate de que la bola\n"
                                                          + "no caiga en el fondo del ring de juego.\n\n"
                                                          + "=== Niveles ===\n"
                                                          + "Basicamente son infinitos, disfruta!!!\n\n"
                                                          + "=== Dificultades ===\n"
                                                          + "D1: Bloques se rompen con un solo toque\n"
                                                          + "D2: Bloques se rompen con dos toques\n"
                                                          + "EXTRA: En todos los niveles la pelota se acelera\n"
                                                          + "al romper ciertos bloques así que ten cuidado!!!\n\n"
                                                          + "=== Puntos por bloque ===\n"
                                                          + "Verde: 10pts (normal) o 70pts (bonus de la suerte)\n"
                                                          + "Rosa: 20pts (normal) o 140pts (bonus de la suerte)\n"
                                                          + "Amarillo: 30pts (normal) o 210pts (bonus de la suerte)\n\n"
                                                          + "=== Controles ===\n"
                                                          + "[<-] Izquierda || [ENTER] Seleccionar || Derecha[->]\n"
                                                          + "[A] Modo Automatico [ESPACIO] PAUSA E INICIAR\n"
                                                          + "[/\\] Arriba || [\\/] Abajo\n"
                                + "");
                        break;
                    case "Ajustar el rendimiento":
                        String [] cpu = {"Muy alto","Alto","Medio","Bajo","Muy bajo"};
                        String procesamiento = (String) JOptionPane.showInputDialog(null, "Selecciona el nivel de procesamiento de tu PC...",
                        "AJUSTAR EL RENDIMIENTO", JOptionPane.QUESTION_MESSAGE, null, cpu, cpu[2]);
                        switch (procesamiento){
                            case "Muy alto":
                                threadVelocidad = 10;
                                break;
                            case "Alto":
                                threadVelocidad = 9;
                                break;                             
                            case "Medio":
                                threadVelocidad = 8;
                                break;
                            case "Bajo":
                                threadVelocidad = 5;
                                break;
                            case "Muy bajo":
                                threadVelocidad = 2;
                                break;
                        }
                        break;
                    default:                        
                }
                }catch (NullPointerException e){
                    
                }
            }
            
            if (dato == 5 && seleccionMenu == 0){
                skins = true;
                dibujarMenu = false;
                while (ciclo){
                    dato = teclado.movimiento();
                    actualizarPaletaPausa(dato);
                    dibujar();
                    Thread.sleep(threadVelocidad);
                    if (dato == 3 && vidas > 0){
                        ejecutar = true;
                        antes = System.nanoTime();

                    }
                    while (ejecutar) {
                        ahora = System.nanoTime();
                        sumatoria = sumatoria + (ahora - antes);
                        antes = ahora;
                        
                        if (sumatoria/100000000 > 1){
                            tt[2]++;
                        }
                        if (sumatoria/1000000000 > 1){
                            tt[1]++;
                            if (tt[1] > 60){
                                tt[0]++;
                                tt[1] = 0;
                            }
                            sumatoria = 0;
                        }

                        if (contadorRestante == 0){
                            reiniciar();
                            contadorNivel++;
                            for (int i = 0; i < 4; i++){
                                vecesVidas[i] = true;
                            }
                        }
                        actualizar();  
                        
                        dibujar();
                        Thread.sleep(threadVelocidad);
                        if (vidas == 0){
                            menuScore = true;                            
                            while (menuScore){
                                Thread.sleep(threadVelocidad);
                                dibujar();
                                mensajeDerrota++;
                                if (mensajeDerrota == 60){
                                    JOptionPane.showMessageDialog(this, "GRACIAS POR JUGAR!!! TU SCORE FUE DE:\n"
                                                                                 +scoreString+" PUNTOS"); 
                                    System.exit(0);
                                }
                            }
                        }
                    }                    
                }
            }
        }
    }
}