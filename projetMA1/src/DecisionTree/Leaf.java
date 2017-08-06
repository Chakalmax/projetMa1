package DecisionTree;

import java.util.ArrayList;

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
		return "Feuille :"+ "\n "+" Taille de la base de connaissances : " + kb.getSamples().size()+"\n" + " classe : " + this.classe.toString() ;
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
	/**
	 * Get the number of node in the tree. Basically, a leaf had a size of 1.
	 */
	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void addTree(DecisionTree tree) {
		// do nothing, it's normal you can't add a thing on a leaf.
		
	}

	@Override
	public ArrayList<Arrow> findEmptyArrows() {
		return new ArrayList<Arrow>();
	}


	

}
