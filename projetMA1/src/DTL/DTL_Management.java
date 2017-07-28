package DTL;

import graphicInterface.PseudoCodePanel;

public class DTL_Management {
	
	PseudoCodePanel codePanel;
	
	public DTL_Management(PseudoCodePanel codePanel){
		this.codePanel=codePanel;
	}
	
	  public void setLineToGreen()
	  {
	    if (this.codePanel.isVisible())
	    {
	      this.codePanel.setGreenHighLight();
	    }
	  }
	  
	  public void setLineToRed()
	  {
	    if (this.codePanel.isVisible())
	    {
	      this.codePanel.setRedHighLight();
	    }
	  }
	  
	  public void setLineToNormal()
	  {
	    if (this.codePanel.isVisible())
	    {
	      
	      this.codePanel.setNormalHighLight();
	    }
	  }

}
