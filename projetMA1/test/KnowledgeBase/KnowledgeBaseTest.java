package KnowledgeBase;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import KnowledgeBase.KnowledgeBase;
import tools.ParseurTxt;

public class KnowledgeBaseTest {

	KnowledgeBase kb;
	@Before
	public void init(){
		String filePath = new File("").getAbsolutePath();
		String fileName = filePath.concat("/test/ressources/test1.txt");
		this.kb = ParseurTxt.readFile(fileName);
	}
	
	@Test
	public void allSameClassTest(){
		assertTrue(kb.AllSameClass(100));
		assertTrue(kb.AllSameClass(90));
		assertFalse(kb.AllSameClass(0));
	}
	@Test
	public void getDominanteClass(){
		assertTrue((boolean)kb.getDominantClass().getValue());
	}
}
