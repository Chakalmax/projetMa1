package tools;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import KnowledgeBase.KnowledgeBase;

public class ParseurTxtTest {

	@Test
	public void readFileTest(){
		String filePath = new File("").getAbsolutePath();
		String fileName = filePath.concat("/test/ressources/test1.txt");
		KnowledgeBase kb = ParseurTxt.readFile(fileName);
		
		assertTrue(!kb.getDescription().equals(null));
		assertTrue(kb.getName().equals("test1"));
		assertEquals(kb.getSamples().size(),15);
		assertEquals(kb.getAttributeList().size(),6);
		assertTrue(kb.getAttributeList().get(4).isClasse());
	}
	
	
	

}
