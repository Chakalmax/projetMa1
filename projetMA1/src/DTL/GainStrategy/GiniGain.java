package DTL.GainStrategy;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;

import KnowledgeBase.Attribute;
import KnowledgeBase.AttributeValue;
import KnowledgeBase.KnowledgeBase;
import KnowledgeBase.Sample;
import KnowledgeBase.Type;

public class GiniGain extends Impurity implements GainStrategy {

	
	public float calculImpurity(int sizekb, ArrayList<Integer> counter) {
		float giniIndex = 0;
		for(int i=0;i<counter.size();i++){
			float pv = ((float)counter.get(i)/sizekb);
			if(notZero(pv))
				giniIndex = (float) (giniIndex + Math.pow(pv,2));
			else
				return 0;
		}
		return 1 - giniIndex;
	}

	@Override
	public String getName() {
		return "GiniGain";
	}


}
