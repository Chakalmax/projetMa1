package graphicInterface;

import DTL.GainStrategy.*;

public class Options {

	//public static boolean automatique = false;
	public static double error = (float)0.0;
	public static GainStrategy gainStrategy = new EntropyGain();
	public static long waitTime = 500;
	public static boolean stop = false;
	public static boolean running = false;
	public static boolean random = true;
	
	public volatile static boolean cancelled;
	
	
}
