/**
 * 
 */
package synalp.commons.utils.configuration;

import java.io.File;

/**
 * Resources File hardcoded for bundle
 * @author cmoro
 */
@SuppressWarnings("javadoc")
public enum ResourcesBundleFile
{
	MINIMAL_GRAMMAR(new File("resources/minimal/grammar.xml")),
	MINIMAL_LEXICON(new File("resources/minimal/lexicon.xml")),
	MINIMAL_TESTSUITE(new File("resources/minimal/test.jeni")),

	FRENCH_GRAMMAR(new File("resources/french/grammar.xml")),
	FRENCH_LEXICON(new File("resources/french/lexicon.xml")),
	FRENCH_TESTSUITE(new File("resources/french/test.jeni")),

	TRANSSEM_GRAMMAR(new File("resources/transsem/grammar.xml")),
	TRANSSEM_LEXICON(new File("resources/transsem/lexicon.lex")),
	TRANSSEM_TESTSUITE(new File("resources/transsem/test.jeni")),
	TRANSSEM_SIMPLESUITE(new File("resources/transsem/simple-suite.jeni")),

	SEMXTAG2_MORPH(new File("resources/sem-xtag2/morph.mph")),
	SEMXTAG2_GRAMMAR(new File("resources/sem-xtag2/grammar.xml")),
	SEMXTAG2_LEXICON(new File("resources/sem-xtag2/lexicon.xml")),
	SEMXTAG2_TESTSUITE(new File("resources/sem-xtag2/test.jeni")),
	SEMXTAG2_FULL_GRAMMAR(new File("resources/sem-xtag2/sources/valuation.xml")),

	SEMXTAG2_AUTO_SEM(new File("resources/sem-xtag2/auto/semantics.xml")),
	SEMXTAG2_AUTO_RULES(new File("resources/sem-xtag2/auto/rules.xml")),
	SEMXTAG2_AUTO_MORPH(new File("resources/sem-xtag2/auto/morph.mph")),
	SEMXTAG2_AUTO_GRAMMAR(new File("resources/sem-xtag2/auto/grammar.xml")),
	SEMXTAG2_AUTO_LEXICON(new File("resources/sem-xtag2/auto/lexicon.xml")),
	SEMXTAG2_AUTO_TESTSUITE(new File("resources/sem-xtag2/auto/test.jeni")),

	KBGEN_GRAMMAR(new File("resources/kbgen/grammar.xml")),
	KBGEN_LEXICON(new File("resources/kbgen/lexicon.lex")),
	KBGEN_TESTSUITE(new File("resources/kbgen/test.jeni")),
	
	PROBABILISTIC_GRAMMAR(new File("resources/probabilistic/valuation.xml")),
	PROBABILISTIC_LEXICON(new File("resources/probabilistic/koda-lexicon.lex")),
	PROBABILISTIC_TESTSUITE(new File("resources/probabilistic/test.geni"));
	

	private final File value;


	/**
	 * Private constructor
	 * @param value
	 */
	private ResourcesBundleFile(File value)
	{
		this.value = value;
	}


	/**
	 * Return the Bundle corresponding to this type
	 * @return bundle
	 */
	public File getFile()
	{
		return this.value;
	}
}
