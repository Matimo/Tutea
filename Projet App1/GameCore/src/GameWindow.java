//----------------------------------------------------------------
//	
//	avec double buffer, la gestion du clavier,
//	GameWindow
//	Fenetre conçue pour les jeux
//	et une animation par timer
//
//----------------------------------------------------------------

/*
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.CommPort;

import COMListener.SerialReader;
import COMListener.SerialWriter;
*/

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.*;

import java.util.Timer; 

public class GameWindow extends JFrame {
	
	GamePanel gpanel = new GamePanel();
	KListener klistener = new KListener();
	MouseButtons mousebuttons = new MouseButtons();
	MouseMotion mousemotion = new MouseMotion();
	java.util.Timer timer = new Timer();
	Timertask timertask = new Timertask();
	//COMListener comlistener = new COMListener();

	GameWindow gwin = this;
	long t0, last_t0;
	boolean inited = false;
	
	int lastkeycode = 0;


	public GameWindow() {
		
		inited = false;
		
		this.setTitle("Jeux");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		gpanel.parent = this;
		this.setContentPane(gpanel);
				
		klistener.parent = this;
		this.addKeyListener(klistener);
		
		mousebuttons.parent  = this;
		this.addMouseListener(mousebuttons);
		
		mousemotion.parent  = this;
		this.addMouseMotionListener(mousemotion);

		this.setVisible(true);
		
		Init();
		inited = true;

		timertask.parent = this; 
	    timer.schedule(timertask, 100, 1000 / 50);
	}
	
	//	Initialisations pour le jeu
	public void Init() {
		
		/*
		try {
			comlistener.connect("COM12");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		t0 = System.currentTimeMillis();
	}
	 

	//	Mise à jour de l'affichage
	public void Paint(Graphics2D g, int w, int h) {
	}
	
	//	Gestion du clavier
	public void Keypressed(KeyEvent event) {
		lastkeycode = event.getKeyCode();
		for (int i=0; i<256; i++) {
			if (klistener.table[i]) {
			}
		}
	}
	
	//	Gestion des boutons de la souris
	public void MouseButtons( MouseEvent event ) {
		if (event.getButton() == MouseEvent.BUTTON1 ) {
		}
	}
	
	//	Gestion des mouvements de la souris
	public void MouseMoved( MouseEvent event ) {
	}
	
	//	Animation du jeu
	public void Animate() {

		/*
		comlistener.Write(c);
		c++; if (c > 'z') c = 'a';
		if (comlistener.DataAvail() != 0) comlastbyte = comlistener.Read();
		*/
		
		gpanel.repaint();
	}
	
	
	//	Largeur de la fenetre
	public int GetWidth() {
		return gpanel.getWidth();
	}
	
	
	//	Hauteur de la fenetre
	public int GetHeight() {
		return gpanel.getHeight();
	}
 
	//----------------------------------------------------------
	//	Contenu de la fenêtre
	//----------------------------------------------------------
	public class GamePanel extends JPanel {
		GameWindow parent;			// la fenêtre parente
		int w = 0, h = 0;	// dimensions de la fenêtre
		Image buffer;		// double buffer
		
		public void paintComponent(Graphics g) {
			int width,height;			
			
			//	Vérifie les dimensions actuelles
			//	redimensionner le double buffer si nécessaire
			width = getWidth();
			height = getHeight();
			if ((width != w) || (height != h)) {
				buffer = createImage(width,height);
				w = width;
				h = height;
			}
			
			//	Récupère le contexte graphique du double buffer
			Graphics2D g2d = (Graphics2D)buffer.getGraphics();
			
			//	Passer le contexte graphique à la fenêtre parante
			//	pour qu'il dessine le contenu
			if (parent.inited) parent.Paint(g2d,w,h);
			
			//	Mise à jour du contenu de la fenêtre à partir
			//	du double buffer
			g.drawImage(buffer,0,0,w,h,this);
		}
	}

	//----------------------------------------------------------
	//	Espion pour le clavier
	//----------------------------------------------------------
	public class KListener implements KeyListener{
		GameWindow parent;
		boolean table[] = new boolean[256];
		
		public void KListener() {
			for (int i=0; i<256; i++) table[i] = false;
		}
		
		public void keyPressed(KeyEvent event) {
			parent.Keypressed(event);
			int i = event.getKeyCode();
			if (i >= 256) i = 0;
			table[i] = true;
			lastkeycode = i;
		}
		
		public void keyReleased(KeyEvent event) {
			//System.out.println("Code touche relâchée : " + event.getKeyCode() + " - caractère touche relâchée : " + event.getKeyChar());
			int i = event.getKeyCode();
			if (i >= 256) i = 0;
			table[i] = false;
		}
		
		public void keyTyped(KeyEvent event) {
			//System.out.println("Code touche tapée : " + event.getKeyCode() + " - caractère touche tapée : " + event.getKeyChar());
		}       
	}
	
	
	//	Tester l'état d'une touche du clavier
	boolean GetKeyState( int keycode ) {
		return klistener.table[keycode];
	}

	
	//----------------------------------------------------------
	//	Espions pour la souris
	//----------------------------------------------------------
	public class MouseButtons implements MouseListener {
		GameWindow parent;
		
		public void mouseEntered( MouseEvent event ) {
			parent.MouseButtons(event);
		}
		
		public void mouseClicked( MouseEvent event ) {
			parent.MouseButtons(event);
		}
		
		public void mouseReleased( MouseEvent event ) {
			parent.MouseButtons(event);
		}
		
		public void mouseExited( MouseEvent event ) {
			parent.MouseButtons(event);
		}
		
		public void mousePressed( MouseEvent event ) {
			parent.MouseButtons(event);
		}
	}
	
	public class MouseMotion implements MouseMotionListener {
		GameWindow parent;
		Point last = new Point();
		
		public void mouseDragged( MouseEvent event ) {
			CoordFrame2Panel(event.getX(),event.getY(),last);
			parent.MouseMoved(event);
		}
		
		public void mouseMoved( MouseEvent event ) {
			CoordFrame2Panel(event.getX(),event.getY(),last);
			parent.MouseMoved(event);
		}
	}
	
	public Point CoordFrame2Panel( int x, int y, Point pp ) {
		Point gpos = gpanel.getLocationOnScreen();
		Point fpos = this.getLocationOnScreen();
		pp.x = x - (gpos.x - fpos.x);
		pp.y = y - (gpos.y - fpos.y);
		return pp;
	}
	
	//----------------------------------)------------------------
	//	Timer
	//----------------------------------------------------------
	public class Timertask extends java.util.TimerTask {
		GameWindow parent;
		
	    public void run() {
	    	parent.Animate();
	    }
	}
	
	//----------------------------------------------------------
	//	Point d'entrée principale
	//----------------------------------------------------------
	
	public static void main(String[] args){
		new GameWindow();
	}
	
}
