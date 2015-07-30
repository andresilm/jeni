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
import synalp.generation.probabilistic.guidemo.PJeniDemoAppConfiguration;

import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

public class GenerationWindow extends JFrame
{

	private JPanel contentPane;

	JTextArea genTextArea;
	JScrollPane scroll;
	PJeniDemoAppConfiguration appConfig;
	boolean generationDone = false;

	/**
	 * Create the frame.
	 */
	public GenerationWindow(PJeniDemoAppConfiguration appConfig)
	{
		setTitle("Generation results");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setMaximumSize(new Dimension(1280, 1024));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JButton btnNewButton = new JButton("Save results");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
						.addComponent(btnNewButton))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(btnNewButton))
		);
		
		genTextArea = new JTextArea();
		genTextArea.setEditable(false);
		//genTextArea.setText("test\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nscrolling");
		genTextArea.setColumns(80);
		genTextArea.setRows(35);
		panel.add(genTextArea);
		scroll= new JScrollPane(genTextArea);
		panel.add(scroll);
		contentPane.setLayout(gl_contentPane);
		
		
		this.appConfig = appConfig;
		
	}
	
	@Override
	public void setVisible(boolean b) {
		
		super.setVisible(b);
		if (b && !generationDone) {
			startGeneration();
			generationDone = true;
		}
	}
	

	
	public void startGeneration() {
		GeneratorConfiguration config = this.appConfig.generationConfig;
		
		if (config == null) {
			System.out.println("Configuration is null");
		}
		else {
		
		JeniGenerator generator = new JeniGenerator(config);
		
		genTextArea.append("=== GENERATION STARTED AT " + LocalDateTime.now().toString() + "===\n\n");
		System.out.println(LocalDateTime.now().toString() + "\n");
		int entryNum = 1;
		System.out.println("*RESOURCES FILE INFO:");
		System.out.println("GRAMMAR: ");
		System.out.println("LEXICON: ");
		System.out.println("TESTSUITE: " + "\n");
		
		                   System.out.println("#ENTRIES: " + config.getTestSuite().size() + "\n");
		for(TestSuiteEntry entry : config.getTestSuite())
		{
			
			genTextArea.append("TESTSUITE ENTRY #" + entryNum + "\n\n");
			System.out.println("TESTSUITE ENTRY #" + entryNum + "\n");
			
			System.out.println("INPUT SEMANTICS:\n" + entry.getSemantics().toString() + "\n");
			genTextArea.append("INPUT SEMANTICS:\n" + entry.getSemantics().toString() + "\n\n");
			
			
			List<JeniRealization> results = generator.generate(entry.getSemantics());
			if (results.isEmpty()) {
				genTextArea.append("*** NO SENTENCE WAS GENERATED ***\n\n");
			}
			int sentenceCount = 1;
			
			
			for (JeniRealization real: results) {
				genTextArea.append("GENERATED SENTENCE #" + sentenceCount + ":\n" + getSurface(real,true)+ "\n\n");
				System.out.println("GENERATED SENTENCE #" + sentenceCount + ":\n" + getSurface(real,true)+ "\n");
				
				genTextArea.append("PROBABILITY:\n" + real.getProbability() + "\n\n");
				System.out.println("PROBABILITY:\n" + real.getProbability() + "\n");
				++sentenceCount;
				genTextArea.update(genTextArea.getGraphics());
			}
			++entryNum;
			//break;//just one entry for now
			
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
}
