package DecisionTree;

import KnowledgeBase.AttributeValue;

public class ArrowNumerical extends Arrow {

	/**
	 * Set to true if the arrow is for value lower OR equals to value.
	 */
	private boolean lower;
	public ArrowNumerical(AttributeValue<Float> value, boolean lower) {
		super(value);
		this.lower = lower;
	}
	
	public ArrowNumerical(AttributeValue<Float> value, DecisionTree tree, boolean lower){
		super(value,tree);
		this.lower = lower;
	}

	public boolean isLower() {
		return lower;
	}

	public void setLower(boolean lower) {
		this.lower = lower;
	}
	
	public boolean rigthPath(AttributeValue<?> valToCompare){
		System.out.println(valToCompare.compareTo(getValue()));
		if(isLower())
			if(valToCompare.compareTo(getValue()) <=0)
				return true;
			else
				return false;
		else
			if(valToCompare.compareTo(getValue())>0)
				return true;
			else
				return false;
	}
	
	@Override
	public String toString(){
		if(isLower())
			return " <= " + getValue().toString();
		else
			return " > " + getValue().toString();
	}
	
	

}
