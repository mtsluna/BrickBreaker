/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interacciones;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author MtsSk
 */
public class Imagenes {
    List<BufferedImage> imagenes = new ArrayList<>();
    
    public Imagenes(){
        
    }
    
    public List<BufferedImage> cargarImagenes(){
        BufferedImage img;
        
        try {
            //IMAGENES
            
            //PELOTAS
            //0
            img = ImageIO.read(new File("imagenes/pelota_0.png"));
            imagenes.add(img);
            //1
            img = ImageIO.read(new File("imagenes/pelota_1.png"));
            imagenes.add(img);
            //2
            img = ImageIO.read(new File("imagenes/pelota_2.png"));
            imagenes.add(img);
            //3
            img = ImageIO.read(new File("imagenes/pelota_3.png"));
            imagenes.add(img);
            //4
            img = ImageIO.read(new File("imagenes/pelota_4.png"));
            imagenes.add(img);
            
            //LADRILLOS
            //5
            img = ImageIO.read(new File("imagenes/ladrillo_0.png"));
            imagenes.add(img);
            //6
            img = ImageIO.read(new File("imagenes/ladrillo_1.png"));
            imagenes.add(img);
            //7
            img = ImageIO.read(new File("imagenes/ladrillo_2.png"));
            imagenes.add(img);
            
            //PALETAS
            //8
            img = ImageIO.read(new File("imagenes/paleta_0.png"));
            imagenes.add(img);
            //9
            img = ImageIO.read(new File("imagenes/paleta_1.png"));
            imagenes.add(img);
            
            //FONDO
            //10
            img = ImageIO.read(new File("imagenes/fondo_0.png"));
            imagenes.add(img);
            //11
            img = ImageIO.read(new File("imagenes/fondo_1.png"));
            imagenes.add(img);
            
        } 
        catch (Exception e) {
            
        }
        return imagenes;
    }
}
