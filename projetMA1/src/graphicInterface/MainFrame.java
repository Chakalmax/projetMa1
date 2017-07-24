package graphicInterface;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import KnowledgeBase.KnowledgeBase;

public class MainFrame extends JFrame{
	
	MenuBar menuBar;
	KnowledgeBase kb;

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
	    this.setSize(400, 500);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             
	    this.setVisible(true);
	    addThings();
	}

	private void addThings() {
		menuBar = new MenuBar();
		this.setJMenuBar(menuBar);

		
	}
}
