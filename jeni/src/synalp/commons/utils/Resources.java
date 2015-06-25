package synalp.commons.utils;

import java.io.*;
import java.util.*;

import synalp.commons.grammar.*;
import synalp.commons.input.*;
import synalp.commons.lexicon.*;
import synalp.generation.morphology.*;


/**
 * Resources contain a set of hardcoded static files names and static methods to read and cache
 * resources. This class should be used in conjunction with GeneratorConfiguration somehow.
 * @author Alexandre Denis
 */
@SuppressWarnings("javadoc")
public class Resources
{
	public static Map<File, Grammar> loadedGrammars = new HashMap<File, Grammar>();
	public static Map<File, SyntacticLexicon> loadedLexicons = new HashMap<File, SyntacticLexicon>();


	/**
	 * Loads the given ResourceBundle. This method modifies the given bundle by setting its grammar,
	 * lexicon and test suite.
	 * @return the input bundle with grammar, lexicon and test suite set.
	 */
	public static ResourceBundle loadBundle(ResourceBundle bundle)
	{
		bundle.setGrammar(loadGrammar(bundle.getGrammarFile()));
		bundle.setSyntacticLexicon(loadLexicon(bundle.getSyntacticLexiconFile()));
		bundle.setTestSuite(loadTestSuite(bundle.getTestSuiteFile()));
		bundle.setMorphLexicon(loadMorphLexicon(bundle.getMorphLexiconFile()));
		return bundle;
	}


	/**
	 * Loads the given resources packaged as a ResourceBundle. If the resources were already loaded,
	 * returns the loaded versions.
	 * @param grammar
	 * @param lexicon
	 * @param testSuite
	 * @return
	 */
	public static ResourceBundle loadBundle(File grammar, File lexicon, File testSuite)
	{
		return loadBundle(grammar, lexicon, testSuite, false);
	}


	/**
	 * Loads the given resources packaged as a ResourceBundle.
	 * @param grammar
	 * @param lexicon
	 * @param testSuite
	 * @param forceReload if true, the resources will be reloaded, if false, the loaded resources
	 *            will be returned or loaded for the first time.
	 * @return
	 */
	public static ResourceBundle loadBundle(File grammar, File lexicon, File testSuite, boolean forceReload)
	{
		return new ResourceBundle(loadGrammar(grammar, forceReload),
									loadLexicon(lexicon, forceReload),
									loadTestSuite(testSuite));
	}


	/**
	 * Loads the given grammar file in XML. If the given File is not a grammar or is not found an
	 * exception is caught and print to stdout. If the grammar has already been loaded, it returns
	 * the loaded grammar.
	 * @param file a grammar file in XML format.
	 * @return a Grammar or null if there has been an exception while reading the file
	 */
	public static Grammar loadGrammar(File file)
	{
		return loadGrammar(file, false);
	}


	/**
	 * Loads the given lexicon file in XML. If the given File is not a lexicon or is not found an
	 * exception is caught and print to stdout. If the lexicon has already been loaded, it returns
	 * the loaded lexicon.
	 * @param file a lexicon file in XML format.
	 * @return a SyntacticLexicon or null if there has been an exception while reading the file
	 */
	public static SyntacticLexicon loadLexicon(File file)
	{
		return loadLexicon(file, false);
	}


	/**
	 * Loads the given grammar file in XML. If the given File is not a grammar or is not found an
	 * exception is caught and print to stdout.
	 * @param file a grammar file in XML format.
	 * @param forceReload if true, the grammar will be reloaded, if false, the loaded grammar will
	 *            be returned or loaded for the first time.
	 * @return a Grammar or null if there has been an exception while reading the file
	 */
	public static Grammar loadGrammar(File file, boolean forceReload)
	{
		if (loadedGrammars.containsKey(file) && !forceReload)
			return loadedGrammars.get(file);

		try
		{
			Grammar ret = GrammarReader.readGrammar(file);
			loadedGrammars.put(file, ret);
			return ret;
		}
		catch (Exception e)
		{
			System.err.println("Error: exception while reading grammar " + file + " : " + e.getMessage());
		}

		return null;
	}


	/**
	 * Loads the given lexicon file in XML or LEX format. If the given File is not a lexicon or is
	 * not found an exception is caught and print to stdout.
	 * @param file a lexicon file in XML or LEX format, the extension distinguishing the type.
	 * @param forceReload if true, the lexicon will be reloaded, if false, the loaded lexicon will
	 *            be returned or loaded for the first time.
	 * @return a SyntacticLexicon or null if there has been an exception while reading the file
	 */
	public static SyntacticLexicon loadLexicon(File file, boolean forceReload)
	{
		if (loadedLexicons.containsKey(file) && !forceReload)
			return loadedLexicons.get(file);

		try
		{
			SyntacticLexicon ret = SyntacticLexiconReader.readLexicon(file);
			loadedLexicons.put(file, ret);
			return ret;
		}
		catch (Exception e)
		{
			System.err.println("Error: exception while reading lexicon " + file + " : " + e.getMessage());
		}

		return null;
	}


	/**
	 * Loads the given test suite file in GenI format. If the given File is not in GeniFormat or is
	 * not found an exception is caught and print to stdout.
	 * @param file a lexicon file in XML format.
	 * @return a TestSuite or null if there has been an exception while reading the file
	 */
	public static TestSuite loadTestSuite(File file)
	{
		try
		{
			return TestSuiteReader.readTestSuite(file);
		}
		catch (Exception e)
		{
			System.err.println("Error: exception while reading test suite " + file + " : " + e.getMessage());
		}

		return null;
	}


	/**
	 * Loads the given morph lexicon in XML or MPH format.
	 * @param file
	 * @return
	 */
	public static MorphLexicon loadMorphLexicon(File file)
	{
		if (file == null)
			return new MorphLexicon();
		else try
		{
			return MorphLexiconReader.readLexicon(file);
		}
		catch (Exception e)
		{
			System.err.println("Error: exception while reading morph lexicon " + file + " : " + e.getMessage());
		}
		return null;
	}

}
