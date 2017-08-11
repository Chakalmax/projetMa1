package DecisionTree;
import KnowledgeBase.*;
public class Arrow {

	private AttributeValue<?> value;
	private DecisionTree target;
	// should not be used if you don't set target after...
	public Arrow(AttributeValue<?> value){
		this.value = value;
	}
	public Arrow(AttributeValue<?> value, DecisionTree target){
		this.value = value;
		this.target = target;
	}
	public AttributeValue<?> getValue() {
		return value;
	}
	public void setValue(AttributeValue<?> value) {
		this.value = value;
	}
	public DecisionTree getTarget() {
		return target;
	}
	public void setTarget(DecisionTree target) {
		this.target = target;
	}
	
	public boolean rigthPath(AttributeValue<?> valToCompare){
		return valToCompare.equals(this.getValue());
	}
	
	@Override
	public String toString(){
		return "" + value.toString();
	}
}