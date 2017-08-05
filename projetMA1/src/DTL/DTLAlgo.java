package DTL;

import java.util.ArrayList;

import DTL.GainStrategy.GainStrategy;
import DecisionTree.*;
import KnowledgeBase.*;
import graphicInterface.MainFrame;
import graphicInterface.Options;
import graphicInterface.PseudoCodePanel;

public class DTLAlgo {
	
	private static String [] pseudoCode = { "Si tout les attributs utilisés ou tous les échantillons sont de même classe",
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
	private static InfoProgressionAlgo infoProg;
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
		int A = maxIndex(gainList);
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
	
	public static DecisionTree Init_DTL_algo_StepByStep(KnowledgeBase kb, double error, GainStrategy strat, MainFrame mainFrame) throws InterruptedException{
		ArrayList<Integer> attIndex = new ArrayList<Integer>();
		attIndex.add(kb.getIndexClass());
		infoProg = InfoProgressionAlgo.getInstance();
		mana = new DTL_Management(mainFrame.getCodePanel(),mainFrame.getInfoPanel());
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
		//Thread.sleep(2000);
		
		ArrayList<Float> gainList = new ArrayList<Float>();
		for(int i=0; i< kb.getAttributeList().size();i++)
			if(!attIndex.contains(i))
				gainList.add(strat.getGain(kb,i));
			else
				gainList.add((float) 0);
		int A = maxIndex(gainList);
		info = kb.getAttributeList().get(A).toString();
		mana.changeInfoToDisplay(info);
		// wait & display things
		mana.nextLine();
		//Thread.sleep(2000);
		InnerDecisionTree tree = new InnerDecisionTree(kb,kb.getAttributeList().get(A),gainList.get(A));
		// Si c'est la racine, la rajouter qqp pour les traces
		if(firstIt){
			infoProg.setDt(tree);
			firstIt = false;}
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
				System.out.println("Ici : "+mana.getLine());
				
				tree.addArrow(new Arrow(attVal,child));}
		else{
			attIndex.add(A);
			
			AttributeValue<Float> attVal = strat.getValueBestSplit(kb,A);
			info = "valeur pour l'attribut "+ kb.getAttributeList().get(A).getName() +" : \n" + attVal.toString();
			mana.changeInfoToDisplay(info);
			mana.goToLine(7);
			//Thread.sleep(2000);
			// wait & display elem selectionné (preciser inférieur)
			mana.nextLine();
			//Thread.sleep(2000);
			KnowledgeBase kbChildLower = kb.SplitNumerical(A,attVal,true);
			mana.nextLine();
			//Thread.sleep(2000);
			DecisionTree childLower = DTL_algo_StepByStep(kbChildLower,attIndex,kb,error,strat);
			mana.goToLine(10);
			//Thread.sleep(2000);
			tree.addArrow(new ArrowNumerical(attVal,childLower,true));
			mana.goToLine(7);
			//Thread.sleep(2000);
			// wait & display elem select (preciser supérieur)
			KnowledgeBase kbChildUpper = kb.SplitNumerical(A,attVal,false);
			mana.nextLine();
			//Thread.sleep(2000);
			DecisionTree childUpper = DTL_algo_StepByStep(kbChildUpper,attIndex,kb,error,strat);
			mana.goToLine(10);
			//Thread.sleep(2000);
			tree.addArrow(new ArrowNumerical(attVal,childUpper,false));		
		}
		mana.goToLine(11);
		return tree;
	}
	
	
}
