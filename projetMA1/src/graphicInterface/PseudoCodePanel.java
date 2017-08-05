package graphicInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import DTL.DTLAlgo;

public class PseudoCodePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String[] instructions; // pseudo code à afficher
	private int[] indentInstruction; // identation du pseudoCode à afficher
	private static Color greenColor = new Color(100, 255, 100);
	private static Color redColor = new Color(255, 100, 100);
	private static Color normalColor = new Color(220, 220, 200);
	private static Color fontColor = new Color (230,220,235);
	private static Color blackColor = new Color (0,0,0);
	private Color highlightColor = normalColor;
	private boolean isMac = System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0;
	private int lineToHighLight = 0;
	
	

	public PseudoCodePanel(){
		super.setBackground(fontColor);
		this.instructions = DTLAlgo.getPseudoCode();
		this.indentInstruction = DTLAlgo.getPseudoCodeIdentation();
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
	        	System.out.println(i);
	          g.setColor(this.highlightColor);
	          g.fillRect(5,(15-fontSize)+25*i,getWidth()-5,fontSize+2);
	        }
	      }
	    
	    // TOUJOURS ECRIRE APRES SURLIGNAGE
		// Ecriture
	    g.setColor(blackColor);
	    
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
		System.out.println("Un appel a setLineToHighLight pour la ligne"+lineToHighLight);
		if(lineToHighLight>0 && lineToHighLight< this.instructions.length)
			this.lineToHighLight = lineToHighLight;
		else
			this.lineToHighLight = this.instructions.length;
		System.out.println("la ligne à highLight est: " + this.lineToHighLight);
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
	
	
	
	
}
