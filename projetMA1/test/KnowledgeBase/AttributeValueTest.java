package KnowledgeBase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class AttributeValueTest {

	AttributeValue<Float> attFloat1;
	AttributeValue<Float> attFloat2;
	AttributeValue<Float> attFloat3;
	
	AttributeValue<String> attString1;
	AttributeValue<String> attString2;
	AttributeValue<String> attString3;
	
	AttributeValue<Boolean> attBool1;
	AttributeValue<Boolean> attBool2;
	AttributeValue<Boolean> attBool3;
	
	@Before
	public void equalsTestInit(){
		
		 attFloat1 = new AttributeValue<Float>((float) 2.2);
		 attFloat2 = new AttributeValue<Float>((float) 2.2);
		 attFloat3 = new AttributeValue<Float>((float) 2.3);
		
		 attString1 = new AttributeValue<String>("haha");
		 attString2 = new AttributeValue<String>("haha");
		 attString3 = new AttributeValue<String>("ha");
		
		 attBool1 = new AttributeValue<Boolean>(true);
		 attBool2 = new AttributeValue<Boolean>(true);
		 attBool3 = new AttributeValue<Boolean>(false);
		
	}
	
	@Test
	public void equalsTestNumerical(){
		assertEquals(attFloat1,attFloat2);
		assertNotEquals(attFloat1,attFloat3);
		assertNotEquals(attFloat1,attString1);
		assertNotEquals(attFloat1,attBool1);
	}
	
	@Test
	public void equalsTestNominal(){
		assertEquals(attString1,attString2);
		assertNotEquals(attString1,attString3);
		assertNotEquals(attString1,attFloat1);
		assertNotEquals(attString1,attBool1);
	}
	
	@Test
	public void equalsTestBoolean(){
		assertEquals(attBool1,attBool2);
		assertNotEquals(attBool1,attBool3);
		assertNotEquals(attBool1,attFloat1);
		assertNotEquals(attBool1,attString1);
	}
}
