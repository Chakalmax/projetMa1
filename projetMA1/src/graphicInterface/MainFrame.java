package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import KnowledgeBase.KnowledgeBase;

public class MainFrame extends JFrame{
	
	private JSplitPane splitPaneRight;
	private JSplitPane splitPaneLeft;
	private JSplitPane splitPaneAll;
	private MenuBar menuBar;
	private KnowledgeBase kb;
	private PseudoCodePanel codePanel;
	private InfoPanel infoPanel;
	private TreePanel treePanel;
	private BoutonPanel boutonPanel;

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
		this.setSize(800, 800);
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
		treePanel = new TreePanel();
		boutonPanel = new BoutonPanel();
		configureBoutonPanel();
		//creation des SplitPanel
		splitPaneRight = new JSplitPane();
		splitPaneLeft = new JSplitPane();
		splitPaneAll = new JSplitPane();
		
		
        
        getContentPane().setLayout(new GridLayout());  
        getContentPane().add(splitPaneAll);  
        // split Pane All est dans toute la Frame et est coupé à la Verticale

        splitPaneRight.setOrientation(JSplitPane.VERTICAL_SPLIT);  
        splitPaneRight.setDividerLocation(525);                    
        splitPaneRight.setTopComponent(codePanel);  
        splitPaneRight.setBottomComponent(infoPanel);          
        
        splitPaneLeft.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPaneLeft.setDividerLocation(650);
        splitPaneLeft.setTopComponent(treePanel);
        splitPaneLeft.setBottomComponent(boutonPanel);
        
        splitPaneAll.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPaneAll.setDividerLocation(400);
        splitPaneAll.setLeftComponent(splitPaneLeft);
        splitPaneAll.setRightComponent(splitPaneRight);
        

        // MenuBar
        menuBar = new MenuBar(this);
		this.setJMenuBar(menuBar);
        
	}

	private void configureBoutonPanel() {
		// TODO Auto-generated method stub
		
	}
	
	public KnowledgeBase getKB(){
		return this.kb;
	}
}
