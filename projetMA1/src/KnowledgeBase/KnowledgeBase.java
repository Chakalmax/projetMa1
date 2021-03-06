package KnowledgeBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * An KnowledgeBase(KB) is a group of samples and contain the Attribute associated at these samples.
 * It alse have a name and some others informations. It provides several usefull fonction.
 * @author mvh
 * @version 1.0
 *
 */
public class KnowledgeBase {

	/**
	 * The name of KnowledgeBase
	 */
	private String name;
	/**
	 * Every Sample of the KnowledgeBase
	 */
	private ArrayList<Sample> samples;
	/**
	 * List of the attribute of the KnowledgeBase
	 */
	private ArrayList<Attribute> attributeList;
	/**
	 * A description of the KnowledgeBase (what it does). Could be Empty.
	 */
	private String description;
	
	public KnowledgeBase(String name,ArrayList<Sample> samples,ArrayList<Attribute> attributeList){
		this.name = name;
		this.samples = samples;
		this.attributeList = attributeList;
		this.description = "No desription";
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
	 * @param name the new name for the KnowledgeBase
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
	 * @param samples the new samples
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
	 * @param attributeList the new attributeList
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
	 * @param description the new description
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
		ArrayList<AttributeValue<?>> possibleValue = getClassAttribute().getPossibleAttributeValue();
		ArrayList<Integer> counter = countClass();
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
			AttributeValue<?> val = possibleValue.get(bestIndex.get(nombreAleatoire));
			
			return val;
		
		}
		
		return null;
	}


	/**
	 * This function is able to count iteration of values for the class.
	 * @return iteration for values for the class attribute
	 */
	public ArrayList<Integer> countClass() {
		return count(getIndexClass());
	}
	
	/**
	 * This function is able to count iteration of values for a specific attribute.
	 * @param index , index of the attribute
	 * @return iteration for values for specific attribute
	 */
	public ArrayList<Integer> count(int index) {
		ArrayList<AttributeValue<?>>possibleValue = attributeList.get(index).getPossibleAttributeValue();
		return count(index,possibleValue);
	}
	
	/**
	 * This function is able to count iteration of values for a specific attribute with specific value.
	 * @param index
	 * @param possibleValue
	 * @return
	 */
	public ArrayList<Integer> count(int index,ArrayList<AttributeValue<?>>possibleValue){
		ArrayList<Integer> counter = init1D(possibleValue);
		for(Sample samp: samples){
			AttributeValue<?> val = samp.get(index);
			for(int i=0;i<possibleValue.size();i++)
				if(val.equals(possibleValue.get(i)))
					counter.set(i,counter.get(i)+1);
			
		}	
		return counter;
	}
	
	/**
	 * init the counter
	 * @see count
	 * @param possibleValue
	 * @return
	 */
	private  ArrayList<Integer> init1D(ArrayList<AttributeValue<?>> possibleValue) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(int i=0;i<possibleValue.size();i++)
			arr.add(0);
		return arr;
	}
	
	private  ArrayList<Integer> init1D(int size) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(int i=0;i<size;i++)
			arr.add(0);
		return arr;
	}

	/**
	 * This function is able to count iteration of values for the class depending on the value of another class.
	 * So this make a Two dimensionnal table of counter.
	 * @param possibleValue for
	 * @param index
	 * @param possibleValueClass
	 * @param indexClass
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> count2D(ArrayList<AttributeValue<?>> possibleValue, int index,
			ArrayList<AttributeValue<?>> possibleValueClass, int indexClass){
		ArrayList<ArrayList<Integer>> counter = new ArrayList<ArrayList<Integer>>();
		if(this.attributeList.get(index).getType()!=TypeAttribute.Numerical){
			counter = init2D(possibleValue.size(),possibleValueClass.size());
			for(Sample samp: samples){
				AttributeValue<?> val = samp.get(index);
				AttributeValue<?> valClass = samp.get(indexClass);
				incrementNonNumerical(counter,val,valClass,possibleValue,possibleValueClass);
				}
		}
		return counter;
	}
	
	public ArrayList<ArrayList<Integer>> count2D(int index, int indexClass){
		return count2D(attributeList.get(index).getPossibleAttributeValue(),index,attributeList.get(indexClass).getPossibleAttributeValue(),indexClass);
	}
	

	private  ArrayList<ArrayList<Integer>> incrementNonNumerical(ArrayList<ArrayList<Integer>> counter, AttributeValue<?> val, AttributeValue<?> valClass,
			ArrayList<AttributeValue<?>> possibleValue, ArrayList<AttributeValue<?>> possibleValueClass) {
		for(int i=0;i<possibleValue.size();i++)
			for(int j=0;j<possibleValueClass.size();j++)
				if(val.equals(possibleValue.get(i))&& valClass.equals(possibleValueClass.get(j)))
				{
					ArrayList<Integer> tmp = counter.get(i);
					tmp.set(j, tmp.get(j)+1);
					counter.set(i,tmp);
					return counter;
				}
		return counter;	
	}

	/**
	 * Initialize the counter2D(empty) with 0 and the size is size1*size2
	 * @param counter
	 * @param size1
	 * @param size2
	 */
	public  ArrayList<ArrayList<Integer>> init2D(int size1, int size2) {
		ArrayList<ArrayList<Integer>> counter = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<size1;i++){
			ArrayList<Integer> inArray = new ArrayList<Integer>();
			for(int j=0;j<size2;j++)
				inArray.add(0);
			counter.add(inArray);
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
	 * Return the index of the parameter Attribute in the attributeList
	 * @param attribute
	 * @return index of the attribute in the attributeList
	 */
	public int getIndexOfAttribute(Attribute attribute){
		int count =0;
			for(Attribute att: attributeList)
				if(att.getName().compareToIgnoreCase(attribute.getName())==0)
					return count;
				else
					count++;
		return count;
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
	public boolean AllSameClass(double error){// error rate as %
		Attribute label = getClassAttribute();
		if(label != null){
		ArrayList<Integer> counter = countClass();
		int max = Collections.max(counter);
		int numberElem = samples.size();
		return (numberElem - ((error/100)*numberElem) <= max);
		}
		return false;
	}
	
	/**
	 * This function split the current knowledgeBase to get a knowledgeBase where Attribute "a" get the value "attVal" 
	 * @param attIndex index of the attribute
	 * @param attVal attributeValue 
	 * @return a KB where Attribute with index attIndex as the value attVal
	 */
	public KnowledgeBase Split(int attIndex, AttributeValue<?> attVal){
		ArrayList <Sample> newSamples = new ArrayList<Sample>();
		for(Sample samp: samples)
			if(samp.get(attIndex).equals(attVal))
				newSamples.add(samp);
		return new KnowledgeBase(name, newSamples, attributeList);
	}

	/**
	 * Same as count but for NumericalAttribute
	 * @param attributeValue
	 * @param attIndex 
	 * @return
	 */
	public ArrayList<Integer> countNumerical(AttributeValue<?> attributeValue, int index) {
		ArrayList<Integer> counter = init1D(2);
		for(Sample samp: samples){
			if(samp.get(index).compareTo(attributeValue)!=1)
				counter.set(0,counter.get(0)+1);
			else
				counter.set(1,counter.get(1)+1);
			
		}	
		return counter;
	}

	public ArrayList<ArrayList<Integer>> count2DNumeric(AttributeValue<?> attributeValueSplit, int attIndex){
		return count2DNumeric(attIndex, getIndexClass() ,attributeValueSplit);
	}
	
	public ArrayList<ArrayList<Integer>> count2DNumeric(int attIndex, int indexClass,
			AttributeValue<?> attributeValueSplit) {
		ArrayList<AttributeValue<?>> possibleValueClass = attributeList.get(indexClass).getPossibleAttributeValue();
		ArrayList<ArrayList<Integer>> counter = new ArrayList<ArrayList<Integer>>();
		counter = init2D(2,possibleValueClass.size());
		for(Sample samp: samples){
			AttributeValue<?> valClass = samp.get(indexClass);
			AttributeValue<?> attval = samp.get(attIndex);
			counter = incrementNumerical(counter,attributeValueSplit,attval,valClass,possibleValueClass);
		}
		return counter;
	}
	
	
	
	private ArrayList<ArrayList<Integer>> incrementNumerical(ArrayList<ArrayList<Integer>> counter, AttributeValue<?> attributeValueSplit,
			AttributeValue<?> attVal, AttributeValue<?> valClass, ArrayList<AttributeValue<?>> possibleValueClass) {
		for(int i=0;i<possibleValueClass.size();i++){
			if(possibleValueClass.get(i).equals(valClass)){
				if(attVal.compareTo(attributeValueSplit)==1){
					ArrayList<Integer> tmp = counter.get(1);
					tmp.set(i, tmp.get(i)+1);
					counter.set(1,tmp);
				}else{
					ArrayList<Integer> tmp = counter.get(0);
					tmp.set(i, tmp.get(i)+1);
					counter.set(0,tmp);
				}
				return counter;	}
					
			}
		return counter;
				
	}

	public KnowledgeBase SplitNumerical(int attIndex, AttributeValue<?> attVal, boolean lower) {
		ArrayList<Sample> newSamples = new ArrayList<Sample>();
		for(Sample samp:samples)
			if(samp.get(attIndex).compareTo(attVal)==1 && !lower)
				newSamples.add(samp);
			else if(samp.get(attIndex).compareTo(attVal)!=1 && lower)
				newSamples.add(samp);
		return new KnowledgeBase(name, newSamples, attributeList);
	}

	/**
	 * Largeur max que peut prendre l'arbre une fois cr�er
	 * @return
	 */
	public int getLargeurMax() {
		int result=0;
		for(Attribute att: attributeList)
			result = result + att.getNumberValue();
		return result;
	}

	public int[] getNumAttVal() {
		int [] result = new int[attributeList.size()];
		int i=0;
		for(Attribute att: attributeList){
			result[i] = att.getNumberValue();
			i++;
		}
		 Arrays.sort(result);
		return result;
	}
	
	@Override
	public boolean equals(Object ob){
		boolean result = false;
		 if(ob instanceof KnowledgeBase){
			result = true;
			if(((KnowledgeBase)ob).getName().compareTo(this.name)==0){
				if(!sameAttList(((KnowledgeBase)ob).getAttributeList()) || ! sameSamples( ((KnowledgeBase)ob).getSamples() ))
					result = false;
			}else
				result = false;
		 }
		 return result;
	}
	private boolean sameSamples(ArrayList<Sample> samples2) {
		return samples2.containsAll(this.samples) && samples.containsAll(samples2);
	}

	private boolean sameAttList(ArrayList<Attribute> attributeList2) {
		return attributeList2.containsAll(this.attributeList) && attributeList.containsAll(attributeList2);
	}

}
