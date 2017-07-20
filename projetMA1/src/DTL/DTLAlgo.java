package DTL;

import java.util.ArrayList;

import DTL.GainStrategy.GainStrategy;
import DecisionTree.*;
import KnowledgeBase.AttributeValue;
import KnowledgeBase.*;
import KnowledgeBase.Type;

public class DTLAlgo {

	/**
	 * DecisionTreeLearning Algorithm to create a decision Tree. Be very careful with initialization.
	 * @param kb the KnowledgeBase on which we create the DecisionTree
	 * @param attIndex must contains the index of the class
	 * @param parent_kb the KnowledgeBase of the parent(=kb on init)
	 * @param error the error needed for the allSameClass function @see KnowledgeBase.allSameClass()
	 * @param strat the strategy to choose the best attribute @see DTL.GainStrategy
	 * @return A DecisionTree
	 */
	public static DecisionTree DTL_algo(KnowledgeBase kb, ArrayList<Integer> attIndex,
			KnowledgeBase parent_kb, float error, GainStrategy strat){
		if(attIndex.size() == kb.getAttributeList().size()||kb.AllSameClass(error))
			return new Leaf(kb,kb.getDominantClass());
		else if(kb.isEmpty())
			return new Leaf(kb,parent_kb.getDominantClass());
		else
			return createInnerTree(kb,attIndex,parent_kb,error, strat);
		
	}
/**
 * Create an InnerTree (see the DTL algo pseudo-code)
 * @param kb
 * @param attIndex
 * @param parent_kb
 * @param error
 * @param strat
 * @return
 */
	private static DecisionTree createInnerTree(KnowledgeBase kb, ArrayList<Integer> attIndex,
			KnowledgeBase parent_kb, float error, GainStrategy strat) {
		ArrayList<Float> gainList = new ArrayList<Float>();
		for(int i=0; i< kb.getAttributeList().size();i++)
			if(!attIndex.contains(i))
				gainList.add(strat.getGain(kb,i));
			else
				gainList.add((float) 0);
		int A = maxIndex(gainList);
		InnerDecisionTree tree = new InnerDecisionTree(kb,kb.getAttributeList().get(A),gainList.get(A));
		for(AttributeValue<?> attVal : kb.getAttributeList().get(A).getPossibleAttributeValue()){
			KnowledgeBase kbChild = kb.Split(A,attVal);

			attIndex.add(A);
			DecisionTree child = DTL_algo(kbChild,attIndex,kb,error,strat);
			tree.addArrow(new Arrow(attVal,child));}
		return tree;
		
	}

	/**
	 * Return index of the maximum value in the ArrayList
	 * @param gainList an arrayList
	 * @return index of the max
	 */
	private static int maxIndex(ArrayList<Float> gainList) {
		int index=0;
		float max = gainList.get(0);
		for(int i=0;i<gainList.size();i++){
			if(gainList.get(i)>max){
				max = gainList.get(i);
				index = i;
			}
		}
		return index;
	}
}
