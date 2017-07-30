package graphicInterface;

import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
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
		componentList = new ArrayList<JComponent>();
		for(int i=0;i<numberAttribute;i++){
			if(kb.getAttributeList().get(i).getType() == TypeAttribute.Numerical)
				componentList.add(new JFormattedTextField(NumberFormat.getNumberInstance()));
			else{ // if TypeAttribute = Boolean
				if(kb.getAttributeList().get(i).getType() == TypeAttribute.Boolean){
					JComboBox<Boolean> cb = new JComboBox<Boolean>();
					for(AttributeValue<?> val : kb.getAttributeList().get(i).getPossibleAttributeValue())
						cb.addItem((boolean)val.getValue());
					componentList.add(cb);
				}else{// if TypeAttribute = Nominal
					JComboBox<String> cb = new JComboBox<String>();
					for(AttributeValue<?> val : kb.getAttributeList().get(i).getPossibleAttributeValue())
						cb.addItem(val.getValue().toString());
					componentList.add(cb);
				}
			}
		}// Tout les composants sont dans le component List
		selectPanel.setLayout(new GridLayout(2,numberAttribute));
		// Ajouter les Labels
		for(int i=0;i<numberAttribute;i++){
			selectPanel.add(new JLabel(kb.getAttributeList().get(i).getName()));
		}
		// Ajouter les ComboBox et JFormattedTextField
		for(int i=0;i<numberAttribute;i++){
			selectPanel.add(componentList.get(i));
		}
		
	}
}
