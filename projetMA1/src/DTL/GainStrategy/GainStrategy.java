package DTL.GainStrategy;

import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;

public interface GainStrategy {

	/**
	 * This function return the gain information if we split the kb parameter with the index
	 * @param kb the current KnowledgeBase
	 * @param attIndex the index of the Attribute
	 * @return the gain
	 */
	public float getGain(KnowledgeBase kb,int attIndex);

	public AttributeValue<Float> getValueBestSplit(KnowledgeBase kb, int a);
	
	public String getName();
}
