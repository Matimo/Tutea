import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Toriki extends GameWindow {
	//private static final int EXTERNAL_BUFFER_SIZE = 128000;
		private static final int EXTERNAL_BUFFER_SIZE = 3000*4;
		
		static int[] left;
		static int[] right;
		
		public Toriki() {
			super();
			setTitle("Wave visualizer");
			setSize(800,600);
			setLocation(100,100);
			left = null;
			right = null;
		}

		public static void main(String[] args) {
			new Toriki();
			String strFilename = ChooseFile(".","WAV files","WAV");
			if (strFilename != null) play(strFilename);
		}

		//	Mise � jour de l'affichage
		public void Paint(Graphics2D g, int w, int h) {
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, w, h);
			
			//	Dessiner les courbes des sons
			if ((left != null) && (right != null)) {
				if ((left.length > 0) && (right.length > 0)) {
					g.setColor(Color.BLUE);
					DrawCurve(g,0,h/4,w,h/2,left);
					g.setColor(Color.RED);
					DrawCurve(g,0,h*3/4,w,h/2,right);
				}
			}
		}
		
		public void DrawCurve( Graphics2D g, int x0, int y0, int xscale, int yscale, int[] p ) {
			int x, y, xx, yy;
			
			xx = x0;
			yy = (int)(y0+(long)p[0]*yscale/65535L);
			
			for (int i=1; i<p.length; i++) {
				x = i*xscale/p.length;
				y = (int)(y0+(long)p[i]*yscale/65535L);
				g.drawLine(xx, yy, x, y);
				xx = x; yy = y;
			}
		}

		public static void play( String strFilename ) {

			File soundFile = new File(strFilename);

			/*
			 *Il faut lire dans le fichier audio.
			 */
			AudioInputStream audioInputStream = null;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (Exception e) {
				/*
				 * En cas d'exception, nous jetons l'exception, y compris le 
				 * Trace de la pile � la sortie de la console. Ensuite, nous sortons du programme.
				 */
				e.printStackTrace();
				System.exit(1);
			}

			/*
			 *De la AudioInputStream, c'est � dire � partir du fichier de son, nous allons chercher 
			 * Les informations sur le format des donn�es audio. Ces informations 
			 * Inclure la fr�quence d'�chantillonnage, le nombre de canaux et la taille 
			 * Des �chantillons. Ces informations sont n�cessaires pour demander Java Sound pour une 
			 * La ligne de sortie appropri� pour ce fichier audio.

			 */
			AudioFormat audioFormat = audioInputStream.getFormat();

			/*
			 * Demande d'une ligne est une chose assez difficile. Nous devons construire un 
			 * Objet Info qui sp�cifie les propri�t�s souhait�es pour la ligne. 
			 * Tout d'abord, nous avons � dire quel type de ligne que nous voulons. les possibilit�s 
			 * Sont: SourceDataLine (pour la lecture), clip (pour la lecture r�p�t�e) et 
			 * TargetDataLine (pour l'enregistrement). Ici, nous voulons faire la lecture normale, 
			 * Si nous demandons un SourceDataLine. Ensuite, nous devons passer un AudioFormat 
			 * Objet, de sorte que la ligne sait quel format les donn�es qui lui sont transmises 
			 * Aura. En outre, nous pouvons donner Java sonore une indication sur la taille 
			 * La m�moire tampon interne pour la ligne devrait �tre. Ce n'est pas utilis� ici, 
			 * Signalisation que nous ne nous soucions pas de la taille exacte. Java SOUND 
			 * Utiliser une valeur par d�faut de la taille de la m�moire tampon.
			 */
			SourceDataLine line = null;
			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					audioFormat);
			try {
				line = (SourceDataLine) AudioSystem.getLine(info);

				/*
				 *La ligne est l�, mais il n'est pas encore pr�t � recevoir des donn�es audio. 
				 * Nous devons ouvrir la ligne.
				 */
				line.open(audioFormat);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			/*
			 * Toujours pas assez. La ligne peut maintenant recevoir des donn�es, mais ne passera pas 
			 * Les sur le p�riph�rique de sortie audio (ce qui signifie � votre carte son). 
			 * Ceci doit �tre activ�.
			 */
			line.start();

			/*
			 * Ok, enfin la ligne est pr�te. Maintenant vient le vrai travail: nous devons 
			 * Donn�es d'�criture � la ligne. Nous faisons cela dans une boucle. Tout d'abord, nous lisons donn�es 
			 * De la AudioInputStream � un tampon. Ensuite, nous �crivons de cette 
		* Tampon de la ligne. Cela se fait jusqu'� ce que la fin du fichier est 
		* Atteint, ce qui est d�tect� par une valeur de retour de -1 � partir de la lecture 
		* M�thode de la AudioInputStream.
			 */
			
			int nBytesRead = 0;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
			while (nBytesRead != 1) {
				try {
					nBytesRead = audioInputStream.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//	Construit les tableaux des �chantillons
				//	� partir des donn�es du fichier WAV
				
		
				int[] left0 = new int[EXTERNAL_BUFFER_SIZE/ 4];
				int[] right0 = new int[EXTERNAL_BUFFER_SIZE/4];
				int j = 0;
				for (int i=0; i<nBytesRead; i+=4) {
					left0[j] = abData[i] + 256*abData[i+1];
					right0[j] = abData[i+2] + 256*abData[i+3];
					j++;
				}
				
				
				
				double rapport_freq = 1.1;
				int n_echant = (int)((double)(nBytesRead/4)*rapport_freq);
				
				left = new int[n_echant];
				right = new int[n_echant];
				
				for (j=0; j<n_echant; j++) {
					int i = (int)(j/rapport_freq);
					if (i >= EXTERNAL_BUFFER_SIZE/4) i=EXTERNAL_BUFFER_SIZE/4-1;
					
					left[j] = left0[i];
					right[j] = right0[i];				
				}
				
				for (j=0; j<n_echant; j++) {
					left[j] = (int)(0.5*left[j]);
					right[j] = (int)(0.25*right[j]);
				}
			
				
				byte[] newData = new byte[n_echant*4];
				int i = 0;
				for (j=0; j<n_echant; j++) {
					newData[i] = (byte)(left[j] % 256);
					newData[i+1] = (byte)(left[j] / 256);
					newData[i+2] = (byte)(right[j] % 256);
					newData[i+3] = (byte)(right[j] / 256);
					i+=4;
				}		
				
				
				if (nBytesRead >= 0) {
					int nBytesWritten = line.write(newData, 0, n_echant*4);
					//System.out.format("%d %d\n",nBytesWritten,nBytesRead);
			 
				}
			}
			
			
			/*
			 *Attendez jusqu'� ce que toutes les donn�es sont lues. Cela n'est n�cessaire en raison de la 
			 * Bug indiqu� ci-dessous. (Si nous n'attendons pas, nous interrompre la lecture 
			 * Par la fermeture pr�matur�e de la ligne et la sortie de la machine virtuelle.) 
			 * 
			 * Merci � Margie Fitch pour me mettre sur la bonne voie pour ce 
			 * Solution.

			 */
			line.drain();

			/*
			 * Toutes les donn�es sont lues. Nous pouvons fermer la boutique.
			 */
			line.close();

			/*
			 * Il ya un bug dans le jdk1.3/1.4. Il emp�che la terminaison correcte de 
			 * La VM. Donc, nous devons nous quitter.
			 */
			System.exit(0);
		}

		private static void printUsageAndExit() {
			out("WaveVisualizer: usage:");
			out("\tjava WaveVisualizer <soundfile>");
			System.exit(1);
		}

		private static void out(String strMessage) {
			System.out.println(strMessage);
		}

		public static String ChooseFile( String default_dir, String filtertext, String filterext ) {
			//	Choix d'un fichier
			
			String pathname = "";

			//	Initialiation du FileChooser avec d�finition du r�pertoire courant
			//	et du filtre de fichiers (existant)
			JFileChooser chooser = new JFileChooser(new File(default_dir));
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(filtertext,filterext);
		    chooser.setFileFilter(filter);
		    
		    //	Ouverture du dialog FileChooser
		    if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    	pathname = chooser.getSelectedFile().getAbsolutePath();
		    	System.out.println("You chose to open this file: " + pathname);
		    } else {
		    	return null;
		    }
		    return pathname;
		}

		public void ChooseFile() {
			// TODO Auto-generated method stub
			
		}

	

}
