package synalp.generation;

import java.util.List;

import synalp.commons.output.*;
import synalp.commons.semantics.Semantics;


/**
 * General interface for all generators.
 * <p>
 * Generator is the generic generation algorithm.
 * </p>
 * @author Céline Moro
 * @author Alexandre Denis
 */
public interface Generator
{
	/**
	 * Generates alternative realizations for the given semantic input.
	 * @param semantics
	 * @return a list of alternative realizations
	 */
	public List<? extends SyntacticRealization> generate(Semantics semantics);


	/**
	 * Return the chosen algorithm to use ({@link GeneratorType#RTGEN}, or
	 * {@link GeneratorType#JENI_DEFAULT}, ...)
	 * @return the chosen algorithm to use
	 */
	public GeneratorType getGeneratorType();
}
