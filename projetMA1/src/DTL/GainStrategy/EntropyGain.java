package DTL.GainStrategy;

import KnowledgeBase.*;
import java.util.ArrayList;

public class EntropyGain implements GainStrategy {

	@Override
	public float getGain(KnowledgeBase kb, int attIndex) {
		Attribute att = kb.getAttributeList().get(attIndex);
		float gain=0;
		if(att.getType()!= Type.Numerical){
			ArrayList <String> attVal = att.getPossibleValue();
			ArrayList<Integer> counter = new ArrayList<Integer>();
			ArrayList<Integer> counter2 = new ArrayList<Integer>();
			for(int i=0;i<attVal.size();i++){
				counter.add(0);
				counter2.add(0);
				}
			kb.countClass(kb.getClassAttribute().getPossibleValue(), counter2);
			kb.count(attVal, counter2, attIndex);
			float entropyClasse = calculEntropy(kb,kb.getIndexClass(),counter);
			return calculGain(kb,attIndex,entropyClasse,counter2);
		
		}else{
			
		}
		return gain;
	}

	public float calculGain(KnowledgeBase kb, int attIndex, float entropyClasse, ArrayList<Integer> counter2) {
		
		return 0;
	}

	public float calculEntropy(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter) {
		float entropy = 0;
		for(int i=0;i<counter.size();i++){
			float pv = ((float)counter.get(i)/kb.getSamples().size());
			entropy = (float) (entropy + ((pv)*(Math.log(pv) / Math.log(2))));
		}
		return - entropy;
	}
	
/*	public float calculB(KnowledgeBase kb, int attIndex, ArrayList<Integer> counter, AttributeValue<?> attval){
		float q =(0/kb.getSamples().size());
		return (float) -(q*(Math.log(q)/Math.log(2))+(1-q)*(Math.log(1-q)/Math.log(2)));
		
	}*/

}
