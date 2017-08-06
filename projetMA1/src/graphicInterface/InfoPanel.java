package graphicInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

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
	    	ArrayList<String> output = TransformInfo();
	    	g.drawString(info, 10, 20);
	}

	private ArrayList<String> TransformInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInformation(String info) {
		this.info = info;
		repaint();		
	}

	public void restart() {
		this.info = "Info";
		repaint();
		
	}
}
