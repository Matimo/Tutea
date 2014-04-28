import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class projet1 extends GameWindow {

	JMenuBar menubar;
	JMenu fichiers_menu;
	JMenuItem ouvrir_image_item, quitter_item;
	JMenu edit_menu;
	JMenuItem new_edit_item, ouvrir_edit_item;

	// Création de la barre de menu et des items
	public projet1() {
		super();

		menubar = new JMenuBar();

		fichiers_menu = new JMenu("Fichiers");
		fichiers_menu.setMnemonic('F');
		menubar.add(fichiers_menu);

		ouvrir_image_item = new JMenuItem("Ouvrir un fichier");
		ouvrir_image_item.setMnemonic('O');
		fichiers_menu.add(ouvrir_image_item);

		fichiers_menu.addSeparator();

		quitter_item = new JMenuItem("Quitter");
		quitter_item.setMnemonic('Q');
		fichiers_menu.add(quitter_item);

		edit_menu = new JMenu("Edition");
		edit_menu.setMnemonic('E');
		menubar.add(edit_menu);

		new_edit_item = new JMenuItem("Nouvelle séquence");
		new_edit_item.setMnemonic('S');
		edit_menu.add(new_edit_item);

		ouvrir_edit_item = new JMenuItem("Charger une séquence");
		ouvrir_edit_item.setMnemonic('C');
		edit_menu.add(ouvrir_edit_item);

		setJMenuBar(menubar);

		SetMenuActions();

		pack();
		setSize(1000, 1000);
		this.setLocation(0, 0);

	}

	public void SetMenuActions() {
		quitter_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Close();
				// System.exit(0);
			}
		});

		ouvrir_image_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OuvrirImage();
			}
		});

		new_edit_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewEdit();
			}
		});

		ouvrir_edit_item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OuvrirFichier();
			}
		});
	}

	protected void Close() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	public void OuvrirImage() {
		OuvrirImage();

	}

	public void NewEdit() {
		NewEdit();

	}

	public void OuvrirFichier() {

		Toriki animator = new Toriki();

		animator.ChooseFile();
	}

	public static void main(String[] args) {
		new projet1();

	}
	
	

}
