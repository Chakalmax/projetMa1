package graphicInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import DecisionTree.DecisionTree;

public class TreePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	DecisionTree DT;
	
	public DecisionTree getDT() {
		return DT;
	}

	public void setDT(DecisionTree dT) {
		DT = dT;
		removeAll();
		repaint();
	}

	public TreePanel()
	{
		super.setBackground(new Color(100, 255, 100));
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(DT == null){
			int fontSize = 15;
	    	Font normalFont = new Font("Dial", 0, fontSize);
	    	g.setFont(normalFont);
	    
	    	g.drawString("THIS IS PANEL Tree", 10, 20);
		}else{
			paintDT(g);
		}
	}

	private void paintDT(Graphics g) {
		int fontSize = 15;
    	Font normalFont = new Font("Dial", 0, fontSize);
    	g.setFont(normalFont);
    
    	g.drawString("THIS IS PANEL Tree BUT REPAINTED YEAH", 10, 20);
		
	}
}
