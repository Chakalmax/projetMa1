package graphicInterface;

import java.awt.Graphics;

import javax.swing.*;

import DecisionTree.*;
import KnowledgeBase.*;

public class DrawPanel extends JPanel{

	DecisionTree dt;
	KnowledgeBase kb;
	
	int hauteurMax = 1;
	int largeurMax = 1;
	int[] numAttVal;
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
		this.numAttVal = kb.getNumAttVal();
		
	}
	/**
	 * Give the number of node in a level 
	 * @param level, the level of the tree (profondeur)
	 * @return
	 */
	private int computeWorstNumberNode(int level){
		if(level ==0)
			return 0;
		else{
			int result =0;
			for(int i=0;i<level;i++){
				
			}
			return result;
		}
	}
}
