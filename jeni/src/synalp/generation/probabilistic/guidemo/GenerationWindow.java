package synalp.generation.probabilistic.guidemo;

import java.awt.BorderLayout;

import synalp.commons.input.TestSuiteEntry;
import synalp.commons.output.MorphRealization;
import synalp.commons.output.SyntacticRealization;
import synalp.commons.utils.Utils;
import synalp.commons.utils.exceptions.TimeoutException;
import synalp.generation.Generator;
import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.configuration.GeneratorConfigurations;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import synalp.generation.configuration.GeneratorConfigurations;
import synalp.generation.jeni.JeniGenerator;
import synalp.generation.jeni.JeniRealization;
import synalp.generation.probabilistic.guidemo.AppConfiguration;

import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class GenerationWindow extends JFrame
{

	private JPanel contentPane;

	JTextArea genTextArea;
	JScrollPane scroll;
	AppConfiguration appConfig;
	boolean generationDone = false;


	/**
	 * Create the frame.
	 */
	public GenerationWindow(AppConfiguration appConfig)
	{
		this.appConfig = appConfig;
		setTitle("Generation results");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setMaximumSize(new Dimension(1280, 1024));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
											gl_contentPane	.createParallelGroup(Alignment.LEADING)
															.addGap(0, 630, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(
										gl_contentPane	.createParallelGroup(Alignment.LEADING)
														.addGap(0, 470, Short.MAX_VALUE));
		contentPane.setLayout(gl_contentPane);

		JPanel panel = new JPanel();

	}


	@Override
	public void setVisible(boolean b)
	{

		super.setVisible(b);
		if (b && !generationDone)
		{
			startGeneration();
			generationDone = true;
		}
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

			System.out.println("= Generation started at");
			System.out.println("*Time: " + time);
			System.out.println("*Date: " + date + "\n");

			int entryNum = 1;
			System.out.println("== File resources info:");
			System.out.println("*Grammar: " + appConfig.getGrammarSource());
			System.out.println("*Lexicon: " + appConfig.getLexiconSource());
			System.out.println("*Testsuite: " + appConfig.getTestsuiteSource());
		
				
			System.out.print("\n");

			System.out.println("== PJeni Generator configuration info");
			System.out.println("*Beam size: " + config.getOption("beam_size") + "\n");

			System.out.println("== Testsuite details:");
			System.out.println("*#Tests: " + config.getTestSuite().size() + "\n");
			System.out.println("== Starting sentences generation\n");
			for(TestSuiteEntry entry : config.getTestSuite())
			{

				//genTextArea.append("TESTSUITE ENTRY #" + entryNum + "\n\n");
				System.out.println("=== Input #" + entryNum);

				System.out.println("*Semantics: " + entry.getSemantics().toString() + "\n");
				//genTextArea.append("INPUT SEMANTICS:\n" + entry.getSemantics().toString() + "\n\n");

				List<JeniRealization> results = generator.generate(entry.getSemantics());
				if (results.isEmpty())
				{
					System.out.println("! No sentence was generated !\n\n");
				}
				int sentenceCount = 1;
				
				Map<String,Integer> resultsGrouped = groupRealizations(results);
				
				for(String real : resultsGrouped.keySet())
				{
					//genTextArea.append("GENERATED SENTENCE #" + sentenceCount + ":\n" + getSurface(real,true)+ "\n\n");
					System.out.println("==== Realization #" + sentenceCount);
					System.out.println("*Sentence: " + real.split(":")[0]);
					System.out.println("*Times: " + resultsGrouped.get(real));

					//genTextArea.append("PROBABILITY:\n" + real.getProbability() + "\n\n");
					System.out.println("*Probability: " + real.split(":")[1] + "\n");
					++sentenceCount;
					//genTextArea.update(genTextArea.getGraphics());
				}
				++entryNum;
				//break;//just one entry for now

			}
			time = LocalDateTime.now().toString().split("T")[1];
			date = LocalDateTime.now().toString().split("T")[0];

			System.out.println("= Generation ended at");
			System.out.println("*Time: " + time);
			System.out.println("*Date: " + date + "\n");

			
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
	
	private static Map<String, Integer> groupRealizations( List<JeniRealization> results)
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
