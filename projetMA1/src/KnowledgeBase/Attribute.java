package KnowledgeBase;

import java.util.ArrayList;

public class Attribute {
	
	private String name;
	private boolean classe;
	private ArrayList<String> possibleValue;
	private ArrayList<AttributeValue<?>> possibleAttributeValue;
	private Type type;
	
	public Attribute(String name, boolean classe){
		this.name = name;
		this.classe = classe;
		possibleValue = new ArrayList<String>();
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
	 * @return the possibleValue
	 */
	public ArrayList<String> getPossibleValue() {
		return possibleValue;
	}
	/**
	 * @param possibleValue the possibleValue to set
	 */
	public void setPossibleValue(ArrayList<String> possibleValue) {
		this.possibleValue = possibleValue;
	}
	/**
	 * Add an value(string value)
	 * @param value
	 */
	public void addValue(String value){
		possibleValue.add(value);
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
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
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
	/**
	 * Add an AttributeValue to the list of possibleValue
	 * @param value
	 */
	public void addValue(AttributeValue<?> value){
		possibleAttributeValue.add(value);
	}
	

	
}
