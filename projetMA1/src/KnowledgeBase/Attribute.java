package KnowledgeBase;

import java.util.ArrayList;

public class Attribute {
	
	private String name;
	private boolean classe;
	private ArrayList<AttributeValue<?>> possibleAttributeValue;
	private TypeAttribute type;
	
	public Attribute(String name, boolean classe){
		this.name = name;
		this.classe = classe;
		possibleAttributeValue = new ArrayList<AttributeValue<?>>();
	}
	public Attribute(String name){
		this.name = name;
		this.classe = false;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the classe
	 */
	public boolean isClasse() {
		return classe;
	}
	/**
	 * @param classe the classe to set
	 */
	public void setClasse(boolean classe) {
		this.classe = classe;
	}
	/**
	 * @return the type
	 */
	public TypeAttribute getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(TypeAttribute type) {
			this.type= type;
	}
	/**
	 * @return the possibleAttributeValue
	 */
	public ArrayList<AttributeValue<?>> getPossibleAttributeValue() {
		return possibleAttributeValue;
	}
	/**
	 * @param possibleAttributeValue the possibleAttributeValue to set
	 */
	public void setPossibleAttributeValue(ArrayList<AttributeValue<?>> possibleAttributeValue) {
		this.possibleAttributeValue = possibleAttributeValue;
	}
	
	public void addPossibleAttributeValue(AttributeValue<?> attval){
		this.possibleAttributeValue.add(attval);
	}
	/**
	 * Add an AttributeValue to the list of possibleValue
	 * @param value
	 */
	public void addValue(AttributeValue<?> value){
		possibleAttributeValue.add(value);
	}
	@Override
	public String toString(){
		String str = "Nom:" + this.name + "type : "+ this.type;
		return str;
	}
	

	
}
