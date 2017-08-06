package graphicInterface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

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
	static ClickNode click;
	
	private ArrayList<Shape> Nodes;
	private ArrayList<DecisionTree> dtNodes;
	
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
			if(click != null)
				this.removeMouseListener(click);
			// rien n'est affiché dans aucune ligne.
			currentPos = new int[kb.getAttributeList().size()];
			dt.computeDeepness(-1);
			Nodes = new ArrayList<Shape>();
			dtNodes = new ArrayList<DecisionTree>();
			for(int i=0;i<currentPos.length;i++)
				currentPos[i]=0;
	        Graphics2D g2d = (Graphics2D) g;
			drawTree(g,dt,g2d);
			click = new ClickNode();
			this.addMouseListener(click);
		}
	}
	
	private class ClickNode extends MouseAdapter{

		@Override
		public void mouseClicked(java.awt.event.MouseEvent me) {
			// TODO Auto-generated method stub
			
			super.mouseClicked(me);
            for (int i=0; i<Nodes.size();i++) {
            	Shape s = Nodes.get(i);
                if (s.contains(me.getPoint())) {
                    System.out.println("Clicked a "+s.getClass().getName());
                    DecisionTree node = dtNodes.get(i);
                    String output = node.toString();
                    System.out.println("me.getPoint()" + me.getPoint());
                    //JOptionPane jop1 = new JOptionPane();
                    JOptionPane.showMessageDialog(null,  output , "Information sur le noeud", JOptionPane.INFORMATION_MESSAGE);
                    

                }
			
			
		}
            }

		
		
	}

	private void drawTree(Graphics g,DecisionTree dt, Graphics2D g2d) {
		//int height = maxHeight - dt.getHeight();
		int deep = dt.getDeep();
		int squareNumber = currentPos[deep];
		if(dt instanceof InnerDecisionTree){
			
			g.setColor(Color.BLACK);
			//g.drawOval(10+(squareNumber*collSize),10+(deep*rowSize), ovalWidth, ovalHeight);
			Ellipse2D.Double node = new Ellipse2D.Double(10+(squareNumber*collSize), 10+(deep*rowSize), ovalWidth, ovalHeight);
			Nodes.add(node);
			dtNodes.add((InnerDecisionTree)dt);
			g2d.draw(node);
			int numberOfArrows =0;
			for(Arrow arr: ((InnerDecisionTree) dt).getArrows())
				if(arr.getTarget()!=null)
					numberOfArrows = numberOfArrows +1;
			drawEdge(g,(InnerDecisionTree)dt,numberOfArrows,deep);
			g.drawString(((InnerDecisionTree)dt).getAttribute().getName(),10+(squareNumber*collSize)+30, 10+(deep*rowSize)+ovalHeight/2);
			currentPos[deep] = currentPos[deep]+1;
			for(Arrow arr: ((InnerDecisionTree) dt).getArrows())
				if(arr.getTarget()!=null)
				drawTree(g,arr.getTarget(),g2d);
		}else{
			g.setColor(Color.RED);
			Ellipse2D.Double node = new Ellipse2D.Double(10+(squareNumber*collSize),10+(deep*rowSize), ovalWidth, ovalHeight);
			Nodes.add(node);
			dtNodes.add((Leaf)dt);
			g2d.draw(node);
			g.drawString(((Leaf)dt).getDecision().toString(),10+(squareNumber*collSize)+30, 10+(deep*rowSize)+ovalHeight/2);
			currentPos[deep] = currentPos[deep]+1;
			
		}
		
	}
	
	private void drawEdge(Graphics g, InnerDecisionTree dt2, int numberOfArrows, int deep) {
		int squareNumber = currentPos[deep];
		for(int i=0;i<numberOfArrows;i++){
			int squareNumberSon = currentPos[deep+1]+i;
			int x1= 10+(squareNumber*collSize + ovalWidth/2);
			int y1 = 10+(deep*rowSize + ovalHeight);
			int x2 =10+(squareNumberSon*collSize+ovalWidth/2);
			int y2 = 10+((deep+1)*rowSize);
			g.drawLine(x1, y1, x2 , y2);

			int x3 = (x1+x2)/2;
			int y3 = (y1+y2)/2;
			g.drawString(dt2.getArrows().get(i).getValue().toString(), x3, y3);

		}
		
	}

	public void setDt(DecisionTree dt){
		this.dt = dt;
		updateInfo();
		repaint();
	}

	private void updateInfo() {
		this.kb = dt.getKb();
		this.maxHeight = dt.getHeight();
		this.hauteurMax = kb.getAttributeList().size();
		this.largeurMax = kb.getLargeurMax();
		this.numAttVal = kb.getNumAttVal();
		
	}

	public void addTree(DecisionTree dT2) {
		this.dt.addTree(dT2);
		repaint();
	}
}
