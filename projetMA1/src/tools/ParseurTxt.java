package tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import KnowledgeBase.*;
/**
 * This class gives a function to read txt file to load a KnowledgeBase @see KnowledgeBase
 * @author mvh
 * @version 1.0
 */
public class ParseurTxt {

	private static String line;
	private static ArrayList<String> attType;
	
	public static KnowledgeBase readFile(String fileName)
	{
		String kBName =(fileName != null) ? fileName.substring(0,fileName.indexOf('.')) : "";;
		kBName = kBName.substring(fileName.lastIndexOf("/")).substring(1);
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			line = br.readLine();
			String description = readDescription(br);
			ArrayList<Attribute> attribute = readAttribute(br);
			ArrayList<Sample> samples = readSample(br);
			return new KnowledgeBase(kBName, description, samples, attribute);
		} catch (FileNotFoundException e) {
			System.out.println("File: "+fileName+" not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O exception");
			e.printStackTrace();
		}
		return null;//debug value
	}

	private static ArrayList<Sample> readSample(BufferedReader br) {
		ArrayList<Sample> samples= new ArrayList<Sample>();
		try {		
			line = br.readLine();
			while(line!=null){
				//System.out.println("Fct readSample (line): "+line);
				ArrayList<String> linesplit = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
				Sample att = new Sample();
				for(int i=0;i< linesplit.size(); i++)
				{
					if(attType.get(i).compareToIgnoreCase("Boolean")==0)
						if(linesplit.get(i).compareToIgnoreCase("True")==0)
							att.add(new AttributeValue<Boolean>(true));
						else
							att.add(new AttributeValue<Boolean>(false));
					if(attType.get(i).compareToIgnoreCase("Nominal")==0)
						att.add(new AttributeValue<String>(linesplit.get(i)));
					if(attType.get(i).compareToIgnoreCase("Numerical")==0)
						att.add(new AttributeValue<Float>(Float.parseFloat(linesplit.get(i))));
				}
				//System.out.println(att);
				samples.add(att);
				line = br.readLine();
			}
		} catch (IOException e) {
			errorPrint(e, "readSample");
		}
		return samples;
	}

	private static ArrayList<Attribute> readAttribute(BufferedReader br) {
		ArrayList<Attribute> attribute = new ArrayList<Attribute>();
		attType = new ArrayList<String>();
		while(line.startsWith("@")||line.startsWith("#")){
			String first = line.substring(0, 1);
			String line2 = line.substring(2);
			//System.out.println("FCT readAttribute(line): "+line);
			ArrayList<String> attList = new ArrayList<String>(Arrays.asList(line2.split("\\s+")));
			//System.out.println("size : " + attList.size()+ "list : " + attList);
			attType.add(attList.get(1));
			//System.out.println(first.compareTo("@")==0? false : true);
			Attribute att = (new Attribute(attList.get(0), first.compareTo("@")==0? false : true));
			
			if(attList.get(1).compareToIgnoreCase("nominal")==0){
				for(int i=3;i<attList.size();i++){
					att.addValue(new AttributeValue<String>(attList.get(i)));}
				att.setType(Type.Nominal);
			} else if(attList.get(1).compareToIgnoreCase("boolean")==0){
				att.addValue(new AttributeValue<Boolean>(true));
				att.addValue(new AttributeValue<Boolean>(false));
				att.setType(Type.Boolean);
			}
			attribute.add(att);
			try {
				line = br.readLine();
			} catch (IOException e) {
				errorPrint(e,"readAttribute");
			}
		}
		
		return attribute;
	}
/**
 * This function read the description of the KB
 * @param br
 * @return
 */
	private static String readDescription(BufferedReader br) {
		String description = "";
		while(line.startsWith("%")){
			//System.out.println("fct readDescription(line): " + line);
			description = description + line.substring(2); //extract "% " !!space
			try {
				line = br.readLine();
			} catch (IOException e) {
				errorPrint(e,"readDescription");
			}
		}
		//System.out.println("Exit readDescription");
		return description;
	}
	/**
	 * This function print and track an IO error
	 * @param e
	 * @param functionName
	 */
	private static void errorPrint(Exception e,String functionName){
		System.out.println("Error while reading in the"+ functionName +"function");
		e.printStackTrace();
	}
}
