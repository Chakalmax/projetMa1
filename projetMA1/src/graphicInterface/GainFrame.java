package graphicInterface;

import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DTL.GainStrategy.EntropyGain;
import DTL.GainStrategy.GainStrategy;
import DTL.GainStrategy.GiniGain;



public class GainFrame extends JFrame{


	private static final long serialVersionUID = 1L;
	JPanel panelBouton;
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
	ArrayList<ArrayList<Integer>> count2D =createCount2D();
	private GainStrategy strategy = new GiniGain();
	ArrayList<JFormattedTextField> textFieldTab;
	//
	boolean calcul;// true si un calcul a deja ete fait.
	//
	public GainFrame(){
		super("Calcul de gains");
		this.calcul=false;
		this.panelBouton = new JPanel();
		this.setSize(500, 95);
	    this.setLocationRelativeTo(null);
	    addThings();
	    this.setResizable(true);
	    this.setVisible(true);
	}
	
	private ArrayList<ArrayList<Integer>> createCount2D() {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<nbAttVal;i++){
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for(int j=0;j<nbClass;j++){
				tmp.add(0);
			}
			result.add(tmp);
		}
		return result;
	}

	private void addThings() {
		addThingsPanelBouton();
		getContentPane().setLayout(new GridLayout(1,1));
		getContentPane().add(panelBouton);
		
	}

	private void addThingsPanelBouton() {
		panelBouton.setLayout(new GridLayout(1,0));
		panelBouton.setPreferredSize(new Dimension(100,300));
		panelBouton.add(buttonCompute);
		panelBouton.add(buttonDetail);
		panelBouton.add(buttonValue);
		panelBouton.add(buttonOption);
		
		buttonCompute.addActionListener(new BoutonCompute());
		buttonDetail.addActionListener(new BoutonDetail());
		buttonValue.addActionListener(new BoutonValue());
		buttonOption.addActionListener(new BoutonOption());
		
	}

	
	
	private class BoutonCompute implements ActionListener{
		@SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent arg0){
			calcul = true;
			ArrayList<Integer> count1D = createCount1D(count2D);
			int kbSize = sumList(count1D);
			float result = strategy.getGain(count2D,count1D,kbSize);
			// affiche le result sur une frame.
			JOptionPane jopt = new JOptionPane();
			String strOut =  "Le gain est de : " + result +"\n";
			strOut = strOut + "La méthode de calcul était : " + strategy.getName();
			jopt.showMessageDialog(null, strOut,"Gain" , JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		private ArrayList<Integer> createCount1D(ArrayList<ArrayList<Integer>> count2d) {
			ArrayList<Integer> count1D = new ArrayList<Integer>();
			// init le count1D
			for(int i=0;i<count2d.get(0).size();i++){
				count1D.add(0);
			}
			//
			for(int i=0;i<count2d.size();i++)
				for(int j=0;j<count2d.get(i).size();j++)
					count1D.set(j, count1D.get(j)+ count2d.get(i).get(j));
			
			return count1D;
		}
		
		protected int sumList(ArrayList<Integer> list){
			int result =0;
			for(int i: list){
				result = result + i;
			}
			return result;
			
		}

	}
	
	private class BoutonDetail implements ActionListener{
		@SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent arg0){
			//TODO
			if(!calcul){
				JOptionPane jopt = new JOptionPane();
				jopt.showMessageDialog(null, "Calculer le gain avant.","Detail" , JOptionPane.WARNING_MESSAGE);
			}else{
				JOptionPane jopt = new JOptionPane();
				String strOut = strategy.getDetail();
				jopt.showMessageDialog(null, strOut,"Detail" , JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
	}
	
	private class BoutonValue implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			new ValueFrame();
		}
		
		private class ValueFrame extends JFrame{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			JButton okButton;
			JButton cancelButton;
			JPanel panelView;
			JPanel panelBouton;
			ArrayList<ArrayList<Integer>> newCount2D;
			
			public ValueFrame(){
				super("Selectionner les valeurs");
				newCount2D = copyList(count2D);
				this.setSize(350, 100+nbAttVal*110);
			    this.setLocationRelativeTo(null);
			    addThings();
			    this.setResizable(true);
			    this.setVisible(true);
			}
			
			private void addThings(){
				addThingsPanelView();
				addThingsPanelBouton();
				getContentPane().setLayout(new GridLayout(2,1));
				getContentPane().add(panelView);
				getContentPane().add(panelBouton);

			}
			
			private void addThingsPanelView(){
				panelView = new JPanel();
				panelView.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				count2D = createCount2D();
				textFieldTab = new ArrayList<JFormattedTextField>(nbAttVal*nbClass);
				// on cree assez de textField
				for(int i=0;i<count2D.size();i++){
					ArrayList<Integer> tmp = count2D.get(i);
					for(int j=0;j<count2D.get(i).size();j++){
						JFormattedTextField txtf = new JFormattedTextField(NumberFormat.getIntegerInstance());
						txtf.setSize(new Dimension(300,40));
						txtf.setValue(tmp.get(j));
						textFieldTab.add(txtf);				
					}
						
				}
				
				// On positionne les objets.
				gbc.gridx = 0;
			    gbc.gridy = 0;
			    gbc.fill=GridBagConstraints.HORIZONTAL;
			    panelView.add(new JLabel(" tab "),gbc);
			    for(int i=1;i<nbClass+1;i++){
			    	gbc.gridx = i;
			        gbc.gridy = 0;
			    	panelView.add(new JLabel("Class"+i),gbc);
			    }
			    int index =0;
			    for(int j=1;j<nbAttVal+1;j++){
			    	gbc.gridy= j;
			    	gbc.gridx =0;
			    	panelView.add(new JLabel("AttVal"+j),gbc);
			    	for(int i=1;i<nbClass+1;i++){
			    		gbc.gridy=j;
			    		gbc.gridx=i;
			    		panelView.add(textFieldTab.get(index),gbc);
			    		index++;
			    	}
			    }
			    
			    
			}
			
			private void addThingsPanelBouton(){
				panelBouton = new JPanel();
				okButton = new JButton("Sauvegarder");
				cancelButton = new JButton("Quitter sans sauvegarder");
				okButton.addActionListener(new ButtonOk(this));
				cancelButton.addActionListener(new ButtonCancel(this));
				panelBouton.add(okButton);
				panelBouton.add(cancelButton);
				
			}
			
			class ButtonOk implements ActionListener{
				ValueFrame optionFrame;
				public ButtonOk(ValueFrame optionFrame) {
					super();
					this.optionFrame=optionFrame;
					
				}
				
				@Override
				public void actionPerformed(ActionEvent e){
					int index =0;
					// i think it's useless lol.
					ArrayList<ArrayList<Integer>> count2DClone = copyList(count2D);
					for(int i=0;i<count2D.size();i++){
						ArrayList<Integer> tmp = new ArrayList<Integer>();
						for(int j=0;j<tmp.size();j++){
							Integer tmpInt = count2D.get(i).get(j);
							ArrayList<Integer> tmpcount = count2DClone.get(i);
							tmpcount.set(i,tmpInt);
							count2DClone.set(i,tmpcount);
							
						}
					}
					//
					for(int i=0;i<count2DClone.size();i++){
						for(int j=0;j<count2DClone.get(i).size();j++){
							int tmpInt = Integer.parseInt(textFieldTab.get(index).getText());
							ArrayList<Integer> tmpcount = count2DClone.get(i);
							tmpcount.set(j,tmpInt);
							count2DClone.set(i,tmpcount);
							index++;
						}
					}
					count2D = count2DClone;
					calcul = false;
					optionFrame.setVisible(false);
					optionFrame.dispose();
				}
			}
			
			class ButtonCancel implements ActionListener{
				ValueFrame optionFrame;
				public ButtonCancel(ValueFrame optionFrame) {
					super();
					this.optionFrame=optionFrame;
				}

				@Override
				public void actionPerformed(ActionEvent e){
					count2D = newCount2D;
					optionFrame.setVisible(false);
					optionFrame.dispose();
				}
			}
		}
	}
	
	private class BoutonOption implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			
			new OptionFrame();
		}
		
		private class OptionFrame extends JFrame{
			
			private static final long serialVersionUID = 1L;
			JPanel panel;
			JButton okButton;
			JButton cancelButton;
			JComboBox<Integer> nbAttBox;
			JComboBox<Integer> nbClassBox;
			JComboBox<String> strategyBox;
			int tmpNbAttVal = nbAttVal;
			int tmpNbClassVal = nbClass;
			GainStrategy tmpStrategy = strategy;
			public OptionFrame(){
				super("Option");
				
				this.setSize(350, 200);
			    this.setLocationRelativeTo(null);
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
			    nbAttBox.setSelectedItem(nbAttVal);;
			    // comboBox nbClassBox
				
			    nbClassBox = new JComboBox<Integer>();
			    nbClassBox.setPreferredSize(new Dimension(40, 20));		    
			    JLabel labelClassBox = new JLabel("Nombre de valeurs pour la classe");			    
			    JPanel mid = new JPanel();
			    mid.add(labelClassBox);
			    mid.add(nbClassBox);
			    for(int i=minClass; i<= maxClass;i++)
			    	nbClassBox.addItem(i);
			    nbClassBox.addActionListener(new nbClassBoxState());
			    nbClassBox.setSelectedItem(nbClass);
			    // combobox strategyBox
			    
			    strategyBox = new JComboBox<String>();
			    strategyBox.setPreferredSize(new Dimension(100, 20));		    
			    JLabel labelStrategyBox = new JLabel("Calcul de gain");			    
			    JPanel mid2 = new JPanel();
			    mid2.add(labelStrategyBox);
			    mid2.add(strategyBox);
			    strategyBox.addItem("Gini");
			    strategyBox.addItem("Entropie");
			    strategyBox.addActionListener(new StrategyState());
			    if(strategy instanceof GiniGain)
			    	strategyBox.setSelectedItem("Gini");
			    else if(strategy instanceof EntropyGain)
			    	strategyBox.setSelectedItem("Entropie");
			    
			    // ajouter les combobox au panel.
			    
			    panel.add(top);
			    panel.add(mid);
			    panel.add(mid2);
			    // ajouter les boutons.
			    okButton = new JButton("Sauvegarder");
			    cancelButton = new JButton("Sortir sans Sauvegarder");
			    okButton.addActionListener(new ButtonOk(this));
			    cancelButton.addActionListener(new ButtonCancel(this));
			    JPanel buttonPanel = new JPanel();
			    buttonPanel.setLayout(new GridLayout(1,2));
			    buttonPanel.add(okButton);
			    buttonPanel.add(cancelButton);
			    panel.add(buttonPanel);
			    
			    //fin
			    this.setContentPane(panel);
			}
			
			class nbAttBoxState implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					tmpNbAttVal = (Integer) nbAttBox.getSelectedItem();
				}
		}
			
			class nbClassBoxState implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					tmpNbClassVal = (Integer) nbClassBox.getSelectedItem();
				}
		}
			
			class StrategyState implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					if(strategyBox.getSelectedItem().toString().compareToIgnoreCase("Gini")==0){
						tmpStrategy = new GiniGain();
					} else if(strategyBox.getSelectedItem().toString().compareToIgnoreCase("Entropie")==0){
						tmpStrategy = new EntropyGain();
					}
				}
		}
			class ButtonOk implements ActionListener{
				OptionFrame optionFrame;
				public ButtonOk(OptionFrame optionFrame) {
					super();
					this.optionFrame=optionFrame;
				}
				@Override
				public void actionPerformed(ActionEvent e){
					boolean change=(strategy != tmpStrategy || nbClass != tmpNbClassVal || nbAttVal != tmpNbAttVal);	
					strategy = tmpStrategy;
					nbClass = tmpNbClassVal;
					nbAttVal = tmpNbAttVal;
					if(change){
						count2D = createCount2D();
					}
					calcul = false;
					optionFrame.setVisible(false);
					optionFrame.dispose();
				}
			}
			
			class ButtonCancel implements ActionListener{
				OptionFrame optionFrame;
				public ButtonCancel(OptionFrame optionFrame) {
					super();
					this.optionFrame=optionFrame;
				}

				@Override
				public void actionPerformed(ActionEvent e){

					optionFrame.setVisible(false);
					optionFrame.dispose();
				}
			}
		}
	}
	
	public ArrayList<ArrayList<Integer>> copyList(ArrayList<ArrayList<Integer>> count2d) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for(ArrayList<Integer> arr : count2d)
			result.add(copyList2(arr));
		return result;
	}
	public ArrayList<Integer> copyList2(ArrayList<Integer> arr) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(Integer inte : arr)
			result.add(inte);
		return result;
	}
}
