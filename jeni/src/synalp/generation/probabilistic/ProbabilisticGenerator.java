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


	@Override
	protected JeniChartItems generate(Semantics semantics, JeniChartItems agenda)
	{
		logger.info("Generating with beam size="+GeneratorOption.BEAM_SIZE);
		Map<Integer, JeniChartItems> itemsPerSize = new HashMap<>();
		itemsPerSize.put(1, agenda);
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
			logger.debug("Reducing from "+tmpItems.size()+" to "+itemsPerSize.get(n).size());
		}

		JeniChartItems ret = itemsPerSize.get(semantics.size());
		ruleOutNonUnifyingTopBotTrees(ret);
		setupLemmaFeatures(ret);
		logResults(ret);
		
		return ret;
	}


	/**
	 * Generates the given semantics with a priori list of selected items.
	 * @param semantics
	 * @param agenda
	 * @return chart items, each one being a surface realization
	 */
	protected JeniChartItems generateOld(Semantics semantics, JeniChartItems agenda)
	{
		TreeCombiner combiner = getCombiner();
		List<List<JeniChartItem>> generatedTrees = new ArrayList<List<JeniChartItem>>();

		/*the maximum number of trees to be generated is the number of literals in the input semantics
		 * if we assume that every trees corresponds to just one literal
		 */
		int inputSize = semantics.size();

		logInitialAgenda(agenda);

		//initial tree set, step i=0
		generatedTrees.add(agenda);

		logger.info("*** Starting ProbabilisticGenerator main loop");

		for(int step = 1; step < inputSize; ++step)
		{
			logger.info("*** ProbabilisticGenerator step: " + step);
			//create empty list for step i
			List<JeniChartItem> genTreeInStep = new ArrayList<JeniChartItem>();

			for(int j = 0; j < java.lang.Math.floor(step / 2); ++j)
			{
				//choose trees of size j and (i-j) to build new trees of size j+(i-j)=i
				List<JeniChartItem> treeSet1 = generatedTrees.get(j);
				List<JeniChartItem> treeSet2 = generatedTrees.get(step - j);

				for(JeniChartItem item1 : treeSet1)
				{
					for(JeniChartItem item2 : treeSet2)
					{
						//generate new trees
						genTreeInStep.addAll(combiner.getSubstitutionCombinations(item1, item2));
						genTreeInStep.addAll(combiner.getAdjunctionCombinations(item1, item2));
					}
				}

			}

			//rank the obtained trees in this step i
			logger.info("*** Will rank trees obtained in step: " + step + " and store them");
			@SuppressWarnings("unchecked")
			List<JeniChartItem> ranked = (List<JeniChartItem>) this.getRanker().rank(genTreeInStep);
			//add the new trees to the tree list of step i
			generatedTrees.add(ranked);
		}

		/*finally, we rank the last list of tree we obtained 
		 * and return that list as the result of the generation process
		 */

		logger.info("*** Ranking final set of trees");
		JeniChartItems ret = new JeniChartItems(getRanker().rank(generatedTrees.get(inputSize - 1)));

		ruleOutNonUnifyingTopBotTrees(ret);
		setupLemmaFeatures(ret);

		logResults(ret);

		return ret;
	}

}
