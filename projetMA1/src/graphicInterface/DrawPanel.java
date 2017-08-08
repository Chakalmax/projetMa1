package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	static final int colSize = 150;
	static final int ovalWidth = 120;
	static final int ovalHeight=75;
	static ClickNode click;
	
	private ArrayList<Shape> Nodes;
	private ArrayList<DecisionTree> dtNodes;
	public int largeurTree=1;
	
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
			if(currentPos.length>0)
				largeurTree = max(currentPos);
			else
				largeurTree = 1;
			
		}
	}
	
	public Dimension getPreferredSize() {
		System.out.println(dt.getHeight());
		System.out.println(largeurTree);
		System.out.println(largeurTree*colSize);
		System.out.println(50+dt.getHeight()*rowSize);
        return new Dimension(largeurTree*colSize,50+dt.getHeight()*rowSize);
    }
	
	private int max(int[] currentPos) {
		int index =0;
		int max = currentPos[index];
		for(int i=1;i<currentPos.length;i++)
			if(currentPos[i]>max)
				index = i;
		return index;
	}

	private class ClickNode extends MouseAdapter{

		@Override
		public void mouseClicked(java.awt.event.MouseEvent me) {
			super.mouseClicked(me);
            for (int i=0; i<Nodes.size();i++) {
            	Shape s = Nodes.get(i);
                if (s.contains(me.getPoint())) {
                    DecisionTree node = dtNodes.get(i);
                    new InfoNodeFrame(node);
                }
			}
         }		
	}
	//
// INFO WHEN YOU CLICK ON A NODE
	//
	private class InfoNodeFrame extends JFrame{

		
		private static final long serialVersionUID = 1L;
		DecisionTree node;
		JPanel panel;

		
		
		public InfoNodeFrame(DecisionTree node) {
			this.setTitle("Information sur le noeud");
			this.node = node;
			this.setSize(400, 300);
		    this.setLocationRelativeTo(null);		
			addThingsInfo();		
		    this.setResizable(true);
		    this.setVisible(true);
		}
		
		private void addThingsInfo(){
			this.panel = new JPanel();
			PanelText panelText = new PanelText("hello");
			System.out.println("Fini le textPanel");
			JPanel panelBouton = new JPanel();
			System.out.println("Fini le boutounPanel");
			panel.setLayout(new BorderLayout());
			panel.add(panelText, BorderLayout.CENTER);
			JButton button = new JButton("Détail");
			button.addActionListener(new DetailButton());
			panelBouton.add(button);
			panel.add(panelBouton,BorderLayout.SOUTH);
			this.add(panel);
		}
		
		private class PanelText extends JPanel{
			
			private static final long serialVersionUID = 1L;
			public PanelText(String h)
			{
				System.out.println(h);
				repaint();
			}

			public void paintComponent(Graphics g){
				super.paintComponent(g);
				ArrayList<String> output = dt.getInfo();
		    	g.setFont(new Font("Dial",0,15));
				for(int i=0;i<output.size();i++){
					g.drawString(output.get(i), 10, 20 +25*i);
				}
			}
		}
		
		private class DetailButton implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
		    	String[] columns = completeCollumns();
		    	String[][] lines = completeLines();
		    	JPanel pane = new JPanel();
		    	JTable table = new JTable(lines, columns);
		    	pane.add(new JScrollPane(table));	
		    	frame.add(pane);
		    	frame.pack();
		    	frame.setTitle("detail");  
		    	frame.setResizable(true);
		        frame.setVisible(true);
			}

			private String[][] completeLines() {
				int numberColl = node.getKb().getAttributeList().size()+1;
				int numberRow = node.getKb().getSamples().size();
				String [][] tab = new String [numberRow][numberColl];
				for(int i=0;i<numberRow;i++){
					Sample samp = node.getKb().getSamples().get(i);
					tab[i][0]= ""+i;
					for(int j=1;j<numberColl;j++)
						tab[i][j] = samp.get(j-1).toString();
				}
				return tab;
			}

			private String[] completeCollumns() {
				int numberAttribute = node.getKb().getAttributeList().size();
				String [] coll = new String[numberAttribute+1] ; 
				coll[0] = "n°";
				for(int i=1;i<numberAttribute+1;i++)
					coll[i]=node.getKb().getAttributeList().get(i-1).getName();
				return coll;
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
			Ellipse2D.Double node = new Ellipse2D.Double(10+(squareNumber*colSize), 10+(deep*rowSize), ovalWidth, ovalHeight);
			Nodes.add(node);
			dtNodes.add((InnerDecisionTree)dt);
			g2d.draw(node);
			int numberOfArrows =0;
			for(Arrow arr: ((InnerDecisionTree) dt).getArrows())
				if(arr.getTarget()!=null)
					numberOfArrows = numberOfArrows +1;
			drawEdge(g,(InnerDecisionTree)dt,numberOfArrows,deep);
			g.drawString(((InnerDecisionTree)dt).getAttribute().getName(),10+(squareNumber*colSize)+30, 10+(deep*rowSize)+ovalHeight/2);
			currentPos[deep] = currentPos[deep]+1;
			for(Arrow arr: ((InnerDecisionTree) dt).getArrows())
				if(arr.getTarget()!=null)
				drawTree(g,arr.getTarget(),g2d);
		}else{
			g.setColor(Color.RED);
			Ellipse2D.Double node = new Ellipse2D.Double(10+(squareNumber*colSize),10+(deep*rowSize), ovalWidth, ovalHeight);
			Nodes.add(node);
			dtNodes.add((Leaf)dt);
			g2d.draw(node);
			g.drawString(((Leaf)dt).getDecision().toString(),10+(squareNumber*colSize)+30, 10+(deep*rowSize)+ovalHeight/2);
			currentPos[deep] = currentPos[deep]+1;
			
		}
		
	}
	
	private void drawEdge(Graphics g, InnerDecisionTree dt2, int numberOfArrows, int deep) {
		int squareNumber = currentPos[deep];
		for(int i=0;i<numberOfArrows;i++){
			int squareNumberSon = currentPos[deep+1]+i;
			int x1= 10+(squareNumber*colSize + ovalWidth/2);
			int y1 = 10+(deep*rowSize + ovalHeight);
			int x2 =10+(squareNumberSon*colSize+ovalWidth/2);
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

	public void restart() {
		this.dt = null;
		repaint();
		
	}
}
