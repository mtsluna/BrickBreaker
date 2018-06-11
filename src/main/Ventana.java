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
    private Color fondo = new Color(43, 10, 19);
    private Color letras = new Color(250,205,157);
    
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
    
    //actualizarPaletaPausa <- TERMINADO Y LIMPIADO
    public void actualizarPaletaPausa(int dato){
        //Si piso es true
        if (piso){
            //Si vida == 4 (Comienzo de partida)
            if (vidas == 4){
                //La pelota adquirira posición del centro de la paleta
                xPelota = 45 + xPaleta;
                //Y su respectiva altura
                yPelota = 410;
                //Si se pulsa espacio
                if (dato == 3){
                    //Se pierde la vida de contención y se empieza el juego
                    vidas--;
                }
            }
        }
        //Si piso NO es true
        else {
            //Si el primer saque de la vida 4 esta habilitado
            if (vecesVidas[3] && vidas == 4) {
                //Pelota toma posicion central de paleta
                xPelota = 45 + xPaleta;
                //Y altura predeterminada
                yPelota = 410;
                //Si se pulsa espacio
                if (dato == 3){
                    //Se invalida la posibilidad de sacar desde la paleta otra vez en la pausa
                    vecesVidas[3] = false;
                }
            }
            //Sino
            else {
                //Si el primer saque de la vida 3 se encuentra habilitado
                if (vecesVidas[2] && vidas == 3){
                    //Pelota toma posicion central de paleta
                    xPelota = 45 + xPaleta;
                    //Y altura predeterminada
                    yPelota = 410;
                    //Si se pulsa espacio
                    if (dato == 3){
                        //Se invalida la posibilidad de sacar desde la paleta otra vez en la pausa
                        vecesVidas[2] =  false;
                    }
                }
                //Sino
                else {
                    //Si el primer saque de la vida 2 se encuentra habilitado
                    if (vecesVidas[1] && vidas == 2){
                        //Pelota toma posicion central de paleta
                        xPelota = 45 + xPaleta;
                        //Y altura predeterminada
                        yPelota = 410;
                        //SI se pulsa espacio
                        if (dato == 3){
                            //Se invalida la posibilidad de sacar desde la paleta otra vez en la pausa
                            vecesVidas[1] = false;
                        }
                    }
                    //Sino
                    else {
                        //Si el primer saque de la vida 1 se encuentra habilitado
                        if (vecesVidas[0] && vidas == 1){
                            //Pelota toma posicion central de paleta
                            xPelota = 45 + xPaleta;
                            //Y altura predeterminada
                            yPelota = 410;
                            //SI se pulsa espacio
                            if (dato == 3){
                                //Se invalida la posibilidad de sacar desde la paleta otra vez en la pausa
                                vecesVidas[0] = false;
                            }
                        }
                    }
                    //Si vidas es igual a 0 -> PERDISTE
                    if (vidas == 0){
                        //Pelota vuelve sobre su lugar en X
                        xPelota = 45 + xPaleta;
                        //E Y junto a la paleta
                        yPelota = 410;
                    }
                }
            }
        }
        //Si se pulsa la flecha derecha y el movimiento de la paleta esta activo
        if (dato == 1 && paletaMov){
            //Si la paleta se encuentra en su maximo limite del eje X
            if (xPaleta >= limitesX[1] - 100 || xPaleta >= limitesX[1] - 110){
                //La paleta ya no puede avanzar
                if (xPaleta != limitesX[1] - 100){
                    //Asignando como maximo el limite[1] - 100 menos la posicion actual
                    xPaleta = xPaleta + ((limitesX[1]-100) - xPaleta);
                }
            }
            //Si la paleta no esta en el limite c/vez que se pulsa o se mantiene apretado
            else{
                //Se le suma +12 a la posicion en X de la paleta
                xPaleta = xPaleta + 12;
            }            
        }
        //Sino
        else {
            //Si se pulsa la flecha izquierda y el movimiento de la paleta se encuentra activado
            if (dato == 2 && paletaMov){
                //Si la X de paleta es inferior o igual al limite[0]
                if (xPaleta <= limitesX[0]+15){
                    //Si la X de paleta no es igual a limite[0]
                    if (xPaleta != limitesX[0]){
                        //xPaleta es igual a xPaleta menos la posicion actual menos el limite
                        xPaleta = xPaleta - (xPaleta - limitesX[0]);
                    }
                }
                //Si la X de paleta es mayor a limite[0]
                else{
                    //Se suma +12 por cada vez que se pulsa la flecha izquierda
                    xPaleta = xPaleta - 12;
                }
            }
            //Sino
            else{
                //Si se pulsa espacio
                if (dato == 3){
                    //Se pausa el juego
                    ejecutar = false;
                }
                //Sino no hace nada
                else{
                    
                }
            }
        }
        //La variable dato mantiene constantemente un valor no ocupado
        dato = -1;
    }
    
    //moverPaletaComputadora <- TERMINADO Y LIMPIADO
    public void moverPaletaComputadora(){
        //dato es igual a la devolucion del metodo movimiento 
        dato = teclado.movimiento();
        //La paleta se centra en la pelota
        xPaleta = xPelota - 45;
        //Si la paleta es mayor que el limite[1]
        if (xPaleta >= limitesX[1] - 100){
            //Se detiene en el limite
            xPaleta = limitesX[1] - 100;
        }
        //Sino
        else{
            //Si la paleta es menor al limite[0]
            if (xPaleta <= limitesX[0]+15){
                //Si la paleta - 15 es menor que 45
                if ((xPaleta-15) < 45){
                    //La paleta se detiene en el limite[0]
                    xPaleta = xPaleta - (xPaleta-15);
                }   
            }
        }
        //Si se presiona espacio
        if (dato == 3){
            //Se detiene el juego
            ejecutar = false;
            //Se detiene el movimiento de la paleta, pasa a ser inmovible
            paletaMov = false;
        }
        //Sino
        else {
            //Si se presiona la letra A o a 
            if (dato == 4){
                //El automatico se desactiva
                automatico = false;
            }
        }
        //La variable dato se mantiene con un valor no usado
        dato = -1;
    }
    
    //moverPaleta <- TERMINADO Y LIMPIADO
    public void moverPaleta(){
        //dato se iguala a la devolucion del metodo movimiento
        dato = teclado.movimiento();
        //Si el automatico esta desactivado
        if (!automatico){
            //Si se pulsa flecha izquierda
            if (dato == 1){
                //Si la paleta es mayor al limite[1]
                if (xPaleta >= limitesX[1] - 100 || xPaleta >= limitesX[1] - 110){
                    //Si la paleta no es igual al limite - 100
                    if (xPaleta != limitesX[1] - 100){
                        //Se le asigna a xPaleta el valor de xPaleta mas el limite menos su valor actual
                        xPaleta = xPaleta + ((limitesX[1]-100) - xPaleta);
                    }
                }
                //Si la pelota no es mayor al limite
                else{
                    //Se le suma +12 por cada pulsacion o tick
                    xPaleta = xPaleta + 12;
                }            
            }
            //Sino
            else {
                //Si se pulsa la flecha izquierda
                if (dato == 2){
                    //Si xPaleta es menor al limite[0]-15
                    if (xPaleta <= limitesX[0]+15){
                        //Si xPaleta no esta exactamente en el limite
                        if (xPaleta != limitesX[0]){
                            //Se iguala a xPaleta, xPaleta - su posicion actual menos el limite
                            xPaleta = xPaleta - (xPaleta - limitesX[0]);
                        }
                    }
                    //Si xPaleta no es menor
                    else{
                        //Se le resta 12 por cada tick o pulsación
                        xPaleta = xPaleta - 12;
                    }
                }
                //Sino
                else{
                    //Si se pulsa espacio
                    if (dato == 3){
                        //Se detiene la ejecución del juego
                        ejecutar = false;
                        //Y la paleta queda inmovible
                        paletaMov = false;
                    }
                    //Sino
                    else {
                        //Si se presiona A o a
                        if (dato == 4){
                            //Se activa el automatico
                            automatico = true;
                        }
                    }
                }
            }
            //El valor de dato se mantiene siempre en un valor que no este en uso
            dato = -1;
        }
        //Sino, si esta en automatico
        else {
            //xPaleta sigue el valor de xPelota - 45
            xPaleta = xPelota - 45;
            //Si xPaleta es mayor que el limite[1] - 100
            if (xPaleta >= limitesX[1] - 100){
                //xPaleta toma el valor de limite[1] - 100
                xPaleta = limitesX[1] - 100;
            }
            //Sino
            else{
                //Si xPaleta es menor o igual que el limite[0} mas 15
                if (xPaleta <= limitesX[0]+15){
                    //Si xPaleta - 15 es menor que 45
                    if ((xPaleta-15) < 45){
                        //xPaleta toma el valor de si mismo menos su valor restado en 15
                        xPaleta = xPaleta - (xPaleta-15);
                    }   
                }
            }
            //Si se pulsa espacio
            if (dato == 3){
                //Se detiene el juego
                ejecutar = false;
                //La paleta queda inmovible
                paletaMov = false;
            }
            //Sino
            else {
                //Si se pulsa A o a
                if (dato == 4){
                    //Se desactiva el automatico
                    automatico = false;
                }
            }
            //La variable dato toma un valor no utilizado.
            dato = -1;
        }
    }
    
    //reiniciar <- TERMINADO Y LIMPIADO
    public void reiniciar(){
        //Se reinician las variables de control a su estado original para dar un cambio de nivel
        i = 0;
        contadorRestante = 30;
        velocidad = 1;
        
        contador = 0;
        //Se reinicia la pelota y paleta
        direccion = true; movX = true; movY = true;
        xPelota = 387; yPelota = 410; xPaleta = 346; yPaleta = 430;

        //Se suma un nivelHecho y se sortea la dificultad nueva
        nivel = (int)(Math.random()*2)+1;
        nivelesHechos++;        
        
        //Se reestablecen los bloques
        for (int j = 0; j < posiciones.length; j++){
            posiciones[j] = true;
            posiciones1[j] = true;
            posicionesContadorToques[j] = 0;
        }     
        
        //Se reestablecen las habilitaciones de primeros saques
        for (int j = 0; j < vecesVidas.length; j++){
            vecesVidas[j] = true;
        }
        
        //Se pausa, se habilita la paleta y se desactiva el piso
        piso = false;
        paletaMov = true;   
        ejecutar = false;
    }
    
    //actualizar <- TERMINADO Y LIMPIADO
    public void actualizar(){ 
        //Si i es igual a 1, es decir es la primera vuelta o uso de actualizar()
        if (i == 0){
            //ColisionAnterior es igual a -1
            colisionAnterior = -1;
            //i queda inutilizado
            i++;
        }
        //Sino
        else {
            //Si colision anterior es igual a la colision actual
            colisionAnterior = bloquesColision[0];
        }
         
        //Desde acá se mueve la paleta
        moverPaleta();      
        
        //Colisiones tanto de pelota, paleta y bloques
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
        
        //Si movX es igual a true la pelota suma en X
        if (movX){
            //Si la pelota toca el limite[1]
            if (xPelota >= limitesX[1]-tamañoPelota){
                //Rebota y cambia de sentido
                movX = false;
            }
            //Sino la pelota avanza dependiendo de la velocidad preestablecida
            else {
                xPelota += velocidad;
            }
        }
        //Si movX es igual a false la pelota resta en X
        else {
            //Si la pelota toca el limite[0]
            if (xPelota <= limitesX[0]){
                //Rebota y cambia de direccion
                movX = true;
            }
            //Sino
            else {
                //La pelota resta en X
                xPelota += velocidad*(-1);
            }
        }
        //Si movY es igual a true la pelota suma en Y
        if (movY){
            //Si el la pelota sobre pasa el limite[1]
            if (yPelota >= limitesY[1]-tamañoPelota){
                //Rebota y la pelota cambia de direccion
                movY = false;
            }
            //Sino
            else {
                //La pelota suma en Y
                yPelota += velocidad;
            }
        }
        //Sino
        else{
            //Si la pelota infiere el limite[0]
            if (yPelota <= limitesY[0]){
                //Rebota y cambia de direccion
                movY = true;
            }
            //Sino
            else {
                //La pelota resta en Y
                yPelota += velocidad*(-1);
            }
        }
        
        //Si se detecta una colision entre la pelota y la paleta
        if (yPelota+8 >= yPaleta && Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY)){
            //Cambia de direccion la pelota
            movY = false;
        }
        //Sino
        else {
            //Si la pelota no colisiona y su posicion es menor que la paleta
            if (yPelota+8 >= yPaleta && !Colisiones.detecta(puntosPelotaX, puntosPelotaY, puntosPaletaX, puntosPaletaY)){
                //Se reinicia la direccion
                movY = true;
                //Piso es falso indicando
                piso = false;
                //Ejecutar es falso deteniendo el juego
                ejecutar = false;
                //Se habilita el movimiento de la paleta
                paletaMov = true;
                //Se resta una vida
                vidas--;
            }
        }
        
        //Si dificultad es igual a 1
        if (nivel == 1){
            //Si colisiona un bloque y ese bloque esta habilitado (no fue destruido)
            if (bloquesColision[0] > -1 && posiciones[bloquesColision[0]]){
                //Se asigna falso al bloque, declarandolo roto
                posiciones[bloquesColision[0]] = false;
                //Se suma score
                scoreAnterior = score;
                //Se pasa por referencia el score
                score = SaveScore.contadorScore(score, bloquesColision[0]);
                
                //Si el score actual menos el anterior es mayor que 35
                if ((score-scoreAnterior)>35){
                    //Significa BONUS, se activa funciones
                    colorScore = true;
                }
                
                //Se pasa el score al String
                scoreString = SaveScore.completarScore(score);
                //Lleva la cuenta de los bloques faltantes
                contadorRestante--;
                //Se determina la velocidad
                velocidad = Velocidad.variacion(velocidad);
                
                //Si colisiona desde abajo
                if (lado == 1 || lado == 2){
                    //Movimiento en X igual a true y rebota
                    movY = true;
                    //Si la colision fuera desde izquierda
                    if (colisionLateral[0]){
                        //Rebota cambiando la direccion
                        movX = false;
                    }
                    //Si la colision fuera desde derecha
                    if (colisionLateral[1]){
                        //Rebota cambiando la direccion
                        movX = true;
                    }
                }
                //Sino
                else {
                    //Si la colision es desde arriba 
                    if (lado == 3 || lado == 4){
                        //Rebota hacia arriba
                        movY = false;
                        //Si es desde izquierda
                        if (colisionLateral[0]){
                            //Cambia direccion en X
                            movX = false;
                        }
                        //Si es desde derecha
                        if (colisionLateral[1]){
                            //Cambia direccion en X
                            movX = true;
                        }
                    }   
                }
            }
        }
        //Sino
        else {
            //Si nivel o dificultad es igual a 2
            if (nivel == 2){
                //Si se detecta una colision de bloques y este esta habilitado
                if (bloquesColision[0] > -1 && posiciones1[bloquesColision[0]]){
                    //Si es desde abajo
                    if (lado == 1 || lado == 2){
                        //Rebota hacia abajo
                        movY = true;
                        //Si es desde izquierda
                        if (colisionLateral[0]){
                            //Cambia direccion en X
                            movX = false;
                        }
                        //Si es desde derecha
                        if (colisionLateral[1]){
                            //Cambia direccion en X
                            movX = true;
                        }
                    }
                    //Sino
                    else {
                        //Si es desde arriba
                        if (lado == 3 || lado == 4){
                            //Rebota en Y
                            movY = false;
                            //Si es desde izquierda
                            if (colisionLateral[0]){
                                //Mov en X es falso
                                movX = false;
                            }
                            //Si es desde derecha
                            if (colisionLateral[1]){
                                //Se invierte el mov en X
                                movX = true;
                            }
                        }   
                    }
                    //Si la colisionAnterior es diferente a la actual
                    if (colisionAnterior != bloquesColision[0]){
                        //Contador de toques suma
                        posicionesContadorToques[bloquesColision[0]]++;
                        //Si el contador de toques es mayor o igual a 2
                        if (posicionesContadorToques[bloquesColision[0]] >= 2){
                            //Se desactiva el bloque
                            posiciones1[bloquesColision[0]] = false;
                            //Guarda el score anterior
                            scoreAnterior = score;
                            //Pasa el nuevo score
                            score = SaveScore.contadorScore(score, bloquesColision[0]);
                            //Si el score actual menos el anterior es mayor que 35
                            if ((score-scoreAnterior)>35){
                                //Hay BONUS de score y lo dibuja
                                colorScore = true;
                            }
                            //El score es pasado a String y completado
                            scoreString = SaveScore.completarScore(score);
                            //Se resta un bloque
                            contadorRestante--;
                            //Se determina una nueva o misma velocidad
                            velocidad = Velocidad.variacion(velocidad);                            
                        }
                    }
                }
            }
        }
    }
    
    //dibujar <- TERMINADO Y LIMPIADO
    public void dibujar(){
        //Se asigna valor al buffer de dibujo
        buffer = canvas.getBufferStrategy();
        //Si el buffer fuese nulo
        if (buffer == null){
            //Se asignan 3 buffers
            canvas.createBufferStrategy(3);
            return;
        }
        
        //La variable g (graphics) adquiere la posibilidad de dibujo
        g = buffer.getDrawGraphics();
        //Dibujo area inicio
        
        //Limpia toda el area de dibujado
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        //Se dibuja el fondo
        g.drawImage(dibujos.get(11), 0, 0, WIDTH, HEIGHT, this);
        //Contorneado
        g.setColor(Color.white);
        g.drawRect(15, 15, 763, 441);
        //Caja de datos relleno
        g.setColor(fondo);
        g.fillRect(15, 465, 763, 25);
        g.drawImage(dibujos.get(10), limitesX[0], limitesY[0], 764, 441, this);
        //Caja de datos contorno
        g.setColor(Color.white);
        g.drawRect(15, 465, 763, 25);
        g.setColor(Color.black);
        
        //Seleccion de pelota se cambian la misma desde el menu
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
        
        //Color especial
        g.setColor(letras);
        //Se dibuja el nivel y dificultad
        g.drawString("N: "+contadorNivel+"| D: "+nivel, 20, 482);
                
        //Si el score es menor que 999999999
        if (score < 999999999){
            //Si color score es TRUE
            if (colorScore){
                //Existe un bonus y lo dibuja en amarillo
                g.setColor(Color.yellow);
                g.drawString("PUNTAJE: "+scoreString, 346, 482);
                //Inicia un contador interno
                contadorColor++;
                //Si el contador llega a 120 frames
                if (contadorColor == 120){
                    //Se desactiva
                    colorScore = false;
                    //Se desactiva y reinicia el contador
                    contadorColor = 0;
                }
            }
            //Sino
            else{
                //Se lo dibuja en color especial
                g.setColor(letras);
                g.drawString("PUNTAJE: "+scoreString, 346, 482);
            }            
        }   
        //Si el score supera ese limite se deja de contar y se pone el slogan INCREIBLE
        else{
            g.setColor(letras);
            g.drawString("PUNTAJE: "+"INCREIBLE", 346, 482);
        }
        
        //Se lo dibuja en color especial
        g.setColor(letras);
        
        //Se dibuja la palabra vida
        g.drawString("VIDAS: ", 650, 482);
        //Dependiendo de cuantas vidas se tengan es la cantidad de pelotas que se dibujan
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
        //Si la vida es 0 muestra un PERDISTE
        if (vidas < 1) {
            g.drawString("PERDISTE", 696, 482);
        }
        
        //PARA COMPROBAR POSICIONES DE PELOTA
//        g.drawString("x:"+xPelota+"|y:"+yPelota, xPelota, yPelota);
//        g.drawString("PELOTA|x:"+xPelota+"y:"+yPelota,10,25);
        
        //Si no se dibuja menu
        if (!dibujarMenu){
        //Ring de JUEGO
        g.setColor(Color.white);
        g.drawLine(limitesX[0], limitesY[0], limitesX[0], limitesY[1]);
        g.drawLine(limitesX[0], limitesY[0], limitesX[1], limitesY[0]);
        g.drawLine(limitesX[1], limitesY[0], limitesX[1], limitesY[1]);
        g.drawLine(limitesX[0], limitesY[1], limitesX[1], limitesY[1]);        
        } 
        
        //Asigna el color negro a los dibujos
        g.setColor(Color.black);
        //Contadores internos para el dibujo de la dificultad 1 y 2
        int contadorInterno = 0;
        int contadorInterno1 = 0;
        //Si nivel o dificultad es 1
        if (nivel == 1){
            //Dos bucles controlan el dibujado
            for (int i = 0; i < 10; i++){
                for (int j = 0; j < 3; j++){
                    //Si la posicion del bloque es falsa no se lo dibuja
                    if (posiciones[contadorInterno] == false) {

                    }
                    //Sino dibuja normalmente usando las imagenes
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
                    //El contador suma
                    contadorInterno++;
                }
            }
        }
        //Sino
        else {
            //Si nivel o dificultad es igual a 2
            if (nivel == 2){
                //2 bucles manejan todo el dibujado de los bloques
                for (int i = 0; i < 10; i++){
                    for (int j = 0; j < 3; j++){
                        //Si el bloque fue deshabilitado no se dibuja
                        if (posiciones1[contadorInterno1] == false) {

                        }
                        //Sino
                        else{
                            //Si el contador de toques es igual a 0 lo dibuja de forma estandar
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
                            //Sino si el contador es igual a 1 lo dibuja roto al bloque
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
                        //El contador interno suma
                        contadorInterno1++;
                    }
                }
            }
        }
        
        //Se dibuja la paleta dependiento de su seleccion, solo puede ser cambiada desde el codigo
        if (seleccionPaleta == 0){
            g.drawImage(dibujos.get(8),xPaleta,yPaleta,100,10,this);
        }
        else {
            if (seleccionPaleta == 1){
                g.drawImage(dibujos.get(9),xPaleta,yPaleta,100,10,this);
            }
        }               
        
        //Si ejecutar es true
        if (ejecutar){
            //Se crean variables minutos y segundo vacia
            String minutos = "", segundos = "";
            //Si los segundos son menores a 10
            if (tt[1] < 10){
                //Se rellenan
                segundos = segundos.concat("0"+tt[1]);
            }
            else{
                //Si no se concatenan como esta
                segundos = segundos.concat(""+tt[1]);
            }
            //Si los minutos son menores a 10
            if (tt[0] < 10){
                //Se rellenan
                minutos = minutos.concat("0"+tt[0]);
            }
            else{
                //Si no se concatenan como estan
                minutos = minutos.concat(""+tt[0]);
            }
            g.setColor(letras);
            //Acá se dibujan
            g.drawString("| TIEMPO: "+minutos+":"+segundos, 100, 482);
        }
        //Sino
        else{
            //El tiempo se pausa si la ejecucion es false
            g.setColor(letras);
            g.drawString("| TIEMPO: PAUSADO", 100, 482);
        }
        
        //Si el menuScore esta habilitado
        if (menuScore){
            //Se setea color por defecto
            g.setColor(Color.black);
            //Se rellena
            g.fillRect(limitesX[0], limitesY[0], 764, 475);
            //Se dibuja el fondo
            g.drawImage(dibujos.get(11), 0, 0, WIDTH, HEIGHT, this);
            
            //Se asigna fuente
            g.setFont(new Font("Impact", Font.BOLD, fuente));
            
            //Fuente de PERDISTE
            g.setColor(Color.yellow);
            g.drawString("PERDISTE", ((limitesX[1]-limitesX[0])/2)-68, 88);
            g.setColor(Color.cyan);
            g.drawString("PERDISTE", ((limitesX[1]-limitesX[0])/2)-72, 92);
            g.setColor(Color.magenta);
            g.drawString("PERDISTE", ((limitesX[1]-limitesX[0])/2)-76, 96);
            g.setColor(Color.white);            
            g.drawString("PERDISTE", ((limitesX[1]-limitesX[0])/2)-80, 100);
            //FIN fuente de PERDISTE
            
            //Se setea el color a negro otra vez
            g.setColor(Color.black);
        }
        
        //Si dibujarMenu se encuentra activado
        if (dibujarMenu){
            //Se setea el color a negro
            g.setColor(Color.black);
            //Se rellena
            g.fillRect(limitesX[0], limitesY[0], 764, 475);
            //Se dibuja el fondo
            g.drawImage(dibujos.get(11), limitesX[0], limitesY[0], 764, 475, this);
            //Se setea el color a blanco
            g.setColor(Color.white);
            //Instrucciones de controles
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
            //Fin de titulo
            
            //Posiciones para el dibujo de la seleccion dar efecto de boton
            int [] x = {100, 321, 538};
            int y = 390;
            
            //Inicio area de seleccionado
            g.setColor(Color.magenta);
            g.fillRect(x[seleccionMenu]-12, y-12, 154, 79);
            g.setColor(Color.yellow);
            g.fillRect(x[seleccionMenu]-8, y-8, 154, 79);
            g.setColor(Color.cyan);
            g.fillRect(x[seleccionMenu]-4, y-4, 154, 79);
            //Fin area de seleccion
            
            //Boton
            g.setColor(Color.white);
            g.fillRect(100, y, 150, 75);
            g.fillRect(538, y, 150, 75);
            g.fillRect(((limitesX[1]-limitesX[0])/2)-60, y, 150, 75);
                        
            //Setea color por defecto
            g.setColor(Color.black);
            //Se dibujan nombres de botones
            g.drawString("JUGAR", x[0]+10, y+55);
            g.drawString("SKINS", x[1]+10, y+55);
            g.drawString("INFO", x[2]+28, y+55);            
            
            //Si la skins es falsa
            if (!skins){
                //Se embelleze
                g.setColor(Color.cyan); g.fillRect(338, 188, 100, 100);
                g.setColor(Color.yellow); g.fillRect(342, 192, 100, 100);
                g.setColor(Color.magenta); g.fillRect(346, 196, 100, 100);
                //Se dibuja la pelota en grande
                g.drawImage(dibujos.get(seleccionPelota), 350, 200, 100, 100, this);
                
                //Se setea color blanco
                g.setColor(Color.white);
                //Se dibujan las flechas para indicar que se debe subir y bajar
                g.drawImage(dibujos.get(15), 390, 160, 20, 20, this);
                g.drawImage(dibujos.get(16), 390, 310, 20, 20, this);
            }                        
        }
        //Dibujo area fin
        g.dispose();
        //Se muestra el buffer
        buffer.show();
        
    }
    
    //SO <- TERMINADO Y LIMPIADO
    private int SO(){
        //Se encierra ante excepciones
        try{
            //Se captura el nombre del sistema operativo
            String so = System.getProperty("os.name");

            //Si es windows retorna 1
            if (so.toLowerCase().trim().startsWith("windows") || so.toLowerCase().trim().startsWith("win")){
                return 1;
            }
            //Sino
            else{
                //Si es linux retorna 2
                if (so.toLowerCase().trim().startsWith("linux") || so.toLowerCase().trim().startsWith("lin")){
                    return 2;
                }
                //Sino
                else{
                    //Tambien retorna 2
                    return 2;
                }
            }
        }
        //Ante captura de expeciones
        catch(Exception e){
            //Tambien retorna 2
            return 2;
        }
    }
    
    //ciclo
    public void ciclo() throws InterruptedException {
        
        //Variables controladoras del tiempo
        long ahora;
        long antes = System.nanoTime();
        //Variable para controlar el SO
        int verificarSO = 0;
        
        //Ciclo infinito que no puede ser detenido
        boolean ciclo = true;
        
        //Si el sistema operativo es 0 = Windows
        if (verificarSO == 0){
            if (SO() == 1){
                //Setea tamaño de fuente a 46
                fuente = 46;
            }
            //Sino lo considera como Linux o alternativa
            else{
                //Sino setea funte en 36
                if (SO() == 2){
                    fuente = 36;
                }
                else{
                    fuente = 36;
                }
            }
            //Se suma y no se vuelve a verificar el SO
            verificarSO++;
        }
        
        //Mientras menuPrincipal sea true
        while (menuPrincipal){   
            //Se le agrega pausa entre dibujo y dibujo
            Thread.sleep(threadVelocidad);
            //Dato es igualado a la devolucion de la funcion movimiento
            int dato = teclado.movimiento();
            //Se dibuja
            dibujar();
            //Si se pulsa flecha derecha
            if (dato == 1){
                //Si seleccionMenu es menor a 2
                if (seleccionMenu < 2){
                    //Suma 1 por cada vez que se pulse
                    seleccionMenu++;
                }
                //Sino
                else{
                    //Se vuelve seleccionMenu a 0
                    seleccionMenu = 0;
                }
            }
            //Sino
            else{
                //Si se pulsa flecha izquierda
                if (dato == 2){
                    //SI es mayor que 0
                    if (seleccionMenu > 0){
                        //Se resta 1
                        seleccionMenu--;
                    }
                    //Sino
                    else{
                        //Se setea en 2
                        seleccionMenu = 2;
                    }
                }
            }
            //Si flecha arriba
            if (dato == 6){
                //Si seleccionPelota es igual a 4
                if (seleccionPelota == 4){
                    //Vuelve a 0
                    seleccionPelota = 0;
                }
                //Sino
                else{
                    //Si seleccionPelota es menor que 
                    if (seleccionPelota < 4){
                        //Suma de a 1
                        seleccionPelota++;
                    }
                }
            }
            //Sino
            else {
                //Si se pulsa flecha abajo
                if (dato == 7){
                    //Si seleccionPelota es mayor que 0
                    if (seleccionPelota > 0){
                        //Se resta de a 1
                        seleccionPelota--;
                    }
                    //Sino
                    else{
                        //Si seleccionPelota es igual a 0
                        if (seleccionPelota == 0){
                            //Se setea a 4
                            seleccionPelota = 4;
                        }
                    }
                }
            }
            //Si seleccion menu es igual a 1
            if (seleccionMenu == 1){
                //Se activan las skins
                skins = false;
            }
            //Sino
            else {
                //Si seleccionMenu es distinto a 1
                if (seleccionMenu != 1){
                    //Se desactivan las skins
                    skins = true;
                }                
            }
            //Si seleccionMenu es 2 y se presiona ENTER
            if (seleccionMenu == 2 && dato == 5){
                //Se intenta
                try{
                    //Mostrar los menu desplegables y sus respectivos switch
                    String [] seleccion2 = {"Mirar la información","Ajustar el rendimiento"};        
                    String info = (String) JOptionPane.showInputDialog(null, "Que deseas realizar...?",
                    "INFO", JOptionPane.QUESTION_MESSAGE, null, seleccion2, seleccion2[0]);
                    switch(info){
                        case "Mirar la información":
                            JOptionPane.showMessageDialog(null, "Información: \n"
                                                              + "Bienvenido a Brick Breaker!!!\n\n"
                                                              + "No hay reglas, solo trate de que la bola\n"
                                                              + "no caiga al fondo del ring de juego.\n\n"
                                                              + "=== Niveles ===\n"
                                                              + "Basicamente son infinitos, disfruta!!!\n\n"
                                                              + "=== Dificultades ===\n"
                                                              + "D1: Bloques se rompen con un solo toque\n"
                                                              + "D2: Bloques se rompen con dos toques\n"
                                                              + "EXTRA: En todos los niveles la pelota se acelera\n"
                                                              + "al romper ciertos bloques así que ten cuidado!!!\n\n"
                                                              + "=== Puntos por bloque ===\n"
                                                              + "Inferior: 10pts (normal) o 70pts (bonus de la suerte)\n"
                                                              + "Central: 20pts (normal) o 140pts (bonus de la suerte)\n"
                                                              + "Superior: 30pts (normal) o 210pts (bonus de la suerte)\n\n"
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
            //Si se pulsa ENTER Y seleccion menu es 0
            if (dato == 5 && seleccionMenu == 0){
                //No se dibujan las skins
                skins = true;
                //Se deja de dibujar el menu
                dibujarMenu = false;
                //Comienza el ciclo
                while (ciclo){
                    //dato toma el valor de la devolucion de movimiento()
                    dato = teclado.movimiento();
                    //Actualizar paleta pausa es usada ya que no hay movimiento de pelota
                    actualizarPaletaPausa(dato);
                    //Se dibuja
                    dibujar();
                    //El programa tiene pausa entre dibujo y dibujo
                    Thread.sleep(threadVelocidad);
                    //Si se pulsa espacio y vida es mayor que 0
                    if (dato == 3 && vidas > 0){
                        //Se inicia la ejecucion
                        ejecutar = true;
                        //Se toma el tiempo en nanosegundos
                        antes = System.nanoTime();

                    }
                    //Mientras ejecutar sea true
                    while (ejecutar) {
                        //Se toma el tiempo actual en milisegundos
                        ahora = System.nanoTime();
                        //Se hace una sumatoria de el valor dado entre ahora menos antes
                        sumatoria = sumatoria + (ahora - antes);
                        //El valor de ahora toma el valorde antes
                        antes = ahora;
                        //Se divide sumatoria entre 100000000, si es mayor que 1
                        if (sumatoria/100000000 > 1){
                            //Se suma un milisegundo
                            tt[2]++;
                        }
                        //Se divide sumatoria entre 100000000, si es mayor que 1
                        if (sumatoria/1000000000 > 1){
                            //Se suma 1 segundo
                            tt[1]++;
                            //Si segundo es mayor que 60
                            if (tt[1] > 60){
                                //Se suma un minuto
                                tt[0]++;
                                //Y segundo vuelve a ser 0
                                tt[1] = 0;
                            }
                            //Sumatoria es igual a 0
                            sumatoria = 0;
                        }
                        //Si no quedan mas bloques
                        if (contadorRestante == 0){
                            //Se reinicia
                            reiniciar();
                            //Se cuenta otro nivel
                            contadorNivel++;
                            //Se restauran las posibilidades de saque
                            for (int i = 0; i < 4; i++){
                                vecesVidas[i] = true;
                            }
                        }
                        //Se actualiza
                        actualizar();  
                        //Se dibuja
                        dibujar();
                        //Se pausa entre dibujo y dibujo
                        Thread.sleep(threadVelocidad);
                        //Si vidas == 0
                        if (vidas == 0){
                            //Se muestra el menu de score
                            menuScore = true;                            
                            while (menuScore){
                                //Se pausa entre dibujo y dibujo
                                Thread.sleep(threadVelocidad);
                                //Se dibuja
                                dibujar();
                                //Se muestra el mensaje de derrota
                                mensajeDerrota++;
                                if (mensajeDerrota == 60){
                                    //Se muestra
                                    JOptionPane.showMessageDialog(this, "GRACIAS POR JUGAR!!! TU SCORE FUE DE:\n"
                                                                                 +scoreString+" PUNTOS"); 
                                    //El programa llega a su fin y se cierra
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