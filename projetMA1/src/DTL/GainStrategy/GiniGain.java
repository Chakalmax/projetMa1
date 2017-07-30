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
		String tmpDetail ="(";
		boolean firstIt = true;
		for(int i=0;i<counter.size();i++){
			if(!firstIt)
				tmpDetail = tmpDetail + "+";
			firstIt=false;
			float pv = ((float)counter.get(i)/sizekb);
			if(notZero(pv)){
				giniIndex = (float) (giniIndex + Math.pow(pv,2));
				tmpDetail = tmpDetail + "("+counter.get(i) + "/"+ sizekb +")²";
			}else{
				tmpDetail = tmpDetail + "0";
			}
			
		}
		this.detail = detail + "( 1 -"+ tmpDetail +"))";
		return 1 - giniIndex;
	}

	@Override
	public String getName() {
		return "GiniGain";
	}


}
