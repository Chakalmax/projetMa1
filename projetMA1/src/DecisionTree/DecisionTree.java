package DecisionTree;

import java.util.ArrayList;

import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;
import KnowledgeBase.Sample;
/**
 * Node of the Tree. Abstract because you need to be Leaf or innerNode
 * @author maxim
 *
 */
public abstract class DecisionTree {
	protected KnowledgeBase kb;
	private int deep =1;
	
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
	
	public int getDeep(){
		return deep;
	}
	
	public void setDeep(int deep){
		this.deep=deep;
	}
	/**
	 * call it with -1 when the DT is done.
	 * @param parent
	 */
	public abstract void computeDeepness(int parentDeepness);
	
	@Override
	public abstract boolean equals(Object ob);
	
	public abstract int getSize();
	
	public abstract void addTree(DecisionTree tree);
	
	public abstract ArrayList<Arrow> findEmptyArrows();


	
	
	
}
