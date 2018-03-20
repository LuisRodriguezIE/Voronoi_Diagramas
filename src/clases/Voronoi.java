/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

//Contains all of the classes for creating user 
//interfaces and for painting graphics and images.
//Abstract Window Toolkit
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

//This package contains the basic classes and interfaces for describing the contents of image files
import javax.imageio.ImageIO;

//Provides for system input and output through data streams, serialization and the file system.
import java.io.File;
import java.io.IOException;

//Provides a set of "lightweight" (all-Java language) components that, 
//to the maximum degree possible, work the same on all platforms.
import javax.swing.JFrame;

//Contains the collections framework, legacy collection classes, event model, date and time facilities
import java.util.*;
//import java.util.Random;

public class Voronoi extends JFrame {

    static double p = 3;

    static BufferedImage I;

    static final int POSX = 400;
    static final int POSY = 300;

    static final int NUMZONAS = 15; //Número de puntos a graficar.
    static final int TAMANOVENTANA = 900; //Tamaño del mapa creado. 

    static int px[] = new int[NUMZONAS];
    static int py[] = new int[NUMZONAS];
    static int color[] = new int[NUMZONAS];

    private static final int POSVX = 600; // Posición de despliegue de la ventana. 
    private static final int POSVY = 80;

    static int pmax = 880;    // Zona maxima de localización coordenada X.
    static int pmin = 20;     // Zona maxima de localización coordenada Y.

    static int n;

    public Voronoi() {
        super("Diagrama de Voronoi, para 15 diferentes zonas.");

        setBounds(POSVX, POSVY, TAMANOVENTANA, TAMANOVENTANA);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Represents an image with 8-bit RGB color components packed into integer pixels
        I = new BufferedImage(TAMANOVENTANA, TAMANOVENTANA, BufferedImage.TYPE_INT_RGB);

        Random rand = new Random();

        for (int i = 0; i < NUMZONAS; i++) {
            px[i] = pmin + rand.nextInt(pmax - pmin);
            py[i] = pmin + rand.nextInt(pmax - pmin);
            color[i] = rand.nextInt(17000000); //WHITE=16777215
        }

        for (int x = 0; x < TAMANOVENTANA; x++) {
            for (int y = 0; y < TAMANOVENTANA; y++) {
                n = 0;
                for (byte i = 0; i < NUMZONAS; i++) {
                    //System.out.println(color[n]);
                    if (distance(px[i], x, py[i], y) < distance(px[n], x, py[n], y)) {
                        n = i;
                    }
                }
                //Sets a pixel in this BufferedImage to the specified RGB value.
                I.setRGB(x, y, color[n]);
            }
        }

        Graphics2D g = I.createGraphics();
        g.setColor(Color.white);
        for (int i = 0; i < NUMZONAS; i++) {
            g.fill(new Ellipse2D.Double(px[i] - 3.5, py[i] - 3.5, 7, 7));
        }

        g.setColor(Color.black);
        g.fill(new Rectangle2D.Double(POSX - 10, POSY - 10, 20, 20));
        
        

        try {
            ImageIO.write(I, "png", new File("voronoi.png"));
        } catch (IOException e) {
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    static double distance(int x1, int x2, int y1, int y2) {
        double d;
        d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)); // Euclidian
        //  d = Math.abs(x1 - x2) + Math.abs(y1 - y2); // Manhattan
        //  d = Math.pow(Math.pow(Math.abs(x1 - x2), p) + Math.pow(Math.abs(y1 - y2), p), (1 / p)); // Minkovski
        return d;
    }

    public static void main(String[] args) {
        new Voronoi().setVisible(true);
    }
}
