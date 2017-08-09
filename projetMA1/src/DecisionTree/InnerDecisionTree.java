package DecisionTree;

import java.util.ArrayList;

import KnowledgeBase.Attribute;
import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;
import KnowledgeBase.Sample;

public class InnerDecisionTree extends DecisionTree {

	Attribute attribute;
	float gain;
	ArrayList<Arrow> arrows;
	public InnerDecisionTree(KnowledgeBase kb) {
		super(kb);
	}
	
	public InnerDecisionTree(KnowledgeBase kb,Attribute attribute,float gain,ArrayList<Arrow> arrows) {
		super(kb);
		this.gain = gain;
		this.attribute= attribute;
		this.arrows = arrows;
	}
	
	public InnerDecisionTree(KnowledgeBase kb,Attribute attribute,float gain) {
		super(kb);
		this.gain = gain;
		this.attribute= attribute;
		arrows = new ArrayList<Arrow>();
	}

	/**
	 * @return the attribute
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the gain
	 */
	public float getGain() {
		return gain;
	}

	/**
	 * @param gain the gain to set
	 */
	public void setGain(float gain) {
		this.gain = gain;
	}

	/**
	 * @return the arrows
	 */
	public ArrayList<Arrow> getArrows() {
		return arrows;
	}

	/**
	 * @param arrows the arrows to set
	 */
	public void setArrows(ArrayList<Arrow> arrows) {
		this.arrows = arrows;
	}
	
	public void addArrow(Arrow arrow){
		this.arrows.add(arrow);
	}

	@Override
	public AttributeValue<?> getDecision(Sample samp) {
		Arrow arr = findGoodArrow(samp);
		return arr.getTarget().getDecision(samp);
	}

	private Arrow findGoodArrow(Sample samp) {
		AttributeValue<?> attvall = samp.get(this.kb.getIndexOfAttribute(this.attribute));
		for(Arrow arr: arrows)
			if(arr.rigthPath(attvall))
				return arr;
		return null;
	}

	@Override
	public String toString(){
		String str = "Attribute : "+ attribute.toString() + "\n";
		str = str + " kb.size() " + kb.getSamples().size() + "\n";
		str = str + " gain : " + gain + " \n";
		/*for(int i=0;i<arrows.size();i++){
			str = str + " arrow n°"+i+ " : " + arrows.get(i).toString() + "\n";
			str = str + arrows.get(i).getTarget().toString() + "\n";
		}*/
		return str;
	}

	@Override
	public int getHeight() {
		int result =1;
		int maxHighChildren =0;
		for(Arrow arr : arrows){
			if(arr.getTarget()!= null && arr.getTarget().getHeight()>maxHighChildren)
				maxHighChildren = arr.getTarget().getHeight();
		}
		return result + maxHighChildren;
	}
	

	@Override
	public boolean equals(Object ob) {
		return (ob instanceof InnerDecisionTree && this.attribute.equals(((InnerDecisionTree) ob).getAttribute()) 
				&& this.kb.equals(((InnerDecisionTree)ob).getKb()));
	}

	@Override
	public void computeDeepness(int parentDeepness) {
		this.setDeep(parentDeepness+1);
		for(Arrow arr: arrows)
			if(arr.getTarget() !=null)
				arr.getTarget().computeDeepness(parentDeepness+1);
		
	}

	@Override
	public int getSize() {
		int result =1;
		for(Arrow arr : arrows)
			if(arr.getTarget() != null)
				result = result + arr.getTarget().getSize();
		return result;
	}

	@Override
	public void addTree(DecisionTree tree) {
		ArrayList<Arrow> arr = findEmptyArrows();
		if(arr.get(0).getTarget() == null)
			arr.get(0).setTarget(tree);
//		if(arr != null)
//			arr.setTarget(tree);
	}

	@Override
	public ArrayList<Arrow> findEmptyArrows() {
		ArrayList<Arrow> list = new ArrayList<Arrow>();
		for(Arrow arr: arrows){
			if(arr.getTarget() != null)
				list.addAll(arr.getTarget().findEmptyArrows());
			else
				list.add(arr);
		}
		return list;
	}

	@Override
	public ArrayList<String> getInfo() {
		ArrayList<String> result = new ArrayList<String>();
		result.add("Noeud interne de l'arbre");
		result.add("Attribut : "+ attribute.getName());
		if(kb.getSamples().size()>1){
			result.add("Il y a "+ kb.getSamples().size()+ " échantillons de la base de connaissances");
			result.add("qui correspondent à ce noeud de l'arbre");
		}
		else{
			result.add("Il y a "+ kb.getSamples().size()+ " échantillon de la base de connaissances");
			result.add("qui correspond à ce noeud de l'arbre");
		}
		result.add("Le gain calculé était de " + this.gain);
		return result;
	}



	
}
