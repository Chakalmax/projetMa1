package KnowledgeBase;

import java.util.ArrayList;
/**
 * A Sample is a list of value for every Attribute of the KnowledgeBase(= a record). The classValue is put 
 * in another variable. So everychange in the list(size or anything) won't affect the class
 * @author mvh
 * @version 1.0
 */
public class Sample extends ArrayList<AttributeValue<?>>{

	/**
	 * Value of the class for this Sample.
	 */
	AttributeValue<?> classValue;
	
	public Sample(){
		super();
	} 
	
	public Sample(AttributeValue<?> classValue){
		this.classValue=classValue;
	}
	
	/**
	 * If the classValue is not empty, you need to add 1 to the size
	 * @return the size of the Sample
	 */
	public int size(){
		return classValue==null ? super.size() : super.size()+1;
	}

	/**
	 * @return the classValue of this Sample
	 */
	public AttributeValue<?> getClassValue() {
		return classValue;
	}

	/**
	 * @param classValue 
	 * 		the classValue to set for the Sample
	 */
	public void setClassValue(AttributeValue<?> classValue) {
		this.classValue = classValue;
	}

	
	
	

}
