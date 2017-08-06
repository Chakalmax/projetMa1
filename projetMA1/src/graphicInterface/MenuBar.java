package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DTL.DTLAlgo;
import DTL.GainStrategy.*;
import KnowledgeBase.KnowledgeBase;
import tools.ParseurTxt;

public class MenuBar extends JMenuBar {

	MainFrame mainFrame;
	public MenuBar(MainFrame mainFrame){
		
		super();
		this.mainFrame = mainFrame;
		JMenu fichier = new JMenu("Fichier");
		JMenuItem load = new JMenuItem("Charger un fichier");
		load.addActionListener(new BoutonLoad());
		fichier.add(load);
		this.add(fichier);
		//
		JMenu option = new JMenu("Option");
		JMenuItem optionItem = new JMenuItem("option");
		optionItem.addActionListener(new BoutonOption());
		option.add(optionItem);
		this.add(option);
		//
		JMenu outils = new JMenu("outils");
		JMenuItem parcoursGraph = new JMenuItem("Parcoureur d'arbre");
		JMenuItem calculGain = new JMenuItem("Calcul de Gain");
		parcoursGraph.addActionListener(new TreeButton());
		calculGain.addActionListener(new GainButton());
		outils.add(calculGain);
		outils.add(parcoursGraph);
		this.add(outils);
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
	    	mainFrame.setKB(ParseurTxt.readFile(dialogue.getSelectedFile().toString()));
	    	
	    	
	      
	    }    
	}
	
	private class BoutonOption implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			new OptionFrame(mainFrame);
			
		}
		
		private class OptionFrame extends JFrame{
			
			private static final long serialVersionUID = 1L;
			private JPanel panel;
			private JButton okButton;
			
			private double error = Options.error;
			//private boolean automatique = Options.automatique;
			private GainStrategy gainStrat = Options.gainStrategy;
			private long waitTime = Options.waitTime;
			
			private JComboBox<Double> comboWaitTime;
			private JComboBox<GainStrategy> comboStrat;
			//private JCheckBox avanceRapide = new JCheckBox("AvanceRapide");
			private JFormattedTextField errorField = new JFormattedTextField(NumberFormat.getPercentInstance());
			
			private GridLayout gl = new GridLayout(0,1);
			
			public OptionFrame(MainFrame mainFrame){
				
			    			
				super("Option");
				this.setSize(300, 300);
			    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    this.setLocationRelativeTo(null);
			    panel = new JPanel();
			    panel.setBackground(Color.white);
			    //panel.setLayout(new BorderLayout());
			    panel.setLayout(gl);
			    panel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			    //  choisir sa strategie.
			    comboStrat = new JComboBox<GainStrategy>();
			    comboStrat.setPreferredSize(new Dimension(200, 20));		    
			    JLabel label = new JLabel("Gain");			    
			    JPanel top = new JPanel();
			    top.add(label);
			    top.add(comboStrat);
			    comboStrat.addItem(new EntropyGain());
			    comboStrat.addItem(new GiniGain());
			    comboStrat.addActionListener(new GainState());
			    //panel.add(top, BorderLayout.NORTH);
			    panel.add(top);
			    // choisir avancee rapide ou pas.
			    //JPanel mid = new JPanel();
			   // avanceRapide.addActionListener(new AutomatiqueState());
			   // mid.add(avanceRapide);
			    //panel.add(mid,BorderLayout.CENTER);
			    //panel.add(mid);
			    // choisir le temps entre deux instructions
			    comboWaitTime = new JComboBox<Double>();
			    comboWaitTime.setPreferredSize(new Dimension(80,20));
			    JLabel label2 = new JLabel("Temps entre deux instructions");
			    JPanel mid1 = new JPanel();
			    mid1.add(label2);
			    mid1.add(comboWaitTime);
			    comboWaitTime.addItem(0.5);
			    for(int i=1;i<10;i++)
			    	comboWaitTime.addItem((double)i);
			    comboWaitTime.addActionListener(new WaitState());
			    panel.add(mid1);
			    //choisir le taux d'erreur.
			    JPanel mid2 = new JPanel();
			    Font police = new Font("Arial", Font.BOLD, 14);
			    errorField.setValue(new Float(error));
			    errorField.setFont(police);
			    errorField.setPreferredSize(new Dimension(150, 30));
			    errorField.setForeground(Color.BLACK);
			    mid2.add(errorField);
			    //panel.add((mid2),BorderLayout.CENTER);
			    panel.add(mid2);
				
				this.okButton = new JButton("Use these options");
			    this.okButton.addActionListener(new BoutonEnd());
			    
			    //panel.add(okButton,BorderLayout.SOUTH);
			    panel.add(okButton);
			    //
			    this.setContentPane(panel);
			    this.setVisible(true);
			    
			    
				
			}
			
			class GainState implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					gainStrat = (GainStrategy) comboStrat.getSelectedItem();
				}
				
				
		}
			
			class WaitState implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					waitTime = (long) Double.parseDouble(comboWaitTime.getSelectedItem().toString()) *1000;
				}
			}
//			class AutomatiqueState implements ActionListener{
//			    public void actionPerformed(ActionEvent e) {
//			      System.out.println("source : " + ((JCheckBox)e.getSource()).getText() + " - état : " + ((JCheckBox)e.getSource()).isSelected());
//			      automatique =  ((JCheckBox)e.getSource()).isSelected();
//			    }
//			  }
			
			private class BoutonEnd implements ActionListener{
				public void actionPerformed(ActionEvent e)
			      {
			    	if(errorField.getText()!=null||errorField.getText()!=""){
			    		double tmp = error;
			    		if(errorField.getValue() instanceof Double){
			    			tmp = (double) errorField.getValue();
			    		}
			    		else if(errorField.getValue() instanceof Float){
			    			String tmpString = errorField.getText();
			    			tmpString = tmpString.substring(0, tmpString.length()-2);
			    			tmp = Double.parseDouble(tmpString);
			    		}
			    		if(tmp>0 && tmp>50)
				    		Options.error = (double) errorField.getValue();
			    		}
			    	else
			    		Options.error = error;
			        //Options.automatique = automatique;
			        Options.gainStrategy = gainStrat;
			       System.out.println("error ici"+ error + "error en option"+ Options.error);
			       //System.out.println("automatique ici"+ automatique + "automatique en option"+ Options.automatique);
			       System.out.println("strat ici"+ gainStrat + "strat en option"+ Options.gainStrategy);
			        setVisible(false);
			        dispose();
			        
			      }
			} 
		
		}
	}
	class TreeButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		  if(mainFrame.getKB() != null)
		  new ParcoursTreeFrame(mainFrame.getKB());
		}
	}
	
	class GainButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		  new GainFrame();
		}
	}
	
}
