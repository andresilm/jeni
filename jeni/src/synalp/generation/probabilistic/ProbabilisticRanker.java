package synalp.generation.probabilistic;

import java.util.Collections;
import java.util.List;

import synalp.generation.ChartItem;
import synalp.generation.jeni.JeniChartItem;
import synalp.generation.ranker.Ranker;

/**
 * @aluna
 */
public class ProbabilisticRanker implements Ranker
{
	private int beamWidth = 5;


	/**
	 * @param items: a list of elements that are an implementation of ChartItem
	 * Ranks the element of the list using probabilities 
	 * @return a list of CharItem elements with the higher probability 
	 */
	@Override
	public List<? extends ChartItem> rank(List<? extends ChartItem> items)
	{
		// order JeniChartItem list in ascending order
		Collections.sort(items, (item1, item2) -> (int) (item2.getProbability() - item1.getProbability()));
		int maxItems = (beamWidth > items.size()) ? items.size() - 1
				: beamWidth - 1;
		return items.subList(0, maxItems);
	}


	/**
	 * @return the beamWidth
	 */
	public int getBeamWidth()
	{
		return beamWidth;
	}


	/**
	 * set the beamWidth
	 */
	public void setBeamWidth(int beamWidth)
	{
		this.beamWidth = beamWidth;
	}

}
