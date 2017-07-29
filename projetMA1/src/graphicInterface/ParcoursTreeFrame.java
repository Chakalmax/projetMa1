package graphicInterface;

import javax.swing.JFrame;
import javax.swing.JPanel;

import DecisionTree.DecisionTree;

public class ParcoursTreeFrame extends JFrame {

	JPanel panel;
	DecisionTree dT;
	public ParcoursTreeFrame(DecisionTree dT){
		super("Parcours d'un arbre");
		this.panel = new JPanel();
		this.dT = dT; // CAREFUL: MENUBAR GET A NULL ATM.
		
		this.setSize(800, 800);
	    this.setLocationRelativeTo(null);
	    addThings();
	    this.setResizable(true);
	    this.setVisible(true);
	}
	private void addThings() {
		// TODO Un panel de tracé (un TreePanel??) et un panel
		
	}
}
