package synalp.generation.probabilistic;

import java.util.List;

import synalp.generation.ChartItem;
import synalp.generation.jeni.JeniChartItem;
import synalp.generation.ranker.Ranker;

/**
 * 
 * @aluna
 */
public class ProbabilisticRanker implements Ranker {
	private int beamWidth = 5;

	@Override
	public List<? extends ChartItem> rank(List<? extends ChartItem> items) {
		if (items.getClass().equals(JeniChartItem.class)) {
			jeniChartItemListQuickSort((List<JeniChartItem>)items,0,items.size()-1);
		}
		//TODO: else {throw ChartItempTypeException(items)}
		
		
		return items.subList(0, beamWidth-1);
	}

	private static void jeniChartItemListQuickSort(List<JeniChartItem> jeniChartItems, int low, int high) {
		if (jeniChartItems == null || jeniChartItems.size() == 0)
			return;

		if (low >= high)
			return;

		// pick the pivot
		int middle = low + (high - low) / 2;
		JeniChartItem pivot = jeniChartItems.get(middle);

		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (jeniChartItems.get(i).getProbability() > pivot.getProbability()) {
				i++;
			}

			while (jeniChartItems.get(j).getProbability() < pivot.getProbability()) {
				j--;
			}

			if (i <= j) {
				JeniChartItem temp = jeniChartItems.get(i);
				jeniChartItems.set(i,jeniChartItems.get(j));
				jeniChartItems.set(j,temp);
				i++;
				j--;
			}
		}

		// recursively sort two sub parts
		if (low < j)
			jeniChartItemListQuickSort(jeniChartItems, low, j);

		if (high > i)
			jeniChartItemListQuickSort(jeniChartItems, i, high);
	}

	/**
	 * @return the beamWidth
	 */
	public int getBeamWidth() {
		return beamWidth;
	}

	/**
	 * set the beamWidth
	 */
	public void setBeamWidth(int beamWidth) {
		 this.beamWidth = beamWidth;
	}
	

}
