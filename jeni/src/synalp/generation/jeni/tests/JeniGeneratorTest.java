package synalp.generation.jeni.tests;

import java.io.File;
import java.util.Arrays;

import org.junit.*;
import org.junit.runners.MethodSorters;

import synalp.commons.grammar.*;
import synalp.commons.input.TestSuite;
import synalp.commons.lexicon.*;
import synalp.commons.semantics.*;
import synalp.commons.tests.GeneratorTest;
import synalp.commons.utils.*;
import synalp.commons.utils.configuration.ResourcesBundleType;
import synalp.commons.utils.configuration.ResourcesBundleFile;
import synalp.generation.configuration.*;
import synalp.generation.jeni.*;
import synalp.generation.jeni.selection.families.*;
import synalp.generation.jeni.semantics.*;
import synalp.generation.jeni.semantics.rules.RuleReader;
import synalp.generation.morphology.*;

import static synalp.commons.utils.Resources.*;

/**
 * Tests JeniGenerator.
 * @author Alexandre Denis
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JeniGeneratorTest extends GeneratorTest
{
	/**
	 * Tests Jeni on the given resource bundle (includes grammar, lexicon and test suite).
	 * @param bundle
	 */
	private void testJeni(ResourceBundle bundle)
	{
		testJeni(bundle, false);
	}


	/**
	 * Tests Jeni on the given resource bundle (includes grammar, lexicon and test suite).
	 * @param bundle
	 * @param catchTimeout
	 */
	private void testJeni(ResourceBundle bundle, boolean catchTimeout)
	{
		loadBundle(bundle);
		test(new JeniGenerator(bundle), bundle.getTestSuite(), catchTimeout);
	}


	/**
	 * This test is kept as an example.
	 */
	@Test
	public void test1_Bill_sleep()
	{
		System.out.println("\n**** Bill sleep");
		test(new JeniGenerator(loadBundle(ResourcesBundleType.MINIMAL_BUNDLE.getBundle())),
				Semantics.readSemantics("A0_1:sleep(e0 a0) A0_1:bill(a0)"), "Bill sleep");
	}
	
	@Test
	public void test1_probabilistic()
	{
		System.out.println("\n**** Test Probabilistic");
		test(new JeniGenerator(loadBundle(ResourcesBundleType.PROBABILISTIC_BUNDLE.getBundle())),
				Semantics.readSemantics("birthPlace(e x y) Yury_Usachov(x) Russia(y)"));
	}


	@Test
	@SuppressWarnings("javadoc")
	public void test2_SuiteTranssem()
	{
		testJeni(ResourcesBundleType.TRANSSEM_BUNDLE.getBundle());
	}


	@Test
	@SuppressWarnings("javadoc")
	public void test3_SuiteFrench()
	{
		testJeni(ResourcesBundleType.FRENCH_BUNDLE.getBundle());
	}


	@Test
	@SuppressWarnings("javadoc")
	public void test4_SuiteSemXTAG2()
	{
		testJeni(ResourcesBundleType.SEMXTAG2_BUNDLE.getBundle());
	}


	@Test
	@SuppressWarnings("javadoc")
	public void test5_SuiteSemXTAG2AutoIdx()
	{
		Grammar grammar = loadGrammar(ResourcesBundleFile.SEMXTAG2_GRAMMAR.getFile());
		FamiliesSemantics traces = FamilySemanticsReader.readFamiliesSemanticsNoException(ResourcesBundleFile.SEMXTAG2_AUTO_SEM.getFile());

		SemanticsBuilder builder = new SemanticsBuilder(traces);
		builder.setSemantics(grammar);
		GrammarWriter.write(grammar, ResourcesBundleFile.SEMXTAG2_AUTO_GRAMMAR.getFile());

		SyntacticLexicon lexicon = builder.createLexicon(grammar);
		SyntacticLexiconWriter.write(lexicon, ResourcesBundleFile.SEMXTAG2_AUTO_LEXICON.getFile());

		MorphRealizer morphRealizer = new DefaultMorphRealizer(loadMorphLexicon(ResourcesBundleFile.SEMXTAG2_MORPH.getFile()));
		JeniGenerator generator = new JeniGenerator(grammar, lexicon, morphRealizer);
		generator.setRules(RuleReader.readRulesNoException(ResourcesBundleFile.SEMXTAG2_AUTO_RULES.getFile()));

		test(generator, loadTestSuite(ResourcesBundleFile.SEMXTAG2_AUTO_TESTSUITE.getFile()));
	}


	@Test
	@SuppressWarnings("javadoc")
	public void test7_Performance()
	{
		Grammar grammar = loadGrammar(ResourcesBundleFile.SEMXTAG2_GRAMMAR.getFile());
		FamiliesSemantics traces = FamilySemanticsReader.readFamiliesSemanticsNoException(ResourcesBundleFile.SEMXTAG2_AUTO_SEM.getFile());

		SemanticsBuilder builder = new SemanticsBuilder(traces);
		builder.setSemantics(grammar);
		GrammarWriter.write(grammar, ResourcesBundleFile.SEMXTAG2_AUTO_GRAMMAR.getFile());

		SyntacticLexicon lexicon = builder.createLexicon(grammar);
		SyntacticLexiconWriter.write(lexicon, ResourcesBundleFile.SEMXTAG2_AUTO_LEXICON.getFile());

		MorphRealizer morphRealizer = new DefaultMorphRealizer(loadMorphLexicon(ResourcesBundleFile.SEMXTAG2_AUTO_MORPH.getFile()));
		JeniGenerator generator = new JeniGenerator(grammar, lexicon, morphRealizer);
		generator.setRules(RuleReader.readRulesNoException(ResourcesBundleFile.SEMXTAG2_AUTO_RULES.getFile()));

		TestSuite testSuite = loadTestSuite(ResourcesBundleFile.SEMXTAG2_AUTO_TESTSUITE.getFile());

		int n = 100;
		long[] times = new long[n];
		for(int i = 0; i < n; i++)
		{
			Perf.logStart("test");
			//test(generator, testSuite);
			//test5_SuiteSemXTAG2AutoIdx();

			test(generator, testSuite);

			times[i] = Perf.logEnd("test");
		}

		long median = 0;
		Arrays.sort(times);
		int middle = ((times.length) / 2);
		if (times.length % 2 == 0)
		{
			long medianA = times[middle];
			long medianB = times[middle - 1];
			median = (medianA + medianB) / 2;
		}
		else median = times[middle + 1];

		long sum = 0;
		for(int i = 0; i < n; i++)
			sum += times[i];

		System.out.println("Median: " + Perf.formatTime(median) + " Avg: " + Perf.formatTime(sum / n));
	}


	@Test
	@SuppressWarnings("javadoc")
	public void test8_PatternLexicalSelection()
	{
		GeneratorConfiguration config = GeneratorConfigurations.getConfig("semxtag");
		JeniGenerator generator = new JeniGenerator(config);
		test(generator, config.getTestSuite());
	}


	@Test
	@SuppressWarnings("javadoc")
	public void test6_KBGen()
	{
		//GeneratorOptions.USE_FILTERING = false;
		testJeni(ResourcesBundleType.KBGEN_BUNDLE.getBundle(), true);
	}
}
