package graphicInterface;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import DTL.GainStrategy.EntropyGain;
import DTL.GainStrategy.GainStrategy;
import DTL.GainStrategy.GiniGain;
import DecisionTree.DecisionTree;



public class GainFrame extends JFrame{

	JPanel panelBouton;
	JPanel panelView;
	//Bouton for panelBouton;
	JButton buttonOption = new JButton("Options");
	JButton buttonCompute = new JButton("Calculer");
	JButton buttonDetail = new JButton("Detail calcul");
	JButton buttonValue = new JButton("Chosir les valeurs");
	//Options:
	private final int minClass = 2;
	private final int maxClass = 4;
	private int nbClass = minClass;
	private final int minAttVal = 2;
	private final int maxAttVal = 5;
	private int nbAttVal = minAttVal;
	//
	
	public GainFrame(){
		super("Calcul de gains");
		this.panelBouton = new JPanel();
		this.panelView = new JPanel();
		this.setSize(800, 800);
	    this.setLocationRelativeTo(null);
	    addThings();
	    this.setResizable(true);
	    this.setVisible(true);
	}
	
	private void addThings() {
		addThingsPanelView();
		addThingsPanelBouton();
		getContentPane().setLayout(new GridLayout(0,1));
		getContentPane().add(panelBouton);
		getContentPane().add(panelView);
		
	}

	private void addThingsPanelBouton() {
		panelBouton.setLayout(new GridLayout(1,0));
		panelBouton.add(buttonCompute);
		panelBouton.add(buttonDetail);
		panelBouton.add(buttonValue);
		panelBouton.add(buttonOption);
		
		buttonCompute.addActionListener(new BoutonCompute());
		buttonDetail.addActionListener(new BoutonDetail());
		buttonValue.addActionListener(new BoutonValue());
		buttonOption.addActionListener(new BoutonOption());
		
	}

	private void addThingsPanelView() {
		// TODO Auto-generated method stub
		
	}
	
	private class BoutonCompute implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			//TODO
			
		}
	}
	
	private class BoutonDetail implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			//TODO
			
		}
	}
	
	private class BoutonValue implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			//TODO
			
		}
	}
	
	private class BoutonOption implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			
			new OptionFrame();
		}
		
		private class OptionFrame extends JFrame{
			
			JPanel panel;
			JButton okButton;
			JButton cancelButton;
			JComboBox<Integer> nbAttBox;
			JComboBox<Integer> nbClassBox;
			int tmpNbAttVal = nbAttVal;
			int tmpNbClassVal = nbClass;
			public OptionFrame(){
				super("Option");
				
				this.setSize(300, 300);
			    this.setLocationRelativeTo(null);
			    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    addThings();
			    this.setResizable(true);
			    this.setVisible(true);
			}
			
			private void addThings(){
				panel = new JPanel();
				okButton = new JButton("Accepter");
				cancelButton = new JButton("Retour");
				this.getContentPane().setLayout(new GridLayout(0,1));
				
				// comboBox nbAttBox
				
				nbAttBox = new JComboBox<Integer>();
			    nbAttBox.setPreferredSize(new Dimension(40, 20));		    
			    JLabel labelNbAttBox = new JLabel("Nombre de valeurs pour l'attribut");			    
			    JPanel top = new JPanel();
			    top.add(labelNbAttBox);
			    top.add(nbAttBox);
			    for(int i=minAttVal; i<= maxAttVal;i++)
			    	nbAttBox.addItem(i);
			    nbAttBox.addActionListener(new nbAttBoxState());
			    
			    // comboBox nbClassBox
				
			    nbClassBox = new JComboBox<Integer>();
			    nbClassBox.setPreferredSize(new Dimension(40, 20));		    
			    JLabel labelClassBox = new JLabel("Nombre de valeurs pour l'attribut");			    
			    JPanel mid = new JPanel();
			    mid.add(labelClassBox);
			    mid.add(nbClassBox);
			    for(int i=minClass; i<= maxClass;i++)
			    	nbClassBox.addItem(i);
			    nbClassBox.addActionListener(new nbAttBoxState());
			    // ajouter les combobox au panel.
			    
			    panel.add(top);
			    panel.add(mid);
			    
			    //fin
			    this.setContentPane(panel);
			}
			
			class nbAttBoxState implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
				//	System.out.println("événement déclenché sur : " + ((GainStrategy) comboStrat.getSelectedItem()).getName());
					tmpNbAttVal = (Integer) nbAttBox.getSelectedItem();
				}
		}
			
			class nbClassBoxState implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
				//	System.out.println("événement déclenché sur : " + ((GainStrategy) comboStrat.getSelectedItem()).getName());
					tmpNbClassVal = (Integer) nbClassBox.getSelectedItem();
				}
		}
		}
	}
}
