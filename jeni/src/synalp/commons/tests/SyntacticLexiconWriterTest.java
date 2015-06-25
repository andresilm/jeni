package synalp.commons.tests;

import java.io.*;

import org.junit.Test;

import synalp.commons.lexicon.*;
import synalp.commons.utils.configuration.ResourcesBundleFile;

import static org.junit.Assert.fail;

/**
 * Reads a lexicon, writes it to a file, then re-read it. We should test if the lexicon is still the same.
 * @author Alexandre Denis
 *
 */
public class SyntacticLexiconWriterTest
{
	
	@Test
	@SuppressWarnings("javadoc")
	public void testReadWrite()
	{
		try
		{
			// it uses directly the SyntacticLexiconReader instead of Resources.loadLexicon to enable the exception
			SyntacticLexicon lexicon = SyntacticLexiconReader.readLexicon(ResourcesBundleFile.FRENCH_LEXICON.getFile());
			File tmp = File.createTempFile("test", ".xml");
			System.out.println("Writing on "+tmp);
			//tmp.deleteOnExit();
			SyntacticLexiconWriter.write(lexicon, tmp);
			SyntacticLexiconReader.readLexicon(tmp);
		}
		catch (Exception e)
		{
			fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
