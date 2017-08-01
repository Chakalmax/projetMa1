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

            add(createComponent());
        
        addMouseListener(listener);
        addMouseMotionListener(listener);
		
	}
	
	 private final static Color[] COLORS= {Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE,
			 Color.BLUE, Color.CYAN, Color.MAGENTA, Color.PINK, Color.WHITE, Color.BLACK};
	 

	private Component createComponent() {
		JPanel component=new JPanel(); // ici on peut faire n'importe quel JComponent, JLabel, par exemple
        component.setLocation(1,1); // position aléatoire
        component.setSize(500,250); // taille aléatoire
        component.setBackground(COLORS[2]); // couleur aléatoire
        component.setEnabled(false); // les composants ne doivent pas intercepter la souris
        return component;
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
