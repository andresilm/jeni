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
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GenerationWindow extends JFrame
{

	private JPanel contentPane;

	JTextArea genTextArea;
	JScrollPane scroll;
	AppConfiguration appConfig;
	boolean generationDone = false;
	boolean stopButtonPressed = false;


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
	
		
		
		genTextArea = new JTextArea();
		genTextArea.setEditable(false);
		genTextArea.setLineWrap(true);
		genTextArea.setText("");
		
		scroll = new JScrollPane(genTextArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scroll);
		
		GenerationWindow thisWindow = this;
		JButton btnSaveToFIle = new JButton("Save results as...");
		btnSaveToFIle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
			
			
				fileChooser.setDialogTitle("Specify a file to save");   
				 
				int userSelection = fileChooser.showSaveDialog(thisWindow);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				    try
					{
						thisWindow.saveTextAreaToFile(fileToSave);
					}
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(12, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(genTextArea, GroupLayout.PREFERRED_SIZE, 605, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSaveToFIle))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(genTextArea, GroupLayout.PREFERRED_SIZE, 388, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnSaveToFIle)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);

		

	}
	
	void saveTextAreaToFile(File file) throws IOException {
		String text = genTextArea.getText();
		FileOutputStream output = new FileOutputStream(file);
		for (char c: text.toCharArray()) {
			output.write((int)c);
		}
		output.close();
		
		
		
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
	
	void addToTextArea(String text) {
		genTextArea.append(text);
		genTextArea.update(genTextArea.getGraphics());
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

			genTextArea.append("= Generation started at"+"\n");
			System.out.println("= Generation started at");
			
			addToTextArea("*Time: " + time + "\n");
			System.out.println("*Time: " + time);
			
			addToTextArea("*Date: " + date + "\n\n");
			System.out.println("*Date: " + date + "\n");

			int entryNum = 1;
			
			addToTextArea("== File resources info:" + "\n");
			System.out.println("== File resources info:");
			
			addToTextArea("*Grammar: " + appConfig.getGrammarSource() + "\n");
			System.out.println("*Grammar: " + appConfig.getGrammarSource());
			
			addToTextArea("*Lexicon: " + appConfig.getLexiconSource() + "\n");
			System.out.println("*Lexicon: " + appConfig.getLexiconSource());
			
			addToTextArea("*Testsuite: " + appConfig.getTestsuiteSource() + "\n");
			System.out.println("*Testsuite: " + appConfig.getTestsuiteSource());
		
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
			
			addToTextArea("== Starting sentences generation\n\n");
			System.out.println("== Starting sentences generation\n");
			
			for(TestSuiteEntry entry : config.getTestSuite())
			{

				addToTextArea("=== Input #" + entryNum + "\n");
				System.out.println("=== Input #" + entryNum);
				
				addToTextArea("*Test item ID: " + entry.getId() + "\n");
				System.out.println("*Test item ID: " + entry.getId());
				
				addToTextArea("*Semantics: " + entry.getSemantics().toString() + "\n\n");
				System.out.println("*Semantics: " + entry.getSemantics().toString() + "\n");
			
			

				List<JeniRealization> results = generator.generate(entry.getSemantics());
				if (results.isEmpty())
				{
					addToTextArea("! No sentence was generated !\n\n");
					System.out.println("! No sentence was generated !\n");
				}
				int sentenceCount = 1;
				
				Map<String,Integer> resultsGrouped = groupRealizations(results);
				
				for(String real : resultsGrouped.keySet())
				{
					
					//addToTextArea("GENERATED SENTENCE #" + sentenceCount + ":\n" + getSurface(real,true)+ "\n\n");
					addToTextArea("==== Realization #" + sentenceCount + "\n");
					System.out.println("==== Realization #" + sentenceCount);
					
					addToTextArea("*Sentence: " + real.split(":")[0]+"\n");
					System.out.println("*Sentence: " + real.split(":")[0]);
					
					
					System.out.println("*Times: " + resultsGrouped.get(real));
					addToTextArea("*Times: " + resultsGrouped.get(real)+"\n");

					addToTextArea("*Probability: " + real.split(":")[1] + "\n\n");
					System.out.println("*Probability: " + real.split(":")[1] + "\n");
					++sentenceCount;
					
				}
				++entryNum;
				//break;//just one entry for now
				if (stopButtonPressed)
					break;

			}
			time = LocalDateTime.now().toString().split("T")[1];
			date = LocalDateTime.now().toString().split("T")[0];
			
			addToTextArea("= Generation ended at"+"\n");
			System.out.println("= Generation ended at");
			
			addToTextArea("*Time: " + time);
			System.out.println("*Time: " + time);
			
			addToTextArea("*Date: " + date + "\n\n");
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
