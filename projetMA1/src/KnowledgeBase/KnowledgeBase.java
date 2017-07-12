package KnowledgeBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import KnowledgeBase.Attribute.Type;
/**
 * An KnowledgeBase(KB) is a group of samples and contain the Attribute associated at these samples.
 * It alse have a name and some others informations. It provides several usefull fonction.
 * @author mvh
 * @version 1.0
 *
 */
public class KnowledgeBase {

	private String name;
	private ArrayList<Sample> samples;
	private ArrayList<Attribute> attributeList;
	private String description;
	
	public KnowledgeBase(String name,ArrayList<Sample> samples,ArrayList<Attribute> attributeList){
		this.name = name;
		this.samples = samples;
		this.attributeList = attributeList;
	}
	
	public KnowledgeBase(String name,String description,ArrayList<Sample> samples,ArrayList<Attribute> attributeList){
		this.name = name;
		this.samples = samples;
		this.attributeList = attributeList;
		this.description = description;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the samples
	 */
	public ArrayList<Sample> getSamples() {
		return samples;
	}

	/**
	 * @param samples the samples to set
	 */
	public void setSamples(ArrayList<Sample> samples) {
		this.samples = samples;
	}

	/**
	 * @return the attributeList
	 */
	public ArrayList<Attribute> getAttributeList() {
		return attributeList;
	}

	/**
	 * @param attributeList the attributeList to set
	 */
	public void setAttributeList(ArrayList<Attribute> attributeList) {
		this.attributeList = attributeList;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * This function verify all the samples are correct and full.
	 * @return true if the KB is correct
	 */
	public boolean isCorrect(){
		int size = attributeList.size();
		for(Sample samp: samples){
			if(samp.size()!=size)
				return false;
		}
		return true;
	}
	/**
	 * This function check if there are only one classe in the Attribute List.
	 * @return true if one class, false if more or zero
	 */
	public boolean oneClass(){
		boolean bool = false;
		for(Attribute att : attributeList)
			if(att.isClasse()&&!bool)
				bool = true;
			else return false;
		return bool;
	}
	/**
	 * This function return the dominantClass on the current KB
	 * If there are more than one dominanteClass (equals number) a random one is given.
	 * @return
	 */
	public AttributeValue<?> getDominantClass(){
		Attribute label = getClassAttribute();
		if(label != null){
		ArrayList<String> possibleValue = getClassAttribute().getPossibleValue();
		ArrayList<Integer> counter = new ArrayList<Integer>();
		for(int i=0;i<possibleValue.size();i++)
			counter.add(0);
		counter = count(possibleValue,counter);
		// trouver le max
		int bestValue = counter.get(0);
		ArrayList<Integer> bestIndex = new ArrayList<Integer>();
		bestIndex.add(0);
		for(int i=1;i<counter.size();i++){
			if(bestValue<counter.get(i)){
				bestValue = counter.get(i);
				bestIndex = new ArrayList<Integer>();
				bestIndex.add(i);
			}else if(bestValue == counter.get(i)){
				bestIndex.add(i);
			}// else do nothing
			
		}
		
			Random rand = new Random();
			int nombreAleatoire = rand.nextInt(bestIndex.size());
			String strval = possibleValue.get(nombreAleatoire);
			Type typeLabel = label.getType();
			return createRigthAttributeValue(typeLabel,strval);
		
		}
		
		return null;
	}
	/**
	 * This function creates an AttributeValue knowing a Type and a string value
	 * @param typeLabel
	 * @param strval
	 * @return AttributeValue
	 */
	private AttributeValue<?> createRigthAttributeValue(Type typeLabel, String strval) {
		if(typeLabel == Type.Numerical)
			return new AttributeValue<Float>(Float.parseFloat(strval));		
		else if(typeLabel == Type.Boolean)
			return new AttributeValue<Boolean>(Boolean.parseBoolean(strval));
		else 
			return new AttributeValue<String>(strval);
	}

	/**
	 * This function is able to count iteration of values for the class.
	 * @param possibleValue
	 * @param counter
	 * @return
	 */
	private ArrayList<Integer> count(ArrayList<String> possibleValue, ArrayList<Integer> counter) {
		int index = getIndexClass();
		System.out.println("possible Value(count function)" + possibleValue);
		for(Sample samp: samples){
			String val = samp.get(index).getValue().toString();
			for(int i=0;i<possibleValue.size();i++){
				String str = possibleValue.get(i);
				if(str.equalsIgnoreCase(val))
					counter.set(i,counter.get(i)+1);
			}
		}	
		return counter;
	}
	/**
	 * This function return the Attribute who's a classe/label
	 * @return the classe(Attribute)
	 */
	public Attribute getClassAttribute(){
		for(Attribute att: attributeList)
			if(att.isClasse())
				return att;
		return null;
	}
	/**
	 * Return the index of the classe/label on the AttributeList.
	 * @return index of classe
	 */
	public int getIndexClass(){
		int count = 0;
		for(Attribute att: attributeList)
			if(att.isClasse())
				return count;
			else
				count++;
		return -1;
	}
	/**
	 * This function return true if the KB is empty, i.e if there is 0 samples
	 * @return true if no samples
	 */
	public boolean isEmpty(){
		return samples.isEmpty();
	}
	/**
	 * This function check if all the sample are of the same class
	 * @return true if all is in the same class (at an error rate(percentage!!!))
	 */
	public boolean AllSameClass(float error){// error rate as %
		Attribute label = getClassAttribute();
		if(label != null){
		ArrayList<String> possibleValue = getClassAttribute().getPossibleValue();
		ArrayList<Integer> counter = new ArrayList<Integer>();
		for(int i=0;i<possibleValue.size();i++)
			counter.add(0);
		System.out.println("AllSameClass : possibleValue: " + possibleValue + "  counter  " + counter);
		counter = count(possibleValue,counter);
		System.out.println("Counter : " + counter);
		int max = Collections.max(counter);
		int numberElem = samples.size();
		return (numberElem - ((error/100)*numberElem) <= max);
		}
		return false;
	}
	
}
