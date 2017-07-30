package graphicInterface;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DecisionTree.DecisionTree;
import KnowledgeBase.*;

public class ParcoursTreeFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	JPanel panel;
	DecisionTree dT;
	KnowledgeBase kb;
	
	JPanel selectPanel;
	JPanel treePanel;
	ArrayList<JLabel> labelList;
	ArrayList<JComponent> componentList;
	public ParcoursTreeFrame(KnowledgeBase kb){
		super("Parcours d'un arbre");
		this.panel = new JPanel();
		//this.dT = dT; // CAREFUL: Null atm
		this.kb = kb;
		
		this.setSize(800, 800);
	    this.setLocationRelativeTo(null);
	    addThings();
	    this.setResizable(true);
	    this.setVisible(true);
	}
	private void addThings() {
		// TODO Un panel de tracé (un TreePanel??) et un panel
		selectPanel = new JPanel();
		addThingsSelectPanel();
		treePanel = new TreePanel();// change it to TreePanel after.(Maybe!!)
		
		this.getContentPane().setLayout(new GridLayout(2,1));
		this.add(treePanel);
		this.add(selectPanel);
		
	}
	private void addThingsSelectPanel() {
		int numberAttribute = kb.getAttributeList().size();
		for(int i=0;i<numberAttribute;i++){
			//if(kb.getAttributeList().get(i).getType() ==)
		}
		
	}
}
