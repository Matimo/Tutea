import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Personnage {
	int state, state2;
	boolean stimuli[] = new boolean[32];
	
	boolean inited = false;
	
	double x, y, x0, y0;
	double vie, force;
	long rate = 200;
	int nimages = 1;
	Image images[] = new Image[nimages];
	String image_pattern = "Personnage";
	double scale = 1.0;
	
	long lasttime;

	Personnage() {
	}
	
	
	void Init() {
		state = 0;
		state2 = 0;
		
		for (int i=0; i<nimages; i++) {
			try {
				images[i] = ImageIO.read(new File(image_pattern + (i+1) + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i=0; i<32; i++) stimuli[i]=false;
		
		lasttime = System.currentTimeMillis();
		
		inited = true;
	}
	
	
	void Paint( Graphics2D g, double factor, int winw, int winh ) {
		if (!inited) return;
		
		AffineTransform aft;
		
		aft = g.getTransform();
		
		Image img = images[state+state2];
		int w = (int)img.getWidth(null);
		int h = (int)img.getHeight(null);
		
		g.translate(x, y);
		g.scale(factor, factor);
		g.translate(-w/2, -h/2);		
		
		g.drawImage(img, 0, 0, null);
		
		g.setTransform(aft);
	}
	
	
	void Animate( int newx, int newy, int winx, int winy ) {
		if (!inited) return;
		
		long time = System.currentTimeMillis();
		long dt = time - lasttime;
		
		if (dt > rate) {
			
			if (x < 0) x = 0;
			if (x > winx) x = winx;

			lasttime = time;
		}
	}

	
	void Stimulus( int i ) {
		if (!inited) return;		
		stimuli[i] = true;
	}
}
