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
		return 1;
	}


	@Override
	public boolean equals(Object ob) {
		return (ob instanceof Leaf && this.classe.equals(((Leaf) ob).getDecision()) && this.kb.equals(((Leaf)ob).getKb()));
	}

	@Override
	public void computeDeepness(int parentDeepness) {
		this.setDeep(parentDeepness+1);
		
	}

}
