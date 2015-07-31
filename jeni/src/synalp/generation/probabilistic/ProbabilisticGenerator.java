package synalp.generation.probabilistic;

import java.util.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import org.apache.log4j.Logger;

import synalp.commons.semantics.Semantics;
import synalp.generation.ChartItem;
import synalp.generation.configuration.*;
import synalp.generation.jeni.*;

/**
 * A ProbabilisticGenerator performs generation iteratively by constructing derivation trees with
 * increasing size.
 * @author Alexandre Denis
 */
public class ProbabilisticGenerator extends JeniGenerator
{
	private static Logger logger = Logger.getLogger(ProbabilisticGenerator.class);


	/**
	 * Creates a new ProbabilisticConfigurator based on given configuration.
	 * @param config
	 */
	public ProbabilisticGenerator(GeneratorConfiguration config)
	{
		super(config);
		//setRanker(l -> l.stream().sorted(comparing(ChartItem::getProbability).reversed()).limit(GeneratorOption.BEAM_SIZE).collect(toList()));
		setRanker(new ProbabilisticRanker());
	}

	/**
	 * Generates the given semantics with a priori list of selected items.
	 * @param semantics
	 * @param agenda
	 * @return chart items, each one being a surface realization
	 */
	@Override
	protected JeniChartItems generate(Semantics semantics, JeniChartItems agenda)
	{
		
		
		
		Map<Integer, JeniChartItems> itemsPerSize = new HashMap<>();
		itemsPerSize.put(1, agenda);
		logger.debug("Agenda size: "+agenda.size());
		for(int n = 2; n <= semantics.size(); n++)
		{
			JeniChartItems tmpItems = new JeniChartItems();
			for(int i = 1; i <= Math.floor(n / 2); i++)
				for(JeniChartItem it1 : itemsPerSize.get(i))
					for(JeniChartItem it2 : itemsPerSize.get(n - i))
						if (!it1.getId().equals(it2.getId()))
						{
							tmpItems.addAll(getCombiner().getAdjunctionCombinations(it1, it2));
							tmpItems.addAll(getCombiner().getSubstitutionCombinations(it1, it2));
						}
			itemsPerSize.put(n, new JeniChartItems(getRanker().rank(tmpItems)));
			logger.trace("Reducing from "+tmpItems.size()+" to "+itemsPerSize.get(n).size());
		}

		JeniChartItems ret = itemsPerSize.get(semantics.size());
		ruleOutNonUnifyingTopBotTrees(ret);
		setupLemmaFeatures(ret);
		logResults(ret);
		logger.info("*******************Beam size="+GeneratorOption.BEAM_SIZE);
		return ret;
	}

}
