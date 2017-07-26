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
		for(int i=0;i<arrows.size();i++){
			str = str + " arrow n°"+i+ " : " + arrows.get(i).toString() + "\n";
			str = str + arrows.get(i).getTarget().toString() + "\n";
		}
		return str;
	}
}
