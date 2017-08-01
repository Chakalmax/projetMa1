package graphicInterface;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DTL.DTLAlgo;
import DTL.GainStrategy.EntropyGain;
import DTL.GainStrategy.GainStrategy;
import DTL.GainStrategy.GiniGain;
import DecisionTree.DecisionTree;
import KnowledgeBase.*;

public class ParcoursTreeFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	JPanel panel;
	DecisionTree dT;
	KnowledgeBase kb;
	GainStrategy gStrat = new GiniGain();
	
	JPanel selectPanel;
	TreePanel treePanel;
	JPanel boutonPanel;
	ArrayList<JLabel> labelList;
	ArrayList<JComponent> componentList;
	public ParcoursTreeFrame(KnowledgeBase kb){
		super("Parcours d'un arbre");
		this.panel = new JPanel();
		//this.dT = dT; // CAREFUL: Null atm
		this.kb = kb;
		this.dT = DTLAlgo.DTL_algo(kb, 0, gStrat);
		int numberAttribute = kb.getAttributeList().size();
		this.setSize(62*numberAttribute, 200);
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
		boutonPanel = new JPanel();
		addThingsBoutonPanel();
		
		this.getContentPane().setLayout(new GridLayout(3,1));
		treePanel.setDT(dT);
		this.add(treePanel);
		this.add(selectPanel);
		this.add(boutonPanel);
		
		
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
	
	private void addThingsBoutonPanel() {
		JButton bouton = new JButton("Verifier");
		bouton.addActionListener(new BoutonVerification());
		this.boutonPanel.add(bouton);
		
	}
	
	private class BoutonVerification implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			treePanel.setDT(dT);
			Sample samp = new Sample();
			int classIndex = kb.getIndexClass();
			AttributeValue<?> classVal = null;
			for(int i=0;i<componentList.size();i++){
				if(i != classIndex){
					if(kb.getAttributeList().get(i).getType() == TypeAttribute.Boolean){
						JComboBox<Boolean> comp = (JComboBox<Boolean>) componentList.get(i);
						boolean val = (boolean) comp.getSelectedItem();
						AttributeValue<Boolean> attVal = new AttributeValue<Boolean>(val);
						samp .add(attVal);
					}else if(kb.getAttributeList().get(i).getType() == TypeAttribute.Nominal){
						JComboBox<String> comp = (JComboBox<String>) componentList.get(i);
						String val = (String) comp.getSelectedItem();
						AttributeValue<String> attVal = new AttributeValue<String>(val);
						samp .add(attVal);
					}else{// == Numerical
						JFormattedTextField comp = (JFormattedTextField) componentList.get(i);
						float val;
						if(comp.getText() != null && comp.getText().equals("")) 
						    val =0;
						else 
						    val = Float.parseFloat(comp.getText()); 
						AttributeValue<Float> attVal = new AttributeValue<Float>(val);
						samp .add(attVal);
					}
				}else{// i == classIndex
					if(kb.getAttributeList().get(i).getType() == TypeAttribute.Boolean){
						JComboBox<Boolean> comp = (JComboBox<Boolean>) componentList.get(i);
						boolean val = (boolean) comp.getSelectedItem();
						classVal = new AttributeValue<Boolean>(val);
					}else if(kb.getAttributeList().get(i).getType() == TypeAttribute.Nominal){
						JComboBox<String> comp = (JComboBox<String>) componentList.get(i);
						String val = (String) comp.getSelectedItem();
						classVal = new AttributeValue<String>(val);
					}else{// == Numerical
						JFormattedTextField comp = (JFormattedTextField) componentList.get(i);
						float val;
						if(comp.getText() != null && comp.getText().equals("")) 
						    val =0;
						else 
						    val = Float.parseFloat(comp.getText()); 
						classVal = new AttributeValue<Float>(val);
					}
					
				}
			}
			AttributeValue<?> decision = dT.getDecision(samp);
			System.out.println(classVal);
			System.out.println(decision);
			if(decision.equals(classVal))
				showCorrectAnswer(decision, classVal);
			else
				showIncorrectAnswer(decision, classVal);
		}

		private void showIncorrectAnswer(AttributeValue<?> decision, AttributeValue<?> classVal) {
			JOptionPane jopt = new JOptionPane();
			jopt.showMessageDialog(null, "Vous avez répondu : " + classVal + "\n" + "mais la réponse correcte est : "+ decision,"Detail",JOptionPane.INFORMATION_MESSAGE);
			
		}

		private void showCorrectAnswer(AttributeValue<?> decision, AttributeValue<?> classVal) {
			JOptionPane jopt = new JOptionPane();
			jopt.showMessageDialog(null, "Vous avez répondu : " + classVal + "\n" + "et c'est correct","Detail" , JOptionPane.INFORMATION_MESSAGE);
			
		}
	}


}
