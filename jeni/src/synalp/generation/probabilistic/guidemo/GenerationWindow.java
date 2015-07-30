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



	/**
	 * Create the frame.
	 */
	public GenerationWindow()
	{
		setTitle("Generation results");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 473, 391);
		contentPane = new JPanel();
		contentPane.setMaximumSize(new Dimension(1280, 1024));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		
		genTextArea = new JTextArea();
		genTextArea.setEditable(false);
		//genTextArea.setText("test\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nscrolling");
		genTextArea.setColumns(36);
		genTextArea.setRows(19);
		panel.add(genTextArea);
		scroll= new JScrollPane(genTextArea);
		panel.add(scroll);
		contentPane.setLayout(gl_contentPane);
		
		
	}
	
	public void startGeneration(GeneratorConfiguration config) {
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
