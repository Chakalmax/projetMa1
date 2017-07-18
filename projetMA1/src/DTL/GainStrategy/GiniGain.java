package DTL.GainStrategy;

import java.util.ArrayList;

import KnowledgeBase.Attribute;
import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;
import KnowledgeBase.Type;

public class GiniGain implements GainStrategy {

	/**
	 * The gain here is Gain(E,X) = Gini(X) - Gini(E,X)
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
	 * Compute the gain of a Non Numerical Attribute (i.e Nominal or Boolean) @see Type
	 * @param kb the KnowledgeBase where you compute the gain
	 * @param attIndex the index of the Attribute in the KnowledgeBase
	 * @return the gain of the attribute(index : attIndex) in the kb
	 */
	private float calculGainNonNumerical(KnowledgeBase kb, int attIndex) {
		ArrayList<Integer> counter1D = kb.count(kb.getIndexClass());
		float giniClass = calculGini(kb,kb.getIndexClass(),counter1D);
		ArrayList<ArrayList<Integer>> counter2D = kb.count2D(attIndex, kb.getIndexClass());
		float gini2D = calculGini2D(kb,attIndex,counter2D);
		return giniClass - gini2D;
	}


	private float calculGainNumerical(KnowledgeBase kb, int attIndex) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private float calculGini2D(KnowledgeBase kb, int attIndex, ArrayList<ArrayList<Integer>> counter2d) {
		float gini=0;
		for(int i=0;i<counter2d.size();i++){
			float pv = (float)sumList(counter2d.get(i))/kb.getSamples().size();
			float giniLine = calculGiniForValue(kb,attIndex,counter2d.get(i),i);
			gini = gini + pv*giniLine;
		}
	return  gini;
	}

	private float calculGiniForValue(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter, int i) {
		AttributeValue<?> attVal = kb.getAttributeList().get(attIndex).getPossibleAttributeValue().get(i);
		KnowledgeBase kb2 = kb.Split(attIndex, attVal);
		return calculGini(kb2,attIndex,counter);
	}

	public float calculGini(KnowledgeBase kb, int indexClass, ArrayList<Integer> counter) {
		float giniIndex = 0;
//		System.out.println("Counter : " + counter);
//		System.out.println("Indes class: " + indexClass);
		for(int i=0;i<counter.size();i++){
			float pv = ((float)counter.get(i)/kb.getSamples().size());
			//System.out.println("i = " + i +" pv : " + pv);
			if(notZero(pv))
				giniIndex = (float) (giniIndex + Math.pow(pv,2));
			else
				return 0;
		}
		return 1 - giniIndex;
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
