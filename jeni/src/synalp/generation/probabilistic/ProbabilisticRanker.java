package synalp.generation.probabilistic;

import java.util.List;

import synalp.generation.ChartItem;
import synalp.generation.ranker.Ranker;

/**
 * 
 * 
 */
public class ProbabilisticRanker implements Ranker
{

	@Override
	public List<? extends ChartItem> rank(List<? extends ChartItem> items)
	{
		return items;
	}

}
