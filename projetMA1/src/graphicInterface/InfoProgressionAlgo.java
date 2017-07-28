package graphicInterface;

import KnowledgeBase.KnowledgeBase;

/**
 * This class is a Singleton, with bunch of Information about the code.
 * @author mvh
 * @version 1.0
 */
public class InfoProgressionAlgo {
 
		private KnowledgeBase kb;
		
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
	

}
