/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ScoreAndTimer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
 *
 * @author mgonzaloluna
 */
public class SaveScore {
    private BufferStrategy buffer;
    private Graphics g;
    PrintWriter pw;
    FileWriter archivo;
    FileReader archivoLectura;
    BufferedReader archivoLecturaBuffer;
    String texto = "";
    
    //Objetivo 1 - crear archivo de texto encargado de llevar el score
    public SaveScore(){
        guardarScore();
    }
    
    public void guardarScore(){
        try {
            archivo = new FileWriter("Scores");
            pw = new PrintWriter(archivo);
            
            for (int i = 0; i < 5; i++){
                pw.println((i+1)+"]"+" Vacío...");
            }
            
            archivo.close();
            
        } catch (Exception e) {
            
        }
    }
    public String leerScore(int score){
        try {
            archivoLectura = new FileReader("Scores.txt");
            archivoLecturaBuffer = new BufferedReader(archivoLectura);
            while ((texto = archivoLecturaBuffer.readLine()) != null){
                
                System.out.println("Hola");
                for(int i = 0; i <= 11; i++){
                    char letra = texto.charAt(i);
                    System.out.println(letra);
                }
                
            }
            
        } catch (Exception e) {
        }
        return texto;
    }
    
    int x = 0;
    public void scores(Canvas canvas){
        buffer = canvas.getBufferStrategy();
        if (buffer == null){
            canvas.createBufferStrategy(3);
            return;
        }
        g = buffer.getDrawGraphics();
        
        //
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1000, 1000);
            g.drawRect(0, 0, 1000, 1000);
            
            g.setColor( Color.WHITE );
            g.setFont( new Font( "Lucida Console", Font.BOLD, 46 ) );
            g.drawString(""+x, 100, 100);
            
        //
        x++;
        g.dispose();
        buffer.show();
    }
    
}
