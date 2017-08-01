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
		repaint();
	}

	public TreePanel()
	{
		super.setBackground(new Color(100, 255, 100));
	}

	public void paintComponent(Graphics g){
			int fontSize = 15;
	    	Font normalFont = new Font("Dial", 0, fontSize);
	    	g.setFont(normalFont);
	    
	    	g.drawString("THIS IS PANEL Tree", 10, 20);
	}
}
