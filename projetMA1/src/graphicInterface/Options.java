package graphicInterface;

import DTL.GainStrategy.*;

public class Options {

	public static boolean automatique = false;
	public static double error = (float)0.0;
	public static GainStrategy gainStrategy = new EntropyGain();
}
