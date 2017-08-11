package graphicInterface;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DTL.GainStrategy.*;
import tools.ParseurTxt;

public class MenuBar extends JMenuBar {

	
	private static final long serialVersionUID = 1L;
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
	        } catch(IOException e) {}
	         
	        JFileChooser dialogue = new JFileChooser(repertoireCourant);
	        if(dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
	    	mainFrame.setKB(ParseurTxt.readFile(dialogue.getSelectedFile().toString()));
	        }
	    	
	    	
	      
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
			private JComboBox<String> comboStrat;
			//private JCheckBox avanceRapide = new JCheckBox("AvanceRapide");
			private JFormattedTextField errorField = new JFormattedTextField(NumberFormat.getPercentInstance());
			
			private GridLayout gl = new GridLayout(0,1);
			
			public OptionFrame(MainFrame mainFrame){
				
			    			
				super("Option");
				Options.stop = true;
				this.addWindowListener(new java.awt.event.WindowAdapter() {
				    @Override
				    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				            Options.stop = false;     
				    }
				});
				
				this.setSize(300, 300);
			    this.setLocationRelativeTo(null);
			    panel = new JPanel();
			    panel.setBackground(Color.white);
			    //panel.setLayout(new BorderLayout());
			    panel.setLayout(gl);
			    panel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			    //  choisir sa strategie.
			    comboStrat = new JComboBox<String>();
			    comboStrat.setPreferredSize(new Dimension(200, 20));		    
			    JLabel label = new JLabel("Gain");			    
			    JPanel top = new JPanel();
			    top.add(label);
			    top.add(comboStrat);
			    comboStrat.addItem("Entropie");
			    comboStrat.addItem("Gini");
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
			    comboWaitTime.addItem(0.1);
			    comboWaitTime.addItem(0.5);
			    for(int i=1;i<10;i++)
			    	comboWaitTime.addItem((double)i);
			    comboWaitTime.addActionListener(new WaitState());
			    comboWaitTime.setSelectedItem((double)(((double)waitTime)/1000));
			    panel.add(mid1);
			    //choisir le taux d'erreur.
			    JPanel mid2 = new JPanel();
			    JLabel errorLabel = new JLabel("Erreur acceptée");
			    Font police = new Font("Arial", Font.BOLD, 14);
			    errorField.setValue(new Float(error/100));
			    errorField.setFont(police);
			    errorField.setPreferredSize(new Dimension(150, 30));
			    errorField.setForeground(Color.BLACK);
			    mid2.add(errorLabel);
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
					if(comboStrat.getSelectedItem().toString().compareToIgnoreCase("Gini")==0){
						gainStrat = new GiniGain();
					} else if(comboStrat.getSelectedItem().toString().compareToIgnoreCase("Entropie")==0){
						gainStrat = new EntropyGain();
					}
				}
				
				
		}
			
			class WaitState implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent e) {
					waitTime = (long) (Double.parseDouble(comboWaitTime.getSelectedItem().toString()) *1000);
				}
			}

			private class BoutonEnd implements ActionListener{
				public void actionPerformed(ActionEvent e)
			      {
					System.out.println(errorField.getValue());
					System.out.println(errorField.getValue() instanceof Float);
					System.out.println(errorField.getText());
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
			    		if(tmp>0 && tmp<0.25)
				    		Options.error = ((double) errorField.getValue())*100;
			    		}
			    	else
			    		Options.error = error;
			    	System.out.println(Options.error);
			    	Options.waitTime = waitTime;
			        Options.gainStrategy = gainStrat;
			        setVisible(false);
			        Options.stop = false;
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
		  else
			  JOptionPane.showMessageDialog(null, "Ouvrir une Kb avant d'acceder à cette section", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	class GainButton implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		  new GainFrame();
		}
	}
	
}
