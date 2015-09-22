package synalp.generation.probabilistic.guidemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import org.xml.sax.SAXException;

import synalp.commons.input.TestSuiteEntry;
import synalp.commons.output.MorphRealization;
import synalp.commons.output.SyntacticRealization;
import synalp.commons.utils.Utils;
import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.configuration.GeneratorConfigurations;
import synalp.generation.jeni.JeniGenerator;
import synalp.generation.jeni.JeniRealization;
import synalp.generation.probabilistic.ProbabilisticGenerator;
import synalp.generation.configuration.GeneratorConfigurationReader;

public class GeneratorThread extends Thread
{
	private AppConfiguration appConfig;
	JTextArea genTextArea;
	JProgressBar progressBar;
	int entryNum = 0, totalEntries = 0;
	JLabel lblGenerationInProcess;


	public void setConfig(AppConfiguration appConfig)
	{
		this.appConfig = appConfig;
	}


	void setWidgetsToUpdate(JTextArea genTextArea, JProgressBar progressBar, JLabel lblGenerationInProcess)
	{
		this.genTextArea = genTextArea;
		this.progressBar = progressBar;
		this.lblGenerationInProcess = lblGenerationInProcess;
	}


	public void run()
	{

		startGeneration();
	}


	public static void main(String args[])
	{

	}


	void addToTextArea(String text)
	{
		genTextArea.append(text);

	}


	public void startGeneration()
	{

		if (this.appConfig == null)
		{
			System.err.println("Configuration is null");
		}
		else
		{
			GeneratorConfiguration config = this.appConfig.getGenerationConfig();

			ProbabilisticGenerator generator = new ProbabilisticGenerator(config);
			String time = LocalDateTime.now().toString().split("T")[1];
			String date = LocalDateTime.now().toString().split("T")[0];

			if (appConfig.isVerboseOutput())
			{
				genTextArea.append("= Generation started at" + "\n");
			

				addToTextArea("*Time: " + time + "\n");
			

				addToTextArea("*Date: " + date + "\n\n");
		
			}
			else
			{
				addToTextArea("= Started at " + time + " " + date + "\n");
			}

			this.totalEntries = config.getTestSuite().size();
			this.progressBar.setMaximum(this.totalEntries);
			this.progressBar.setString(0 + "/" + this.totalEntries);
			this.progressBar.update(this.progressBar.getGraphics());
			this.lblGenerationInProcess.setText("Generation in process...");

			addToTextArea("== File resources info:" + "\n");
		

			addToTextArea("*Grammar: " + appConfig.getGrammarSource() + "\n");
		

			addToTextArea("*Lexicon: " + appConfig.getLexiconSource() + "\n");
			
		

			if (this.appConfig.getUserInputType() == 0)
			{
				addToTextArea("*Testsuite: " + appConfig.getTestsuiteSource() + "\n");
				
			}

			if (appConfig.isVerboseOutput())
			{
				addToTextArea("\n");
				

				addToTextArea("== PJeni Generator configuration info\n");
				

				addToTextArea("*Beam size: " + config.getOption("beam_size") + "\n\n");
				

				if (appConfig.getUserInputType() == 0)
				{
					addToTextArea("== Testsuite details:\n");
				
					addToTextArea("*#Tests: " + config.getTestSuite().size() + "\n\n");
				
				}
				else
				{
					addToTextArea("== Generating from specified input\n");
				}

			}
			else
			{
				addToTextArea("== Beam size: " + config.getOption("beam_size") + "\n");
				if (this.appConfig.getUserInputType() == 0)
					addToTextArea("== Number of tests in suite: " + config.getTestSuite().size() + "\n");
			}

			if (appConfig.isVerboseOutput())
			{
				addToTextArea("== Starting sentences generation\n\n");
				
			}

			for(TestSuiteEntry entry : config.getTestSuite())
			{
				System.err.println(entry.getSemantics());
				this.progressBar.setValue(this.entryNum);
				this.progressBar.setString(this.entryNum + "/" + this.totalEntries);
				this.progressBar.update(this.progressBar.getGraphics());

				addToTextArea("=== Input #" + entryNum + "\n");
				

				addToTextArea("*Test item ID: " + entry.getId() + "\n");
				

				addToTextArea("*Semantics: " + entry.getSemantics().toString() + "\n");
				

				List<JeniRealization> results = generator.generate(entry.getSemantics());
				if (results.isEmpty())
				{
					addToTextArea("No sentence\n");
				
				}
				int sentenceCount = 1;

				Map<String, Integer> resultsGrouped = groupRealizations(results);

				for(String real : resultsGrouped.keySet())
				{
					if (appConfig.isVerboseOutput())
					{
						//addToTextArea("GENERATED SENTENCE #" + sentenceCount + ":\n" + getSurface(real,true)+ "\n\n");
						addToTextArea("==== Realization #" + sentenceCount + "\n");
				

						addToTextArea("*Sentence: " + real.split(":")[0] + "\n");
				

				
						addToTextArea("*Times: " + resultsGrouped.get(real) + "\n");

						addToTextArea("*Probability: " + real.split(":")[1] + "\n\n");
				
					}
					else
					{
						addToTextArea("\"" + real.split(":")[0] + "\"" + " (" + resultsGrouped.get(real) + "," + real.split(":")[1] + ")\n");
					}

					++sentenceCount;

				}
				++this.entryNum;

			}

			this.progressBar.setValue(this.entryNum);
			this.progressBar.setString(this.entryNum + "/" + this.totalEntries);
			this.progressBar.update(this.progressBar.getGraphics());
			this.lblGenerationInProcess.setText("Done.");

			time = LocalDateTime.now().toString().split("T")[1];
			date = LocalDateTime.now().toString().split("T")[0];

			if (appConfig.isVerboseOutput())
			{
				addToTextArea("= Generation ended at" + "\n");
				System.out.println("= Generation ended at");

				addToTextArea("*Time: " + time + "\n");
				

				addToTextArea("*Date: " + date + "\n\n");
				
			}
			else
			{
				addToTextArea("= Ended at " + time + " " + date + "\n");
			}

		}

	}


	/**
	 * Returns the surface form of given realizations. If morph is true, returns the morphological
	 * realizations, if false returns the lemmas separated by space.
	 * @param realizations
	 * @param morph
	 * @return a list of surface forms
	 */
	private static List<String> getSurface(List<? extends SyntacticRealization> realizations, boolean morph)
	{
		List<String> ret = new ArrayList<String>();
		for(SyntacticRealization real : realizations)
			ret.addAll(getSurface(real, morph));
		return ret;
	}


	/**
	 * Returns the surface form of given realization. If morph is true, returns the morphological
	 * realizations, if false returns the lemmas separated by space.
	 * @param real
	 * @param morph
	 * @return a list of surface forms
	 */
	private static List<String> getSurface(SyntacticRealization real, boolean morph)
	{
		List<String> ret = new ArrayList<String>();
		if (morph)
		{
			for(MorphRealization morphReal : real.getMorphRealizations())
				ret.add(morphReal.asString());
		}
		else ret.add(Utils.print(real.getLemmas(), " "));
		return ret;
	}


	private static Map<String, Integer> groupRealizations(List<JeniRealization> results)
	{
		Map<String, Integer> ret = new HashMap<String, Integer>();

		for(JeniRealization result : results)
			for(MorphRealization morphReal : result.getMorphRealizations())
			{
				String surface = morphReal.asString() + ":" + result.getProbability();
				if (!ret.containsKey(surface))
					ret.put(surface, 0);
				ret.put(surface, ret.get(surface) + 1);
			}

		return ret;
	}



}
