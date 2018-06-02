/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adicionales;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
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
            archivo = new FileWriter("Scores.txt");
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
            archivoLectura = new FileReader(new File("Scores.txt"));
            archivoLecturaBuffer = new BufferedReader(archivoLectura);

            String linea;
            int i = 0;
            while((linea=archivoLecturaBuffer.readLine())!=null){
                if (i == score){
                    return linea;
                }
                i++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "No disponible";        
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
    
    public static int contadorScore(int score, int b){
        int aleatorioAdicional = (int)(Math.random()*((15-1)+1))+1;
        boolean bonus = false;
        if  (aleatorioAdicional == 7){
            bonus = true;
        }
        
        if (b == 0 || b == 3 || b == 6 || b == 9 || b == 12 || b == 15 || b == 18 || b == 21 || b == 24 || b == 27){
            if (bonus){
                return (score + (30 * aleatorioAdicional));
            }
            else {
                return (score + 30);
            }
        }
        else {
            if (b == 1 || b == 4 || b == 7 || b == 10 || b == 13 || b == 16 || b == 19 || b == 22 || b == 25 || b == 28){
                if (bonus){
                    return (score + (20 * aleatorioAdicional));
                }
                else {
                    return (score + 20);
                }
            }
            else {
                if (b == 2 || b == 5 || b == 8 || b == 11 || b == 14 || b == 17 || b == 20 || b == 23 || b == 26 || b == 29){
                    if (bonus){
                        return (score + (10 * aleatorioAdicional));
                    }
                    else {
                        return (score + 10);
                    }
                }
            }
        }
        return 0;
    }
    
    public static String completarScore(int score){
        String numero;
        String ceros = "";  
        numero = Integer.toString(score);
        
        for (int j = 0; j < 9-numero.length(); j++){
            ceros = "0".concat(ceros);
        }
        
        return (ceros.concat(numero));
    }    
}
