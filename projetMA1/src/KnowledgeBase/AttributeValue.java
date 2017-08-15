package KnowledgeBase;

/**
 * This class represents the value for a particular attribute in a sample.
 * @author mvh
 * @version 1.0
 */
public class AttributeValue<T> {

	private T value;
	
	public AttributeValue(T value){
		this.setValue(value);
	}


	/**
	 * @return the value fo the AttributeValue.
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value 
	 * 		the new value for the AttributeValue
	 */
	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		return value.toString();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object ob){
		if((this.value instanceof Boolean))
				return ob instanceof AttributeValue &&
						((AttributeValue)ob).getValue().equals(value) &&
						((AttributeValue)ob).getValue().toString()==value.toString();
		else if(this.value instanceof String){
			return ob instanceof AttributeValue &&
					((AttributeValue) ob).getValue() instanceof String &&
					((String)((AttributeValue)ob).getValue()).compareTo((String)value)==0;
		}
		else{ //si numerique, alors on doit faire gaffe aux représentation virgule flottante
			float epsilon = (float) 0.000001;
				return (ob instanceof AttributeValue) &&  ((AttributeValue)ob).getValue() instanceof Float
						&& (float)((AttributeValue)ob).getValue() < (float)this.getValue() + epsilon 
						&& (float)((AttributeValue)ob).getValue() > (float)this.getValue() - epsilon;
		}
	}

	public int compareTo(AttributeValue<?> attributeValue) {
		if(value instanceof Float&& attributeValue.getValue() instanceof Float){
			float val = (float) attributeValue.getValue();
			float epsilon = (float) 0.000000000001;
			if((float)value < val+epsilon && (float)value > val-epsilon)
				return 0;
			if((float)value > val+epsilon)
				return 1;
			else
				return -1;
		}else{
			if(this.equals(attributeValue))
				return 0;
			else
				return 1;
		}
	}
}
