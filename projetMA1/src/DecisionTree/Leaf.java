package DecisionTree;

import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;

public class Leaf extends Node{

	AttributeValue<?> classe;
	public Leaf(KnowledgeBase kb, AttributeValue<?> classe) {
		super(kb);
		this.classe = classe;
	}

	@Override
	public AttributeValue<?> getDecision() {
		return classe;
	}

}
