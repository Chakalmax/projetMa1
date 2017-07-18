package DTL.GainStrategy;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import KnowledgeBase.*;
import tools.ParseurTxt;

public class GiniGainTest {

	KnowledgeBase kb;
	
	@Before
	public void init(){
		String filePath = new File("").getAbsolutePath();
		String fileName = filePath.concat("/test/ressources/test1.txt");
		this.kb = ParseurTxt.readFile(fileName);
	}
	
	@Test
	public void getGainTest(){
		
		GainStrategy strat = new GiniGain();
		//
		for(int i=0;i<6;i++){
		ArrayList<Integer> counter = kb.count(i);
		System.out.println(counter);
		float gini = ((GiniGain) strat).calculGini(kb,i,counter);
		System.out.println("gini : "+ gini);
		float gain = strat.getGain(kb, i);
		System.out.println("gain : " +gain);
		}
		ArrayList<Integer> counter = kb.count(1);
		float gini1 = ((GiniGain) strat).calculGini(kb,1,counter);
		assertTrue(nearlyEquals(gini1,0.49));
		
		float gain = ((GiniGain) strat).getGain(kb, 5);
		assertTrue(nearlyEquals(gain,0.49));
		
		float gain2 = ((GiniGain) strat).getGain(kb, 3);
		assertTrue(nearlyEquals(gain2,0.07));
		
	}
	
	/**
	 * This function is necessary to compare 2 float (approx)
	 * @param a a float
	 * @param d a float
	 * @return true if a between d+epsilon and d-epsilon
	 */
	private boolean nearlyEquals(float a, double d) {
		float epsilon = (float) 0.01;
		return a< d+epsilon && a> d-epsilon;
	}
	
}
