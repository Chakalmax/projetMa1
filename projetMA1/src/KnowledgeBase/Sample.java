package KnowledgeBase;

import java.util.ArrayList;
/**
 * A Sample is a list of value for every Attribute of the KnowledgeBase(= a record). The classValue is put 
 * in another variable. So everychange in the list(size or anything) won't affect the class
 * @author mvh
 * @version 1.0
 */
public class Sample extends ArrayList<AttributeValue<?>>{

	private static final long serialVersionUID = 1L;

	public Sample(){
		super();
	} 
	
	@Override
	public boolean equals(Object ob){
		boolean result = false;
		if(ob instanceof Sample){
			result = true;
			if(((Sample)ob).size()==this.size()){
				for(int i=0;i<size();i++)
					if(!((Sample)ob).get(i).equals(get(i)))
						result = false;
			}
			
		}
		return result;
	}

}
