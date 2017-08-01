package graphicInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import DecisionTree.DecisionTree;


public class TreePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	DecisionTree DT;
	Component compGlissant;
	
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
		
		setLayout(null); // on supprime le layout manager
		 
        TreeMove listener = new TreeMove(this);
        compGlissant = createComponent();
        addMouseListener(listener);
        addMouseMotionListener(listener);
		
	}
	
	 private final static Color[] COLORS= {Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE,
			 Color.BLUE, Color.CYAN, Color.MAGENTA, Color.PINK, Color.WHITE, Color.BLACK};
	 

	private Component createComponent() {
		JPanel component=new DrawPanel(); // Le fon de label
        component.setLocation(1,1); // position
        component.setSize(500,250); // taille 
       // component.setBackground(COLORS[2]); // couleur 
        component.setEnabled(false); // les composants ne doivent pas intercepter la souris
        //component.add(new DrawPanel(DT));
        return component;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
	    add(compGlissant);
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
