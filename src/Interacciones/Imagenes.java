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
            img = ImageIO.read(new File("pelota_0.png"));
            imagenes.add(img);
            //1
            img = ImageIO.read(new File("pelota_1.png"));
            imagenes.add(img);
            //2
            img = ImageIO.read(new File("pelota_2.png"));
            imagenes.add(img);
            //3
            img = ImageIO.read(new File("pelota_3.png"));
            imagenes.add(img);
            //4
            img = ImageIO.read(new File("pelota_4.png"));
            imagenes.add(img);
            
            //LADRILLOS
            //5
            img = ImageIO.read(new File("ladrillo_0.png"));
            imagenes.add(img);
            //6
            img = ImageIO.read(new File("ladrillo_1.png"));
            imagenes.add(img);
            //7
            img = ImageIO.read(new File("ladrillo_2.png"));
            imagenes.add(img);
            
            //PALETAS
            //8
            img = ImageIO.read(new File("paleta_0.png"));
            imagenes.add(img);
            //9
            img = ImageIO.read(new File("paleta_1.png"));
            imagenes.add(img);
            
            //FONDO
            //10
            img = ImageIO.read(new File("fondo_0.png"));
            imagenes.add(img);
            //11
            img = ImageIO.read(new File("fondo_1.png"));
            imagenes.add(img);
            
            //LADRILLOS ROTOS
            //12
            img = ImageIO.read(new File("ladrillo_roto_0.png"));
            imagenes.add(img);
            //13
            img = ImageIO.read(new File("ladrillo_roto_1.png"));
            imagenes.add(img);
            //14
            img = ImageIO.read(new File("ladrillo_roto_2.png"));
            imagenes.add(img);
            
        } 
        catch (Exception e) {
            
        }
        return imagenes;
    }
}
