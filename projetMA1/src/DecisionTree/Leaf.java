package DecisionTree;

import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;
import KnowledgeBase.Sample;

public class Leaf extends DecisionTree{

	AttributeValue<?> classe;
	public Leaf(KnowledgeBase kb, AttributeValue<?> classe) {
		super(kb);
		this.classe = classe;
	}

	@Override
	public AttributeValue<?> getDecision(Sample samp) {
		return classe;
	}
	
	public AttributeValue<?> getDecision(){
		return classe;
	}
	
	@Override
	public String toString(){
		return "Feuille atteinte : kb.size : " + kb.getSamples().size() + " classe : " + this.classe.toString() ;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 1;
	}

}
