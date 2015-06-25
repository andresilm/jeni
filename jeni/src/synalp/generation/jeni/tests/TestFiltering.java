package synalp.generation.jeni.tests;

import synalp.commons.grammar.Grammar;
import synalp.commons.input.*;
import synalp.commons.lexicon.SyntacticLexicon;
import synalp.commons.utils.*;
import synalp.commons.utils.configuration.ResourcesBundleFile;
import synalp.commons.utils.exceptions.TimeoutException;
import synalp.commons.utils.loggers.LoggerConfiguration;
import synalp.generation.jeni.JeniLexicalSelection;

public class TestFiltering
{
	public static void main(String[] args)
	{
		Grammar grammar = Resources.loadGrammar(ResourcesBundleFile.KBGEN_GRAMMAR.getFile());
		SyntacticLexicon lexicon = Resources.loadLexicon(ResourcesBundleFile.KBGEN_LEXICON.getFile());
		TestSuite testSuite = Resources.loadTestSuite(ResourcesBundleFile.KBGEN_TESTSUITE.getFile());
		
		LoggerConfiguration.init();
		JeniLexicalSelection selection = new JeniLexicalSelection(grammar, lexicon);
		for(TestSuiteEntry entry : testSuite)
		{
			System.out.println(entry.getId());
			try
			{
				TimeoutManager.start();
				selection.selectEntries(entry.getSemantics());
			}
			catch(TimeoutException e)
			{
				System.err.println(e.getMessage());
			}
		}
	}
}
