package graphicInterface;


import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import DTL.DTLAlgo;
import DecisionTree.DecisionTree;
import KnowledgeBase.KnowledgeBase;

public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JSplitPane splitPaneRight;
	private JSplitPane splitPaneAll;
	private MenuBar menuBar;
	private KnowledgeBase kb;
	private PseudoCodePanel codePanel;
	private InfoPanel infoPanel;
	private DrawPanel drawPanel;
	private BoutonPanel boutonPanel;
	private Thread thread;

	public static void main(String[] args)
	  {
	    SwingUtilities.invokeLater(new Runnable()
	    {
	      public void run()
	      {
	        new MainFrame();
	      }
	    });
	  }
	
	public MainFrame(){
		
		this.setTitle("DTL learning tool");
		this.setSize(1400, 800);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    addThings();
	    this.setResizable(true);
	    this.setVisible(true);
	   
	}

	private void addThings() {
		
		// création des Panel
		codePanel = new PseudoCodePanel();
		infoPanel = new InfoPanel();
		drawPanel = new DrawPanel();
		boutonPanel = new BoutonPanel(this);
		//creation des SplitPanel
		splitPaneRight = new JSplitPane();
		splitPaneAll = new JSplitPane();
		
		JPanel leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new BorderLayout());
		leftBottomPanel.add(infoPanel, BorderLayout.CENTER);
		leftBottomPanel.add(boutonPanel,BorderLayout.SOUTH);
        
        getContentPane().setLayout(new GridLayout());  
        getContentPane().add(splitPaneAll);  
        // split Pane All est dans toute la Frame et est coupé à la Verticale

        splitPaneRight.setOrientation(JSplitPane.VERTICAL_SPLIT);  
        splitPaneRight.setDividerLocation(325);                    
        splitPaneRight.setTopComponent(codePanel);  
        splitPaneRight.setBottomComponent(leftBottomPanel);          
        
        
        
        splitPaneAll.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneAll.setDividerLocation(800);
        splitPaneAll.setLeftComponent(drawPanel);
        splitPaneAll.setRightComponent(splitPaneRight);
        

        // MenuBar
        menuBar = new MenuBar(this);
		this.setJMenuBar(menuBar);
        
	}

	
	
	public KnowledgeBase getKB(){
		return this.kb;
	}
	
	public void setKB(KnowledgeBase kb){
		this.kb = kb;
	}

	public PseudoCodePanel getCodePanel() {
		return this.codePanel;
	}

	public void startAlgo() {
		if(this.kb !=null){
			MainFrame mf = this;
		this.thread = new AlgoThread(mf);
		thread.start();
		}
		else
			JOptionPane.showMessageDialog(null, "Chargez une base de connaissance","Error", JOptionPane.ERROR_MESSAGE);
		
	}
	
	private class AlgoThread extends Thread{
		
		
		AlgoThread(MainFrame mf){
			super(new RunnableThread(mf));
		}
		
		
	}
	
	private class RunnableThread implements Runnable{

		MainFrame mf;
		
		public RunnableThread(MainFrame mf) {
			super();
			this.mf = mf;
		}

		@Override
		public void run()
        { 
			Options.cancelled = false;
			while(!Options.cancelled)
         	try {
        	  Options.running = true;
        	  DTLAlgo.firstIt = true;
        	  DecisionTree dtalgo = DTLAlgo.Init_DTL_algo_StepByStep(kb, Options.error, Options.gainStrategy, mf);
        	  Options.running = false;
        	  boutonPanel.changeBoutonToStart();
        	  boutonPanel.repaint();
        	  mf.drawPanel.setDt(dtalgo);
        	  Options.cancelled = true;
			
         	}catch(InterruptedException e){
				e.printStackTrace();
				System.out.println("Start Algo failed");
			}
			System.out.println("un thread est fini");
        }
		
		
	}

	public void restartAlgo(){
		this.thread.interrupt();
		Options.cancelled = true;
		Options.stop = false;
		infoPanel.restart();
		drawPanel.restart();
		codePanel.restart();
		
		this.startAlgo();	
	}
	
	public void resetPanels(){
	}
	
	public void HardReset(){
		this.removeAll();
		this.addThings();
	}


	public InfoPanel getInfoPanel() {
		return this.infoPanel;
	}

	public DrawPanel getTreePanel() {
		return this.drawPanel;
	}
}