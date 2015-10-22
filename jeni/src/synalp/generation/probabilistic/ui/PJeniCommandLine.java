package synalp.generation.probabilistic.ui;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import synalp.generation.probabilistic.common.AppConfiguration;
import synalp.generation.probabilistic.common.TestsuiteRunner;

public class PJeniCommandLine
{
	public static void main(String args[]) throws IOException
	{
		if (args.length >= 4)
		{
			String outName = null;
			if (args.length == 5)
			{
				outName = args[4];
			}
			String grammarFile = args[0];
			String lexiconFile = args[1];
			String testsuiteFile = args[2];
			int beamSize = Integer.valueOf(args[3]);
			AppConfiguration appConfig = new AppConfiguration();

			//app configuration
			appConfig.setBeamSize(beamSize);
			appConfig.setConfiguration(grammarFile, lexiconFile, testsuiteFile);
			OutputStreamWriter output;
		
			if (outName != null)
			{
				OutputStream outputStream = new FileOutputStream(outName);
				output = new OutputStreamWriter(outputStream);
			}
			else
			{

				output = new OutputStreamWriter(System.out);
			}

			TestsuiteRunner.runJeniConfiguration(appConfig, output);
			output.close();

		}
		else {
			System.out.println("Usage: java -Xmx3g -jar [this .jar] grammar_file lexicon_file testsuite_file beam_size [output_file]");
		}
	}

}
