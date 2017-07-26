package DTL;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import DTL.GainStrategy.GiniGain;
import DecisionTree.*;
import KnowledgeBase.*;
import tools.ParseurTxt;

public class DTLAlgoTest {

	KnowledgeBase kb;
	ArrayList<Integer> attIndex;
	KnowledgeBase kb2;
	ArrayList<Integer> attIndex2;
	KnowledgeBase kb3;
	ArrayList<Integer> attIndex3;
	@Before
	public void init(){
		String filePath = new File("").getAbsolutePath();
		String fileName = filePath.concat("/test/ressources/test1.txt");
		this.kb = ParseurTxt.readFile(fileName);
		attIndex = new ArrayList<Integer>();
		attIndex.add(kb.getIndexClass());
		String fileName2 = filePath.concat("/test/ressources/test2.txt");
		this.kb2 = ParseurTxt.readFile(fileName2);
		attIndex2 = new ArrayList<Integer>();
		attIndex2.add(kb2.getIndexClass());
		String fileName3 = filePath.concat("/test/ressources/test3.txt");
		this.kb3 = ParseurTxt.readFile(fileName3);
		attIndex3 = new ArrayList<Integer>();
		attIndex3.add(kb3.getIndexClass());
	}
	
	@Test
	public void DTL_no_Numeric(){
		
		DecisionTree DT = DTLAlgo.DTL_algo(kb, attIndex, kb, 0, new GiniGain());
		Sample samp = new Sample();
		AttributeValue<Boolean> add_conn = new AttributeValue<Boolean>(true);
		AttributeValue<Boolean> money = new AttributeValue <Boolean>(true);
		AttributeValue<Boolean> faute = new AttributeValue<Boolean>(true);
		AttributeValue<String> taille_text = new AttributeValue <String>("aucun");
		AttributeValue<Boolean> piece_jointe = new AttributeValue<Boolean>(true);
		samp.add(add_conn);
		samp.add(money);
		samp.add(faute);
		samp.add(taille_text);
		samp.add(piece_jointe);
		assertTrue((boolean)DT.getDecision(samp).getValue());
		DecisionTree DT2 = DTLAlgo.DTL_algo(kb3, attIndex3, kb3, 0, new GiniGain());
		add_conn = new AttributeValue<Boolean>(true);
		money = new AttributeValue <Boolean>(false);
		faute = new AttributeValue<Boolean>(false);
		taille_text = new AttributeValue <String>("court");
		piece_jointe = new AttributeValue<Boolean>(false);
		samp = new Sample();
		samp.add(add_conn);
		samp.add(money);
		samp.add(faute);
		samp.add(taille_text);
		samp.add(piece_jointe);
		assertFalse((boolean)DT2.getDecision(samp).getValue());
	}
	
	@Test
	public void DTL_Numerica(){
		
		DecisionTree DT = DTLAlgo.DTL_algo(kb2, attIndex2, kb2, 0, new GiniGain());
		Sample samp = new Sample();
		AttributeValue<Boolean> add_conn = new AttributeValue<Boolean>(true);
		AttributeValue<Boolean> money = new AttributeValue <Boolean>(true);
		AttributeValue<Float> faute = new AttributeValue<Float>((float) 0);
		AttributeValue<String> taille_text = new AttributeValue <String>("aucun");
		AttributeValue<Boolean> piece_jointe = new AttributeValue<Boolean>(true);
		samp.add(add_conn);
		samp.add(money);
		samp.add(faute);
		samp.add(taille_text);
		samp.add(piece_jointe);
		assertFalse((boolean)DT.getDecision(samp).getValue());
		
	}
	
}
