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
		System.out.println("DomiClass:" + kb.getDominantClass() + "indexClass " + kb.getIndexClass() + "sample : " + kb.getSamples());
		return classe;
	}
	
	@Override
	public String toString(){
		System.out.println(kb.getSamples().get(0));
		return "Feuille atteinte : kb.size : " + kb.getSamples().size() + " classe : " + this.classe.toString() ;
	}

}
