package DecisionTree;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

import DTL.DTLAlgo;
import DTL.GainStrategy.*;
import KnowledgeBase.*;
import tools.ParseurTxt;

public class DecisionTreeTest {

	KnowledgeBase kb;
	DecisionTree dt;
	@Before
	public void init(){
		String filePath = new File("").getAbsolutePath();
		String fileName = filePath.concat("/test/ressources/test1.txt");
		this.kb = ParseurTxt.readFile(fileName);
		this.dt = DTLAlgo.DTL_algo(kb,0, new GiniGain());
		}
	
	@Test
	public void getSizeTest(){

		assertEquals(9,dt.getSize());
	}
}
