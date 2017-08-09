package graphicInterface;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

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
	    	for(int i=0;i<output.size();i++)
	    		g.drawString(output.get(i), 10, 20 +25*i);
	}

	private ArrayList<String> TransformInfo() {
		return new ArrayList<String>(Arrays.asList(info.split("\\n")));
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
