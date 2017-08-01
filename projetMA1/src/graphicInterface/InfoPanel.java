package graphicInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	public InfoPanel()
	{
		//super.setBackground(new Color(100, 255, 100));
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
			int fontSize = 15;
	    	Font normalFont = new Font("Dial", 0, fontSize);
	    	g.setFont(normalFont);
	    
	    	g.drawString("THIS IS PANEL INFO", 10, 20);
	}
}
