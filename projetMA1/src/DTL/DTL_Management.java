package DTL;

import graphicInterface.InfoPanel;
import graphicInterface.Options;
import graphicInterface.PseudoCodePanel;

public class DTL_Management {
	
	PseudoCodePanel codePanel;
	InfoPanel infoPanel;
	
	public DTL_Management(PseudoCodePanel codePanel, InfoPanel infoPanel){
		this.codePanel=codePanel;
		this.infoPanel = infoPanel;
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
	      // pas de sleep quand on revient � la normale
	    
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
	}
	
	public void changeInfoToDisplay(String info){
		infoPanel.setInformation(info);
	}

}
