package graphicInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import KnowledgeBase.KnowledgeBase;
import tools.ParseurTxt;

public class MenuBar extends JMenuBar {

	public MenuBar(){
		super();
		JMenu fichier = new JMenu("File");
		JMenuItem load = new JMenuItem("Load a file");
		load.addActionListener(new BoutonLoad());
		fichier.add(load);
		this.add(fichier);
	}
	
	private class BoutonLoad implements ActionListener{
	    public void actionPerformed(ActionEvent arg0) {   	
	      
	    	File repertoireCourant = null;
	        try {
	            repertoireCourant = new File(".").getCanonicalFile();
	            System.out.println("Répertoire courant : " + repertoireCourant);
	        } catch(IOException e) {}
	         
	        JFileChooser dialogue = new JFileChooser(repertoireCourant);
	        dialogue.showOpenDialog(null);
	        System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
	    	Info info = Info.getInstance();
	    	info.setKb(ParseurTxt.readFile(dialogue.getSelectedFile().toString()));
	      
	    }    
	}
}
