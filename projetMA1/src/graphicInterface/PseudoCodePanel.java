package graphicInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import DTL.DTLAlgo;

public class PseudoCodePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String[] instructions; // pseudo code � afficher
	private int[] indentInstruction; // identation du pseudoCode � afficher
	private static Color greenColor = new Color(100, 255, 100);
	private static Color redColor = new Color(255, 100, 100);
	private static Color normalColor = new Color(220, 220, 200);
	private static Color fontColor = new Color (230,220,235);
	private static Color writeColor = new Color (0,0,0);
	private Color highlightColor;
	private boolean isMac = System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0;
	private int lineToHighLight = 0;
	
	

	public PseudoCodePanel(){
		super.setBackground(fontColor);
		this.instructions = DTLAlgo.getPseudoCode();
		this.indentInstruction = DTLAlgo.getPseudoCodeIdentation();
		this.highlightColor = normalColor;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int marginTop = 20;
	    int fontSize = Math.min(15, (getHeight() - this.instructions.length * 8 - marginTop) / this.instructions.length);
	    Font normalFont;
	    
	    if (this.isMac) {
	        normalFont = new Font("Dial", 0, fontSize - 2);
	      } else {
	        normalFont = new Font("Dial", 0, fontSize);
	      }
	    g.setFont(normalFont);
		
	  //Surlignage
	    for (int i = 0; i < this.instructions.length; i++)
	      {
	        if (this.lineToHighLight == i)
	        {
	          g.setColor(this.highlightColor);
	          g.fillRect(5,(15-fontSize)+25*i,getWidth()-5,fontSize+2);
	        }
	      }
	    
	    // TOUJOURS ECRIRE APRES SURLIGNAGE
		// Ecriture
	    g.setColor(writeColor);
	    
	    if(this.instructions.length == this.indentInstruction.length){
	    	int i = 0;
	    	int ident;
	    	while(i < this.instructions.length){
	    		ident = this.indentInstruction[i];
	    		g.drawString(this.instructions[i],10 + 25*ident, 15+25*i);
	    		i++;
	    	}
	    }else{
	    	g.drawString("Can't load the pseudoCode", 10, 20);
	    }
	    
	    
	  }

	public int getLineToHighLight() {
		return lineToHighLight;
	}

	public void setLineToHighLight(int lineToHighLight) {
		if(lineToHighLight>=0 && lineToHighLight< this.instructions.length)
			this.lineToHighLight = lineToHighLight;
		else
			this.lineToHighLight = this.instructions.length;
		repaint();
	}
	
	public void nextLineToHighLight(){
		if(lineToHighLight < this.instructions.length)
			this.lineToHighLight = lineToHighLight +1;
		else
			this.lineToHighLight = 0;
		repaint();
	}

	public void setGreenHighLight() {
		this.highlightColor = greenColor;
		repaint();
	}

	public void setRedHighLight() {
		this.highlightColor = redColor;
		repaint();
		
	}

	public void setNormalHighLight() {
		this.highlightColor = normalColor;
		repaint();
		
	}

	public void restart() {
		setNormalHighLight();
		this.setLineToHighLight(0);
		repaint();
		
	}
	
	
	
	
}
