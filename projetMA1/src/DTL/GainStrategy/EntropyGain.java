package DTL.GainStrategy;

import KnowledgeBase.*;
import java.util.ArrayList;

public class EntropyGain extends Impurity implements GainStrategy {
	
	public float calculImpurity(KnowledgeBase kb, ArrayList<Integer> counter) {
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

	@Override
	public String getName() {
		return "EntropyGain";
	}
	


}
