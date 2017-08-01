package graphicInterface;

import java.awt.Graphics;

import javax.swing.*;

import DecisionTree.*;
import KnowledgeBase.*;

public class DrawPanel extends JPanel{

	DecisionTree dt;
	KnowledgeBase kb;
	
	int hauteurMax;
	int largeurMax;
	public DrawPanel(){
		super();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(dt != null){
			drawTree(g,dt);
			
		}
	}

	private void drawTree(Graphics g,DecisionTree dt) {
		if(dt instanceof InnerDecisionTree){
			
		}else{// it's a leaf
			
		}
		
	}
	
	public void setDt(DecisionTree dt){
		this.dt = dt;
		updateInfo();
	}

	private void updateInfo() {
		this.kb = dt.getKb();
		this.hauteurMax = kb.getAttributeList().size();
		this.largeurMax = kb.getLargeurMax();
		
	}
}
