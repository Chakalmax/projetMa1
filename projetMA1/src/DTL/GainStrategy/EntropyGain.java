package DTL.GainStrategy;

import KnowledgeBase.*;
import java.util.ArrayList;

public class EntropyGain implements GainStrategy {

	/**
	 * The gain here is Gain(E,X) = Entropy(X) - Entropy(E,X)
	 */
	@Override
	public float getGain(KnowledgeBase kb, int attIndex) {
		Attribute att = kb.getAttributeList().get(attIndex);
		if(att.getType()== Type.Numerical)
			return calculGainNumerical(kb,attIndex);
		else
			return calculGainNonNumerical(kb,attIndex);
	}
	/**
	 * Compute the gain of a Numerical Attribute @see Type
	 * @param kb the KnowledgeBase where you compute the gain
	 * @param attIndex the index of the Attribute in the KnowledgeBase
	 * @return the gain of the attribute(index : attIndex) in the kb
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
		float entropyClass = calculEntropy(kb,kb.getIndexClass(),counter1D);
		int indexBestSplit = find_bestSplit(kb,attIndex,multiple_counters);
		///
		ArrayList<ArrayList<Integer>> counter2D = kb.count2DNumeric(attIndex, kb.getIndexClass(),possibleValue.get(indexBestSplit));
		float entropy2D = calculEntropy2DForNumerical(kb,attIndex,counter2D,possibleValue.get(indexBestSplit));
		return entropyClass - entropy2D;
	}

	
	private int find_bestSplit(KnowledgeBase kb, int attIndex, ArrayList<ArrayList<Integer>> multiple_counters) {
		float best_Entropy = 0; // it's a minimum!!
		int index_Best_Entropy =0;
		float tmp_Entropy;
		for(int i=0;i<multiple_counters.size();i++){
			tmp_Entropy = calculEntropy(kb,attIndex,multiple_counters.get(i));
			if(tmp_Entropy<best_Entropy){
				best_Entropy = tmp_Entropy;
				index_Best_Entropy = i;
			}
		}
		return index_Best_Entropy;
		
	}
	
	private float calculEntropy2DForNumerical(KnowledgeBase kb, int attIndex, ArrayList<ArrayList<Integer>> counter2d, AttributeValue<?> attributeValue) {
		float pv1 = (float)sumList(counter2d.get(0))/kb.getSamples().size();
		float entropyLine1 = calculEntropyForValueNumerical(kb,attIndex,counter2d.get(0),0,attributeValue,true);
		float pv2 = (float)sumList(counter2d.get(1))/kb.getSamples().size();
		float entropyLine2 = calculEntropyForValueNumerical(kb,attIndex,counter2d.get(1),1,attributeValue,false);
		return pv1*entropyLine1 +pv2*entropyLine2;
	}
	
	private float calculEntropyForValueNumerical(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter, int i,
			AttributeValue<?> attributeValue, boolean lower) {
		KnowledgeBase kb2 = kb.SplitNumerical(attIndex, attributeValue,lower);
		return calculEntropy(kb2,attIndex,counter);
	}
	
	
	/**
	 * Compute the gain of a Non Numerical Attribute (i.e Nominal or Boolean) @see Type
	 * @param kb the KnowledgeBase where you compute the gain
	 * @param attIndex the index of the Attribute in the KnowledgeBase
	 * @return the gain of the attribute(index : attIndex) in the kb
	 */
	private float calculGainNonNumerical(KnowledgeBase kb, int attIndex) {
		ArrayList<Integer> counter1D = kb.count(kb.getIndexClass());
		float entropyClass = calculEntropy(kb,kb.getIndexClass(),counter1D);
		ArrayList<ArrayList<Integer>> counter2D = kb.count2D(attIndex, kb.getIndexClass());
		float entropy2D = calculEntropy2D(kb,attIndex,counter2D);
		return entropyClass - entropy2D;
	}


	/**
	 * Calcul Entropy for 2 attribute: regular and class: Entropy(E,X) = sum(P(c)E(c))
	 * @param kb the KnowlegdeBase
	 * @param attIndex
	 * @param counter2d
	 * @return Entropy
	 */
	public float calculEntropy2D(KnowledgeBase kb, int attIndex, ArrayList<ArrayList<Integer>> counter2d) {
		float entropy=0;
			for(int i=0;i<counter2d.size();i++){
				float pv = (float)sumList(counter2d.get(i))/kb.getSamples().size();
				float entropyLine = calculEntropyForValue(kb,attIndex,counter2d.get(i),i);
				entropy = entropy + pv*entropyLine;
			}
		return  entropy;
	}
	
	public float calculEntropyForValue(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter, int i) {
		AttributeValue<?> attVal = kb.getAttributeList().get(attIndex).getPossibleAttributeValue().get(i);
		KnowledgeBase kb2 = kb.Split(attIndex, attVal);
		return calculEntropy(kb2,attIndex,counter);
	}
	
	public float calculEntropy(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter) {
		float entropy = 0;
		for(int i=0;i<counter.size();i++){
			float pv = ((float)counter.get(i)/kb.getSamples().size());
			if(notZero(pv))
				entropy = (float) (entropy + ((pv)*(Math.log(pv) / Math.log(2))));
			else
				return 0;
		}
		return - entropy;
	}
	
	public int sumList(ArrayList<Integer> list){
		int result =0;
		for(int i: list){
			result = result + i;
		}
		return result;
		
	}
	
	private boolean notZero(float pv){
		float epsilon = (float) 0.00000001;
		return (pv>epsilon)||(pv<-epsilon);
	}
	


}
