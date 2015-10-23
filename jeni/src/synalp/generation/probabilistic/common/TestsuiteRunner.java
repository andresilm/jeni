package synalp.generation.probabilistic.common;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import synalp.commons.input.TestSuiteEntry;
import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.jeni.JeniRealization;
import synalp.generation.probabilistic.ProbabilisticGenerator;
import synalp.generation.probabilistic.Surface;

public class TestsuiteRunner
{
	public static void runJeniConfiguration(AppConfiguration appConfig, OutputStreamWriter out) throws IOException
	{
		int entryNum=0;
		

		if (appConfig == null)
		{
			System.err.println("Configuration is null");
		}
		else
		{
			DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
			df.setMaximumFractionDigits(3); //340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

			//System.out.println(df.format(myValue))
			GeneratorConfiguration config = appConfig.getGenerationConfig();

			ProbabilisticGenerator generator = new ProbabilisticGenerator(config);
			String time = LocalDateTime.now().toString().split("T")[1];
			String date = LocalDateTime.now().toString().split("T")[0];

			if (appConfig.isVerboseOutput())
			{
				out.write("= Generation started at" + "\n");
			

				out.write("*Time: " + time + "\n");
			

				out.write("*Date: " + date + "\n\n");
		
			}
			else
			{
				out.write("= Started at " + time + " " + date + "\n");
			}

			

			out.write("== File resources info:" + "\n");
		

			out.write("*Grammar: " + appConfig.getGrammarSource() + "\n");
		

			out.write("*Lexicon: " + appConfig.getLexiconSource() + "\n");
			
		

			if (appConfig.getUserInputType() == 0)
			{
				out.write("*Testsuite: " + appConfig.getTestsuiteSource() + "\n");
				
			}

			if (appConfig.isVerboseOutput())
			{
				out.write("\n");
				

				out.write("== PJeni Generator configuration info\n");
				

				out.write("*Beam size: " + config.getOption("beam_size") + "\n\n");
				

				if (appConfig.getUserInputType() == 0)
				{
					out.write("== Testsuite details:\n");
				
					out.write("*#Tests: " + config.getTestSuite().size() + "\n\n");
				
				}
				else
				{
					out.write("== Generating from specified input\n");
				}

			}
			else
			{
				out.write("== Beam size: " + config.getOption("beam_size") + "\n");
				if (appConfig.getUserInputType() == 0)
					out.write("== Number of tests in suite: " + config.getTestSuite().size() + "\n");
			}

			if (appConfig.isVerboseOutput())
			{
				out.write("== Starting sentences generation\n\n");
				
			}
			
			for(TestSuiteEntry entry : config.getTestSuite())
			{
				

				out.write("=== Input #" + entryNum + "\n");
				

				out.write("*Test item ID: " + entry.getId() + "\n");
				

				out.write("*Semantics: " + entry.getSemantics().toString() + "\n");
				

				List<JeniRealization> results = generator.generate(entry.getSemantics());
				if (results.isEmpty())
				{
					out.write("No sentence\n");
				
				}
				int sentenceCount = 1;

				List<Surface> resultsGrouped = Surface.groupSurfaces(results);
				
				
				if (results.size()>0 && resultsGrouped.size() == 0)
					out.write("ERROR when getting surfaces\n");
				
				for(Surface surf : resultsGrouped)
				{
					
					
					
					
					if (appConfig.isVerboseOutput())
					{
						//out.write("GENERATED SENTENCE #" + sentenceCount + ":\n" + getSurface(real,true)+ "\n\n");
						out.write("==== Surface #" + sentenceCount + "\n");
				

						out.write("*Sentence: " + surf.getSentence() + "\n");
				

				
						out.write("*Times: " + surf.getTimes() + "\n");

						out.write("*Probability: " + df.format(surf.getProbability()) + "\n\n");
				
					}
					else
					{
						out.write("\"" + surf.getSentence() + "\"" + " (" + surf.getTimes()  + "," + df.format(surf.getProbability()) + ")\n");
					}

					++sentenceCount;

				}
				++entryNum;

			}
			
			out.flush();

			

			time = LocalDateTime.now().toString().split("T")[1];
			date = LocalDateTime.now().toString().split("T")[0];

			if (appConfig.isVerboseOutput())
			{
				out.write("= Generation ended at" + "\n");
			

				out.write("*Time: " + time + "\n");
				

				out.write("*Date: " + date + "\n\n");
				
			}
			else
			{
				out.write("= Ended at " + time + " " + date + "\n");
			}

		}

	}

}
