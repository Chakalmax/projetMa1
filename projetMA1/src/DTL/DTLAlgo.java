package DTL;

import java.util.ArrayList;
import java.util.Random;

import DTL.GainStrategy.GainStrategy;
import DecisionTree.*;
import KnowledgeBase.*;
import graphicInterface.MainFrame;
import graphicInterface.Options;

public class DTLAlgo {
	
	private static String [] pseudoCode = { "Si tous les attributs utilisés ou tous les échantillons sont de même classe",
			"retourner Feuille(classeDominante)",
			"Sinon si aucun échantillon restant",
			"Retourne Feuille(classeDominante_Parent)",
			"Sinon",
			"A <- argmax{Gain(A,samples)}",
			"tree <- DT dont la racine est A",
			"Pour chaque valeur v de A faire:",
			"sample_fils <- {e|e dans examples et e.A = valeur v}",
			"DT_fils = DTL(samples_fils,attribute-A,examples)",
			"ajouter DT_Fils aux fils de tree avec le marquage A = vk sur la branche",
			"retourner tree"};
	private static int [] pseudoCodeIdentation = {0,1,0,1,0,1,1,1,2,2,2,0};
	private static DTL_Management mana;
	private static boolean firstIt;

	public static DecisionTree DTL_algo(KnowledgeBase kb, double error, GainStrategy strat){
		ArrayList<Integer> attIndex = new ArrayList<Integer>();
		attIndex.add(kb.getIndexClass());
		return DTL_algo(kb,attIndex,kb,error,strat);
	}
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
		int A = maxIndex(gainList,attIndex);
		InnerDecisionTree tree = new InnerDecisionTree(kb,kb.getAttributeList().get(A),gainList.get(A));
		if(kb.getAttributeList().get(A).getType() != TypeAttribute.Numerical)
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
	private static int maxIndex(ArrayList<Float> gainList,ArrayList<Integer> attIndex) {
		int index=0;
		float max = gainList.get(0);
		for(int i=0;i<gainList.size();i++){
			if(!attIndex.contains(i))
			if(Options.random && nearlyEquals(gainList.get(i),max)){
			Random randomGenerator = new Random(); 
			if(randomGenerator.nextBoolean()){
					max = gainList.get(i);
					index =i;
				}
			} else 
				if(gainList.get(i)>max){
				max = gainList.get(i);
				index = i;
			}
		}
		return index;
	}
	
	private static boolean nearlyEquals(float f1, float f2){
		float epsilon = (float)0.00001;
		return (f1+epsilon > f2 && f1-epsilon < f2);
	}
	
	public static String[] getPseudoCode() {
		return pseudoCode;
	}
	public static int[] getPseudoCodeIdentation() {
		return pseudoCodeIdentation;
	}
	
	public static DecisionTree Init_DTL_algo_StepByStep(KnowledgeBase kb, double error, GainStrategy strat, MainFrame mainFrame) throws InterruptedException{
		ArrayList<Integer> attIndex = new ArrayList<Integer>();
		attIndex.add(kb.getIndexClass());
		System.out.println("attIndex : "+attIndex);
		mana = new DTL_Management(mainFrame.getCodePanel(),mainFrame.getInfoPanel(),mainFrame.getTreePanel());
		firstIt = true;
		return DTL_algo_StepByStep(kb,attIndex,kb,error,strat);
	}
	
	public static DecisionTree DTL_algo_StepByStep(KnowledgeBase kb, ArrayList<Integer> attIndex,
			KnowledgeBase parent_kb, double error, GainStrategy strat) throws InterruptedException{
		String info;
		mana.goToLine(0);
		if(attIndex.size() == kb.getAttributeList().size()||kb.AllSameClass(error)){
			if(attIndex.size() == kb.getAttributeList().size())
				info = "Plus d'attribut restant \n";
			else
				info = "Tous les attributs sont de la même classe \n";
			mana.changeInfoToDisplay(info);
			mana.setLineToGreen();
			// Donner l'info de ce qui a provoqué le true:
			
			mana.setLineToNormal();
			Leaf leaf = new Leaf(kb,kb.getDominantClass());
			info = info + leaf.toString();
			mana.addTree(leaf);
			mana.changeInfoToDisplay(info);
			mana.nextLine();
			return leaf;
		}
		else {mana.setLineToRed();
		
		mana.setLineToNormal();
		mana.jumpLine(2);
		}
		if(kb.isEmpty()){
			info = "Plus d'échantillon restant";
			mana.changeInfoToDisplay(info);
			mana.setLineToGreen();
			Leaf leaf = new Leaf(kb,parent_kb.getDominantClass());
			info = info + leaf.toString();
			mana.addTree(leaf);
			mana.changeInfoToDisplay(info);
			mana.setLineToNormal();
			mana.nextLine();
			return leaf;
		}
		else{
			mana.setLineToRed();
			
			mana.setLineToNormal();
			mana.jumpLine(2);
		}
		mana.setLineToGreen();
			return createInnerTree_StepByStep(kb,attIndex,parent_kb,error, strat);
	}
	
	private static DecisionTree createInnerTree_StepByStep(KnowledgeBase kb, ArrayList<Integer> attIndex,
			KnowledgeBase parent_kb, double error, GainStrategy strat) throws InterruptedException {
		String info;
		mana.setLineToNormal();
		mana.goToLine(5);
	
		ArrayList<Float> gainList = new ArrayList<Float>();
		for(int i=0; i< kb.getAttributeList().size();i++)
			if(!attIndex.contains(i))
				gainList.add(strat.getGain(kb,i));
			else
				gainList.add((float) 0);
		int A = maxIndex(gainList,attIndex);
		info = "Les attributs et leur gains sont: \n";
		info = info + TabGainToInfo(gainList,kb.getAttributeList());
		info = info + "Attribut choisi: " +kb.getAttributeList().get(A).getName();
		mana.changeInfoToDisplay(info);
		// wait & display things
		mana.nextLine();
		InnerDecisionTree tree = new InnerDecisionTree(kb,kb.getAttributeList().get(A),gainList.get(A));
		// ici c'est pour l'affichage,on a une copie mais qui a des arrows vide.
		InnerDecisionTree tree2 = new InnerDecisionTree(kb,kb.getAttributeList().get(A),gainList.get(A));

		if(kb.getAttributeList().get(A).getType() != TypeAttribute.Numerical)
			for(AttributeValue<?> attVal : kb.getAttributeList().get(A).getPossibleAttributeValue()){
				tree2.addArrow(new Arrow(attVal));
			}
		else{ // if Numerical
			AttributeValue<Float> attVal = strat.getValueBestSplit(kb,A);
			tree2.addArrow(new ArrowNumerical(attVal,true));
			tree2.addArrow(new ArrowNumerical(attVal,false));
		}
		if(firstIt){
			mana.setTree(tree2);
			firstIt = false;}
		else{
			mana.addTree(tree2);
		}
		if(kb.getAttributeList().get(A).getType() != TypeAttribute.Numerical)
			for(AttributeValue<?> attVal : kb.getAttributeList().get(A).getPossibleAttributeValue()){
				info = "valeur pour l'attribut "+ kb.getAttributeList().get(A).getName() +" : \n" + attVal.toString();
				mana.changeInfoToDisplay(info);
				mana.goToLine(7);
				
				// display l'elem selectionné
				mana.nextLine();
				
				KnowledgeBase kbChild = kb.Split(A,attVal);
				attIndex.add(A);
				mana.nextLine();
				
				DecisionTree child = DTL_algo_StepByStep(kbChild,attIndex,kb,error,strat);
				mana.goToLine(10);
				
				tree.addArrow(new Arrow(attVal,child));}
		else{// Numeric
			attIndex.add(A);
			
			AttributeValue<Float> attVal = strat.getValueBestSplit(kb,A);
			info = "valeur pour l'attribut "+ kb.getAttributeList().get(A).getName() +" : \n" + attVal.toString();
			mana.changeInfoToDisplay(info);
			mana.goToLine(7);
			// wait & display elem selectionné (preciser inférieur)
			mana.nextLine();
			KnowledgeBase kbChildLower = kb.SplitNumerical(A,attVal,true);
			mana.nextLine();
			DecisionTree childLower = DTL_algo_StepByStep(kbChildLower,attIndex,kb,error,strat);
			mana.goToLine(10);
			tree.addArrow(new ArrowNumerical(attVal,childLower,true));
			mana.goToLine(7);
			// wait & display elem select (preciser supérieur)
			KnowledgeBase kbChildUpper = kb.SplitNumerical(A,attVal,false);
			mana.nextLine();
			DecisionTree childUpper = DTL_algo_StepByStep(kbChildUpper,attIndex,kb,error,strat);
			mana.goToLine(10);
			tree.addArrow(new ArrowNumerical(attVal,childUpper,false));		
		}
		mana.goToLine(11);
		return tree;
	}
	
	
	private static String TabGainToInfo(ArrayList<Float> gainList, ArrayList<Attribute> arrayList) {
		String info = "";
		for(int i=0;i<gainList.size();i++)
			if(gainList.get(i) != 0)
				info = info + arrayList.get(i).getName()+ " " + gainList.get(i) + "\n";
		return info;
	}
	
	
}
