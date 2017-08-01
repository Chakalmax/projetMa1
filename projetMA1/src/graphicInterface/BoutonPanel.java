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

public class BoutonPanel extends JPanel {

	private JButton buttonNext;
	private JButton buttonRestart;
	private JButton buttonEnd;
	
	private static final long serialVersionUID = 1L;
	public BoutonPanel()
	{
		//super.setBackground(new Color(100, 255, 100));
		
		buttonNext = new JButton("Next");
		buttonRestart = new JButton("Restart");
		buttonEnd = new JButton("Finir l'algo");
		
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
			System.out.println("Restart");
			int option = JOptionPane.showConfirmDialog(null, 
			        "Voulez vous recommencer l'algo depuis le début?", 
			        "Restart", 
			        JOptionPane.YES_NO_OPTION, 
			        JOptionPane.QUESTION_MESSAGE);
			
			if(option == JOptionPane.OK_OPTION){
				
			}
		}
	}
	
	private class BoutonEnd implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			System.out.println("End");
		}
	}
}
