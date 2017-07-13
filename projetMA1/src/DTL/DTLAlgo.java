package DTL;

import java.util.ArrayList;

import DTL.GainStrategy.GainStrategy;
import DecisionTree.*;
import KnowledgeBase.KnowledgeBase;

public class DTLAlgo {

	public static DecisionTree DTL_algo(KnowledgeBase kb, ArrayList<Integer> attIndex,
			KnowledgeBase parent_kb, float error, GainStrategy strat){
		if(attIndex.size() == kb.getAttributeList().size()||kb.AllSameClass(error))
			return new Leaf(kb,kb.getDominantClass());
		else if(kb.isEmpty())
			return new Leaf(kb,parent_kb.getDominantClass());
		else
			createInnerTree(kb,attIndex,parent_kb,error, strat);
		return null;
		
	}

	private static void createInnerTree(KnowledgeBase kb, ArrayList<Integer> attIndex,
			KnowledgeBase parent_kb, float error, GainStrategy strat) {
		ArrayList<Float> gainList = new ArrayList<Float>();
		for(int i=0; i< kb.getAttributeList().size();i++)
			if(!attIndex.contains(i))
				gainList.set(i, strat.getGain(kb,i));
			else
				gainList.set(i,(float) 0);
		int A = max(gainList);
		DecisionTree tree = new InnerDecisionTree(kb,kb.getAttributeList().get(A),gainList.get(A));
		for(String attValstr : kb.getAttributeList().get(A).getPossibleValue()){
			KnowledgeBase kbFils = kb.Split(A,attValstr);}
		
	}

	private static int max(ArrayList<Float> gainList) {
		// TODO Auto-generated method stub
		return 0;
	}
}
