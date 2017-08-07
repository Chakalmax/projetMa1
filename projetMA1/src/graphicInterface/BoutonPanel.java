package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DTL.DTLAlgo;

public class BoutonPanel extends JPanel {

	private JButton buttonNext;
	private JButton buttonRestart;
	private JButton buttonEnd;
	private boolean runing;
	private MainFrame mainFrame;
	
	private static final long serialVersionUID = 1L;
	public BoutonPanel(MainFrame mainFrame)
	{
		//super.setBackground(new Color(100, 255, 100));
		runing = false;
		buttonNext = new JButton("Next");
		buttonRestart = new JButton("Restart");
		buttonEnd = new JButton("Finir l'algo");
		this.mainFrame = mainFrame;
		
		buttonNext.addActionListener(new BoutonNext());
		buttonEnd.addActionListener(new BoutonEnd());
		buttonRestart.addActionListener(new BoutonRestart());
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		

		this.add(buttonEnd,BorderLayout.EAST);
		this.add(buttonNext,BorderLayout.CENTER);
		this.add(buttonRestart,BorderLayout.WEST);
		
		
		
	}
	
	private class BoutonNext implements ActionListener{
	    public void actionPerformed(ActionEvent arg0) {   	
	      System.out.println("Next");
	    	  
	      }
	    }  
	private class BoutonRestart implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			if(mainFrame.getKB() != null)
			if(runing){
			System.out.println("start");
			int option = JOptionPane.showConfirmDialog(null, 
			        "Voulez vous recommencer l'algo depuis le début?", 
			        "Restart", 
			        JOptionPane.YES_NO_OPTION, 
			        JOptionPane.QUESTION_MESSAGE);
			
			if(option == JOptionPane.OK_OPTION){
				mainFrame.restartAlgo();
			}}
			else{
				mainFrame.startAlgo();
				runing = true;
			}
			else
				JOptionPane.showMessageDialog(null, "Ouvrir une Kb avant", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private class BoutonEnd implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			System.out.println("End");
		}
	}
}
