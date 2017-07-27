package graphicInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import KnowledgeBase.KnowledgeBase;

public class MainFrame extends JFrame{
	
	private JSplitPane splitPane;
	private MenuBar menuBar;
	private KnowledgeBase kb;
	private PseudoCodePanel codePanel;
	private InfoPanel infoPanel;

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
		menuBar = new MenuBar();
		this.setJMenuBar(menuBar);
		codePanel = new PseudoCodePanel();
		infoPanel = new InfoPanel();
		splitPane = new JSplitPane();

		
        // the contentPane is the container that holds all our components
        getContentPane().setLayout(new GridLayout());  // the default GridLayout is like a grid with 1 column and 1 row,
        // we only add one element to the window itself
        getContentPane().add(splitPane);  
     // let's configure our splitPane:
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  
        splitPane.setDividerLocation(525);                    
        splitPane.setTopComponent(codePanel);                  // at the top we want our "topPanel"
        splitPane.setBottomComponent(infoPanel);            // and at the bottom we want our "bottomPanel"
        

        
	}
}
