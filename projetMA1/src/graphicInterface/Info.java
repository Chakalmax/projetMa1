package graphicInterface;

import KnowledgeBase.KnowledgeBase;

/**
 * This class is a Singleton, with bunch of Information about the code.
 * @author mvh
 * @version 1.0
 */
public class Info {
 
		KnowledgeBase kb;
		
		private Info()
		{
			this.kb = null;
		}

		private static Info instance = null;
	 

		public static Info getInstance()
		{			
			if ( instance == null)
			{ 	instance = new Info();	
			}
			return instance;
		}
		
		/**
		 * @return the kb
		 */
		public KnowledgeBase getKb() {
			return kb;
		}

		/**
		 * @param kb the kb to set
		 */
		public void setKb(KnowledgeBase kb) {
			this.kb = kb;
		}
	

}
