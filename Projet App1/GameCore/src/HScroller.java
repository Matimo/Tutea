//	HScroller
//
//	Fond d'écran défilant horizontalement
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


public class HScroller {
	int nimages = 5;					//	nombre d'images
	double img_width = 1000;			//	largeur des images affichées
	Image img[] = new Image[nimages];	//	Les variables représentant les images
	
	HScroller() {		
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
	void Draw( Graphics2D g, double x, int winw, int winh ) {
		double x1, x2, xd;
		int n1, n2;
		
		x = fModulo(x,nimages*img_width);
		
		x1 = fModulo(x - (double)winw / 2.0, nimages*img_width);
		x2 = fModulo(x + (double)winw / 2.0, nimages*img_width);
		
		n1 = Modulo((int)(x1 / img_width), nimages);
		n2 = Modulo((int)(x2 / img_width), nimages);
		if (n2 < n1) {
			n2 += nimages;
		}
		
		for (int i=n1; i<=n2; i++) {
			int j = i % nimages;
			xd = (double)i*img_width - x1;
			g.drawImage(img[j], (int)xd, 0, (int)(xd+img_width), winh, 
					0, 0, img[j].getWidth(null), img[j].getHeight(null), null);
			
			g.setColor(Color.YELLOW);
			g.drawRect((int)xd, 0, (int)img_width, winh);
			g.drawLine((int)xd, 0, (int)(xd+img_width), winh);
			g.drawLine((int)xd, winh, (int)(xd+img_width), 0);
			
			Font font = new Font("Courier", Font.BOLD, 20);
		    g.setFont(font);
		    g.setColor(Color.YELLOW);
		    g.drawString(Integer.toString((int)j), (int)xd+10, 30);	    
		}
	}
}
