package DTL;

import DecisionTree.DecisionTree;
import graphicInterface.DrawPanel;
import graphicInterface.InfoPanel;
import graphicInterface.Options;
import graphicInterface.PseudoCodePanel;

public class DTL_Management {
	
	PseudoCodePanel codePanel;
	InfoPanel infoPanel;
	DrawPanel treePanel;
	
	public DTL_Management(PseudoCodePanel codePanel, InfoPanel infoPanel,DrawPanel treePanel){
		this.codePanel=codePanel;
		this.infoPanel = infoPanel;
		this.treePanel = treePanel;
	}
	
	  public void setLineToGreen()
	  {
	    if (this.codePanel.isVisible())
	    {
	      this.codePanel.setGreenHighLight();
	      sleep();
	    }
	  }
	  
	  public void setLineToRed()
	  {
	    if (this.codePanel.isVisible())
	    {
	      this.codePanel.setRedHighLight();
	      sleep();
	    }
	  }
	  
	  public void setLineToNormal()
	  {
	    if (this.codePanel.isVisible()) 
	      this.codePanel.setNormalHighLight();
	      // pas de sleep quand on revient à la normale
	    
	  }
	  
	  public void goToLine(int i){
		  if(this.codePanel.isVisible()){
			  this.codePanel.setLineToHighLight(i);
			  sleep();
		  }
	  }
	  
	  public void nextLine(){
		  if(this.codePanel.isVisible()){
			  this.codePanel.nextLineToHighLight();
			  sleep();
		  }
	  }

	public void jumpLine(int i) {
		if(this.codePanel.isVisible()){
			this.codePanel.setLineToHighLight(this.codePanel.getLineToHighLight()+i);
			sleep();
		}
		
	}
	
	public int getLine(){
		return this.codePanel.getLineToHighLight();
	}
	
	public void sleep (){
		try {
			Thread.sleep(Options.waitTime);
		} catch (InterruptedException e) {
			System.out.println("Erreur lors du sleep (DTL_MANAGEMENT)");
			e.printStackTrace();
		}
		while(Options.stop){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Erreur lors du sleep (DTL_MANAGEMENT)");
				e.printStackTrace();
			}
		}
	}
	
	public void changeInfoToDisplay(String info){
		infoPanel.setInformation(info);
	}
	
	public void setTree(DecisionTree dT){
		treePanel.setDt(dT);
	}
	
	public void addTree(DecisionTree dT){
		treePanel.addTree(dT);
	}

}
