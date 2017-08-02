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
	static final int rowSize =130;
	static final int collSize = 150;
	static final int ovalWidth = 120;
	static final int ovalHeight=75;
	public DrawPanel(){
		super();
	}
	
	public DrawPanel(DecisionTree dt){
		super();
		if(dt != null)
		setDt(dt);
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		if(dt != null){
			// rien n'est affiché dans aucune ligne.
			currentPos = new int[kb.getAttributeList().size()];
			dt.computeDeepness(-1);
			for(int i=0;i<currentPos.length;i++)
				currentPos[i]=0;
			drawTree(g,dt);
			
		}
	}

	private void drawTree(Graphics g,DecisionTree dt) {
		//int height = maxHeight - dt.getHeight();
		int deep = dt.getDeep();
		int squareNumber = currentPos[deep];
		if(dt instanceof InnerDecisionTree){
			
			g.setColor(Color.BLACK);
			g.drawOval(10+(squareNumber*collSize),10+(deep*rowSize), ovalWidth, ovalHeight);
			System.out.println("Inner att:"+((InnerDecisionTree)dt).getAttribute()+" nb fils :"+((InnerDecisionTree)dt).getArrows().size());	
			int numberOfArrows =0;
			for(Arrow arr: ((InnerDecisionTree) dt).getArrows())
				if(arr.getTarget()!=null)
					numberOfArrows = numberOfArrows +1;
			drawEdge(g,(InnerDecisionTree)dt,numberOfArrows,deep);
			currentPos[deep] = currentPos[deep]+1;
			for(Arrow arr: ((InnerDecisionTree) dt).getArrows())
				drawTree(g,arr.getTarget());
		}else{
			g.setColor(Color.RED);
			g.drawOval(10+(squareNumber*collSize),10+(deep*rowSize), ovalWidth, ovalHeight);
			System.out.println("leaf , value: "+ ((Leaf)dt).getDecision());
			currentPos[deep] = currentPos[deep]+1;
			
		}
		
	}
	
	private void drawEdge(Graphics g, InnerDecisionTree dt2, int numberOfArrows, int deep) {
		int squareNumber = currentPos[deep];
		for(int i=0;i<numberOfArrows;i++){
			int squareNumberSon = currentPos[deep+1]+i;
			g.drawLine(10+(squareNumber*collSize + ovalWidth/2), 10+(deep*rowSize + ovalHeight),
					10+(squareNumberSon*collSize+ovalWidth/2) , 10+((deep+1)*rowSize));
			//g.drawString(dt2.getArrows().get(i).getValue().toString(), x, y);

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
