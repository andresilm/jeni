package synalp.generation.probabilistic.guidemo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import synalp.commons.input.TestSuiteEntry;
import synalp.commons.output.MorphRealization;
import synalp.commons.output.SyntacticRealization;
import synalp.commons.utils.Utils;
import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.jeni.JeniGenerator;
import synalp.generation.jeni.JeniRealization;

public class GeneratorThread extends Thread
{
	private AppConfiguration appConfig;
	JTextArea genTextArea; JProgressBar progressBar;
	int entryNum=0,totalEntries=0;
	JLabel lblGenerationInProcess;
	
	public void setConfig(AppConfiguration appConfig) {
		this.appConfig = appConfig;
	}
	
	void setWidgetsToUpdate(JTextArea genTextArea, JProgressBar progressBar,JLabel lblGenerationInProcess) {
		this.genTextArea = genTextArea;
		this.progressBar = progressBar;
		this.lblGenerationInProcess = lblGenerationInProcess;
	}
    public void run() {
        startGeneration();
    }

    public static void main(String args[]) {
    
    }
    
    void addToTextArea(String text)
	{
		genTextArea.append(text);

	}
    
    public void startGeneration()
	{

		if (this.appConfig == null)
		{
			System.out.println("Configuration is null");
		}
		else
		{
			GeneratorConfiguration config = this.appConfig.generationConfig;

			JeniGenerator generator = new JeniGenerator(config);
			String time = LocalDateTime.now().toString().split("T")[1];
			String date = LocalDateTime.now().toString().split("T")[0];

			if (appConfig.isVerboseOutput())
			{
				genTextArea.append("= Generation started at" + "\n");
				System.out.println("= Generation started at");

				addToTextArea("*Time: " + time + "\n");
				System.out.println("*Time: " + time);

				addToTextArea("*Date: " + date + "\n\n");
				System.out.println("*Date: " + date + "\n");
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
			System.out.println("== File resources info:");

			addToTextArea("*Grammar: " + appConfig.getGrammarSource() + "\n");
			System.out.println("*Grammar: " + appConfig.getGrammarSource());

			addToTextArea("*Lexicon: " + appConfig.getLexiconSource() + "\n");
			System.out.println("*Lexicon: " + appConfig.getLexiconSource());

			addToTextArea("*Testsuite: " + appConfig.getTestsuiteSource() + "\n");
			System.out.println("*Testsuite: " + appConfig.getTestsuiteSource());
if (appConfig.isVerboseOutput())
{
			addToTextArea("\n");
			System.out.print("\n");

			addToTextArea("== PJeni Generator configuration info\n");
			System.out.println("== PJeni Generator configuration info");

			addToTextArea("*Beam size: " + config.getOption("beam_size") + "\n\n");
			System.out.println("*Beam size: " + config.getOption("beam_size") + "\n");

			addToTextArea("== Testsuite details:\n");
			System.out.println("== Testsuite details:");

			addToTextArea("*#Tests: " + config.getTestSuite().size() + "\n\n");
			System.out.println("*#Tests: " + config.getTestSuite().size() + "\n");
}
else {
	addToTextArea("== Beam size: " + config.getOption("beam_size")+"\n");
	addToTextArea("== Number of tests in suite: " +  config.getTestSuite().size() + "\n");
}

			if (appConfig.isVerboseOutput()) {
			addToTextArea("== Starting sentences generation\n\n");
			System.out.println("== Starting sentences generation\n");
			}

			for(TestSuiteEntry entry : config.getTestSuite())
			{
				this.progressBar.setValue(this.entryNum);
				this.progressBar.setString(this.entryNum + "/" + this.totalEntries);
				this.progressBar.update(this.progressBar.getGraphics());

				addToTextArea("=== Input #" + entryNum + "\n");
				System.out.println("=== Input #" + entryNum);

				addToTextArea("*Test item ID: " + entry.getId() + "\n");
				System.out.println("*Test item ID: " + entry.getId());

				addToTextArea("*Semantics: " + entry.getSemantics().toString() + "\n");
				System.out.println("*Semantics: " + entry.getSemantics().toString() + "\n");

				List<JeniRealization> results = generator.generate(entry.getSemantics());
				if (results.isEmpty())
				{
					addToTextArea("! No sentence was generated !\n");
					System.out.println("! No sentence was generated !\n");
				}
				int sentenceCount = 1;

				Map<String, Integer> resultsGrouped = groupRealizations(results);

				for(String real : resultsGrouped.keySet())
				{
					if (appConfig.isVerboseOutput())
					{
						//addToTextArea("GENERATED SENTENCE #" + sentenceCount + ":\n" + getSurface(real,true)+ "\n\n");
						addToTextArea("==== Realization #" + sentenceCount + "\n");
						System.out.println("==== Realization #" + sentenceCount);

						addToTextArea("*Sentence: " + real.split(":")[0] + "\n");
						System.out.println("*Sentence: " + real.split(":")[0]);

						System.out.println("*Times: " + resultsGrouped.get(real));
						addToTextArea("*Times: " + resultsGrouped.get(real) + "\n");

						addToTextArea("*Probability: " + real.split(":")[1] + "\n\n");
						System.out.println("*Probability: " + real.split(":")[1] + "\n");
					}
					else
					{
						addToTextArea("\"" + real.split(":")[0] + "\"" + " (" + resultsGrouped.get(real) + "," + real.split(":")[1] + ")\n");
					}

					++sentenceCount;
					genTextArea.update(genTextArea.getGraphics());
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
				System.out.println("*Time: " + time);

				addToTextArea("*Date: " + date + "\n\n");
				System.out.println("*Date: " + date + "\n");
			}
			else
			{
				addToTextArea("= Ended at " + time + " " + date + "\n");
			}

		}
		this.stop();
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
