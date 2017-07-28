package DTL;

import java.util.ArrayList;

import DTL.GainStrategy.GainStrategy;
import DecisionTree.*;
import KnowledgeBase.*;
import graphicInterface.InfoProgressionAlgo;

public class DTLAlgo {
	
	private static String [] pseudoCode = { "Si tout les attributs utilisés ou tous les échantillons sont de même classe",
			"retourner Feuille(classeDominante)",
			"Sinon si aucun échantillon restant",
			"Retourne Feuille(classeDominante_Parent)",
			"Sinon",
			"A <- argmax{Gain(A,samples)}",
			"tree <- DT dont la racine est A",
			" pour chaque valeur v de A faire:",
			"sample_fils <- {e|e dans examples et e.A = valeur v}",
			"DT_fils = DTL(samples_fils,attribute-A,examples)",
			"ajouter DT_Fils aux fils de tree avec le marquage A = vk sur la branche",
			"retourner tree"};
	private static int [] pseudoCodeIdentation = {0,1,0,1,0,1,1,1,1,1,1,0};
	private static InfoProgressionAlgo infoProg;

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
			KnowledgeBase parent_kb, double error, GainStrategy strat){
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
			KnowledgeBase parent_kb, double error, GainStrategy strat) {
		ArrayList<Float> gainList = new ArrayList<Float>();
		for(int i=0; i< kb.getAttributeList().size();i++)
			if(!attIndex.contains(i))
				gainList.add(strat.getGain(kb,i));
			else
				gainList.add((float) 0);
		int A = maxIndex(gainList);
		InnerDecisionTree tree = new InnerDecisionTree(kb,kb.getAttributeList().get(A),gainList.get(A));
		if(kb.getAttributeList().get(A).getType() != Type.Numerical)
			for(AttributeValue<?> attVal : kb.getAttributeList().get(A).getPossibleAttributeValue()){
				KnowledgeBase kbChild = kb.Split(A,attVal);
				attIndex.add(A);
				DecisionTree child = DTL_algo(kbChild,attIndex,kb,error,strat);
				tree.addArrow(new Arrow(attVal,child));}
		else{
			AttributeValue<Float> attVal = strat.getValueBestSplit(kb,A);
			KnowledgeBase kbChildLower = kb.SplitNumerical(A,attVal,true);
			attIndex.add(A);
			DecisionTree childLower = DTL_algo(kbChildLower,attIndex,kb,error,strat);
			KnowledgeBase kbChildUpper = kb.SplitNumerical(A,attVal,false);
			DecisionTree childUpper = DTL_algo(kbChildUpper,attIndex,kb,error,strat);
			tree.addArrow(new ArrowNumerical(attVal,childLower,true));
			tree.addArrow(new ArrowNumerical(attVal,childUpper,false));		
		}
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
	
	public static String[] getPseudoCode() {
		return pseudoCode;
	}
	public static int[] getPseudoCodeIdentation() {
		return pseudoCodeIdentation;
	}
	
	public DecisionTree Init_DTL_algo_StepByStep(KnowledgeBase kb, float error, GainStrategy strat){
		ArrayList<Integer> attIndex = new ArrayList<Integer>();
		attIndex.add(kb.getIndexClass());
		infoProg = InfoProgressionAlgo.getInstance();
		return DTL_algo_StepByStep(kb,attIndex,kb,error,strat);
	}
	
	public static DecisionTree DTL_algo_StepByStep(KnowledgeBase kb, ArrayList<Integer> attIndex,
			KnowledgeBase parent_kb, float error, GainStrategy strat){
		//TODO
		boolean firstCondition = testFirstCondition(kb,attIndex,error);
		if(firstCondition){
			return new Leaf(kb,kb.getDominantClass());
		}
		return null;
	}
	
	private static boolean testFirstCondition(KnowledgeBase kb, ArrayList<Integer> attIndex, double error) {		
		return (attIndex.size() == kb.getAttributeList().size()||kb.AllSameClass(error));
	}
}
