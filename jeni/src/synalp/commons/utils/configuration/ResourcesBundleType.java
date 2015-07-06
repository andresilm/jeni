/**
 * 
 */
package synalp.commons.utils.configuration;

import synalp.commons.utils.ResourceBundle;
import static synalp.commons.utils.configuration.ResourcesBundleFile.*;

/**
 * Enum with name of the existing Resources Bundle
 * @author cmoro
 *
 */
public enum ResourcesBundleType
{
	/**
	 * Set of MINIMAL_GRAMMAR, MINIMAL_LEXICON, MINIMAL_TESTSUITE
	 */
	MINIMAL_BUNDLE(new ResourceBundle(MINIMAL_GRAMMAR.getFile(), 
	                                  MINIMAL_LEXICON.getFile(), 
	                                  MINIMAL_TESTSUITE.getFile())),
	/**
	 * Set of FRENCH_GRAMMAR, FRENCH_LEXICON, FRENCH_TESTSUITE
	 */
	FRENCH_BUNDLE(new ResourceBundle(FRENCH_GRAMMAR.getFile(), 
	                                 FRENCH_LEXICON.getFile(), 
	                                 FRENCH_TESTSUITE.getFile())),
	/**
	 * Set of TRANSSEM_GRAMMAR, TRANSSEM_LEXICON, TRANSSEM_TESTSUITE
	 */
	TRANSSEM_BUNDLE(new ResourceBundle(TRANSSEM_GRAMMAR.getFile(), 
	                                   TRANSSEM_LEXICON.getFile(), 
	                                   TRANSSEM_TESTSUITE.getFile())),
	/**
	 * Set of SEMXTAG2_FULL_GRAMMAR, SEMXTAG2_LEXICON, SEMXTAG2_TESTSUITE, SEMXTAG2_MORPH
	 */
	SEMXTAG2_BUNDLE(new ResourceBundle(SEMXTAG2_GRAMMAR.getFile(), 
	                                   SEMXTAG2_LEXICON.getFile(), 
	                                   SEMXTAG2_TESTSUITE.getFile(),
	                                   SEMXTAG2_MORPH.getFile())),
	/**
	 * Set of SEMXTAG2_AUTO_GRAMMAR, SEMXTAG2_AUTO_LEXICON, SEMXTAG2_AUTO_TESTSUITE, SEMXTAG2_AUTO_MORPH
	 */
	SEMXTAG2_AUTO_BUNDLE(new ResourceBundle(SEMXTAG2_AUTO_GRAMMAR.getFile(), 
	                                        SEMXTAG2_AUTO_LEXICON.getFile(), 
	                                        SEMXTAG2_AUTO_TESTSUITE.getFile(), 
	                                        SEMXTAG2_MORPH.getFile())),
	/**
	 * Set of KBGEN_GRAMMAR, KBGEN_LEXICON, KBGEN_TESTSUITE
	 */
	KBGEN_BUNDLE(new ResourceBundle(KBGEN_GRAMMAR.getFile(), 
	                                KBGEN_LEXICON.getFile(), 
	                                KBGEN_TESTSUITE.getFile())),
	
	PROBABILISTIC_BUNDLE(new ResourceBundle(PROBABILISTIC_GRAMMAR.getFile(),
											PROBABILISTIC_LEXICON.getFile(),
											PROBABILISTIC_TESTSUITE.getFile()));
	
	private final ResourceBundle value;
	
	/**
	 * Private constructor
	 * @param value 
	 */
	private ResourcesBundleType (ResourceBundle value) {
		this.value = value;
		
	}
	
	/**
	 * Return the Bundle corresponding to this type
	 * @return bundle
	 */
	public ResourceBundle getBundle() {
		return this.value;
		//return GUIMessages.getString("categoryOptions."+this.toString().toLowerCase());
	}
	
}
