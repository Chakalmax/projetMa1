package DTL.GainStrategy;

import java.util.ArrayList;

import KnowledgeBase.Attribute;
import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;
import KnowledgeBase.Sample;
import KnowledgeBase.Type;
/**
 * Impurity is the mesure of "how not homogene the class value is". Entropy & Gini are Impurity.
 * @author mvh
 * @version 1.0
 */
public abstract class Impurity implements GainStrategy {

	@Override
	public float getGain(KnowledgeBase kb, int attIndex) {
		Attribute att = kb.getAttributeList().get(attIndex);
		if(att.getType()== Type.Numerical)
			return calculGainNumerical(kb,attIndex);
		else
			return calculGainNonNumerical(kb,attIndex);
	}

	/**
	 * Compute the gain of a Non Numerical Attribute (i.e Nominal or Boolean) @see Type
	 * @param kb the KnowledgeBase where you compute the gain
	 * @param attIndex the index of the Attribute in the KnowledgeBase
	 * @return the gain of the attribute(index : attIndex) in the kb
	 */
	private float calculGainNonNumerical(KnowledgeBase kb, int attIndex) {
		ArrayList<Integer> counter1D = kb.count(kb.getIndexClass());
		float giniClass = calculImpurity(kb,kb.getIndexClass(),counter1D);
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
		ArrayList<AttributeValue<?>> possibleValue = new ArrayList<AttributeValue<?>>();
		for(Sample samp: kb.getSamples())
		{
			if(!possibleValue.contains(samp.get(attIndex)))
				possibleValue.add(samp.get(attIndex));
		}
		ArrayList<ArrayList<Integer>> multiple_counters = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<possibleValue.size()-1;i++)
			multiple_counters.add(kb.countNumerical(possibleValue.get(i),attIndex));
		ArrayList<Integer> counter1D = kb.count(kb.getIndexClass());
		float gini1D = calculImpurity(kb,kb.getIndexClass(),counter1D);
		int indexBestSplit = find_bestSplit(kb,attIndex,multiple_counters);
		///
		ArrayList<ArrayList<Integer>> counter2D = kb.count2DNumeric(attIndex, kb.getIndexClass(),possibleValue.get(indexBestSplit));
		float gini2D = calculImpurity2DForNumerical(kb,attIndex,counter2D,possibleValue.get(indexBestSplit));
		return gini1D - gini2D;
	}
	
	/**
	 * Only works if the impurity calculus method reason that the lower the impurity number is, the beter it's. In fact it should always be the case.
	 * This function try to find the best Numerical value (index on the list of possible value).
	 * @param kb
	 * @param attIndex
	 * @param multiple_counters
	 * @return
	 */
	private int find_bestSplit(KnowledgeBase kb, int attIndex, ArrayList<ArrayList<Integer>> multiple_counters) {
		float best_Gini = 0; // it's a minimum!!
		int index_Best_Gini =0;
		float tmp_gini;
		for(int i=0;i<multiple_counters.size();i++){
			tmp_gini = calculImpurity(kb,attIndex,multiple_counters.get(i));
			if(tmp_gini<best_Gini){
				best_Gini = tmp_gini;
				index_Best_Gini = i;
			}
		}
		return index_Best_Gini;
		
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
		float tmp = pv1*giniLine1 +pv2*giniLine2;
		return pv1*giniLine1 +pv2*giniLine2;
	}

	private float calculImpurityForValue(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter, int i) {
		AttributeValue<?> attVal = kb.getAttributeList().get(attIndex).getPossibleAttributeValue().get(i);
		KnowledgeBase kb2 = kb.Split(attIndex, attVal);
		return calculImpurity(kb2,attIndex,counter);
	}

	private float calculImpurityForValueNumerical(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter, int i,
			AttributeValue<?> attributeValue, boolean lower) {
		KnowledgeBase kb2 = kb.SplitNumerical(attIndex, attributeValue,lower);
		return calculImpurity(kb2,attIndex,counter);
	}
	
	protected abstract float calculImpurity(KnowledgeBase kb, int indexClass, ArrayList<Integer> counter);
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