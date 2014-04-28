import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.UIManager;

public class Projet extends Projet2 {

	static int n = 0; // compteur pour le nombre de clics

	static JButton button; // variable représentant le bouton
	static JButton button1;
	static JButton button2;
	static JButton button3;
	static JButton button4;

	JMenuBar menubar;
	JMenu fichiers_menu;
	JMenuItem ouvrir_image_item, quitter_item;
	JMenu edit_menu;
	JMenuItem new_edit_item, ouvrir_edit_item;

	int Bass;
	int Claqué;
	int Tonique;
	int Maté;
	int MatéClaqué;
	int MatéTonique;

	int A0;
	int A1;
	int A2;
	int A3;
	int A31;
	int A32;

	COMListener serialport = null;

	public Projet() {
		super();

		// Création du bouton
		button = new JButton("Tonique");
		button1 = new JButton("Claqué");
		button2 = new JButton("Basse");
		button3 = new JButton("Maté Claqué");
		button4 = new JButton("Maté Tonique");

		// Création de la fenêtre

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(2000, 2000);

		// Récupération de l'intérieure de la fenêtre
		// avec style de placement des composants
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new FlowLayout());
		// contentPane.setLayout(null);

		// Rajouter le bouton dans le panneau intérieur
		// de la fenêtre
		// button.setBounds(10,10,300,50);
		contentPane.add(button);
		contentPane.add(button1);
		contentPane.add(button2);
		contentPane.add(button3);
		contentPane.add(button4);

		// Afficher la fenêtre
		frame.setVisible(true);

		// Rajouter un "Listener" pour capturer les actions
		// sur le bouton
		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// Lorsqu'on clique sur le bouton,
				// incrémenter n le compteur de clics
				// et mettre à jour le texte
				n++;
				button.setText(String.format("Tonique : %d", n));
			}
		});

		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// Lorsqu'on clique sur le bouton,
				// incrémenter n le compteur de clics
				// et mettre à jour le texte
				n++;
				button1.setText(String.format("Claqué: %d", n));
			}
		});

		button2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// Lorsqu'on clique sur le bouton,
				// incrémenter n le compteur de clics
				// et mettre à jour le texte
				n++;
				button2.setText(String.format("Basse : %d", n));
			}
		});

		button3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// Lorsqu'on clique sur le bouton,
				// incrémenter n le compteur de clics
				// et mettre à jour le texte
				n++;
				button3.setText(String.format("Maté Tonique : %d", n));
			}
		});

		button4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				// Lorsqu'on clique sur le bouton,
				// incrémenter n le compteur de clics
				// et mettre à jour le texte
				n++;
				button4.setText(String.format("Maté Claqué : %d", n));
			}
		});
	}

	public static void main(String[] args) {
		new Projet();

	}

	public void OuvrirFichier() {

		while (true) {

			// Initialiser le port série
			serialport = new COMListener();
			serialport.port = "COM6";
			serialport.rate = 115200;
			serialport.Init();

			// lecture du port série

			if (serialport.DataAvail() > 0) {
				char c = serialport.Read();
				if (Claqué == A0)
					;
			}
			// interpretation du message

			// lancement de la fonction Play()
		}

	}

}
