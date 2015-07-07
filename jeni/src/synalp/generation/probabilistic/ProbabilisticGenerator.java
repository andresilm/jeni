package synalp.generation.probabilistic;

import synalp.commons.semantics.Semantics;
import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.jeni.*;


public class ProbabilisticGenerator extends JeniGenerator
{

	
	public ProbabilisticGenerator(GeneratorConfiguration config)
	{
		super(config);
		setRanker(new ProbabilisticRanker());
	}

	
	@Override
	protected JeniChartItems generate(Semantics semantics, JeniChartItems agenda)
	{
		TreeCombiner combiner = getCombiner();
		//semantics.size()
		
		return super.generate(semantics, agenda);
	}

	
}
