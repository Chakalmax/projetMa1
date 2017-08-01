package graphicInterface;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

import DecisionTree.*;
import KnowledgeBase.*;

public class DrawPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DecisionTree dt;
	KnowledgeBase kb;
	
	int hauteurMax = 1;
	int largeurMax = 1;
	int maxHeight = 1;
	int[] numAttVal = {};
	int[] currentPos ={};
	static final int rowSize =100;
	static final int collSize = 150;
	static final int ovalWidth = 55;
	static final int ovalHeight=35;
	public DrawPanel(){
		super();
	}
	
	public DrawPanel(DecisionTree dt){
		super();
		setDt(dt);
	}
	
	public void paintComponent(Graphics g){
		// rien n'est affiché dans aucune ligne.
		currentPos = new int[kb.getAttributeList().size()-1];
		for(int i=0;i<currentPos.length;i++)
			currentPos[i]=0;
		super.paintComponent(g);
		if(dt != null){
			drawTree(g,dt);
			
		}
	}

	private void drawTree(Graphics g,DecisionTree dt) {
		int height = dt.getHeight();
		int squareNumber = currentPos[height];
		if(dt instanceof InnerDecisionTree){
			g.setColor(Color.BLACK);
			g.drawOval((squareNumber*collSize + collSize/2),(height*rowSize + rowSize/2), ovalWidth, ovalHeight);
		}else{
			g.setColor(Color.RED);
			g.drawOval((squareNumber*collSize + collSize/2),(height*rowSize + rowSize/2), ovalWidth, ovalHeight);
			
		}
		
	}
	
	public void setDt(DecisionTree dt){
		this.dt = dt;
		updateInfo();
	}

	private void updateInfo() {
		this.kb = dt.getKb();
		this.maxHeight = dt.getHeight();
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
				result = result + numAttVal[i];
			}
			return result;
		}
	}
}
