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
		ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
		arr = kb.count2D(0,5);
		assertTrue(arr.get(0).get(0) == 3);
		assertTrue(arr.get(0).get(1) == 5);
		assertTrue(arr.get(1).get(0) == 5);
		assertTrue(arr.get(1).get(1) == 2);
		int count =0;
		for(int i=0;i<2;i++)
			for(int j=0;j<2;j++)
				count = count + arr.get(i).get(j);
		assertEquals(count,15);
		
	}
	@Test
	public void getIndexClass(){
		assertEquals(kb.getIndexClass(),5);
	}
	
	@Test
	public void testNumericalThings(){
		String filePath = new File("").getAbsolutePath();
		String fileName = filePath.concat("/test/ressources/test2.txt");
		this.kb = ParseurTxt.readFile(fileName);
		KnowledgeBase kb2 = kb.Split(2, new AttributeValue<Float>((float) 2.0));
		assertEquals(kb2.getSamples().size(),2);
		assertEquals(kb2.getDominantClass(),new AttributeValue<Boolean>(true));
	}
	

	
	
}
