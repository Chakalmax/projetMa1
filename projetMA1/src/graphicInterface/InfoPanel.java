package graphicInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	String info;
	public InfoPanel()
	{
		this.info = "Info";
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
			int fontSize = 15;
	    	Font normalFont = new Font("Dial", 0, fontSize);
	    	g.setFont(normalFont);
	    
	    	g.drawString(info, 10, 20);
	}

	public void setInformation(String info) {
		this.info = info;
		repaint();		
	}
}
