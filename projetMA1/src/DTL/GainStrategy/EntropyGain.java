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
		// TODO Auto-generated method stub
		return 0;
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
