package KnowledgeBase;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

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
	
	@Test 
	public void SplitTest(){
		KnowledgeBase kb2 = kb.Split(0, new AttributeValue<Boolean>(true));
		assertEquals(kb2.getSamples().size(),8);
		for(Sample samp: kb2.getSamples())
			assertTrue((boolean)samp.get(0).getValue());	
	}
	@Test
	public void count2DTest(){
		System.out.println(kb.count2D(0,5));
		ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
		kb.init2D(arr,kb.getAttributeList().get(0).getPossibleAttributeValue().size(), kb.getAttributeList().get(5).getPossibleAttributeValue().size());
		System.out.println(kb.getAttributeList().get(0).getPossibleAttributeValue().size());
		System.out.println(arr);
	
	}
	
	
}
