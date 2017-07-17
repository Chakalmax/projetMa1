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
		float entropy1 = ((EntropyGain) strat).calculEntropy(kb,1,counter);
		assertTrue(nearlyEquals(entropy1,0.99));
		//
		for(int i=0;i<kb.getAttributeList().size();i++){
			ArrayList<Integer> counter1D = kb.count(i);
			System.out.println("Arraylist counter1D: " + counter1D);
			float entropyAttribute = ((EntropyGain) strat).calculEntropy(kb,i,counter1D);
			System.out.println("L entropie de la classe � l'attribut " + i  +"(qui est de type : "+
						kb.getAttributeList().get(i).getType() +") : "+entropyAttribute);
			ArrayList<ArrayList<Integer>> counter2d = kb.count2D(i,kb.getIndexClass());
			float entropy2D = ((EntropyGain)strat).calculEntropy2D(kb,i , counter2d);
			System.out.println("counter 2d de " + i + "   :   "+ counter2d);
			System.out.println("L entropie 2D entre la classe et l attribut" + i  +"(qui est de type : "+
					kb.getAttributeList().get(i).getType() +") : "+entropy2D);
			System.out.println("gain " +  strat.getGain(kb,i));
			System.out.println("_____________");
		}
		

		System.out.println((7.0/15.0)*Math.log((7.0/15.0)) / Math.log(2));
		System.out.println(kb.getIndexClass());
	}

	private boolean nearlyEquals(float entropyAttribute, double d) {
		float epsilon = (float) 0.01;
		return entropyAttribute< d+epsilon && entropyAttribute> d-epsilon;
	}
}
