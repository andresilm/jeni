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


	@Override
	public List<? extends ChartItem> rank(List<? extends ChartItem> items)
	{
		if (items.getClass().equals(JeniChartItem.class))
		{
			List<JeniChartItem> jeniChartItemList = (List<JeniChartItem>) items;
			// order JeniChartItem list in ascending order
			Collections.sort(jeniChartItemList, (JeniChartItem item1, JeniChartItem item2) 
			                 					-> (int) (item2.getProbability() - item1.getProbability()
			                 					)
			                 );
		}

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
