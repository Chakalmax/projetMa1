package DTL;

import DecisionTree.*;
import KnowledgeBase.*;

/**
 * This class is a Singleton, with bunch of Information about the code.
 * @author mvh
 * @version 1.0
 */
public class InfoProgressionAlgo {
 
		private KnowledgeBase kb;
		private DecisionTree dt;
		
		private InfoProgressionAlgo()
		{
			this.kb = null;
		}

		private static InfoProgressionAlgo instance = null;
	 

		public static InfoProgressionAlgo getInstance()
		{			
			if ( instance == null)
			{ 	instance = new InfoProgressionAlgo();	
			}
			return instance;
		}
		
		public KnowledgeBase getKb() {
			return kb;
		}

		public void setKb(KnowledgeBase kb) {
			this.kb = kb;
		}
		
		public DecisionTree getDt(){
			return this.dt;
		}
		
		public void setDt(DecisionTree dt){
			this.dt = dt;
		}
	

}
