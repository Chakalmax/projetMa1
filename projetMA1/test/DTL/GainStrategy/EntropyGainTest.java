package DTL.GainStrategy;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import KnowledgeBase.*;
import tools.ParseurTxt;

public class EntropyGainTest {

	KnowledgeBase kb;
	
	@Before
	public void init(){
		String filePath = new File("").getAbsolutePath();
		String fileName = filePath.concat("/test/ressources/test1.txt");
		this.kb = ParseurTxt.readFile(fileName);
	}
	
	@Test
	public void getGaintest(){
		GainStrategy strat = new EntropyGain();
		//
		ArrayList<Integer> counter = kb.count(1);
		float entropy1 = ((EntropyGain) strat).calculImpurity(kb.getSamples().size(),counter);
		assertTrue(nearlyEquals(entropy1,0.99)); //calculer à la main
		
		float gain_taille_text = strat.getGain(kb, 3);
		assertTrue(nearlyEquals(gain_taille_text,0.11)); // calculer à la main
		
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
