//	VScroller
//
//	Fond d'écran défilant verticalement
//

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.*;


public class VScroller {
	int nimages = 4;					//	nombre d'images
	double img_height = 1000;			//	largeur des images affichées
	Image img[] = new Image[nimages];	//	Les variables représentant les images
	
	VScroller() {		
	}

	//	Initialisation
	//	Chargement des images
	void Init() {
		for (int i=0; i<nimages; i++) {
			try {
				img[i] = ImageIO.read(new File("fond" + (i+1) + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//	Calculer le "x modulo n"
	static int Modulo( int x, int n )
	{
		if (x >= 0) {
			return x % n;
		} else {
			return n - ((-x) % n);
		}
	}
	
	//	Calculer le "x modulo n" (version virgules flottantes)
	static double fModulo( double x, double n )
	{
		if (x >= 0) {
			return x % n;
		} else {
			return n - ((-x) % n);
		}
	}
	
	//	Dessiner le fond défilant
	void Draw( Graphics2D g, double y, int winw, int winh ) {
		double y1, y2, yd;
		int n1, n2;
		
		y = fModulo(y,nimages*img_height);
		
		y1 = fModulo(y - (double)winh / 2.0, nimages*img_height);
		y2 = fModulo(y + (double)winh / 2.0, nimages*img_height);
		
		n1 = Modulo((int)(y1 / img_height), nimages);
		n2 = Modulo((int)(y2 / img_height), nimages);
		if (n2 < n1) {
			n2 += nimages;
		}
		
		for (int i=n1; i<=n2; i++) {
			int j = i % nimages;
			yd = (double)i*img_height - y1;
			g.drawImage(img[j], 0, (int)yd, winw, (int)(yd+img_height), 
					0, 0, img[j].getWidth(null), img[j].getHeight(null), null);
			
			/*
			g.setColor(Color.YELLOW);
			g.drawRect(0, (int)yd, winw, (int)img_height);
			g.drawLine(0, (int)yd, winw, (int)(yd+img_height));
			g.drawLine(winw, (int)yd, 0, (int)(yd+img_height));
			
			Font font = new Font("Courier", Font.BOLD, 20);
		    g.setFont(font);
		    g.setColor(Color.YELLOW);
		    g.drawString(Integer.toString((int)j), 10, (int)yd+30);
		    */
		}
	}
}
