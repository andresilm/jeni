package synalp.commons.utils;

import java.io.File;

import synalp.commons.grammar.Grammar;
import synalp.commons.input.TestSuite;
import synalp.commons.lexicon.SyntacticLexicon;
import synalp.generation.morphology.MorphLexicon;


/**
 * A ResourceBundle is a quadruplet of Grammar, SyntacticLexicon, TestSuite and MorphLexicon.
 * @author Alexandre Denis
 */
public class ResourceBundle
{
	private Grammar grammar;
	private TestSuite testSuite;
	private MorphLexicon morphLexicon;
	private SyntacticLexicon synLexicon;

	private File grammarFile;
	private File testSuiteFile;
	private File synLexiconFile;
	private File morphLexiconFile;


	/**
	 * @param grammarFile
	 * @param synLexiconFile
	 * @param testSuiteFile
	 */
	public ResourceBundle(File grammarFile, File synLexiconFile, File testSuiteFile)
	{
		this.grammarFile = grammarFile;
		this.testSuiteFile = testSuiteFile;
		this.synLexiconFile = synLexiconFile;
	}


	/**
	 * @param grammarFile
	 * @param synLexiconFile
	 * @param testSuiteFile
	 * @param morphLexiconFile
	 */
	public ResourceBundle(File grammarFile, File synLexiconFile, File testSuiteFile, File morphLexiconFile)
	{
		this.grammarFile = grammarFile;
		this.testSuiteFile = testSuiteFile;
		this.synLexiconFile = synLexiconFile;
		this.morphLexiconFile = morphLexiconFile;
	}


	/**
	 * @param grammar
	 * @param synLexicon
	 * @param testSuite
	 */
	public ResourceBundle(Grammar grammar, SyntacticLexicon synLexicon, TestSuite testSuite)
	{
		this.grammar = grammar;
		this.testSuite = testSuite;
		this.synLexicon = synLexicon;
	}


	/**
	 * @param grammar the grammar to set
	 */
	public void setGrammar(Grammar grammar)
	{
		this.grammar = grammar;
	}


	/**
	 * @param testSuite the testSuite to set
	 */
	public void setTestSuite(TestSuite testSuite)
	{
		this.testSuite = testSuite;
	}


	/**
	 * @param synLexicon the synLexicon to set
	 */
	public void setSyntacticLexicon(SyntacticLexicon synLexicon)
	{
		this.synLexicon = synLexicon;
	}


	/**
	 * @param grammarFile the grammarFile to set
	 */
	public void setGrammarFile(File grammarFile)
	{
		this.grammarFile = grammarFile;
	}


	/**
	 * @param testSuiteFile the testSuiteFile to set
	 */
	public void setTestSuiteFile(File testSuiteFile)
	{
		this.testSuiteFile = testSuiteFile;
	}


	/**
	 * @param synLexiconFile the synLexiconFile to set
	 */
	public void setSynLexiconFile(File synLexiconFile)
	{
		this.synLexiconFile = synLexiconFile;
	}


	/**
	 * @return the grammarFile
	 */
	public File getGrammarFile()
	{
		return grammarFile;
	}


	/**
	 * @return the testSuiteFile
	 */
	public File getTestSuiteFile()
	{
		return testSuiteFile;
	}


	/**
	 * @return the synLexiconFile
	 */
	public File getSyntacticLexiconFile()
	{
		return synLexiconFile;
	}


	/**
	 * @return the grammar
	 */
	public Grammar getGrammar()
	{
		return grammar;
	}


	/**
	 * @return the testSuite
	 */
	public TestSuite getTestSuite()
	{
		return testSuite;
	}


	/**
	 * @return the synLexicon
	 */
	public SyntacticLexicon getSyntacticLexicon()
	{
		return synLexicon;
	}


	/**
	 * @return the morphLexicon
	 */
	public MorphLexicon getMorphLexicon()
	{
		return morphLexicon;
	}


	/**
	 * @param morphLexicon the morphLexicon to set
	 */
	public void setMorphLexicon(MorphLexicon morphLexicon)
	{
		this.morphLexicon = morphLexicon;
	}


	/**
	 * @return the morphLexiconFile
	 */
	public File getMorphLexiconFile()
	{
		return morphLexiconFile;
	}


	/**
	 * @param morphLexiconFile the morphLexiconFile to set
	 */
	public void setMorphLexiconFile(File morphLexiconFile)
	{
		this.morphLexiconFile = morphLexiconFile;
	}


	/**
	 * Loads this bundle.
	 */
	public void load()
	{
		Resources.loadBundle(this);
	}

}
