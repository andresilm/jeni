package synalp.generation.jeni.selection.families;

import java.io.File;
import java.util.*;

import synalp.commons.grammar.*;
import synalp.commons.utils.configuration.ResourcesBundleFile;
import synalp.commons.utils.loggers.LoggerConfiguration;

@SuppressWarnings("javadoc")
public class TestSemanticsBuilder
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		LoggerConfiguration.init();
		File grammarFile = ResourcesBundleFile.SEMXTAG2_GRAMMAR.getFile();
		File patternFile = new File("resources/sem-xtag2/semantics.sem");
		Grammar grammar = FamilySemanticsBuilder.setGrammarSemantics(grammarFile, patternFile, false);
		List<GrammarEntry> entries = new ArrayList<GrammarEntry>(grammar.getEntries());
		Grammar.sortByNames(entries);
		Grammar.printShort(entries);
	}

}
