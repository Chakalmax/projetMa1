package DTL.GainStrategy;

import KnowledgeBase.*;
import java.util.ArrayList;

public class EntropyGain extends Impurity implements GainStrategy {
	
	public float calculImpurity(int sizekb, ArrayList<Integer> counter) {
		float entropy = 0;
		String tmpDetail ="";
		boolean firstIt = true;
		for(int i=0;i<counter.size();i++){
			if(!firstIt)
				tmpDetail = tmpDetail + "+";
			firstIt = false;
			float pv = ((float)counter.get(i)/sizekb);
			if(notZero(pv)){
				entropy = (float) (entropy + ((pv)*(Math.log(pv) / Math.log(2))));
				tmpDetail = tmpDetail+"(" + counter.get(i)+"/"+sizekb+")*log_2("+counter.get(i)+"/"+sizekb+")";
			}else{
				tmpDetail = tmpDetail+"0";
			}
			
		}
		this.detail = detail+ "(" + tmpDetail +")";
		return - entropy;
	}
	

	@Override
	public String getName() {
		return "EntropyGain";
	}
	


}
