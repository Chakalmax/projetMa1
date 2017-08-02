package DecisionTree;

import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;
import KnowledgeBase.Sample;
/**
 * Node of the Tree. Abstract because you need to be Leaf or innerNode
 * @author maxim
 *
 */
public abstract class DecisionTree {
	KnowledgeBase kb;
	
	public DecisionTree(KnowledgeBase kb){
		this.kb = kb;
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

	/**
	 * Get the Decision, i.e Classification based on the current Tree Node
	 * @return the class;
	 */
	public abstract AttributeValue<?> getDecision(Sample samp);
	
	public abstract int getHeight();
	
	public abstract int getDeep(DecisionTree dt, int deepParent);
	
	@Override
	public abstract boolean equals(Object ob);

	
	
	
}
