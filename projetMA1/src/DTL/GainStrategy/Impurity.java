package DTL.GainStrategy;

import java.util.ArrayList;

import KnowledgeBase.Attribute;
import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;
import KnowledgeBase.Sample;
import KnowledgeBase.TypeAttribute;
/**
 * Impurity is the mesure of "how not homogene the class value is". Entropy & Gini are Impurity.
 * @author mvh
 * @version 1.0
 */
public abstract class Impurity implements GainStrategy {
	String detail ="Pas de detail";
	
	@Override
	public float getGain(ArrayList<ArrayList<Integer>> count2D, ArrayList<Integer> count1D, int kbSize){
		detail = "";
		float ImpurityClass = calculImpurity(kbSize,count1D);
		detail = detail + "-[";
		float Impurity2D = calculImpurity2D(kbSize,count2D);
		detail = detail + "]";
		return ImpurityClass - Impurity2D;
	}

	
	private float calculImpurity2D(int kbSize, ArrayList<ArrayList<Integer>> count2d) {
		float impurity =0;
		boolean firstIt = true;
		for(int i=0;i<count2d.size();i++){
			if(!firstIt)
				detail = detail + "+";
			firstIt = false;
			float pv = (float)sumList(count2d.get(i))/kbSize;
			detail = detail + "(" + sumList(count2d.get(i)) + "/" + kbSize+")*(";
			float impurityLine = calculImpurityForValue(count2d.get(i),kbSize);
			detail = detail + ")";
			impurity = impurity + pv*impurityLine; 
		}
		return impurity;
	}

	

	private float calculImpurityForValue(ArrayList<Integer> countValue,int kbSize) {
		int countValueSize = sumList(countValue);
		return calculImpurity(countValueSize,countValue);
	}


	@Override
	public float getGain(KnowledgeBase kb, int attIndex) {
		Attribute att = kb.getAttributeList().get(attIndex);
		if(att.getType()== TypeAttribute.Numerical)
			return calculGainNumerical(kb,attIndex);
		else
			return calculGainNonNumerical(kb,attIndex);
	}
	
	@Override
	public AttributeValue<Float> getValueBestSplit(KnowledgeBase kb, int attIndex){
		ArrayList<AttributeValue<Float>> possibleValue =findPossibleValueNumerical(kb,attIndex);
		ArrayList<ArrayList<ArrayList<Integer>>> multiple_counters = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i=0;i<possibleValue.size()-1;i++)
			multiple_counters.add(kb.count2DNumeric(possibleValue.get(i),attIndex));
		int indexBestSplit = find_bestSplit(kb,attIndex,multiple_counters);
		return possibleValue.get(indexBestSplit);
	}

	/**
	 * Compute the gain of a Non Numerical Attribute (i.e Nominal or Boolean) @see Type
	 * @param kb the KnowledgeBase where you compute the gain
	 * @param attIndex the index of the Attribute in the KnowledgeBase
	 * @return the gain of the attribute(index : attIndex) in the kb
	 */
	private float calculGainNonNumerical(KnowledgeBase kb, int attIndex) {
		ArrayList<Integer> counter1D = kb.count(kb.getIndexClass());
		float giniClass = calculImpurity(kb.getSamples().size(),counter1D);
		ArrayList<ArrayList<Integer>> counter2D = kb.count2D(attIndex, kb.getIndexClass());
		float gini2D = calculImpurity2D(kb,attIndex,counter2D);
		return giniClass - gini2D;
	}
	
	/**
	 * Compute the gain for Numerical Attribute
	 * @param kb
	 * @param attIndex
	 * @return the gain
	 */
	private float calculGainNumerical(KnowledgeBase kb, int attIndex) {
		ArrayList<AttributeValue<Float>> possibleValue =findPossibleValueNumerical(kb,attIndex);
		System.out.println(possibleValue);
		ArrayList<ArrayList<ArrayList<Integer>>> multiple_counters2D = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i=0;i<possibleValue.size()-1;i++){
			multiple_counters2D.add(kb.count2DNumeric(possibleValue.get(i),attIndex));		
		}
		ArrayList<Integer> counter1D = kb.count(kb.getIndexClass());
		float gini1D = calculImpurity(kb.getSamples().size(),counter1D);
		int indexBestSplit = find_bestSplit(kb,attIndex,multiple_counters2D);
		///
		ArrayList<ArrayList<Integer>> counter2D = kb.count2DNumeric(attIndex, kb.getIndexClass(),possibleValue.get(indexBestSplit));
		float gini2D = calculImpurity2DForNumerical(kb,attIndex,counter2D,possibleValue.get(indexBestSplit));
		return gini1D - gini2D;
	}
	
	private ArrayList<AttributeValue<Float>> findPossibleValueNumerical(KnowledgeBase kb, int attIndex){
		ArrayList<AttributeValue<Float>> possibleValue = new ArrayList<AttributeValue<Float>>();
		for(Sample samp: kb.getSamples())
		{
			if(!possibleValue.contains(samp.get(attIndex)))
				possibleValue.add((AttributeValue<Float>) samp.get(attIndex));
		}
		return possibleValue;
	}
	
	/**
	 * Only works if the impurity calculus method reason that the lower the impurity number is, the beter it's. In fact it should always be the case.
	 * This function try to find the best Numerical value (index on the list of possible value).
	 * @param kb
	 * @param attIndex
	 * @param multiple_counters
	 * @return
	 */
	private int find_bestSplit(KnowledgeBase kb, int attIndex, ArrayList<ArrayList<ArrayList<Integer>>> multiple_counters) {
		if(multiple_counters.size() >0){
			float best_Gini = calculImpurity2D(kb.getSamples().size(),multiple_counters.get(0));; // it's a minimum!!
			int index_Best_Gini =0;
			float tmp_gini;
			for(int i=1;i<multiple_counters.size();i++){
				tmp_gini = calculImpurity2D(kb.getSamples().size(),multiple_counters.get(i));
				System.out.println(multiple_counters.get(i) + "  " + tmp_gini + " u " + best_Gini + "   "+ multiple_counters.get(index_Best_Gini));
				if(tmp_gini<best_Gini){
					best_Gini = tmp_gini;
					index_Best_Gini = i;
				}
			}
			return index_Best_Gini;
		}
		return 0;
		
		
	}

	protected float calculImpurity2D(KnowledgeBase kb, int attIndex, ArrayList<ArrayList<Integer>> counter2d){
		float gini=0;
		for(int i=0;i<counter2d.size();i++){
			float pv = (float)sumList(counter2d.get(i))/kb.getSamples().size();
			float giniLine = calculImpurityForValue(kb,attIndex,counter2d.get(i),i);
			gini = gini + pv*giniLine;
		}
	return  gini;
	}

	private float calculImpurity2DForNumerical(KnowledgeBase kb, int attIndex, ArrayList<ArrayList<Integer>> counter2d, AttributeValue<?> attributeValue) {
		float pv1 = (float)sumList(counter2d.get(0))/kb.getSamples().size();
		float giniLine1 = calculImpurityForValueNumerical(kb,attIndex,counter2d.get(0),0,attributeValue,true);
		float pv2 = (float)sumList(counter2d.get(1))/kb.getSamples().size();
		float giniLine2 = calculImpurityForValueNumerical(kb,attIndex,counter2d.get(1),1,attributeValue,false);
		return pv1*giniLine1 +pv2*giniLine2;
	}

	private float calculImpurityForValue(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter, int i) {
		AttributeValue<?> attVal = kb.getAttributeList().get(attIndex).getPossibleAttributeValue().get(i);
		KnowledgeBase kb2 = kb.Split(attIndex, attVal);
		return calculImpurity(kb2.getSamples().size(),counter);
	}

	private float calculImpurityForValueNumerical(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter, int i,
			AttributeValue<?> attributeValue, boolean lower) {
		KnowledgeBase kb2 = kb.SplitNumerical(attIndex, attributeValue,lower);
		return calculImpurity(kb2.getSamples().size(),counter);
	}
	
	protected abstract float calculImpurity(int sizeKb, ArrayList<Integer> counter);
	
	@Override
	public String getDetail(){
		return this.detail;
	}
	
	@Override
	public void resetDetail(){
		this.detail = "Pas de detail";
	}
	
	/**
	 * Sum the element of the list
	 * @param list
	 * @return
	 */
	protected int sumList(ArrayList<Integer> list){
		int result =0;
		for(int i: list){
			result = result + i;
		}
		return result;
		
	}
	
	protected boolean notZero(float pv){
		float epsilon = (float) 0.00000001;
		return (pv>epsilon)||(pv<-epsilon);
	}
}
