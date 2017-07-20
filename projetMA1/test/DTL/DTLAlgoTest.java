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
	@Before
	public void init(){
		String filePath = new File("").getAbsolutePath();
		String fileName = filePath.concat("/test/ressources/test1.txt");
		this.kb = ParseurTxt.readFile(fileName);
		attIndex = new ArrayList<Integer>();
		attIndex.add(kb.getIndexClass());
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
	}
	
}
