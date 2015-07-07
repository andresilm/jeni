package synalp.generation.probabilistic;

import java.util.ArrayList;
import java.util.List;

import synalp.commons.semantics.Semantics;
import synalp.commons.utils.ResourceBundle;
import synalp.generation.configuration.GeneratorConfiguration;
import synalp.generation.jeni.*;
import synalp.generation.morphology.DefaultMorphRealizer;
import synalp.generation.ranker.DefaultRanker;


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
		List<List<JeniChartItem>> generatedTrees = new ArrayList<List<JeniChartItem>>();
		
		/*the maximum number of trees to be generated is the number of literals in the input semantics
		 * if we assume that every trees corresponds to just one literal
		 */
		int inputSize = semantics.size();
		
		//initial tree set, step i=0
		generatedTrees.add(agenda);
		
		for (int i=1; i< inputSize;++i) {
			 //create empty list for step i
			generatedTrees.add(new ArrayList<JeniChartItem>());
			
			for (int j=0;j<java.lang.Math.floor(i/2);++j) {
				//choose trees of size j and (i-j) to build new trees of size j+(i-j)=i
				List<JeniChartItem> treeSet1 = generatedTrees.get(j);
				List<JeniChartItem> treeSet2 = generatedTrees.get(i-j);
				
				for (JeniChartItem item1: treeSet1) {
					for (JeniChartItem item2: treeSet2) {
						//generate new trees
						List<JeniChartItem> genTreeInStep = combiner.getSubstitutionCombinations(item1, item2, agenda);
						genTreeInStep.addAll(combiner.getAdjunctionCombinations(item1, item2));
						//rank the obtained trees
						List<JeniChartItem> ranked = (List<JeniChartItem>)this.getRanker().rank(genTreeInStep);
						//add the new trees to the tree list of step i
						generatedTrees.get(i).addAll(ranked);
					
					}
				}
				
			}
		}
		
		
		/*finally, we rank the last list of tree we obtained 
		 * and return that list as the result of the generation process
		 */
		JeniChartItems ret = new JeniChartItems(getRanker().rank(generatedTrees.get(inputSize-1)));
		
		
		
		return ret;
	}

	
}
